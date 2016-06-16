package by.hrychanok.training.shop.web.page.home;

import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import com.googlecode.wicket.jquery.ui.JQueryUIBehavior;
import by.hrychanok.training.shop.model.Category;
import by.hrychanok.training.shop.service.CategoryService;
import by.hrychanok.training.shop.web.page.AbstractPage;
import by.hrychanok.training.shop.web.page.catalog.CatalogPage;

public class HomePage extends AbstractPage {

	@SpringBean
	CategoryService categoryService;
	private Category category;
	public HomePage() {
		super();
		this.add(new JQueryUIBehavior("#accordion", "accordion"));
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
        category  = categoryService.findOne(3L);
		Link wheelCategoryLink = new Link("wheelCategoryLink") {
			@Override
			public void onClick() {
				PageParameters parameters = new PageParameters();
				parameters.add("id", 7L);
				setResponsePage(new CatalogPage(parameters));
			}
		};
		ContextImage wheelImage = new ContextImage("wheelImage", "images/product/wheel.jpg");
		wheelCategoryLink.add(wheelImage);
		wheelCategoryLink.setOutputMarkupId(true);
		wheelImage.setOutputMarkupId(true);

		add(wheelCategoryLink);
		
		Link tireCategoryLink = new Link("tireCategoryLink") {
			@Override
			public void onClick() {
				PageParameters parameters = new PageParameters();
				parameters.add("id", 3L);
				setResponsePage(new CatalogPage(parameters));
			}
		};
		ContextImage tireImage = new ContextImage("tireImage", "images/product/tire.jpg");
		tireCategoryLink.add(tireImage);
		add(tireCategoryLink);

		Link lampCategoryLink = new Link("lampCategoryLink") {
			@Override
			public void onClick() {
				PageParameters parameters = new PageParameters();
				parameters.add("id", 12L);
				setResponsePage(new CatalogPage(parameters));
			}
		};
		ContextImage lampImage = new ContextImage("lampImage", "images/product/lamp.jpg");
		lampCategoryLink.add(lampImage);
		add(lampCategoryLink);

		Link batteryCategoryLink = new Link("batteryCategoryLink") {
			@Override
			public void onClick() {
				PageParameters parameters = new PageParameters();
				parameters.add("id", 9L);
				setResponsePage(new CatalogPage(parameters));
			}
		};
		ContextImage batteryImage = new ContextImage("batteryImage", "images/product/battery.jpg");
		batteryCategoryLink.add(batteryImage);
		add(batteryCategoryLink);

		Link oilCategoryLink = new Link("oilCategoryLink") {
			@Override
			public void onClick() {
				PageParameters parameters = new PageParameters();
				parameters.add("id", 15L);
				setResponsePage(new CatalogPage(parameters));
			}
		};
		ContextImage oilImage = new ContextImage("oilImage", "images/product/oil.jpg");
		oilCategoryLink.add(oilImage);
		add(oilCategoryLink);

		Link coolantCategoryLink = new Link("coolantCategoryLink") {
			@Override
			public void onClick() {
				PageParameters parameters = new PageParameters();
				parameters.add("id", 18L);
				setResponsePage(new CatalogPage(parameters));
			}
		};
		ContextImage coolantImage = new ContextImage("coolantImage", "images/product/coolant.jpg");
		coolantCategoryLink.add(coolantImage);
		add(coolantCategoryLink);

		Link screenWashCategoryLink = new Link("screenWashCategoryLink") {
			@Override
			public void onClick() {
				PageParameters parameters = new PageParameters();
				parameters.add("id", 17L);
				setResponsePage(new CatalogPage(parameters));
			}
		};
		ContextImage washerImage = new ContextImage("washerImage", "images/product/washer.jpg");
		screenWashCategoryLink.add(washerImage);
		add(screenWashCategoryLink);
	}
}