package by.hrychanok.training.shop.web.page.product;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.form.spinner.AjaxSpinner;
import com.googlecode.wicket.kendo.ui.form.button.IndicatingAjaxButton;
import com.googlecode.wicket.kendo.ui.widget.tabs.AjaxTab;
import com.googlecode.wicket.kendo.ui.widget.tabs.TabbedPanel;

import by.hrychanok.training.shop.model.CustomerCredentials;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.service.CartService;
import by.hrychanok.training.shop.service.ProductService;
import by.hrychanok.training.shop.web.app.AuthorizedSession;
import by.hrychanok.training.shop.web.app.MySession;
import by.hrychanok.training.shop.web.page.AbstractPage;

public class ProductPage extends AbstractPage {

	@SpringBean
	ProductService productService;

	@SpringBean
	CartService cartService;
	private boolean visibleForUser = AuthorizedSession.get().isSignedIn();

	private Product product;
	private CustomerCredentials customer;
	private Integer amount = 1;

	public ProductPage(PageParameters parametrs) {
		StringValue productId = parametrs.get("id");
		product = productService.findOne(productId.toLong());
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		CustomerCredentials customer = AuthorizedSession.get().getLoggedUser();
		ContextImage image = new ContextImage("image", product.getImageURL());
		add(image);
		add(new Label("id", Model.of(product.getId())));
		add(new Label("name", Model.of(product.getName())));
		add(new Label("manufacturer", Model.of(product.getManufacturer())));
		add(new Label("model", Model.of(product.getModel())));
		add(new Label("price", Model.of(product.getPrice())));
		add(new Label("countOrder", Model.of(product.getCountOrder())));
		add(new Label("countRecommended", Model.of(product.getCountRecommended())));
		add(new Label("available", Model.of(product.getAvailable())));

		// Spiner amount and buyButton
		final Form<Integer> form = new Form<Integer>("form", Model.of(1));
		add(form);

		// Spinner //
		final AjaxSpinner<Integer> spinner = new AjaxSpinner<Integer>("spinner", form.getModel(), Integer.class) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSpin(AjaxRequestTarget target, Integer value) {
				amount = value;
			}
		};
		spinner.setMin(1);
		spinner.setEnabled(product.getAvailable() > 0);
		spinner.setMax(product.getAvailable());
		form.add(spinner);

		ContextImage imageToCart = new ContextImage("imageToCart", "images/toCart.png");
		IndicatingAjaxButton buyButton = new IndicatingAjaxButton("buyButton") {

			private static final long serialVersionUID = 1L;

			@Override
			protected boolean isDisabledOnClick() {
				return true;
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (visibleForUser) {
					if (cartService.addProductToCart(product.getId(), customer.getId(), amount)) {
						addedInfo(form);
						target.add(ProductPage.this.feedback);
					} else {
						notAddedInfo(form);
						target.add(ProductPage.this.feedback);
					}
				} else {
					if (MySession.get().addToCart(product, amount)) {
						addedInfo(form);
						target.add(ProductPage.this.feedback);
					} else {
						notAddedInfo(form);
						target.add(ProductPage.this.feedback);
					}
				}
			}
		};
		buyButton.add(imageToCart);
		form.add(buyButton);

		// add tabs

		Options options = new Options();
		options.set("collapsible", true);
		add(new TabbedPanel("tabs", this.newTabList(), options));
	}

	private List<ITab> newTabList() {
		List<ITab> tabs = new ArrayList<ITab>();
		String description = getString("description");
		tabs.add(new AjaxTab(Model.of(description)) {

			private static final long serialVersionUID = 1L;

			@Override
			public WebMarkupContainer getLazyPanel(String panelId) {
				try {
					// sleep the thread for a half second to simulate a long
					// load
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return new ProductCombinePanel(panelId, product.getId());
			}
		});
		String review = getString("review");
		tabs.add(new AjaxTab(Model.of(review)) {

			private static final long serialVersionUID = 1L;

			@Override
			public WebMarkupContainer getLazyPanel(String panelId) {
				try {
					// sleep the thread for a half second to simulate a long
					// load
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return new ReviewPanel(panelId, product.getId());
			}
		});

		return tabs;
	}

	private void addedInfo(Component component) {
		String productAdded = getString("productAdded");
		this.success(productAdded);
	}

	private void notAddedInfo(Component component) {
		String productNotAdded = getString("productNotAdded");
		this.error(productNotAdded);
	}
}
