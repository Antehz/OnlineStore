package by.hrychanok.training.shop.web.component.leftMenu;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import by.hrychanok.training.shop.model.CartContent;
import by.hrychanok.training.shop.service.CartService;
import by.hrychanok.training.shop.web.app.AuthorizedSession;
import by.hrychanok.training.shop.web.page.cart.CartPage;
import by.hrychanok.training.shop.web.page.home.HomePage;
import by.hrychanok.training.shop.web.page.personalCabinet.CustomerOrderPage;
import by.hrychanok.training.shop.web.page.personalCabinet.OrderHistoryPage;
import by.hrychanok.training.shop.web.page.personalCabinet.PersonalDataPage;

public class PersonalCabinetPanel extends InfoPanel {

	private int countItemIntoCart = 0;
	@SpringBean
	CartService cartService;

	public PersonalCabinetPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new Link("toPersonalData") {
			@Override
			public void onClick() {
				setResponsePage(new PersonalDataPage());
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
				getSession().invalidate();
				setResponsePage(HomePage.class);
			}
		};
		add(logoutLink);

		

	}
}
