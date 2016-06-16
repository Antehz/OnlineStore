package by.hrychanok.training.shop.web.page.cart;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import by.hrychanok.training.shop.model.CartContent;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.web.app.MySession;
import by.hrychanok.training.shop.web.component.login.LoginPanel;
import by.hrychanok.training.shop.web.page.AbstractPage;
import by.hrychanok.training.shop.web.page.product.ProductPage;

public class CartNotSignUser extends Panel {

	private List<CartContent> listContent = new ArrayList<CartContent>();
	private CartContent cartContent;

	public CartNotSignUser(ModalWindow modalWindow) {
		super(modalWindow.getContentId());
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onInitialize() {
		super.onInitialize();
		WebMarkupContainer wmc = new WebMarkupContainer("wmc");
		add(wmc);
		wmc.setOutputMarkupId(true);
		listContent = MySession.get().getCartList();
		Label totalPrice = new Label("totalPrice", Model.of(countTotal()));
		totalPrice.setOutputMarkupId(true);
		wmc.add(totalPrice);
		final DataView dataView = new DataView("simple", new ListDataProvider(listContent)) {
			public void populateItem(final Item item) {
				cartContent = (CartContent) item.getModelObject();
				Product product = cartContent.getProduct();
				Link productLink = new Link("productLink") {
					@Override
					public void onClick() {
						PageParameters parameters = new PageParameters();
						parameters.add("id", product.getId());
						setResponsePage(new ProductPage(parameters));
					}
				};

				ContextImage imageProduct = new ContextImage("itemImage", cartContent.getProduct().getImageURL());
				productLink.add(imageProduct);
				item.add(productLink);

				item.add(new Label("nameProduct", Model.of(product.getName())));
				item.add(new Label("manufacturerProduct", Model.of(product.getManufacturer())));
				item.add(new Label("modelProduct", Model.of(product.getModel())));
				item.add(new Label("amount", Model.of(cartContent.getAmount())));
				item.add(new Label("price", Model.of(cartContent.getPrice())));

				final Form<Void> form = new Form<Void>("form");
				item.add(form);

				// Buttons //
				form.add(new AjaxButton("deleteButton") {

					private static final long serialVersionUID = 1L;

					@Override
					protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
						listContent.remove(cartContent);
						totalPrice.setDefaultModelObject(countTotal());
						target.add(totalPrice);
						String deleteItem = getString("deleteItem");
						info(deleteItem);
						target.add(AbstractPage.feedback);
						target.add(wmc);

					}
				});
			}
		};

		dataView.setItemsPerPage(7);

		wmc.add(dataView);

		wmc.add(new PagingNavigator("navigator", dataView));
		ModalWindow modalWindow = new ModalWindow("modal");

		add(modalWindow);

		final Form<Void> formToLogin = new Form<Void>("formToLogin");
		add(formToLogin);

		// Buttons //
		formToLogin.add(new AjaxButton("toLogin") {

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
		});

	}

	private Integer countTotal() {
		Integer summ = 0;
		for (CartContent cartContent : listContent) {
			summ = summ + cartContent.getPrice();
		}
		return summ;
	}

}
