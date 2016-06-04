package by.hrychanok.training.shop.web.page.order;

import java.util.Iterator;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.data.domain.PageRequest;

import com.googlecode.wicket.jquery.ui.form.spinner.AjaxSpinner;
import com.googlecode.wicket.kendo.ui.form.button.IndicatingButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

import by.hrychanok.training.shop.model.CartContent;
import by.hrychanok.training.shop.model.Order;
import by.hrychanok.training.shop.model.OrderContent;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.model.StatusOrder;
import by.hrychanok.training.shop.model.UserRole;
import by.hrychanok.training.shop.repository.filter.Comparison;
import by.hrychanok.training.shop.repository.filter.Condition;
import by.hrychanok.training.shop.repository.filter.Filter;
import by.hrychanok.training.shop.service.OrderService;
import by.hrychanok.training.shop.web.app.AuthorizedSession;
import by.hrychanok.training.shop.web.page.AbstractPage;
import by.hrychanok.training.shop.web.page.GenericSortableTypeDataProvider;
import by.hrychanok.training.shop.web.page.product.ProductPage;
@AuthorizeAction(roles = { "admin" }, action = "ENABLE")
public class OrderPage extends AbstractPage {

	@SpringBean
	OrderService orderService;
	private Order order;
	public Label summaryOrderCostLabel;
	boolean visibleForOnlyAdmin = AuthorizedSession.get().isSignedIn()
			&& AuthorizedSession.get().getLoggedUser().getRole().equals(UserRole.admin);
	public OrderPage() {
		super();
	}

	public OrderPage(Long id) {
		order = orderService.findOne(id);
	}

	private Filter filterState = new Filter();

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new Label("orderId", Model.of(order.getId())));
		add(new Label("shippingMethod", Model.of(order.getShippingMethod())));

		Model<Integer> summaryOrderCostModel = Model.of(orderService.findOne(order.getId()).getTotalPrice());
		summaryOrderCostLabel = new Label("summaryOrderCostLabel", summaryOrderCostModel);
		summaryOrderCostLabel.setOutputMarkupId(true);
		add(summaryOrderCostLabel);

		defineStyleStatus();

		add(new Label("additionalInfo", Model.of(order.getAdditionalInfo())));

		// Table 2 - order content
		filterState.addCondition(
				new Condition.Builder().setComparison(Comparison.eq).setField("order").setValue(order.getId()).build());
		GenericSortableTypeDataProvider<OrderContent> dp = new GenericSortableTypeDataProvider<OrderContent>(
				filterState) {

			public Iterator<? extends OrderContent> returnIterator(PageRequest pageRequest) {
				return orderService.findAllContent(filterState, pageRequest).iterator();
			}

			@Override
			public long size() {
				return orderService.countContent(filterState);
			}
		};
		final DataView<OrderContent> dataView = new DataView<OrderContent>("orderContentTable", dp) {
			private static final long serialVersionUID = 1L;
			int counter = 1;

			@Override
			protected void populateItem(Item<OrderContent> item) {
				OrderContent orderContent = item.getModelObject();

				item.add(new Label("counter", counter++));

				Link linkProductId = new Link<Void>("linkProduct") {
					@Override
					public void onClick() {
						PageParameters parameters = new PageParameters();
						parameters.add("id", orderContent.getProduct().getId());
						setResponsePage(new ProductPage(parameters));
					}
				};
				linkProductId.setBody(Model.of(orderContent.getProduct().getId()));
				item.add(linkProductId);

				Model<Integer> priceTotalOneProductModel = Model.of(orderContent.getPrice());
				Label priceTotalOneProductLabel = new Label("priceTotalOneProductLabel", priceTotalOneProductModel);
				priceTotalOneProductLabel.setOutputMarkupId(true);
				item.add(priceTotalOneProductLabel);

				final Form<Integer> formTable = new Form<Integer>("formTable", Model.of(orderContent.getAmount()));
				formTable.add(createSpinner(summaryOrderCostModel, orderContent, formTable, priceTotalOneProductModel,
						priceTotalOneProductLabel));
				item.add(formTable);
				// Buttons //
				final Button deleteButton = new IndicatingButton("deleteButton") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onError() {
						super.onError();
					}

					@Override
					protected boolean isDisabledOnClick() {
						return false; // default value
					}

					@Override
					public void onSubmit() {
						orderService.deleteContent(orderContent);
						orderService.countOrderTotalPrice(order.getId());
						summaryOrderCostModel.setObject(orderService.findOne(order.getId()).getTotalPrice());
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
						}

					}
				};
				deleteButton.setVisible(visibleForOnlyAdmin);
				// FeedbackPanel //
				final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
				formTable.add(feedback.setOutputMarkupId(true));
				formTable.add(deleteButton);

			}
		};
		dataView.setItemsPerPage(6L);
		dataView.setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());
		add(new PagingNavigator("navigator", dataView));
		add(dataView);
	}

	private void defineStyleStatus() {
		final WebMarkupContainer wmc = new WebMarkupContainer("trStyle");
		add(wmc);
		if (order.getStatus().equals(StatusOrder.Cancelled)) {
			wmc.add(new AttributeModifier("class", "danger"));
		}
		if (order.getStatus().equals(StatusOrder.Accepted)) {
			wmc.add(new AttributeModifier("class", "active"));
		}
		if (order.getStatus().equals(StatusOrder.Done)) {
			wmc.add(new AttributeModifier("class", "success"));
		}
		if (order.getStatus().equals(StatusOrder.Making)) {
			wmc.add(new AttributeModifier("class", "info"));
		}
		if (order.getStatus().equals(StatusOrder.Pending)) {
			wmc.add(new AttributeModifier("class", "warning"));

		}

		wmc.add(new Label("status", Model.of(order.getStatus())));
	}

	private AjaxSpinner<Integer> createSpinner(Model<Integer> summaryOrderCostModel, OrderContent orderContent,
			Form<Integer> formTable, Model<Integer> priceTotalOneProductModel, Label priceTotalOneProductLabel) {
		Product product = orderContent.getProduct();
		final AjaxSpinner<Integer> spinner = new AjaxSpinner<Integer>("spinner", formTable.getModel(), Integer.class) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSpin(AjaxRequestTarget target, Integer value) {
				orderService.editContent(orderContent, value);
				priceTotalOneProductModel.setObject(orderContent.getPrice());
				target.add(priceTotalOneProductLabel);
				summaryOrderCostModel.setObject(orderService.findOne(order.getId()).getTotalPrice());
				target.add(summaryOrderCostLabel);
			}
		};
		spinner.setMin(1);
		spinner.setMax(orderContent.getAmount() + product.getAvailable());
		return spinner;
	};

}