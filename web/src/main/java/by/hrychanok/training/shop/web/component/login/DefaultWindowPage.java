package by.hrychanok.training.shop.web.component.login;

import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.string.Strings;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.form.button.IndicatingAjaxButton;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import com.googlecode.wicket.kendo.ui.widget.window.AbstractWindow;

import by.hrychanok.training.shop.web.page.AbstractPage;
import by.hrychanok.training.shop.web.page.home.HomePage;

public class DefaultWindowPage extends AbstractPage {
	private static final long serialVersionUID = 1L;

	public DefaultWindowPage() {
		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		this.add(feedback);

		// Form //
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// Window //
		final MyWindow window = new MyWindow("window", "My Window") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onOpen(IPartialPageRequestHandler handler) {
				super.onOpen(handler);

				handler.add(feedback); // clear previous messages
			}

			@Override
			public void onClose(IPartialPageRequestHandler handler) {
				this.info("Window has been closed");
				handler.add(feedback);
			}
		};

		this.add(window); // attached to the page

		// Buttons //
		form.add(new AjaxButton("open") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				window.open(target);
			}
		});
	}

	/**
	 * This window class is located here for convenience in this sample<br/>
	 * Associated markup file is ActionWindowPage$MyWindow.html
	 */
	abstract static class MyWindow extends AbstractWindow<Void> {
		private static final long serialVersionUID = 1L;
		private String username;
	    private String password;
		private KendoFeedbackPanel feedback;

		public MyWindow(String id, String title) {
			super(id, title, true);

			
		}

		@Override
		public void onConfigure(JQueryBehavior behavior) {
			super.onConfigure(behavior);

			behavior.setOption("actions", "['Custom', 'Pin', 'Maximize', 'Minimize', 'Close']");
		}

		@Override
		protected void onOpen(IPartialPageRequestHandler handler) {
			handler.add(this.feedback); // clear previous messages
		}

		@Override
		public void onAction(AjaxRequestTarget target, String action) {
			this.info("Clicked " + action);

			if (ACTION_CUSTOM.equals(action)) {
				this.info("Performing custom action...");
			}

			target.add(this.feedback);
		}

		@Override
		public boolean isCloseEventEnabled() {
			return true;
		}

		@Override
		public boolean isActionEventEnabled() {
			return true;
		}
		
		@Override
		protected void onInitialize() {
			// TODO Auto-generated method stub
			super.onInitialize();
			
			final Form<Void> formlogin = new Form<Void>("formlogin");
			formlogin.add(new RequiredTextField<String>("username"));
			formlogin.add(new PasswordTextField("password"));

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
			formlogin.add(loginButton);
			add(formlogin);

		}
	}
}