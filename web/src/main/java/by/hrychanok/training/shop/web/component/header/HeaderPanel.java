package by.hrychanok.training.shop.web.component.header;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import by.hrychanok.training.shop.model.CartContent;
import by.hrychanok.training.shop.model.UserRole;
import by.hrychanok.training.shop.repository.filter.Filter;
import by.hrychanok.training.shop.service.CartService;
import by.hrychanok.training.shop.web.app.AuthorizedSession;
import by.hrychanok.training.shop.web.app.MySession;
import by.hrychanok.training.shop.web.component.adminPanel.CustomerManagerPage;
import by.hrychanok.training.shop.web.component.login.LoginPanel;
import by.hrychanok.training.shop.web.page.cart.CartNotSignUser;
import by.hrychanok.training.shop.web.page.cart.CartPage;
import by.hrychanok.training.shop.web.page.home.HomePage;
import by.hrychanok.training.shop.web.page.info.AboutUsPage;
import by.hrychanok.training.shop.web.page.info.ContactPage;
import by.hrychanok.training.shop.web.page.info.NewsPage;
import by.hrychanok.training.shop.web.page.info.PaymentPage;
import by.hrychanok.training.shop.web.page.info.ShipmentPage;

public class HeaderPanel extends Panel {

	@SpringBean
	CartService cartService;

	private Filter filter = new Filter();

	boolean visibleForOnlyAdmin = AuthorizedSession.get().isSignedIn()
			&& AuthorizedSession.get().getLoggedUser().getRole().equals(UserRole.admin);

	boolean visibleForUser = AuthorizedSession.get().isSignedIn();

	public HeaderPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		ModalWindow modalWindow = new ModalWindow("modal");
		add(modalWindow);

		final Form<Void> formLogin = new Form<Void>("formLogin");
		this.add(formLogin);

		AjaxButton loginButton = new AjaxButton("login") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				modalWindow.setInitialWidth(465);
				modalWindow.setInitialHeight(320);
				modalWindow.setAutoSize(false);
				modalWindow.setResizable(false);
				modalWindow.setContent(new LoginPanel(modalWindow));
				modalWindow.show(target);

			}
		};

		formLogin.add(loginButton);

		formLogin.setVisible(!AuthenticatedWebSession.get().isSignedIn());

		add(new Link("toHome") {
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
				setResponsePage(new ShipmentPage());
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

		Link adminLink = new Link("toCustomerManagerPage") {
			@Override
			public void onClick() {
				setResponsePage(new CustomerManagerPage());
			}
		};
		adminLink.setVisible(visibleForOnlyAdmin);
		add(adminLink);

		final Form<Void> formFinderAndCart = new Form<Void>("formFinderAndCart");
		this.add(formFinderAndCart);

		AjaxButton cartButton = new AjaxButton("toCart") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (visibleForUser) {
					setResponsePage(new CartPage());
				} else {
					modalWindow.setInitialHeight(400);
					modalWindow.setAutoSize(true);
					modalWindow.setContent(new CartNotSignUser(modalWindow));
					modalWindow.show(target);
				}
			}
		};
		ContextImage imageFullCart = new ContextImage("cartImage", "images/cart-red.png");
		ContextImage imageEmptyCart = new ContextImage("cartImage", "images/cart-black.png");
		formFinderAndCart.add(cartButton);

		if (visibleForUser) {
			Long customerId = AuthorizedSession.get().getLoggedUser().getId();
			List<CartContent> cartContentList = cartService.getCustomerCartContent(customerId);
			int countItemIntoCart = cartContentList.size();
			if (countItemIntoCart > 0) {
				cartButton.add(imageFullCart);
			} else {
				cartButton.add(imageEmptyCart);
			}
		} else if (!MySession.get().getCartList().isEmpty()) {
			cartButton.add(imageFullCart);
		} else {
			cartButton.add(imageEmptyCart);
		}
	}
}
