package by.hrychanok.training.shop.web.page.common;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import by.hrychanok.training.shop.model.Category;

public class CategoryChoiceRenderer extends ChoiceRenderer<Category> {

	public static final CategoryChoiceRenderer INSTANCE = new CategoryChoiceRenderer();

	private CategoryChoiceRenderer() {
		super();
	}

	@Override
	public Object getDisplayValue(Category object) {
		return object.getName();
	}

	@Override
	public String getIdValue(Category object, int index) {
		return String.valueOf(object.getName());
	}
}