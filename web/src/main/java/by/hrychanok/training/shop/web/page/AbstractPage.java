package by.hrychanok.training.shop.web.page;

import java.util.Date;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import by.hrychanok.training.shop.model.Category;
import by.hrychanok.training.shop.service.CategoryService;
import by.hrychanok.training.shop.web.component.footer.FooterPanel;
import by.hrychanok.training.shop.web.component.header.HeaderPanel;
import by.hrychanok.training.shop.web.component.leftMenu.LeftMenuPanel;
import by.hrychanok.training.shop.web.component.leftMenu.LeftMenuPanelForLoggedUser;
import by.hrychanok.training.shop.web.component.leftMenu.PanelContent;
import by.hrychanok.training.shop.web.component.leftMenu.ProductCategoryPanel;
import by.hrychanok.training.shop.web.page.product.BeginnersTreePage;

public abstract class AbstractPage extends WebPage {

	private Component headerPanel;
	private Component leftMenuPanel;
	private Component footerPanel;
	private Component panelContent;

	public AbstractPage() {
		super();
	}

	public AbstractPage(PageParameters parameters) {
		super(parameters);
	}
	@SpringBean
	CategoryService cat ;

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		add(headerPanel=new HeaderPanel("headerPanel"));
		add(leftMenuPanel=new LeftMenuPanelForLoggedUser("leftMenuPanel"));
		add(footerPanel=new FooterPanel("footerPanel"));
		
		Form form = new Form("form"){
		    @Override
		    protected void onSubmit() {
		    	System.out.println("Form submitted.");
		    }
		};
		add(form);
	}

}
