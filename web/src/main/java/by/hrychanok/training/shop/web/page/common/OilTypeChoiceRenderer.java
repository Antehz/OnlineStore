package by.hrychanok.training.shop.web.page.common;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import by.hrychanok.training.shop.model.OilType;

public class OilTypeChoiceRenderer extends ChoiceRenderer<OilType> {

	public static final OilTypeChoiceRenderer INSTANCE = new OilTypeChoiceRenderer();

	private OilTypeChoiceRenderer() {
		super();
	}

	@Override
	public Object getDisplayValue(OilType object) {
		return object.name();
	}

	@Override
	public String getIdValue(OilType object, int index) {
		return String.valueOf(object.ordinal());
	}
}