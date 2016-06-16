package by.hrychanok.training.shop.web.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import by.hrychanok.training.shop.model.CartContent;
import by.hrychanok.training.shop.model.CustomerCredentials;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.service.CustomerService;
import by.hrychanok.training.shop.web.component.localization.LanguageSelectionComponent;

public class MySession extends AuthorizedSession {

	@SpringBean
	private CustomerService userService;

	private CustomerCredentials loggedUser;

	public void setLoggedUser(CustomerCredentials loggedUser) {
		this.loggedUser = loggedUser;
	}

	private Roles roles;

	private static final long serialVersionUID = 1L;

	public MySession(Request request) {
		super(request);
		Injector.get().inject(this);

	}

	public List<CartContent> getCartList() {
		return cartList;
	}

	public void setCartList(List<CartContent> cartList) {
		this.cartList = cartList;
	}

	public boolean addToCart(Product product, Integer amount) {
		CartContent cartContent = new CartContent();
		cartContent.setAmount(amount);
		cartContent.setProduct(product);
		cartContent.setPrice(product.getPrice());
		for (CartContent temp : cartList) {
			if (temp.getProduct().equals(product)) {
				if (temp.getAmount() + amount > product.getAvailable()) {
					return false;
				} else {
					temp.setAmount(temp.getAmount() + amount);
					temp.setPrice(temp.getAmount() * temp.getProduct().getPrice());
					return true;
				}
			}
		}
		cartList.add(cartContent);
		return true;
	}

	private List<CartContent> cartList = new ArrayList<CartContent>();

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

	public static MySession get() {
		return (MySession) Session.get();
	}
	
	  @Override
	    public Locale getLocale() {
	        Locale locale = super.getLocale();
	        if (locale == null || !LanguageSelectionComponent.SUPPORTED_LOCALES.contains(locale)) {
	            setLocale(LanguageSelectionComponent.SUPPORTED_LOCALES.get(0));
	        }
	        return super.getLocale();
	    }
}
