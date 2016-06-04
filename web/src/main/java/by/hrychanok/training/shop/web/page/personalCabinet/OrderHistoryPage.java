package by.hrychanok.training.shop.web.page.personalCabinet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.util.string.ComponentRenderer;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.data.domain.PageRequest;

import com.googlecode.wicket.jquery.ui.form.button.ConfirmAjaxButton;
import com.googlecode.wicket.kendo.ui.form.dropdown.AjaxDropDownList;
import com.googlecode.wicket.kendo.ui.form.dropdown.DropDownList;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

import by.hrychanok.training.shop.model.AbstractModel;
import by.hrychanok.training.shop.model.CartContent;
import by.hrychanok.training.shop.model.Customer;
import by.hrychanok.training.shop.model.CustomerCredentials;
import by.hrychanok.training.shop.model.OilType;
import by.hrychanok.training.shop.model.Order;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.model.StatusOrder;
import by.hrychanok.training.shop.model.UserRole;
import by.hrychanok.training.shop.repository.filter.Comparison;
import by.hrychanok.training.shop.repository.filter.Condition;
import by.hrychanok.training.shop.repository.filter.Filter;
import by.hrychanok.training.shop.service.OrderService;
import by.hrychanok.training.shop.web.app.AuthorizedSession;
import by.hrychanok.training.shop.web.page.BasePageForTable;
import by.hrychanok.training.shop.web.page.GenericSortableTypeDataProvider;
import by.hrychanok.training.shop.web.page.catalog.CatalogPage;
import by.hrychanok.training.shop.web.page.order.OrderPage;
import by.hrychanok.training.shop.web.page.product.ProductPage;

/**
 * 
 * @author Ante
 */
public class OrderHistoryPage extends Panel {

	@SpringBean
	OrderService orderService;

	boolean visibleForOnlyAdmin = AuthorizedSession.get().isSignedIn()
			&& AuthorizedSession.get().getLoggedUser().getRole().equals(UserRole.admin);

	boolean visibleForOnlyCustomer = AuthorizedSession.get().isSignedIn()
			&& AuthorizedSession.get().getLoggedUser().getRole().equals(UserRole.customer);

	private static final long serialVersionUID = 1L;

	List<Condition> conditionsList = new ArrayList<Condition>();

	private Filter filterState = new Filter();

	public OrderHistoryPage(String id) {
		super(id);
	}

	public OrderHistoryPage(String id, List<Condition> conditionsList) {
		super(id);
		this.conditionsList = conditionsList;

	}

	@Override
	protected void onInitialize() {
		CustomerCredentials customer = AuthorizedSession.get().getLoggedUser();

		super.onInitialize();

		if (visibleForOnlyCustomer) {
			filterState.addCondition(new Condition.Builder().setComparison(Comparison.eq).setField("customer")
					.setValue(customer.getId()).build());
		}

		GenericSortableTypeDataProvider<Order> dp = new GenericSortableTypeDataProvider<Order>(filterState) {

			public Iterator<? extends Order> returnIterator(PageRequest pageRequest) {
				return orderService.findAll(filterState, pageRequest).iterator();
			}

			@Override
			public long size() {
				return orderService.count(filterState);
			}
		};
		dp.addConditionToFilterState(conditionsList);

		final DataView<Order> dataView = new DataView<Order>("oir", dp) {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			protected void populateItem(final Item<Order> item) {
				item.setOutputMarkupId(true);
				Order order = item.getModelObject();
				Link linkId = new Link<Void>("linkId") {
					@Override
					public void onClick() {
						setResponsePage(new OrderPage(order.getId()));
					}
				};
				linkId.setBody(Model.of(order.getId()));
				item.add(linkId);

				item.add(new Label("customerId", Model.of(order.getCustomer().getId())));
				item.add(DateLabel.forDatePattern("created", Model.of(order.getStartDate()), "dd-MM-yyyy"));

				Label statusLabel = new Label("status", Model.of(order.getStatus()));
				statusLabel.setOutputMarkupId(true);
				item.add(statusLabel);

				item.add(new Label("price", order.getTotalPrice()));

				AttributeModifier attributeItem = AttributeModifier.replace("class",
						new AbstractReadOnlyModel<String>() {
							private static final long serialVersionUID = 1L;

							@Override
							public String getObject() {
								return dehineStyleStatus(order);
							}
						});

				item.add(attributeItem);
				Form formManageOrder = new Form("formManageOrder", new CompoundPropertyModel<Order>(order));
				item.add(formManageOrder);
				formManageOrder.setVisible(visibleForOnlyAdmin);
				// FeedbackPanel //
				final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
				formManageOrder.add(feedback);

				// ComboBox //
				List<StatusOrder> statusList = Arrays.asList(StatusOrder.values());
				final AjaxDropDownList<StatusOrder> dropdown = new AjaxDropDownList<StatusOrder>("select",
						new Model<StatusOrder>(), Model.ofList(statusList)) {

					private static final long serialVersionUID = 1L;

					@Override
					public void onSelectionChanged(AjaxRequestTarget target) {
						StatusOrder choice = this.getModelObject();
						if (choice != null) {
							order.setStatus(choice);
							orderService.save(order);
							item.setDefaultModelObject(order);
							target.add(item);
							statusLabel.setDefaultModelObject(order.getStatus());
							target.add(statusLabel);
							success("Статус изменен");
							target.add(feedback);
						}

					}
				};

				formManageOrder.add(dropdown);
				// Buttons //
				ConfirmAjaxButton deleteOrderbutton = new ConfirmAjaxButton("deleteButton", "Удалить",
						"Пожалуйста потвердите", "Вы потверждаете удаление этого заказа?") {

					private static final long serialVersionUID = 1L;

					@Override
					protected void onError(AjaxRequestTarget target, Form<?> form) {
						this.error("Заказ не может быть удален");
						target.add(feedback);
					}

					@Override
					protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
						orderService.delete(order);
						target.add(feedback);

						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						setResponsePage(new CustomerOrderPage());
						success("Заказ удален");
					}
				};
				formManageOrder.add(deleteOrderbutton);

			}

			private String dehineStyleStatus(Order order) {
				if (order.getStatus().equals(StatusOrder.Cancelled)) {
					return "danger";
				}
				if (order.getStatus().equals(StatusOrder.Accepted)) {
					return "active";
				}
				if (order.getStatus().equals(StatusOrder.Done)) {
					return "success";
				}
				if (order.getStatus().equals(StatusOrder.Making)) {
					return "info";
				}
				if (order.getStatus().equals(StatusOrder.Pending)) {
					return "warning";

				}
				return null;
			}
		};

		dataView.setItemsPerPage(12L);
		dataView.setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());

		add(new OrderByBorder("orderById", "id", dp) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});

		add(new OrderByBorder("orderByCreated", "startDate", dp) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});

		add(new OrderByBorder("orderByStatus", "status", dp) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});

		add(new OrderByBorder("orderByPrice", "totalPrice", dp) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});

		add(dataView);
		add(new PagingNavigator("navigator", dataView));
	}
}
