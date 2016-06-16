package by.hrychanok.training.shop.web.page.personalCabinet;

import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.PatternValidator;

import com.googlecode.wicket.jquery.ui.form.button.ConfirmAjaxButton;
import com.googlecode.wicket.kendo.ui.form.Radio;
import com.googlecode.wicket.kendo.ui.form.TextField;
import com.googlecode.wicket.kendo.ui.form.Radio.Label;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

import by.hrychanok.training.shop.model.Customer;
import by.hrychanok.training.shop.model.CustomerCredentials;
import by.hrychanok.training.shop.model.Gender;
import by.hrychanok.training.shop.service.CustomerService;
import by.hrychanok.training.shop.web.page.AbstractPage;

public class CustomerProfile extends AbstractPage {

	private static final long serialVersionUID = 1L;
	private static final String USERNAME_PATTERN = "((?=.*[a-z])(?=.*[A-Z]).{4,15})";
	private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{7,20})";
	
	@SpringBean
	CustomerService customerService;

	private Customer customer;
	private CustomerCredentials customerCredentials;

	public CustomerProfile(PageParameters parameters) {
		super(parameters);
		Long customerId = parameters.get("id").toLong();
		customer=customerService.findOne(customerId);
		customerCredentials=customer.getCustomerCredentials();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		Form<CustomerCredentials> registrationCredentialsForm = new Form<CustomerCredentials>(
				"registrationCredetialsForm", new CompoundPropertyModel<CustomerCredentials>(customerCredentials));
		add(registrationCredentialsForm);
		
		KendoFeedbackPanel feedbackCredentials = new KendoFeedbackPanel("feedbackCredentials");
		registrationCredentialsForm.add(feedbackCredentials);

		TextField<String> login = new TextField<>("login");
		login.setOutputMarkupId(true);
		login.setRequired(true);
	    login.add(new PatternValidator(USERNAME_PATTERN)); 
		registrationCredentialsForm.add(login);

		PasswordTextField password = new PasswordTextField("password");
		password.setOutputMarkupId(true);
		password.setRequired(true);
		registrationCredentialsForm.add(password);
		password.add(new PatternValidator(PASSWORD_PATTERN));

		registrationCredentialsForm.add(new AjaxButton("checkCredentials") {

			private static final long serialVersionUID = 1L;
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				
				if (customerService.loginIsAvailable(login.getDefaultModelObjectAsString()))
				{
					String loginAvailable = getString("loginAvailable");
			     	info(loginAvailable);
					target.add(feedbackCredentials);
				} else {
					login.setDefaultModel(Model.of(""));
					target.add(login);
					password.setDefaultModel(Model.of(""));
					target.add(password);
					String loginNotAvailable = getString("loginNotAvailable");
					error(loginNotAvailable);
					target.add(feedbackCredentials);
				}
			}
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackCredentials);
				super.onError(target, form);
			}
		});

		Form<Customer> ÒustomerForm = new Form<Customer>("registrationCustomerForm",
				new CompoundPropertyModel<Customer>(customer));
		add(ÒustomerForm);

		TextField<String> firstName = new TextField<>("firstName");
		firstName.setRequired(true);
		ÒustomerForm.add(firstName);

		TextField<String> lastName = new TextField<>("lastName");
		lastName.setRequired(true);
		ÒustomerForm.add(lastName);

		EmailTextField email = new EmailTextField("email");
		email.setOutputMarkupId(true);
		email.setRequired(true);
		ÒustomerForm.add(email);

		DateTextField birth = new DateTextField("dateBirth");
		birth.add(new DatePicker());
		birth.setRequired(true);
		ÒustomerForm.add(birth);

		final IModel<String> radioModel = new Model<String>(customer.getGender().toString());

		final RadioGroup<String> group = new RadioGroup<String>("radiogroup", radioModel);
		ÒustomerForm.add(group);

		Radio<String> radio1 = new Radio<String>("radio1", Model.of("FEMALE"), group);
		String female = getString("female");
		Label label1 = new Label("label1",female, radio1);
		group.add(radio1, label1);
		String male = getString("male");
		Radio<String> radio2 = new Radio<String>("radio2", Model.of("MALE"), group);
		Label label2 = new Label("label2", male, radio2);
		group.add(radio2, label2);

		TextField<String> country = new TextField<>("country", Model.of("Belarus"));
		country.setEnabled(false);
		ÒustomerForm.add(country);

		TextField<String> city = new TextField<>("city");
		city.setRequired(true);
		ÒustomerForm.add(city);

		TextField<String> address = new TextField<>("address");
		address.setRequired(true);
		ÒustomerForm.add(address);

		TextField<String> zipCode = new TextField<>("zipCode");
		zipCode.setRequired(true);
		ÒustomerForm.add(zipCode);

		KendoFeedbackPanel feedbackCustomer = new KendoFeedbackPanel("feedbackCustomer");
		ÒustomerForm.add(feedbackCustomer);
		
		ÒustomerForm
				.add(new ConfirmAjaxButton("save", "»ÁÏÂÌËÚ¸ ‰‡ÌÌ˚Â", "œÓÊ‡ÎÛÈÒÚ‡ ÔÓ‰Ú‚Â‰ËÚÂ", "¬˚ ÔÓ‰Ú‚ÂÊ‰‡ÂÚÂ ËÁÏÂÌÂÌËÂ ‰‡ÌÌ˚ı?") {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;
					@Override
					protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
						
						setRadioGender(radioModel);
						customer.setEmail(email.getDefaultModelObjectAsString());
						customer.setCustomerCredentials(customerCredentials);
						customerService.save(customer);
						String update = getString("update");
						success(update);
						target.add(feedbackCustomer);
						
					}
					@Override
					protected void onError(AjaxRequestTarget target, Form<?> form) {
						String notUpdate = getString("notUpdate");
						error(notUpdate);
						target.add(feedbackCustomer);
					}
				});
	}

	private void setRadioGender(IModel<String> model) {
		if (model.getObject().equals("FEMALE")) {
			customer.setGender(Gender.FEMALE);
		} else {
			customer.setGender(Gender.MALE);
		}
	}
}
