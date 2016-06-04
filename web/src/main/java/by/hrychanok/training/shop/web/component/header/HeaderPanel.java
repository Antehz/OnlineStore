package by.hrychanok.training.shop.web.component.header;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.panel.SignInPanel;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.googlecode.wicket.jquery.ui.JQueryIcon;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;

import by.hrychanok.training.shop.model.CartContent;
import by.hrychanok.training.shop.model.Customer;
import by.hrychanok.training.shop.model.CustomerCredentials;
import by.hrychanok.training.shop.model.UserRole;
import by.hrychanok.training.shop.service.CartService;
import by.hrychanok.training.shop.web.app.AuthorizedSession;
import by.hrychanok.training.shop.web.component.adminPanel.AdminPage;
import by.hrychanok.training.shop.web.component.login.LoginPage;
import by.hrychanok.training.shop.web.component.login.LoginPanel;
import by.hrychanok.training.shop.web.component.login.RegistrationPage;
import by.hrychanok.training.shop.web.page.cart.CartPage;
import by.hrychanok.training.shop.web.page.home.HomePage;
import by.hrychanok.training.shop.web.page.info.AboutUsPage;
import by.hrychanok.training.shop.web.page.info.ContactPage;
import by.hrychanok.training.shop.web.page.info.NewsPage;
import by.hrychanok.training.shop.web.page.info.PaymentPage;
import by.hrychanok.training.shop.web.page.product.ProductCombinePanel;

public class HeaderPanel extends Panel {

	@SpringBean
	CartService cartService;
	
	
	boolean visibleForOnlyAdmin = AuthorizedSession.get().isSignedIn()
			&& AuthorizedSession.get().getLoggedUser().getRole().equals(UserRole.admin);
	boolean visibleForUser = AuthorizedSession.get().isSignedIn();

	public HeaderPanel(String id) {
		super(id);
		Injector.get().inject(this); 

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		/*ModalWindow modalWindow = new ModalWindow("modal");
		add(modalWindow);*/
		
		final Form<Void> formLogin = new Form<Void>("formLogin");
		this.add(formLogin);

		AjaxButton loginButton = new AjaxButton("login") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				/*modalWindow.setContent(new LoginPanel(modalWindow));
				modalWindow.show(target);*/
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
		Link cartLink = new Link("toCart") {
			@Override
			public void onClick() {
				setResponsePage(new CartPage());
			}
		};
		cartLink.setVisible(visibleForUser);
		add(cartLink);
		
	
		
		Link adminLink = new Link("toAdminPage") {
			@Override
			public void onClick() {
				setResponsePage(new AdminPage());
			}
		};
		adminLink.setVisible(visibleForOnlyAdmin);
		add(adminLink);

	}
}
