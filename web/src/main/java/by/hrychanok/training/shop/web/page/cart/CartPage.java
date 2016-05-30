package by.hrychanok.training.shop.web.page.cart;

import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.data.domain.PageRequest;

import com.googlecode.wicket.jquery.ui.form.spinner.AjaxSpinner;
import by.hrychanok.training.shop.model.CartContent;
import by.hrychanok.training.shop.model.Customer;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.model.ShippingMethod;
import by.hrychanok.training.shop.repository.filter.Comparison;
import by.hrychanok.training.shop.repository.filter.Condition;
import by.hrychanok.training.shop.service.CartService;
import by.hrychanok.training.shop.service.CustomerService;
import by.hrychanok.training.shop.service.OrderService;
import by.hrychanok.training.shop.web.app.AuthorizedSession;
import by.hrychanok.training.shop.web.page.BasePageForTable;
import by.hrychanok.training.shop.web.page.GenericSortableTypeDataProvider;
import by.hrychanok.training.shop.web.page.personalCabinet.CustomerOrderPage;
import by.hrychanok.training.shop.web.page.personalCabinet.OrderHistoryPage;
import by.hrychanok.training.shop.web.page.product.ProductPage;

import com.googlecode.wicket.kendo.ui.form.Check;
import com.googlecode.wicket.kendo.ui.form.Radio;
import com.googlecode.wicket.kendo.ui.form.TextArea;
import com.googlecode.wicket.kendo.ui.form.TextField;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.form.button.Button;
import com.googlecode.wicket.kendo.ui.form.button.IndicatingAjaxButton;
import com.googlecode.wicket.kendo.ui.form.button.IndicatingButton;
import com.googlecode.wicket.kendo.ui.markup.html.link.SubmitLink;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class CartPage extends BasePageForTable {

	private static final long serialVersionUID = 1L;

	@SpringBean
	CartService cartService;

	@SpringBean
	OrderService orderService;

	@SpringBean
	CustomerService customerService;

	public static final Integer SHIPCOST = 50_000;

	private Integer summaryPrice;
	private Label shipPriceLabel = new Label("shipPriceLabel", Model.of(""));

	public Integer getSummaryPrice() {
		return cartService.getTotalPriceCart(customer.getId());
	}

	public ShippingMethod shipMethod;
	public Label totalPriceLabel = null;
	private Customer customer;

	@SuppressWarnings("unchecked")
	public CartPage() {
		customer = AuthorizedSession.get().getLoggedUser().getCustomer();

		GenericSortableTypeDataProvider<CartContent> dp = new GenericSortableTypeDataProvider<CartContent>() {

			public Iterator<? extends CartContent> returnIterator(PageRequest pageRequest) {
				filterState.addCondition(new Condition.Builder().setComparison(Comparison.eq).setField("customer")
						.setValue(customer.getId()).build());
				return cartService.findAll(filterState, pageRequest).iterator();

			}

			@Override
			public long size() {
				return cartService.count(filterState);
			}

		};

		Model<Integer> totalPriceModel = Model.of(getSummaryPrice());
		totalPriceLabel = new Label("totalPrice", totalPriceModel);
		totalPriceLabel.setOutputMarkupId(true);
		add(totalPriceLabel);

		shipPriceLabel.setOutputMarkupId(true);
		add(shipPriceLabel);

		final DataView<CartContent> dataView = new DataView<CartContent>("cartTable", dp) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(Item<CartContent> item) {
				CartContent cartContent = item.getModelObject();
				Product product = cartContent.getProduct();

				Link linkProductId = new Link<Void>("linkProduct") {
					@Override
					public void onClick() {
						PageParameters parameters = new PageParameters();
						parameters.add("id", product.getId());
						setResponsePage(new ProductPage(parameters));
					}
				};
				linkProductId.setBody(Model.of(product.getId()));
				item.add(linkProductId);

				String nameProduct = cartContent.getProduct().getName() + "  "
						+ cartContent.getProduct().getManufacturer() + "  " + cartContent.getProduct().getModel();

				item.add(new Label("name", nameProduct));

				item.add(DateLabel.forDatePattern("added", Model.of(cartContent.getDateAdd()), "dd-MM-yyyy"));

				item.add(new Label("pricePiece", cartContent.getProduct().getPrice()));

				// Spinner //
				final Form<Integer> formTable = new Form<Integer>("formTable", Model.of(cartContent.getAmount()));
				Model<Integer> priceTotalModel = Model.of(cartContent.getPrice());
				Label priceTotal = new Label("priceTotal", priceTotalModel);
				priceTotal.setOutputMarkupId(true);

				formTable.add(
						createSpinner(totalPriceModel, cartContent, product, formTable, priceTotalModel, priceTotal));

				item.add(formTable);
				item.add(priceTotal);

				final Form<Void> formDeleteItem = new Form<Void>("formDeleteItem");
				item.add(formDeleteItem);
				formDeleteItem.add(addDeleteButton(cartContent));

				item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
					private static final long serialVersionUID = 1L;

					@Override
					public String getObject() {
						return (item.getIndex() % 2 == 1) ? "even" : "odd";
					}
				}));

			};
		};

		dataView.setRenderBodyOnly(true);
		dataView.setItemsPerPage(7L);
		dataView.setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());

		add(new OrderByBorder("orderByPriceTotal", "price", dp) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(dataView.getCurrentPage());
			}
		});
		add(dataView);

		// Add radio-button for choosing parameters new order
		final IModel<String> radioModel = createShipMethodChoosen(totalPriceModel);

		createContactDataForm();

		Form formOrder = new Form("formOrder");

		createFormOrder(radioModel, formOrder);
		add(new PagingNavigator("navigator", dataView));

	}

	private void createFormOrder(final IModel<String> radioModel, Form formOrder) {

		TextArea<String> orderAdditionalInfo = new TextArea<String>("additionalInfo", Model.of(""));
		formOrder.add(orderAdditionalInfo);
		add(formOrder);

		final KendoFeedbackPanel feedbackCreateOrder = new KendoFeedbackPanel("feedbackCreateOrder");
		formOrder.add(feedbackCreateOrder.setOutputMarkupId(true));

		formOrder.add(new IndicatingAjaxButton("createOrderButton") {

			private static final long serialVersionUID = 1L;

			@Override
			protected boolean isDisabledOnClick() {
				return true;
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

				defineMethodShip(radioModel);
				if (cartService.getCustomerCartContent(customer.getId()).isEmpty()) {
					CartPage.this.emptyCartWarn(this);
					target.add(feedbackCreateOrder);
				} else {
					try {
						orderService.createOrder(customer.getId(), shipMethod,
								orderAdditionalInfo.getDefaultModelObjectAsString());
						Thread.sleep(1500);
						CartPage.this.successOrderInfo(this);
						target.add(feedbackCreateOrder);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		});
	}

	private void createContactDataForm() {

		Form formCustomerUpdateData = new Form("formCustomerUpdateData", new CompoundPropertyModel<Customer>(customer));
		add(formCustomerUpdateData);

		TextField<String> firstName = new TextField<>("firstName");
		firstName.setRequired(true);
		formCustomerUpdateData.add(firstName);

		TextField<String> lastName = new TextField<>("lastName");
		lastName.setRequired(true);
		formCustomerUpdateData.add(lastName);

		TextField<String> email = new TextField<>("email");
		email.setRequired(true);
		formCustomerUpdateData.add(email);

		TextField<String> address = new TextField<>("address");
		address.setRequired(true);
		formCustomerUpdateData.add(address);

		formCustomerUpdateData.add(new SubmitLink("save") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				customerService.saveAndFlush(customer);
				setResponsePage(new CartPage());
			}
		});
	}

	private IModel<String> createShipMethodChoosen(Model<Integer> totalPriceModel) {
		Form<Void> formContactData = new Form<Void>("formChoseShipMethod");
		add(formContactData);

		final IModel<String> radioModel = new Model<String>("Самовывоз");
		final RadioGroup<String> group = new RadioGroup<String>("radiogroup", radioModel);
		formContactData.add(group);

		Radio<String> radio1 = new Radio<String>("radio1", Model.of("Курьер"), group);
		com.googlecode.wicket.kendo.ui.form.Radio.Label label1 = new com.googlecode.wicket.kendo.ui.form.Radio.Label(
				"label1", "Доставка по городу(50 000 руб.)", radio1);

		group.add(radio1, label1);

		Radio<String> radio2 = new Radio<String>("radio2", Model.of("Самовывоз"), group);
		Label label2 = new com.googlecode.wicket.kendo.ui.form.Radio.Label("label2", "Самовывоз", radio2);
		group.add(radio2, label2);

		formContactData.add(new AjaxButton("Ok") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (defineMethodShip(radioModel)) {
					totalPriceModel.setObject(getSummaryPrice());
					shipPriceLabel.setDefaultModelObject("0");

				} else {
					totalPriceModel.setObject(getSummaryPrice() + SHIPCOST);
					shipPriceLabel.setDefaultModelObject(SHIPCOST);

				}
				target.add(totalPriceLabel);
				target.add(shipPriceLabel);
			}
		});
		return radioModel;
	}

	private Label createLabel(final IModel<String> radioModel) {
		Integer shipCost = 50_000;
		Integer totalPrice = cartService.getTotalPriceCart(customer.getId());
		if (!defineMethodShip(radioModel)) {
			totalPrice = +shipCost;
		}
		Label totalPriceLabel = new Label("totalPrice", totalPrice);

		return totalPriceLabel;
	}

	private Boolean defineMethodShip(final IModel<String> radioModel) {

		if (radioModel.getObject().equals("Самовывоз")) {
			shipMethod = ShippingMethod.Pickup;
			return true;

		} else {
			shipMethod = ShippingMethod.Courier;
			return false;

		}

	}

	private final void successOrderInfo(Component component) {
		this.success("Заказ оформлен!");
	}

	private final void emptyCartWarn(Component component) {
		this.error("Корзина пуста! Заказ не может быть оформлен!");
	}

	private void confirmRulesInfo(Component component) {
		this.warn("Вы должны согласится с правилами пользования сайта  и получения товара!");
	}

	private Button addDeleteButton(CartContent cartContent) {
		final Button deleteItem = new IndicatingButton("deleteItem") {

			private static final long serialVersionUID = 1L;

			@Override
			protected boolean isDisabledOnClick() {
				/*
				 * Warning: if true the button will not be send as part of the
				 * post because of its disabled state. Therefore
				 * Button.onSubmit() will not be reached, Form.onSubmit() should
				 * be used instead.
				 */
				return false; // default value
			}

			@Override
			public void onSubmit() {
				try {
					cartService.deleteProductFromCart(cartContent.getId());
					Thread.sleep(1000);

				} catch (InterruptedException e) {
					/*
					 * if (LOG.isDebugEnabled()) { LOG.debug(e.getMessage(), e);
					 * }
					 */
				}
				setResponsePage(new CartPage());
			}
		};

		return deleteItem;

	}

	private AjaxSpinner<Integer> createSpinner(Model<Integer> totalPriceModel, CartContent cartContent, Product product,
			final Form<Integer> formTable, Model<Integer> priceTotalModel, Label priceTotal) {
		final AjaxSpinner<Integer> spinner = new AjaxSpinner<Integer>("spinner", formTable.getModel(), Integer.class) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSpin(AjaxRequestTarget target, Integer value) {
				changeProductAmountIntoCart(cartContent, value);
				priceTotalModel.setObject(cartContent.getPrice());
				target.add(priceTotal);
				totalPriceModel.setObject(getSummaryPrice());
				target.add(totalPriceLabel);
			}

			private void changeProductAmountIntoCart(CartContent cartContent, Integer value) {
				cartContent.setAmount(value);
				cartContent.setPrice(cartContent.getProduct().getPrice() * value);
				cartService.save(cartContent);
			}
		};
		spinner.setMin(1);
		spinner.setMax(product.getAvailable());
		return spinner;
	};
}