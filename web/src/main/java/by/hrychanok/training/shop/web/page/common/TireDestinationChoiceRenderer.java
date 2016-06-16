package by.hrychanok.training.shop.web.page.common;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import by.hrychanok.training.shop.model.TireDestination;

public class TireDestinationChoiceRenderer extends ChoiceRenderer<TireDestination> {

    public static final TireDestinationChoiceRenderer INSTANCE = new TireDestinationChoiceRenderer();

    private TireDestinationChoiceRenderer() {
        super();
    }

    @Override
    public Object getDisplayValue(TireDestination object) {
        return object.name();
    }

    @Override
    public String getIdValue(TireDestination object, int index) {
        return String.valueOf(object.ordinal());
    }
}