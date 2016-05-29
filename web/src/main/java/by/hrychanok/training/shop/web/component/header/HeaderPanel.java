package by.hrychanok.training.shop.web.component.header;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.JQueryIcon;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;

import by.hrychanok.training.shop.model.Customer;
import by.hrychanok.training.shop.model.CustomerCredentials;
import by.hrychanok.training.shop.web.component.login.LoginPage;
import by.hrychanok.training.shop.web.component.login.RegistrationPage;
import by.hrychanok.training.shop.web.page.cart.CartPage;
import by.hrychanok.training.shop.web.page.home.HomePage;
import by.hrychanok.training.shop.web.page.info.AboutUsPage;
import by.hrychanok.training.shop.web.page.info.ContactPage;
import by.hrychanok.training.shop.web.page.info.NewsPage;
import by.hrychanok.training.shop.web.page.info.PaymentPage;

public class HeaderPanel extends Panel {

	public HeaderPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		final Form<Void> formLogin = new Form<Void>("formLogin");
		this.add(formLogin);

		AjaxButton loginButton = new AjaxButton("login") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				setResponsePage(LoginPage.class);
			}
		};

		formLogin.add(loginButton);
		AjaxButton createButton = new AjaxButton("create") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				setResponsePage(RegistrationPage.class);
			}
		};
		formLogin.add(createButton);

		formLogin.setVisible(!AuthenticatedWebSession.get().isSignedIn());

		add(new Link("toHome") {
			@Override
			public void onClick() {
				setResponsePage(new HomePage());
			}
		});
		add(new Link("toHomeForLogo") {
			@Override
			public void onClick() {
				setResponsePage(new HomePage());
			}
		});
		add(new Link("toAboutUs") {
			@Override
			public void onClick() {
				setResponsePage(new AboutUsPage());
			}
		});
		add(new Link("toNews") {
			@Override
			public void onClick() {
				setResponsePage(new NewsPage());
			}
		});
		add(new Link("toShipment") {
			@Override
			public void onClick() {
				setResponsePage(new NewsPage());
			}
		});
		add(new Link("toPayment") {
			@Override
			public void onClick() {
				setResponsePage(new PaymentPage());
			}
		});
		add(new Link("toContact") {
			@Override
			public void onClick() {
				setResponsePage(new ContactPage());
			}
		});
		add(new Link("toCart") {
			@Override
			public void onClick() {
				setResponsePage(new CartPage());
			}
		});

	}
}
