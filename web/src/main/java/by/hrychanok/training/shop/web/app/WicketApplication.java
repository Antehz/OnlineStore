package by.hrychanok.training.shop.web.app;

import javax.inject.Inject;
import javax.servlet.http.Cookie;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AnnotationsRoleAuthorizationStrategy;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import by.hrychanok.training.shop.model.CustomerCredentials;
import by.hrychanok.training.shop.service.CustomerService;
import by.hrychanok.training.shop.web.component.login.LoginPage;
import by.hrychanok.training.shop.web.page.home.HomePage;
import de.agilecoders.wicket.core.Bootstrap;

@Component
public class WicketApplication extends AuthenticatedWebApplication {
	
	public static final int REMEMBER_ME_DURATION_IN_DAYS = 15;
	public static final String REMEMBER_ME_LOGIN_COOKIE = "loginCookie";
	public static final String REMEMBER_ME_PASSWORD_COOKIE = "passwordCookie";
	
	private CustomerCredentials loggedUser;

	public void setLoggedUser(CustomerCredentials loggedUser) {
		this.loggedUser = loggedUser;
	}

	@Inject
	private CustomerService userService;

	private CookieService cookieService = new CookieService();

	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return AuthorizedSession.class;
	}

	public CookieService getCookieService() {
		return cookieService;
	}

	@Override
	public final Session newSession(Request request, Response response) {
		MySession mySession = new MySession(request);
		Cookie loginCookie = cookieService.loadCookie(request, REMEMBER_ME_LOGIN_COOKIE);
		Cookie passwordCookie = cookieService.loadCookie(request, REMEMBER_ME_PASSWORD_COOKIE);
		if (loginCookie != null && passwordCookie != null) {
			try {
				loggedUser = userService.getCustomerByCredentials(loginCookie.getValue(), passwordCookie.getValue());
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		if (loggedUser != null) {
			mySession.signIn(loginCookie.getValue(), passwordCookie.getValue());
			mySession.setLoggedUser(loggedUser);
			mySession.info("You were automatically logged in.");
		}
		return mySession;

	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return LoginPage.class;
	}

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage() {
		return HomePage.class;
	}

	@Override
	public void init() {
		super.init();
		Bootstrap.install(this);
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfigForWeb.class);
		getComponentInstantiationListeners().add(new SpringComponentInjector(this, ctx));
		getMarkupSettings().setStripWicketTags(true);
		getSecuritySettings().setAuthorizationStrategy(new AnnotationsRoleAuthorizationStrategy(this));
		Injector.get().inject(this);
	}
}
