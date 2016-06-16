package by.hrychanok.training.shop.web.page.common;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import by.hrychanok.training.shop.model.WheelType;

public class WheelTypeChoiceRenderer extends ChoiceRenderer<WheelType> {

	public static final WheelTypeChoiceRenderer INSTANCE = new WheelTypeChoiceRenderer();

	private WheelTypeChoiceRenderer() {
		super();
	}

	@Override
	public Object getDisplayValue(WheelType object) {
		return object.name();
	}

	@Override
	public String getIdValue(WheelType object, int index) {
		return String.valueOf(object.ordinal());
	}
}