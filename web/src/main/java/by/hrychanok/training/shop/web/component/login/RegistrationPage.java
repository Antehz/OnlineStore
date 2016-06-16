package by.hrychanok.training.shop.web.component.login;

import java.util.Date;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.PatternValidator;
import com.googlecode.wicket.kendo.ui.form.Radio;
import com.googlecode.wicket.kendo.ui.form.Radio.Label;
import com.googlecode.wicket.kendo.ui.form.TextField;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import com.googlecode.wicket.jquery.ui.form.button.ConfirmAjaxButton;
import by.hrychanok.training.shop.model.Customer;
import by.hrychanok.training.shop.model.CustomerCredentials;
import by.hrychanok.training.shop.model.Gender;
import by.hrychanok.training.shop.model.UserRole;
import by.hrychanok.training.shop.service.CustomerService;
import by.hrychanok.training.shop.service.exeption.ServiceException;
import by.hrychanok.training.shop.web.page.AbstractPage;

public class RegistrationPage extends AbstractPage {

	private static final long serialVersionUID = 1L;
	private static final String USERNAME_PATTERN = "((?=.*[a-z])(?=.*[A-Z]).{4,15})";
	private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{7,20})";

	@SpringBean
	CustomerService customerService;

	private Customer customer;
	private CustomerCredentials customerCredentials;

	public RegistrationPage() {
		super();
		customer = new Customer();
		customerCredentials = new CustomerCredentials();
	}

	public RegistrationPage(PageParameters parameters) {
		super(parameters);
		Long customerId = parameters.get("id").toLong();
		customer = customerService.findOne(customerId);
		customerCredentials = customer.getCustomerCredentials();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		if (AuthenticatedWebSession.get().isSignedIn()) {
			setResponsePage(Application.get().getHomePage());
		}
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

		PasswordTextField confirmPasswordTextField = new PasswordTextField("confirmPassword", new Model<String>());
		password.setOutputMarkupId(true);
		password.setRequired(true);
		registrationCredentialsForm.add(confirmPasswordTextField);
		registrationCredentialsForm.add(new EqualPasswordInputValidator(password, confirmPasswordTextField) {
			protected String resourceKey() {
				return "user.errorConfirmPassNeeded";
			}
		});

		password.add(new PatternValidator(PASSWORD_PATTERN));

		registrationCredentialsForm.add(new AjaxButton("checkCredentials") {

			private static final long serialVersionUID = 1L;

			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

				if (customerService.loginIsAvailable(login.getDefaultModelObjectAsString())) {
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

		Form<Customer> registrationCustomerForm = new Form<Customer>("registrationCustomerForm",
				new CompoundPropertyModel<Customer>(customer));
		add(registrationCustomerForm);

		TextField<String> firstName = new TextField<>("firstName");
		firstName.setRequired(true);
		registrationCustomerForm.add(firstName);

		TextField<String> lastName = new TextField<>("lastName");
		lastName.setRequired(true);
		registrationCustomerForm.add(lastName);

		EmailTextField email = new EmailTextField("email");
		email.setOutputMarkupId(true);
		email.setRequired(true);
		registrationCustomerForm.add(email);

		DateTextField birth = new DateTextField("dateBirth");
		birth.add(new DatePicker());
		birth.setRequired(true);
		registrationCustomerForm.add(birth);

		final IModel<String> radioModel = new Model<String>();

		final RadioGroup<String> group = new RadioGroup<String>("radiogroup", radioModel);
		registrationCustomerForm.add(group);

		Radio<String> radio1 = new Radio<String>("radio1", Model.of("FEMALE"), group);
		String female = getString("female");
		Label label1 = new Label("label1", female, radio1);
		group.add(radio1, label1);
		String male = getString("male");
		Radio<String> radio2 = new Radio<String>("radio2", Model.of("MALE"), group);
		Label label2 = new Label("label2", male, radio2);
		group.add(radio2, label2);

		TextField<String> country = new TextField<>("country", Model.of("Belarus"));
		country.setEnabled(false);
		registrationCustomerForm.add(country);

		TextField<String> city = new TextField<>("city");
		city.setRequired(true);
		registrationCustomerForm.add(city);

		TextField<String> address = new TextField<>("address");
		address.setRequired(true);
		registrationCustomerForm.add(address);

		TextField<String> zipCode = new TextField<>("zipCode");
		zipCode.setRequired(true);
		registrationCustomerForm.add(zipCode);

		KendoFeedbackPanel feedbackCustomer = new KendoFeedbackPanel("feedbackCustomer");
		registrationCustomerForm.add(feedbackCustomer);
		String submitRegistration = getString("submitRegistration");

		registrationCustomerForm.add(new ConfirmAjaxButton("save", submitRegistration, "Please confirm",
				"Do you confirm the entered value?") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

				customer.setCountry("Belarus");
				customerCredentials.setRole(UserRole.customer);
				setRadioGender(radioModel);
				customer.setCreated(new Date());
				customer.setEmail(email.getDefaultModelObjectAsString());
				try {
					customerService.registerCustomer(customer, customerCredentials);
					String successReg = getString("successReg");
					success(successReg);
					target.add(feedbackCustomer);
				} catch (ServiceException e) {
					onError(target, registrationCustomerForm);
				}

			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				email.setDefaultModel(Model.of(""));
				target.add(email);
				String emailExist = getString("emailExist");
				error(emailExist);
				target.add(feedbackCustomer);
				form.clearInput();

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
