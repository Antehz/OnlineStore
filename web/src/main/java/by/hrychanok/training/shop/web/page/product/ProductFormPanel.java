package by.hrychanok.training.shop.web.page.product;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.RangeValidator;
import com.googlecode.wicket.kendo.ui.form.TextArea;
import com.googlecode.wicket.kendo.ui.form.TextField;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import by.hrychanok.training.shop.model.Category;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.model.UserRole;
import by.hrychanok.training.shop.service.CategoryService;
import by.hrychanok.training.shop.service.ProductService;
import by.hrychanok.training.shop.web.app.AuthorizedSession;
import by.hrychanok.training.shop.web.page.common.CategoryChoiceRenderer;

@AuthorizeAction(roles = { "admin" }, action = "ENABLE")
public class ProductFormPanel<T extends Product> extends FormComponentPanel<T> {

	@SpringBean
	ProductService productService;

	@SpringBean
	CategoryService categoryService;

	private Product product;
	boolean visibleForOnlyAdmin = AuthorizedSession.get().isSignedIn()
			&& AuthorizedSession.get().getLoggedUser().getRole().equals(UserRole.admin);

	public ProductFormPanel(String id, IModel<T> model) {
		super(id, new CompoundPropertyModel<T>(model));
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		add(feedback.setOutputMarkupId(true));

		TextField<String> name = new TextField<>("name");
		name.setVisible(visibleForOnlyAdmin);
		name.setRequired(true);
		add(name);

		final DropDownChoice<Category> category = new DropDownChoice<>("category", categoryService.findAll(),
				CategoryChoiceRenderer.INSTANCE);
		category.setVisible(visibleForOnlyAdmin);
		category.setRequired(true);
		add(category);

		TextField<String> manufacturer = new TextField<>("manufacturer");
		manufacturer.setRequired(true);
		add(manufacturer);

		TextField<String> modelProduct = new TextField<>("model");
		modelProduct.setRequired(true);
		add(modelProduct);

		TextField<Integer> price = new TextField<>("price");
		price.add(RangeValidator.<Integer> range(0, Integer.MAX_VALUE));
		price.setRequired(true);
		add(price);

		TextArea<String> description = new TextArea<>("description");
		description.setRequired(true);
		add(description);

		TextField<String> imageURL = new TextField<>("imageURL");
		imageURL.setVisible(visibleForOnlyAdmin);
		imageURL.setRequired(true);
		add(imageURL);

		TextField<Integer> countOrder = new TextField<>("countOrder");
		countOrder.add(RangeValidator.<Integer> range(0, Integer.MAX_VALUE));
		countOrder.setRequired(true);
		add(countOrder);

		TextField<Integer> countRecommended = new TextField<>("countRecommended");
		countRecommended.add(RangeValidator.<Integer> range(0, Integer.MAX_VALUE));
		countRecommended.setRequired(true);
		add(countRecommended);
		TextField<Integer> available = new TextField<>("available");
		available.add(RangeValidator.<Integer> range(0, Integer.MAX_VALUE));
		available.setRequired(true);
		add(available);
	}
}
