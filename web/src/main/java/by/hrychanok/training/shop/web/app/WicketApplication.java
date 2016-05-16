package by.hrychanok.training.shop.web.app;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import by.hrychanok.training.shop.web.page.home.HomePage;

@Component
public class WicketApplication extends WebApplication {
	//LOGGER...
    @Override
    public Class<? extends WebPage> getHomePage() {
        return HomePage.class;
    }
    @Override
    public void init() {
        super.init();
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfigForWeb.class);
        getComponentInstantiationListeners().add(new SpringComponentInjector(this, ctx));
        getMarkupSettings().setStripWicketTags(true);

        // add your configuration here
    }
}
