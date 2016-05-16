package by.hrychanok.training.shop.web.page;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import by.hrychanok.training.shop.web.component.footer.FooterPanel;
import by.hrychanok.training.shop.web.component.header.HeaderPanel;
import by.hrychanok.training.shop.web.component.leftMenu.LeftMenuPanel;
import by.hrychanok.training.shop.web.component.leftMenu.LeftMenuPanelForLoggedUser;

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
		
		add(headerPanel=new HeaderPanel("headerPanel"));
		add(leftMenuPanel=new LeftMenuPanelForLoggedUser("leftMenuPanel"));
		add(footerPanel=new FooterPanel("footerPanel"));

	}

}