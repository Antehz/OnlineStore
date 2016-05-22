package by.hrychanok.training.shop.web.page.personalCabinet;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

import by.hrychanok.training.shop.model.AbstractModel;
import by.hrychanok.training.shop.model.Order;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.service.OrderService;
import by.hrychanok.training.shop.web.page.BasePageForTable;
import by.hrychanok.training.shop.web.page.catalog.CatalogPage;
import by.hrychanok.training.shop.web.page.order.OrderPage;
import by.hrychanok.training.shop.web.page.product.ProductPage;
/**
 * 
 * @author Ante
 */
public class OrderHistoryPage extends BasePageForTable {

	@SpringBean
	OrderService orderService;
	
	private static final long serialVersionUID = 1L;

/*	private static class HighlitableDataItem<T> extends Item<T> {
		private static final long serialVersionUID = 1L;

		private boolean highlite = false;

		*//**
		 * toggles highlite
		 *//*
		public void toggleHighlite() {
			highlite = !highlite;
		}

		public HighlitableDataItem(String id, int index, IModel<T> model) {
			super(id, index, model);
			add(new AttributeModifier("style", "background-color:#80b6ed;") {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isEnabled(Component component) {
					return HighlitableDataItem.this.highlite;
				}
			});
		}
	}*/

	public OrderHistoryPage() {
		super();
		SortableTypeDataProvider dp = new SortableTypeDataProvider();

		final DataView<Order> dataView = new DataView<Order>("oir", dp) {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			protected void populateItem(final Item<Order> item) {
				Order order = item.getModelObject();
				Link linkId = new Link<Void>("linkId") {
                    @Override
                    public void onClick() {
                        setResponsePage(new OrderPage(order.getId()));
                    }
				};
				linkId.setBody(Model.of(order.getId()));
				item.add(linkId);

			/*	item.add(new Link<Void>("toggleHighlite") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						HighlitableDataItem<Order> hitem = (HighlitableDataItem<Order>) item;
						hitem.toggleHighlite();
					}
				});*/
				item.add(new Label("customerId", Model.of(order.getCustomer().getId())));
                item.add(DateLabel.forDatePattern("created", Model.of(order.getStartDate()), "dd-MM-yyyy"));
				item.add(new Label("status", order.getStatus()));
				item.add(new Label("price", order.getTotalPrice()));

				item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
					private static final long serialVersionUID = 1L;

					@Override
					public String getObject() {
						return (item.getIndex() % 2 == 1) ? "even" : "odd";
					}
				}));
			}
//
//			@Override
//			protected Item<Order> newItem(String id, int index, IModel<Order> model) {
//				return new HighlitableDataItem<>(id, index, model);
//			}
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
