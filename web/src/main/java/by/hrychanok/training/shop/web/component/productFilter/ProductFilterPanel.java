package by.hrychanok.training.shop.web.component.productFilter;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.form.combobox.ComboBox;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class ProductFilterPanel extends Panel {

	private static final List<String> MANUFACTURER = Arrays.asList("Белшина", "Cordiant", "Goodyear", "Michelin", "Hankook");
	
	public ProductFilterPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		Form<Void> generalForm = new Form<Void>("generalForm");
		add(generalForm);
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		generalForm.add(feedback);
		
		//Manufacturer combo-box
		
		final ComboBox<String> combobox = new ComboBox<String>("combobox", new Model<String>(), MANUFACTURER);
		generalForm.add(combobox); 
		
		generalForm.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				ProductFilterPanel.this.info(combobox);
				target.add(feedback);
			}
		});
		
	}
	
	private void info(ComboBox<String> combobox)
	{
		String choice =  combobox.getModelObject();

		this.info(choice != null ? choice : "no choice");
	}
}
