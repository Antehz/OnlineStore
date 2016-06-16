package by.hrychanok.training.shop.web.page.common;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import by.hrychanok.training.shop.model.Season;

public class SeasonChoiceRenderer extends ChoiceRenderer<Season> {

	public static final SeasonChoiceRenderer INSTANCE = new SeasonChoiceRenderer();

	private SeasonChoiceRenderer() {
		super();
	}

	@Override
	public Object getDisplayValue(Season object) {
		return object.name();
	}

	@Override
	public String getIdValue(Season object, int index) {
		return String.valueOf(object.ordinal());
	}
}