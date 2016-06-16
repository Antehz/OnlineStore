package by.hrychanok.training.shop.web.page.common;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import by.hrychanok.training.shop.model.BatteryType;

public class BatteryTypeChoiceRenderer extends ChoiceRenderer<BatteryType> {

	public static final BatteryTypeChoiceRenderer INSTANCE = new BatteryTypeChoiceRenderer();

	private BatteryTypeChoiceRenderer() {
		super();
	}

	@Override
	public Object getDisplayValue(BatteryType object) {
		return object.name();
	}

	@Override
	public String getIdValue(BatteryType object, int index) {
		return String.valueOf(object.ordinal());
	}
}