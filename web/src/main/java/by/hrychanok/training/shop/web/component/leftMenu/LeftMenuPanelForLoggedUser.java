package by.hrychanok.training.shop.web.component.leftMenu;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import by.hrychanok.training.shop.web.page.cart.CartPage;
import by.hrychanok.training.shop.web.page.home.HomePage;
import by.hrychanok.training.shop.web.page.personalCabinet.CustomerOrderPage;
import by.hrychanok.training.shop.web.page.personalCabinet.OrderHistoryPage;
import by.hrychanok.training.shop.web.page.personalCabinet.PersonalDataPage;

public class LeftMenuPanelForLoggedUser extends LeftMenuPanel {

	public LeftMenuPanelForLoggedUser(String id) {
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
		 add(new Link("toCart") {
	            @Override
	            public void onClick() {
	                setResponsePage(new CartPage());
	            }
	        });
		 add(new Link("toLogout") {
	            @Override
	            public void onClick() {
	                setResponsePage(new HomePage());
	            }
	        });
	}
}
