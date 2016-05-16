package by.hrychanok.training.shop.web.component.header;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import by.hrychanok.training.shop.web.page.cart.CartPage;
import by.hrychanok.training.shop.web.page.home.HomePage;
import by.hrychanok.training.shop.web.page.info.AboutUsPage;
import by.hrychanok.training.shop.web.page.info.ContactPage;
import by.hrychanok.training.shop.web.page.info.NewsPage;
import by.hrychanok.training.shop.web.page.info.PaymentPage;

public class HeaderPanel extends Panel {
	
	public HeaderPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		 add(new Link("toHome") {
	            @Override
	            public void onClick() {
	                setResponsePage(new HomePage());
	            }
	        });
		 add(new Link("toHomeForLogo") {
	            @Override
	            public void onClick() {
	                setResponsePage(new HomePage());
	            }
	        });
		 add(new Link("toAboutUs") {
	            @Override
	            public void onClick() {
	                setResponsePage(new AboutUsPage());
	            }
	        });
		 add(new Link("toNews") {
	            @Override
	            public void onClick() {
	                setResponsePage(new NewsPage());
	            }
	        });
		 add(new Link("toShipment") {
	            @Override
	            public void onClick() {
	                setResponsePage(new NewsPage());
	            }
	        });
		 add(new Link("toPayment") {
	            @Override
	            public void onClick() {
	                setResponsePage(new PaymentPage());
	            }
	        });
		 add(new Link("toContact") {
	            @Override
	            public void onClick() {
	                setResponsePage(new ContactPage());
	            }
	        });
		 add(new Link("toCart") {
	            @Override
	            public void onClick() {
	                setResponsePage(new CartPage());
	            }
	        });
		
	}
}
