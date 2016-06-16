package by.hrychanok.training.shop.web.app;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;

import by.hrychanok.training.shop.model.CustomerCredentials;
import by.hrychanok.training.shop.service.CustomerService;

public class AuthorizedSession extends AuthenticatedWebSession {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SpringBean
	private CustomerService userService;

	private CustomerCredentials loggedUser;

	private Roles roles;

	public AuthorizedSession(Request request) {
		super(request);
		Injector.get().inject(this);
	}

	public static AuthorizedSession get() {
		return (AuthorizedSession) Session.get();
	}

	@Override
	public boolean authenticate(final String userName, final String password) {
		try {
			loggedUser = userService.getCustomerByCredentials(userName, password);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return loggedUser != null;
	}

	@Override
	public Roles getRoles() {
		if (isSignedIn() && (roles == null)) {
			roles = new Roles();
			roles.addAll(userService.resolveRoles(loggedUser.getId()));
		}
		return roles;
	}

	@Override
	public void signOut() {
		super.signOut();
		loggedUser = null;
		roles = null;
	}

	public CustomerCredentials getLoggedUser() {
		return loggedUser;
	}
}
