package by.hrychanok.training.shop.web.page.common;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import by.hrychanok.training.shop.model.FluidState;

public class FluidStateChoiceRenderer extends ChoiceRenderer<FluidState> {

	public static final FluidStateChoiceRenderer INSTANCE = new FluidStateChoiceRenderer();

	private FluidStateChoiceRenderer() {
		super();
	}

	@Override
	public Object getDisplayValue(FluidState object) {
		return object.name();
	}

	@Override
	public String getIdValue(FluidState object, int index) {
		return String.valueOf(object.ordinal());
	}
}