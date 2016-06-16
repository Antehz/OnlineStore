package by.hrychanok.training.shop.web.component.login;

import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.string.Strings;
import static by.hrychanok.training.shop.web.app.WicketApplication.REMEMBER_ME_DURATION_IN_DAYS;
import static by.hrychanok.training.shop.web.app.WicketApplication.REMEMBER_ME_LOGIN_COOKIE;
import static by.hrychanok.training.shop.web.app.WicketApplication.REMEMBER_ME_PASSWORD_COOKIE;
import com.googlecode.wicket.kendo.ui.form.button.IndicatingAjaxButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import by.hrychanok.training.shop.web.app.CookieService;
import by.hrychanok.training.shop.web.app.MySession;
import by.hrychanok.training.shop.web.app.WicketApplication;
import by.hrychanok.training.shop.web.page.cart.CartPage;

public class LoginPanel extends Panel {

	public LoginPanel(String id) {
		super(id);
	}

	public LoginPanel(ModalWindow modalWindow) {
		super(modalWindow.getContentId());
		getSession().bind();

	}

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
		form.setDefaultModel(new CompoundPropertyModel<LoginPanel>(this));
		form.add(new RequiredTextField<String>("username"));
		form.add(new PasswordTextField("password"));

		final IndicatingAjaxButton loginButton = new IndicatingAjaxButton("submit-btn") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (Strings.isEmpty(username) || Strings.isEmpty(password)) {
					return;
				}
				final boolean authResult = AuthenticatedWebSession.get().signIn(username, password);
				if (authResult) {
					CookieService cookieService = ((WicketApplication) WicketApplication.get()).getCookieService();
					cookieService.saveCookie(getResponse(), REMEMBER_ME_LOGIN_COOKIE, username,
							REMEMBER_ME_DURATION_IN_DAYS);
					cookieService.saveCookie(getResponse(), REMEMBER_ME_PASSWORD_COOKIE, password,
							REMEMBER_ME_DURATION_IN_DAYS);

					if (MySession.get().getCartList().isEmpty()) {
						setResponsePage(Application.get().getHomePage());
					} else {
						setResponsePage(new CartPage());

					}
				} else {
					 String errorAutorizationMessage = getString("errorAutorization");
					error(errorAutorizationMessage);
				}
				try {
					Thread.sleep(1300);
				} catch (InterruptedException e) {

				}
				target.add(form);
			}
		};
		form.add(loginButton);
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedbackpanel");
		form.add(feedback);

		Link createUserLink = new Link("createUserLink") {
			@Override
			public void onClick() {
				setResponsePage(RegistrationPage.class);

			}
		};
		form.add(createUserLink);
		add(form);

	}
}
