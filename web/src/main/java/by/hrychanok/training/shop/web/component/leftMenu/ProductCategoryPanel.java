package by.hrychanok.training.shop.web.component.leftMenu;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import by.hrychanok.training.shop.model.Category;

public class ProductCategoryPanel extends Panel {

	public ProductCategoryPanel(String id, IModel<Category> category) {
		super(id, new CompoundPropertyModel<>(category));
		add(new TextField<>("Name"));
	}

}
