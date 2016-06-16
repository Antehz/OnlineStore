package by.hrychanok.training.shop.web.page.common;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import by.hrychanok.training.shop.model.Polarity;

public class PolarityChoiceRenderer extends ChoiceRenderer<Polarity> {

	public static final PolarityChoiceRenderer INSTANCE = new PolarityChoiceRenderer();

	private PolarityChoiceRenderer() {
		super();
	}

	@Override
	public Object getDisplayValue(Polarity object) {
		return object.name();
	}

	@Override
	public String getIdValue(Polarity object, int index) {
		return String.valueOf(object.ordinal());
	}
}