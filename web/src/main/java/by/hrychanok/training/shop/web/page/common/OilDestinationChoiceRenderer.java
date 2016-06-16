package by.hrychanok.training.shop.web.page.common;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import by.hrychanok.training.shop.model.OilDestination;

public class OilDestinationChoiceRenderer extends ChoiceRenderer<OilDestination> {

	public static final OilDestinationChoiceRenderer INSTANCE = new OilDestinationChoiceRenderer();

	private OilDestinationChoiceRenderer() {
		super();
	}

	@Override
	public Object getDisplayValue(OilDestination object) {
		return object.name();
	}

	@Override
	public String getIdValue(OilDestination object, int index) {
		return String.valueOf(object.ordinal());
	}
}