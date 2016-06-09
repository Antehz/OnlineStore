package by.hrychanok.training.shop.web.page;

import org.apache.wicket.Component;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

import by.hrychanok.training.shop.web.component.footer.FooterPanel;
import by.hrychanok.training.shop.web.component.header.HeaderPanel;
import by.hrychanok.training.shop.web.component.leftMenu.InfoPanel;
import by.hrychanok.training.shop.web.component.leftMenu.PersonalCabinetPanel;
import by.hrychanok.training.shop.web.component.productFilter.ProductFilterPanel;
import by.hrychanok.training.shop.web.component.productFilter.SearchProduct;
import by.hrychanok.training.shop.web.page.catalog.CatalogPage;

public abstract class AbstractPage extends WebPage {

	private Component headerPanel;
	private Component leftMenuPanel;
	private Component footerPanel;
	private Component filterProductPanel;

	public AbstractPage() {
		super();
	}

	public AbstractPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		add(feedback);

		if (AuthenticatedWebSession.get().isSignedIn()) {

			add(leftMenuPanel = new PersonalCabinetPanel("leftMenuPanel"));
		} else {
			add(leftMenuPanel = new InfoPanel("leftMenuPanel"));
		}
		add(new SearchProduct("searchProduct"));
		
		filterProductPanel = new ProductFilterPanel("filterProductPanel");
		filterProductPanel.setVisible(false);
		if (getPage().getClass().equals(CatalogPage.class)) {
			filterProductPanel.setVisible(true);
		}
		add(filterProductPanel);
		add(footerPanel = new FooterPanel("footerPanel"));
		add(headerPanel = new HeaderPanel("headerPanel"));
		
		
		
	}
}
