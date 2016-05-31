package by.hrychanok.training.shop.web.page.product;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.widget.tabs.AjaxTab;
import com.googlecode.wicket.kendo.ui.widget.tabs.TabbedPanel;

import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.service.CustomerService;
import by.hrychanok.training.shop.service.ProductService;
import by.hrychanok.training.shop.web.page.AbstractPage;
import by.hrychanok.training.shop.web.page.StaticImage;
import by.hrychanok.training.shop.web.page.catalog.CatalogPage;
import by.hrychanok.training.shop.web.page.home.HomePage;
import by.hrychanok.training.shop.web.page.personalCabinet.OrderHistoryPage;

public class ProductPage extends AbstractPage {

	@SpringBean
	ProductService productService;

	private Product product;

	public ProductPage(PageParameters parametrs) {
		StringValue productId = parametrs.get("id");
		product = productService.findOne(productId.toLong());
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
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

		// add tabs

		Options options = new Options();
		options.set("collapsible", true);
		add(new TabbedPanel("tabs", this.newTabList(), options));
	}

	private List<ITab> newTabList() {
		List<ITab> tabs = new ArrayList<ITab>();
		tabs.add(new AjaxTab(Model.of("Описание")) {

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

				return new ProductCombinePanel(panelId, Model.of(product));
			}
		});

	/*	tabs.add(new AjaxTab(Model.of("Характеристики")) {

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
				return defineProduct(panelId);
			}
		});
		tabs.add(new AjaxTab(Model.of("Все заказы")) {

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
				return new OrderHistoryPage(panelId);
			}
		});*/
		return tabs;
	}

	/*private Panel defineProduct(String panelId) {
		Boolean tire = "Шины".equals(product.getCategory().getParent().getName());

		if (tire) {
			return new TireFeatures(panelId, Model.of(product));
		}
		return null;
	};*/
}
