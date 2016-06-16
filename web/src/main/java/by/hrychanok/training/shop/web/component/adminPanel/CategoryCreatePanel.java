package by.hrychanok.training.shop.web.component.adminPanel;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import com.googlecode.wicket.kendo.ui.form.TextArea;
import com.googlecode.wicket.kendo.ui.form.TextField;
import com.googlecode.wicket.kendo.ui.form.button.IndicatingAjaxButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

import by.hrychanok.training.shop.model.Category;
import by.hrychanok.training.shop.service.CategoryService;
import by.hrychanok.training.shop.web.page.AbstractPage;
import by.hrychanok.training.shop.web.page.common.CategoryChoiceRenderer;

public class CategoryCreatePanel extends Panel {

	@SpringBean
	CategoryService categoryService;
	private List<String> listCategory;
	private Category category;
	private Category parentCategory;

	public CategoryCreatePanel(ModalWindow modalWindow, Category parentCategory) {
		super(modalWindow.getContentId());
		category = new Category();
		this.parentCategory = parentCategory;
	}

	public CategoryCreatePanel(ModalWindow modalWindow, Long categoryId) {
		super(modalWindow.getContentId());
		category = categoryService.findOne(categoryId);
		this.parentCategory = parentCategory;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		category.setParent(parentCategory);
		Form<Category> generalForm = new Form<Category>("generalForm", new CompoundPropertyModel<Category>(category));
		add(generalForm);
		KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		add(feedback);
		TextField<String> name = new TextField<>("name");
		name.setRequired(true);
		generalForm.add(name);

		TextField<String> nameEng = new TextField<>("nameEng");
		name.setRequired(true);
		generalForm.add(nameEng);

		final DropDownChoice<Category> choiceParent = new DropDownChoice<>("parent", categoryService.findAll(),
				CategoryChoiceRenderer.INSTANCE);
		choiceParent.setRequired(true);
		generalForm.add(choiceParent);

		TextArea<String> description = new TextArea<String>("description");
		description.setRequired(true);
		generalForm.add(description);

		generalForm.add(new IndicatingAjaxButton("createCategory") {

			private static final long serialVersionUID = 1L;

			@Override
			protected boolean isDisabledOnClick() {
				return true;
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				categoryService.save(category);
				String saveData = getString("update");
				success(saveData);
				target.add(AbstractPage.feedback);
			}
		});

	}

}
