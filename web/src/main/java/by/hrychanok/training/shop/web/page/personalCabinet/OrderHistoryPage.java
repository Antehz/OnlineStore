package by.hrychanok.training.shop.web.page.personalCabinet;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import by.hrychanok.training.shop.model.Order;
import by.hrychanok.training.shop.service.OrderService;
import by.hrychanok.training.shop.web.page.AbstractPage;
import by.hrychanok.training.shop.web.page.BasePageForTable;

public class OrderHistoryPage extends BasePageForTable {

	@SpringBean
	OrderService orderService;
	private static final long serialVersionUID = 1L;

	private static class HighlitableDataItem<T> extends Item<T> {
		private static final long serialVersionUID = 1L;

		private boolean highlite = false;

		/**
		 * toggles highlite
		 */
		public void toggleHighlite() {
			highlite = !highlite;
		}

		/**
		 * Constructor
		 * 
		 * @param id
		 * @param index
		 * @param model
		 */
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
	}

	/**
	 * Constructor
	 */
	public OrderHistoryPage() {
		SortableTypeDataProvider dp = new SortableTypeDataProvider(orderService.findAll());

		final DataView<Order> dataView = new DataView<Order>("oir", dp) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final Item<Order> item) {
				Order contact = item.getModelObject();
				 item.add(new ActionPanel("actions", item.getModel())); 
				item.add(new Link<Void>("toggleHighlite") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						HighlitableDataItem<Order> hitem = (HighlitableDataItem<Order>) item;
						hitem.toggleHighlite();
					}
				});
				item.add(new Label("contactid", String.valueOf(contact.getId())));
				item.add(new Label("firstname", contact.getAdditionalInfo()));
				item.add(new Label("lastname", contact.getCustomer().getLastName()));
				item.add(new Label("homephone", contact.getStatus()));
				item.add(new Label("cellphone", contact.getShippingMethod()));

				item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
					private static final long serialVersionUID = 1L;

					@Override
					public String getObject() {
						return (item.getIndex() % 2 == 1) ? "even" : "odd";
					}
				}));
			}

			@Override
			protected Item<Order> newItem(String id, int index, IModel<Order> model) {
				return new HighlitableDataItem<>(id, index, model);
			}
		};

		dataView.setItemsPerPage(8L);
		dataView.setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());

		add(new OrderByBorder("orderByFirstName", "additionalInfo", dp) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});

		add(new OrderByBorder("orderByLastName", "lastName", dp) {
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
