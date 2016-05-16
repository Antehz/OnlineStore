package by.hrychanok.training.shop.web.component.leftMenu;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import by.hrychanok.training.shop.web.page.home.HomePage;
import by.hrychanok.training.shop.web.page.product.BeginnersTreePage;

public class LeftMenuPanel extends Panel {

	public LeftMenuPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		 add(new Link("toHome1") {
	            @Override
	            public void onClick() {
	                setResponsePage(new HomePage());
	            }
	        });
		 add(new Link("toProductTree") {
	            @Override
	            public void onClick() {
	                setResponsePage(new BeginnersTreePage());
	            }
	        });
		 add(new Link("toHome3") {
	            @Override
	            public void onClick() {
	                setResponsePage(new HomePage());
	            }
	        });
		 add(new Link("toHome4") {
	            @Override
	            public void onClick() {
	                setResponsePage(new HomePage());
	            }
	        });
	}
}
