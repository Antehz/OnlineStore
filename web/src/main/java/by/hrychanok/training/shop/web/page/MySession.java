package by.hrychanok.training.shop.web.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import by.hrychanok.training.shop.model.CartContent;

public class MySession extends WebSession {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<CartContent> getCartList() {
		return cartList;
	}

	public void setCartList(List<CartContent> cartList) {
		this.cartList = cartList;
	}

	private  List<CartContent> cartList = new ArrayList<CartContent>();

	public MySession(Request request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	
}
