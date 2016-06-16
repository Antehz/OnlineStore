package by.hrychanok.training.shop.web.page.common;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import by.hrychanok.training.shop.model.CoolantColor;

public class CoolantColorChoiceRenderer extends ChoiceRenderer<CoolantColor> {

	public static final CoolantColorChoiceRenderer INSTANCE = new CoolantColorChoiceRenderer();

	private CoolantColorChoiceRenderer() {
		super();
	}

	@Override
	public Object getDisplayValue(CoolantColor object) {
		return object.name();
	}

	@Override
	public String getIdValue(CoolantColor object, int index) {
		return String.valueOf(object.ordinal());
	}
}