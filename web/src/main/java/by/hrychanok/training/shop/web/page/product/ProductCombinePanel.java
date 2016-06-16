package by.hrychanok.training.shop.web.page.product;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import com.googlecode.wicket.kendo.ui.markup.html.link.AjaxSubmitLink;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import by.hrychanok.training.shop.model.CarBattery;
import by.hrychanok.training.shop.model.Category;
import by.hrychanok.training.shop.model.Coolant;
import by.hrychanok.training.shop.model.Lamp;
import by.hrychanok.training.shop.model.Oil;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.model.ScreenWash;
import by.hrychanok.training.shop.model.Tire;
import by.hrychanok.training.shop.model.UserRole;
import by.hrychanok.training.shop.model.Wheel;
import by.hrychanok.training.shop.service.CategoryService;
import by.hrychanok.training.shop.service.GenericProductService;
import by.hrychanok.training.shop.web.app.AuthorizedSession;

public class ProductCombinePanel<T extends Product> extends Panel {

	@SpringBean
	GenericProductService<T> genericService;
	@SpringBean
	CategoryService categoryService;
	boolean visibleForOnlyAdmin = AuthorizedSession.get().isSignedIn()
			&& AuthorizedSession.get().getLoggedUser().getRole().equals(UserRole.admin);
	private IModel<T> model;
	private T product;
	private Category category;
	private Form<T> productForm;

	/**
	 * Thi's constructor for editing existing product
	 * 
	 * @param id
	 * @param productId
	 */
	public ProductCombinePanel(String id, Long productId) {
		super(id);
		product = genericService.findOne(productId);
		category = product.getCategory();
		model = Model.of(product);
	}

	public ProductCombinePanel(ModalWindow modalWindow, IModel<T> model) {
		super(modalWindow.getContentId());
		product = model.getObject();
		category = product.getCategory();
		this.model = model;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onInitialize() {
		super.onInitialize();

		productForm = new Form<T>("productForm");
		add(productForm);

		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");

		productForm.add(feedback.setOutputMarkupId(true));

		addProductPanel(category);

		AjaxSubmitLink confirmButton = new AjaxSubmitLink("save") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				super.onError(target, form);
				this.error("Неверно введеные данные!");
				target.add(feedback);
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				genericService.saveAndFlush(product);
				this.success("Данные изменены");
				target.add(feedback);
			}

		};
		confirmButton.setVisible(visibleForOnlyAdmin);
		productForm.add(confirmButton);

		AjaxSubmitLink deleteButton = new AjaxSubmitLink("delete") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				super.onError(target, form);
				String deleteError = getString("deleteProductError");
				this.error(deleteError);
				target.add(feedback);
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				try {
					genericService.delete(product);
					String deleteSuccess = getString("deleteItem");
					this.success(deleteSuccess);
					target.add(feedback);
				} catch (Exception e) {
					onError(target, form);
				}

			}
		};
		deleteButton.setVisible(visibleForOnlyAdmin);
		productForm.add(deleteButton);
	}

	private void addProductPanel(Category category) {
		if (categoryDefine(category, 2L)) {
			productForm.add(new TireFeatures("productPanel", (IModel<Tire>) model));
		}
		if (categoryDefine(category, 6L)) {
			productForm.add(new WheelFeatures("productPanel", (IModel<Wheel>) model));
		}
		if (categoryDefine(category, 9L)) {
			productForm.add(new CarBatteryFeatures("productPanel", (IModel<CarBattery>) model));
		}
		if (categoryDefine(category, 10L)) {
			productForm.add(new LampFeatures("productPanel", (IModel<Lamp>) model));
		}
		if (categoryDefine(category, 18L)) {
			productForm.add(new CoolantFeatures("productPanel", (IModel<Coolant>) model));
		}
		if (categoryDefine(category, 14L)) {
			productForm.add(new OilFeatures("productPanel", (IModel<Oil>) model));
		}
		if (categoryDefine(category, 17L)) {
			productForm.add(new ScreenWashFeatures("productPanel", (IModel<ScreenWash>) model));
		}

	}

	private boolean categoryDefine(Category category, Long targetId) {
		boolean find = false;
		while (true) {
			if (category.getId() != targetId && category.getId() != 1L) {
				category = categoryService.findOne(category.getParent().getId());
			} else {
				if (category.getId() == targetId) {
					find = true;
					return find;
				} else {
					return find;
				}
			}

		}
	}
}
