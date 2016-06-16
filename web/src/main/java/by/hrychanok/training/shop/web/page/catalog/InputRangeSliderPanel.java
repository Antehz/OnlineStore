package by.hrychanok.training.shop.web.page.catalog;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.RangeValidator;

import com.googlecode.wicket.jquery.ui.form.slider.RangeSlider;
import com.googlecode.wicket.jquery.ui.form.slider.RangeValue;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;

import by.hrychanok.training.shop.repository.filter.Comparison;
import by.hrychanok.training.shop.repository.filter.Condition;
import by.hrychanok.training.shop.repository.filter.Filter;

public class InputRangeSliderPanel extends Panel {
	private Filter filterState;
	private Condition condition;
	Integer currentLower=100000;
	Integer currentUpper=2000000;
	public InputRangeSliderPanel(String id) {
		super(id);
	}

	public InputRangeSliderPanel(ModalWindow modalWindow, Filter filterState) {
		super(modalWindow.getContentId());
		this.filterState = filterState;
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void onInitialize() {
		
		super.onInitialize();
		
		condition = filterState.getConditionByField("price");
		if (condition != null) {
			 currentLower = (Integer) condition.value;
			 currentUpper = (Integer) condition.limitValue;
		}
		final Form<RangeValue> form = new Form<RangeValue>("form", Model.of(new RangeValue(currentLower, currentUpper)));
		this.add(form);

		// FeedbackPanel //
		form.add(new JQueryFeedbackPanel("feedback"));

		// Sliders //
		TextField<Integer> lower = new TextField<Integer>("lower",
				new PropertyModel<Integer>(form.getModelObject(), "lower"), Integer.class);
		form.add(lower);

		TextField<Integer> upper = new TextField<Integer>("upper",
				new PropertyModel<Integer>(form.getModelObject(), "upper"), Integer.class);
		form.add(upper);

		RangeSlider slider = new RangeSlider("slider", form.getModel(), lower, upper);
		slider.setMin(0);
		slider.setMax(5000000);
		slider.setRangeValidator(new RangeValidator<Integer>(0, 5000000));
		form.add(slider);

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(form);
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				filterState.removeCondition("price");
				RangeValue value = (RangeValue) form.getModelObject();
				condition = new Condition.Builder().setComparison(Comparison.between).setField("price")
						.setValue(value.getLower()).setLimitValue(value.getUpper()).build();
				filterState.addCondition(condition);
			
			}
		});
	}
}
