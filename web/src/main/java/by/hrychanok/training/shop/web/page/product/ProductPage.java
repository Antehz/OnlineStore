package by.hrychanok.training.shop.web.page.product;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

import by.hrychanok.training.shop.service.CustomerService;
import by.hrychanok.training.shop.service.ProductService;
import by.hrychanok.training.shop.web.page.AbstractPage;
import by.hrychanok.training.shop.web.page.home.HomePage;

public class ProductPage extends AbstractPage {
	
	@SpringBean
	ProductService productService;


	public ProductPage(PageParameters parametrs) {
		StringValue productId = parametrs.get("id");
		
		add(new Label("productProfile", new Model(productService.findOne(productId.toLong()))));
	}

	public ProductPage(Long id) {
		
		add(new Label("productProfile", new Model(productService.findOne(id))));
	}
}
