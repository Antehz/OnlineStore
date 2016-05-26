package by.hrychanok.training.shop.web.page;

import java.util.Date;

import org.apache.wicket.Component;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

import by.hrychanok.training.shop.model.Category;
import by.hrychanok.training.shop.service.CategoryService;
import by.hrychanok.training.shop.web.component.footer.FooterPanel;
import by.hrychanok.training.shop.web.component.header.HeaderPanel;
import by.hrychanok.training.shop.web.component.leftMenu.CatalogTreePanel;
import by.hrychanok.training.shop.web.component.leftMenu.InfoPanel;
import by.hrychanok.training.shop.web.component.leftMenu.PersonalCabinetPanel;
import by.hrychanok.training.shop.web.component.productFilter.ProductFilterPanel;
import by.hrychanok.training.shop.web.page.catalog.CatalogPage;
@AuthorizeInstantiation(value = { "admin", "customer" })

public abstract class AbstractPage extends WebPage {

	private Component headerPanel;
	private Component leftMenuPanel;
	private Component footerPanel;

	public AbstractPage() {
		super();
	}

	public AbstractPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(headerPanel = new HeaderPanel("headerPanel"));
		add(leftMenuPanel = new PersonalCabinetPanel("leftMenuPanel"));
		add(footerPanel = new FooterPanel("footerPanel"));
		 
	}
}
