package by.hrychanok.training.shop.web.component.login;

import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.string.Strings;

import org.apache.wicket.markup.html.form.Button;
import com.googlecode.wicket.jquery.ui.form.button.IndicatingAjaxButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class LoginPage extends WebPage {

	public static final String ID_FORM = "form";

	private String username;
	private String password;

	@Override
	protected void onInitialize() {
		super.onInitialize();

		// if already logged then should not see login page at all
		if (AuthenticatedWebSession.get().isSignedIn()) {
			setResponsePage(Application.get().getHomePage());
		}

		final Form<Void> form = new Form<Void>(ID_FORM);
		form.setDefaultModel(new CompoundPropertyModel<LoginPage>(this));
		form.add(new RequiredTextField<String>("username"));
		form.add(new PasswordTextField("password"));

		final Button loginButton = new IndicatingAjaxButton("submit-btn") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (Strings.isEmpty(username) || Strings.isEmpty(password)) {
					return;
				}
				final boolean authResult = AuthenticatedWebSession.get().signIn(username, password);
				if (authResult) {
					// continueToOriginalDestination();
					setResponsePage(Application.get().getHomePage());
					success("¬ы успешно зашли в систему");
				} else {
					error("¬ведены неверные данные, или пользователь не существует.");
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {

				}
				target.add(form);
			}
		};
		form.add(loginButton);
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedbackpanel");
		form.add(feedback);
		add(form);

	}
}
