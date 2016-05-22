package by.hrychanok.training.shop.web.page.catalog;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
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
import org.apache.wicket.request.UrlUtils;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

import by.hrychanok.training.shop.model.AbstractModel;
import by.hrychanok.training.shop.model.Order;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.service.CartService;
import by.hrychanok.training.shop.service.OrderService;
import by.hrychanok.training.shop.service.ProductService;
import by.hrychanok.training.shop.web.page.AbstractPage;
import by.hrychanok.training.shop.web.page.BasePageForTable;
import by.hrychanok.training.shop.web.page.StaticImage;
import by.hrychanok.training.shop.web.page.personalCabinet.SortableTypeDataProvider;
import by.hrychanok.training.shop.web.page.product.ProductPage;

public class CatalogPage extends BasePageForTable {

	@SpringBean
	ProductService productService;
	
	@SpringBean
	CartService cartService;
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

	public CatalogPage(PageParameters parametrs) {
		Long categoryId = parametrs.get("id").toLong();

		SortableProductDataProvider dp = new SortableProductDataProvider(categoryId);

		final DataView<Product> dataView = new DataView<Product>("productTable", dp) {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			protected void populateItem(final Item<Product> item) {
				Product product = item.getModelObject();
				item.add(new ActionPanel("action", item.getModel()) {
					@Override
					public void createLink(IModel model) {
						Product selectedProduct = (Product) model.getObject();
						Image image = new Image("imagetest",
								new PackageResourceReference(CatalogPage.class, selectedProduct.getImageURL()));

						Link link = new Link("select") {
							@Override
							public void onClick() {
								selected = (AbstractModel) getParent().getDefaultModelObject();
								setResponsePage(new ProductPage(selected.getId()));
							}
						};

						link.add(image);
						add(link);
					}

				});
				/*
				 * item.add(new Link<Void>("toggleHighlite") { private static
				 * final long serialVersionUID = 1L;
				 * 
				 * @Override public void onClick() {
				 * HighlitableDataItem<Product> hitem =
				 * (HighlitableDataItem<Product>) item; hitem.toggleHighlite();
				 * } });
				 */
				String info = product.getModel() + "/n " + product.getDescription();
				item.add(new Label("manufacturer", product.getManufacturer()));
				item.add(new Label("model", info));
				item.add(new Label("price", product.getPrice()));
				item.add(new Label("recomended", product.getCountRecommended()));
				item.add(new Label("available", product.getAvailable()));
                item.add(new Link("addToCart"){

					@Override
					public void onClick() {
						cartService.addProductToCart(product.getId(), 1580L);
						
					}
                	
                	
                });
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

		dataView.setItemsPerPage(12L);
		dataView.setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());

		add(new OrderByBorder("orderByManufacturer", "manufacturer", dp) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});

		add(new OrderByBorder("orderByModel", "model", dp) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});
		add(new OrderByBorder("orderByPrice", "price", dp) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});
		add(new OrderByBorder("orderByRecomended", "available", dp) {
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