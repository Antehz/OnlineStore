package by.hrychanok.training.shop.web.page.cart;

import javax.persistence.PersistenceException;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.googlecode.wicket.jquery.ui.form.spinner.AjaxSpinner;
import com.googlecode.wicket.jquery.ui.form.spinner.Spinner;

import by.hrychanok.training.shop.model.CartContent;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.service.CartService;
import by.hrychanok.training.shop.web.page.AbstractPage;
import by.hrychanok.training.shop.web.page.BasePageForTable;
import by.hrychanok.training.shop.web.page.product.ProductPage;

public class CartPage extends BasePageForTable {

	@SpringBean
	CartService cartService;
	private static final long serialVersionUID = 1L;

	public CartPage() {
		super();

		SortableCartDataProvider dp = new SortableCartDataProvider();
		
		final DataView<CartContent> dataView = new DataView<CartContent>("cartTable", dp) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(Item<CartContent> item) {
				CartContent cartContent = item.getModelObject();
                Product product = cartContent.getProduct();
				Link linkProductId = new Link<Void>("linkProduct") {
					@Override
					public void onClick() {
						setResponsePage(new ProductPage(cartContent.getProduct().getId()));
					}
				};
				linkProductId.setBody(Model.of(cartContent.getProduct().getId()));

				item.add(linkProductId);

				String nameProduct = cartContent.getProduct().getName() + " "
						+ cartContent.getProduct().getManufacturer() + " " + cartContent.getProduct().getModel();

				item.add(new Label("name", nameProduct));

				item.add(DateLabel.forDatePattern("added", Model.of(cartContent.getDateAdd()), "dd-MM-yyyy"));

				item.add(new Label("pricePiece", cartContent.getProduct().getPrice()));

				final Form<Integer> form = new Form<Integer>("form", Model.of(cartContent.getAmount()));
				// Spinner //
				final AjaxSpinner<Integer> spinner = new AjaxSpinner<Integer>("spinner", form.getModel(), Integer.class) {

					private static final long serialVersionUID = 1L;

					@Override
					public void onSpin(AjaxRequestTarget target, Integer value)
					{
						cartContent.setAmount(value);
						cartService.save(cartContent);
						
					}
					
					
				};
				spinner.setMin(1);
				spinner.setMax(product.getAvailable());
				form.add(spinner);
				item.add(form);
				
				
				
				item.add(new Label("priceTotal", cartContent.getPrice()));

				item.add(new Link<Void>("delete-link") {
					@Override
					public void onClick() {
						try {
							cartService.deleteProductFromCart(cartContent.getId());
						} catch (PersistenceException e) {
							System.out.println("caughth persistance exception");
						}
						setResponsePage(new CartPage());
					}
				});
				
				item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
					private static final long serialVersionUID = 1L;

					@Override
					public String getObject() {
						return (item.getIndex() % 2 == 1) ? "even" : "odd";
					}
				}));
			
			};
		};
		
		dataView.setItemsPerPage(7L);
		dataView.setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());
		
		add(new OrderByBorder("orderByPriceTotal", "price", dp) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});
		add(dataView);
		add(new PagingNavigator("navigator", dataView));
	}
}