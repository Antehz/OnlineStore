package by.hrychanok.training.shop.web.page.common;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import by.hrychanok.training.shop.model.LampType;

public class LampTypeChoiceRenderer extends ChoiceRenderer<LampType> {

	public static final LampTypeChoiceRenderer INSTANCE = new LampTypeChoiceRenderer();

	private LampTypeChoiceRenderer() {
		super();
	}

	@Override
	public Object getDisplayValue(LampType object) {
		return object.name();
	}

	@Override
	public String getIdValue(LampType object, int index) {
		return String.valueOf(object.ordinal());
	}
}