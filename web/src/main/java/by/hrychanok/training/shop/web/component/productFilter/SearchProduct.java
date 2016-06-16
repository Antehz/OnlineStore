package by.hrychanok.training.shop.web.component.productFilter;

import java.util.Arrays;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import com.googlecode.wicket.jquery.core.template.IJQueryTemplate;
import com.googlecode.wicket.jquery.core.utils.ListUtils;
import com.googlecode.wicket.kendo.ui.form.autocomplete.AutoCompleteTextField;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.repository.filter.Condition;
import by.hrychanok.training.shop.repository.filter.Filter;
import by.hrychanok.training.shop.service.ProductService;
import by.hrychanok.training.shop.web.page.catalog.CatalogPage;
import by.hrychanok.training.shop.web.page.product.ProductPage;

public class SearchProduct extends Panel {

	@SpringBean
	ProductService productService;
	
	private Condition condition;
	
	private Filter filterState;
	
	private Long categoryId;
	
	private List<Product> productList;

	public SearchProduct(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		filterState = new Filter();
		// Filter initialization
		if (getPage().getClass().equals(CatalogPage.class)) {
			CatalogPage catalog = (CatalogPage) this.getParent();
			filterState = catalog.getFilterState();
		}
		productList = productService.findAll(filterState);
		// Model //
		final IModel<Product> model = Model.of();

		// Form //
		final Form<Void> form = new Form<Void>("form");
		this.add(form);
		// Auto-complete //
		final AutoCompleteTextField<Product> autocomplete = new AutoCompleteTextField<Product>("autocomplete", model) {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<Product> getChoices(String input) {
				return ListUtils.contains(input, productList);
			}

			@Override
			protected void onSelected(AjaxRequestTarget target) {
				PageParameters parameters = new PageParameters();
				parameters.add("id", this.getModelObject().getId());
				setResponsePage(new ProductPage(parameters));
			}

			@Override
			protected IJQueryTemplate newTemplate() {
				return new IJQueryTemplate() {

					private static final long serialVersionUID = 1L;

					@Override
					public String getText() {
						return "<table style='width: 100%' cellspacing='0' cellpadding='0'>\n" + " <tr>\n" + "  <td>\n"
								+ "   <img src='${ data.imageURL }' height='40%' />\n"
								+ "   &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp   \n " + "   ${data.manufacturer   }\n"
								+ " &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp    \n " + "   ${data.model}\n"
								+ "   &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp   " + " ${data.available} רע.\n"
								+ "   &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp   " + " ${data.price}\n" + "  </td>\n"
								+ " </tr>\n" + "</table>\n";

					}

					@Override
					public List<String> getTextProperties() {
						return Arrays.asList("manufacturer", "model", "imageURL", "price", "available");
					}
				};
			}
		};
		autocomplete.setListWidth(700);
		form.add(autocomplete);
	}
}