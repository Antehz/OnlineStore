package by.hrychanok.training.shop.web.page.catalog;

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
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.service.OrderService;
import by.hrychanok.training.shop.service.ProductService;
import by.hrychanok.training.shop.web.page.AbstractPage;
import by.hrychanok.training.shop.web.page.BasePageForTable;
import by.hrychanok.training.shop.web.page.personalCabinet.SortableTypeDataProvider;
import by.hrychanok.training.shop.web.page.product.ProductPage;

public class CatalogPage extends BasePageForTable {

	@SpringBean
	ProductService productService;
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
	public CatalogPage() {
		SortableProductDataProvider dp = new SortableProductDataProvider(productService.findAll());

		final DataView<Product> dataView = new DataView<Product>("oir", dp) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final Item<Product> item) {
				Product product = item.getModelObject();
				item.add(new ActionPanel("actions", item.getModel()) {
					@Override
					public void goResponsePage() {
						setResponsePage(new ProductPage(selected.getId()));
					};
				}

				);

				item.add(new Link<Void>("toggleHighlite") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						HighlitableDataItem<Product> hitem = (HighlitableDataItem<Product>) item;
						hitem.toggleHighlite();
					}
				});
				item.add(new Label("productid", String.valueOf(product.getId())));
				item.add(new Label("name", product.getName()));
				item.add(new Label("model", product.getModel()));
				item.add(new Label("description", product.getDescription()));
				item.add(new Label("price", product.getPrice()));

				item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
					private static final long serialVersionUID = 1L;

					@Override
					public String getObject() {
						return (item.getIndex() % 2 == 1) ? "even" : "odd";
					}
				}));
			}

			@Override
			protected Item<Product> newItem(String id, int index, IModel<Product> model) {
				return new HighlitableDataItem<>(id, index, model);
			}
		};

		dataView.setItemsPerPage(8L);
		dataView.setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());

		add(new OrderByBorder("orderByModel", "model", dp) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});

		add(new OrderByBorder("orderByDescription", "description", dp) {
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
