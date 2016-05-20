package by.hrychanok.training.shop.web.page.order;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import by.hrychanok.training.shop.service.OrderService;
import by.hrychanok.training.shop.web.page.AbstractPage;

public class OrderPage extends AbstractPage {

	@SpringBean
	OrderService orderService;

	public OrderPage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderPage(Long id) {
		add(new Label("orderProfile", new Model(orderService.findOne(id))));
	}
	
	
}
