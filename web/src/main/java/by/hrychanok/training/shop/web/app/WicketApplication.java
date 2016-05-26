package by.hrychanok.training.shop.web.app;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.IPackageResourceGuard;
import org.apache.wicket.markup.html.SecurePackageResourceGuard;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import by.hrychanok.training.shop.web.page.home.HomePage;
import de.agilecoders.wicket.core.Bootstrap;

@Component("wicketWebApplicationBean")
public class WicketApplication extends WebApplication  {
	// LOGGER...
	
	AnnotationConfigApplicationContext ctx ;
	
      @Override
	       public Class<? extends WebPage> getHomePage() {
	           return HomePage.class;
	       }
	   

	@Override
	public void init() {
		super.init();
		Bootstrap.install(this);
		 ctx = new AnnotationConfigApplicationContext(SpringConfigForWeb.class);
		getComponentInstantiationListeners().add(new SpringComponentInjector(this, ctx));

		/*IPackageResourceGuard packageResourceGuard = this.getResourceSettings().getPackageResourceGuard();
		if (packageResourceGuard instanceof SecurePackageResourceGuard) {
			SecurePackageResourceGuard guard = (SecurePackageResourceGuard) packageResourceGuard;
			// Allow to access only to pdf files placed in the �public�
			// directory.
			guard.addPattern("+*.jpg");
		}*/
	}
}
