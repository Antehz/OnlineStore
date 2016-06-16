package by.hrychanok.training.shop.web.component.localization;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import by.hrychanok.training.shop.web.page.common.LocaleChoiceRenderer;

public class LanguageSelectionComponent extends Panel {
    public static final List<Locale> SUPPORTED_LOCALES = new ArrayList<>();

    static {
        SUPPORTED_LOCALES.add(new Locale("en", "US"));
        SUPPORTED_LOCALES.add(new Locale("ru", "RU"));
    }

    public LanguageSelectionComponent(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Locale locale = Session.get().getLocale();

        DropDownChoice<Locale> dropDownChoice = new DropDownChoice<Locale>("language", Model.of(locale), SUPPORTED_LOCALES, LocaleChoiceRenderer.INSTANCE);
        add(dropDownChoice);

        dropDownChoice.add(new AjaxFormComponentUpdatingBehavior("change") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                Locale selectedLocale = dropDownChoice.getModelObject();
                Session.get().setLocale(selectedLocale);
                setResponsePage(getPage().getClass());
            }
        });

    }
}