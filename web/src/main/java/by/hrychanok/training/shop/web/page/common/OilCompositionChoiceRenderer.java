package by.hrychanok.training.shop.web.page.common;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import by.hrychanok.training.shop.model.OilComposition;

public class OilCompositionChoiceRenderer extends ChoiceRenderer< OilComposition> {

	public static final OilCompositionChoiceRenderer INSTANCE = new OilCompositionChoiceRenderer();

	private OilCompositionChoiceRenderer() {
		super();
	}

	@Override
	public Object getDisplayValue( OilComposition object) {
		return object.name();
	}

	@Override
	public String getIdValue( OilComposition object, int index) {
		return String.valueOf(object.ordinal());
	}
}