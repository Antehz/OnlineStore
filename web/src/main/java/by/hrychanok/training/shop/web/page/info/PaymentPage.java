package by.hrychanok.training.shop.web.page.info;

import java.util.List;

import by.hrychanok.training.shop.model.CartContent;
import by.hrychanok.training.shop.web.app.MySession;
import by.hrychanok.training.shop.web.page.AbstractPage;

public class PaymentPage extends AbstractPage {

	public PaymentPage() {
		super();
		List<CartContent> list = MySession.get().getCartList();
		for (CartContent cartContent : list) {
			System.out.println(cartContent);
		}	
	}

	
}
