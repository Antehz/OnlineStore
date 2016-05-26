package by.hrychanok.training.shop.web.page.catalog;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.jquery.ui.form.button.IndicatingAjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

import by.hrychanok.training.shop.model.AbstractModel;
import by.hrychanok.training.shop.model.CartContent;
import by.hrychanok.training.shop.model.Customer;
import by.hrychanok.training.shop.model.Order;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.service.CartService;
import by.hrychanok.training.shop.service.CustomerService;
import by.hrychanok.training.shop.service.OrderService;
import by.hrychanok.training.shop.service.ProductService;
import by.hrychanok.training.shop.service.impl.CartServiceImpl;
import by.hrychanok.training.shop.web.component.header.HeaderPanel;
import by.hrychanok.training.shop.web.component.leftMenu.PersonalCabinetPanel;
import by.hrychanok.training.shop.web.component.productFilter.ProductFilterPanel;
import by.hrychanok.training.shop.web.page.AbstractPage;
import by.hrychanok.training.shop.web.page.BasePageForTable;
import by.hrychanok.training.shop.web.page.StaticImage;
import by.hrychanok.training.shop.web.page.personalCabinet.SortableTypeDataProvider;
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
		Customer customer = customerService.findOne(1580L); // FIX!!!

		SortableProductDataProvider dp = new SortableProductDataProvider(categoryId);

		final DataView<Product> dataView = new DataView<Product>("productTable", dp) {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			protected void populateItem(final Item<Product> item) {
				Product product = item.getModelObject();
				// Customer customer =
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
				
				Button buyButton = new Button("buyButton") {
			        public void onSubmit() {
			        boolean df=	cartService.addProductToCart(product.getId(), customer.getId());
			        
			    }};
				form.add(buyButton);
/*
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
				});*/

				item.add(form);
				item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
					private static final long serialVersionUID = 1L;

					@Override
					public String getObject() {
						return (item.getIndex() % 2 == 1) ? "even" : "odd";
					}
				}));
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

	private void addedInfo(Component component) {
		this.success("Добавлено");
	}

	private void notAddedInfo(Component component) {
		this.error("Ошибка");
	}
	
}