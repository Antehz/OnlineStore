package by.hrychanok.training.shop.web.page.personalCabinet;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import by.hrychanok.training.shop.web.page.AbstractPage;
import by.hrychanok.training.shop.web.page.home.HomePage;

public class CustomerOrderPage extends AbstractPage{

	public CustomerOrderPage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerOrderPage(PageParameters parameters) {
		super(parameters);
		// TODO Auto-generated constructor stub
	}
     @Override
    protected void onInitialize() {
    	super.onInitialize();
    	 add(new Link("toOrderHistory"){
    		 @Override
    		public void onClick() {
    			setResponsePage(new OrderHistoryPage());  //Сделать для конкретного пользователя
    			//zxczxczxc
    		}
    	 });
    }
}
