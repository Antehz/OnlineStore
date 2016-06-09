package by.hrychanok.training.shop.web.component.leftMenu;

import static by.hrychanok.training.shop.web.app.WicketApplication.REMEMBER_ME_DURATION_IN_DAYS;
import static by.hrychanok.training.shop.web.app.WicketApplication.REMEMBER_ME_LOGIN_COOKIE;
import static by.hrychanok.training.shop.web.app.WicketApplication.REMEMBER_ME_PASSWORD_COOKIE;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import by.hrychanok.training.shop.model.CartContent;
import by.hrychanok.training.shop.model.CustomerCredentials;
import by.hrychanok.training.shop.service.CartService;
import by.hrychanok.training.shop.web.app.AuthorizedSession;
import by.hrychanok.training.shop.web.app.CookieService;
import by.hrychanok.training.shop.web.app.MySession;
import by.hrychanok.training.shop.web.app.WicketApplication;
import by.hrychanok.training.shop.web.component.login.RegistrationPage;
import by.hrychanok.training.shop.web.page.cart.CartPage;
import by.hrychanok.training.shop.web.page.home.HomePage;
import by.hrychanok.training.shop.web.page.personalCabinet.CustomerOrderPage;
import by.hrychanok.training.shop.web.page.personalCabinet.CustomerProfile;
import by.hrychanok.training.shop.web.page.personalCabinet.OrderHistoryPanel;

public class PersonalCabinetPanel extends InfoPanel {

	private int countItemIntoCart = 0;
	@SpringBean
	CartService cartService;
	CustomerCredentials customer;
	public PersonalCabinetPanel(String id) {
		super(id);
	    customer = AuthorizedSession.get().getLoggedUser();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new Link("toPersonalData") {
			@Override
			public void onClick() {
				PageParameters parameters = new PageParameters();
				parameters.add("id", customer.getId());
				setResponsePage(new CustomerProfile(parameters));
			}
		});
		add(new Link("toCustomerOrder") {
			@Override
			public void onClick() {
				setResponsePage(new CustomerOrderPage());
			}
		});
		Link cartLink = new Link("toCart") {
			@Override
			public void onClick() {
				setResponsePage(new CartPage());
			}
		};
		Long customerId = AuthorizedSession.get().getLoggedUser().getId();
		List<CartContent> cartContentList = cartService.getCustomerCartContent(customerId);
		int countItemIntoCart = cartContentList.size();
		cartLink.setBody( Model.of("Корзина  "+countItemIntoCart));
		if(countItemIntoCart>0){
			cartLink.add(new AttributeModifier("color", "red"));
		} //Не работает ДОДУМАТЬ!
		add(cartLink);
		
		
		
		Link logoutLink = new Link("toLogout") {
			@Override
			public void onClick() {
				CookieService cookieService = ((WicketApplication) WicketApplication.get()).getCookieService();
				cookieService.saveCookie(getResponse(), REMEMBER_ME_LOGIN_COOKIE, null,
						REMEMBER_ME_DURATION_IN_DAYS);
				cookieService.saveCookie(getResponse(), REMEMBER_ME_PASSWORD_COOKIE, null,
						REMEMBER_ME_DURATION_IN_DAYS);
		        cookieService.removeCookieIfPresent(getRequest(), getResponse(), WicketApplication.REMEMBER_ME_LOGIN_COOKIE);
		        cookieService.removeCookieIfPresent(getRequest(), getResponse(), WicketApplication.REMEMBER_ME_PASSWORD_COOKIE);
		        ((WicketApplication) WicketApplication.get()).setLoggedUser(null);
		        MySession.get().setLoggedUser(null);
		        MySession.get().signOut();
				getSession().invalidateNow();
				setResponsePage(HomePage.class);
			}
		};
		add(logoutLink);

		

	}
}
