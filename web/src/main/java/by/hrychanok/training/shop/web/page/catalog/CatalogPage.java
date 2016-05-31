package by.hrychanok.training.shop.web.page.catalog;

import java.util.Iterator;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;

import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import by.hrychanok.training.shop.model.AbstractModel;
import by.hrychanok.training.shop.model.CustomerCredentials;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.repository.filter.Comparison;
import by.hrychanok.training.shop.repository.filter.Condition;
import by.hrychanok.training.shop.service.CartService;
import by.hrychanok.training.shop.service.CustomerService;
import by.hrychanok.training.shop.service.ProductService;
import by.hrychanok.training.shop.web.app.AuthorizedSession;
import by.hrychanok.training.shop.web.page.BasePageForTable;
import by.hrychanok.training.shop.web.page.GenericSortableTypeDataProvider;
import by.hrychanok.training.shop.web.page.product.ProductPage;

public class CatalogPage extends BasePageForTable {
	private static Logger LOGGER = LoggerFactory.getLogger(CatalogPage.class);

	@SpringBean
	ProductService productService;

	@SpringBean
	CartService cartService;

	@SpringBean
	CustomerService customerService;

	private static final long serialVersionUID = 1L;

	public CatalogPage(PageParameters parametrs) {

		Long categoryId = parametrs.get("id").toLong();
		CustomerCredentials customer = AuthorizedSession.get().getLoggedUser();

		GenericSortableTypeDataProvider<Product> dp = new GenericSortableTypeDataProvider<Product>() {

			public Iterator<? extends Product> returnIterator(PageRequest pageRequest) {
				filterState.addCondition(new Condition.Builder().setComparison(Comparison.eq).setField("category")
						.setValue(categoryId).build());
				return productService.findAll(filterState, pageRequest).iterator();
			}
			@Override
			public long size() {
				return productService.count(filterState);
			}
		};

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
						ContextImage image = new ContextImage("imagetest",  selectedProduct.getImageURL());

						Link link = new Link("select") {
							@Override
							public void onClick() {
								selected = (AbstractModel) getParent().getDefaultModelObject();
								PageParameters parameters = new PageParameters();
								parameters.add("id", selectedProduct.getId());
								setResponsePage(new ProductPage(parameters));
							}
						};

						link.add(image);
						add(link);
					}

				});
				String info = product.getModel() + "  " + product.getDescription();
				item.add(new Label("manufacturer", product.getManufacturer()));
				item.add(new Label("model", info));
				item.add(new Label("price", product.getPrice()));
				item.add(new Label("recomended", product.getCountRecommended()));
				item.add(new Label("available", product.getAvailable()));
				final Form<Void> form = new Form<Void>("form");

				// FeedbackPanel //
				final KendoFeedbackPanel feedbackBuyItem = new KendoFeedbackPanel("feedbackBuyItem");
				form.add(feedbackBuyItem.setOutputMarkupId(true));

				form.add(new AjaxButton("buyButton") {

					private static final long serialVersionUID = 1L;

					@Override
					protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

						if (cartService.addProductToCart(product.getId(), customer.getId())) {
							CatalogPage.this.addedInfo(form);
							target.add(feedbackBuyItem);
						} else {
							CatalogPage.this.notAddedInfo(form);
							target.add(feedbackBuyItem);
						}
					}
				}).setEnabled(product.getAvailable()>0);
				
				item.add(form);
			}
		};
		dataView.setItemsPerPage(8L);
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

	
	
	private void addedInfo(Component component) {
		this.success("Добавлено");
	}

	private void notAddedInfo(Component component) {
		this.error("Ошибка");
	}

}