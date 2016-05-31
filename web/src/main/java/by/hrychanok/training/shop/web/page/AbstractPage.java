package by.hrychanok.training.shop.web.page;

import org.apache.wicket.Component;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import by.hrychanok.training.shop.web.component.footer.FooterPanel;
import by.hrychanok.training.shop.web.component.header.HeaderPanel;
import by.hrychanok.training.shop.web.component.leftMenu.InfoPanel;
import by.hrychanok.training.shop.web.component.leftMenu.PersonalCabinetPanel;

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

		if (AuthenticatedWebSession.get().isSignedIn()) {
			
			add(leftMenuPanel = new PersonalCabinetPanel("leftMenuPanel"));
		} else {
			add(leftMenuPanel = new InfoPanel("leftMenuPanel"));
		}

		add(footerPanel = new FooterPanel("footerPanel"));
		add(headerPanel = new HeaderPanel("headerPanel"));
		
	}
}
