package by.hrychanok.training.shop.web.page.catalog;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import by.hrychanok.training.shop.web.page.AbstractPage;
import by.hrychanok.training.shop.web.page.personalCabinet.OrderHistoryPage;

public class CatalogPage extends AbstractPage {

	public CatalogPage() {
		super();
	}

	public CatalogPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new CatalogPanel("catalogPanel"));
	}
}
