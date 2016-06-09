package by.hrychanok.training.shop.web.component.productFilter;

import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.googlecode.wicket.kendo.ui.form.CheckBox;
import com.googlecode.wicket.kendo.ui.form.CheckBox.Label;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.form.combobox.ComboBox;
import com.googlecode.wicket.kendo.ui.markup.html.link.AjaxLink;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import by.hrychanok.training.shop.repository.filter.Comparison;
import by.hrychanok.training.shop.repository.filter.Condition;
import by.hrychanok.training.shop.repository.filter.Filter;
import by.hrychanok.training.shop.service.ProductService;
import by.hrychanok.training.shop.web.page.catalog.CatalogPage;
import by.hrychanok.training.shop.web.page.catalog.InputRangeSliderPanel;

public class ProductFilterPanel extends Panel {

	@SpringBean
	ProductService productService;

	private List<String> manufacturers;
	private List<String> models;
	private Class<? extends Page> parameters;
	private Filter filterState;
	private Condition condition;
	private Long categoryId;
	private String manufacturerChoice;
	private Form<Boolean> formCheck;
    private Boolean checkChoice = Boolean.FALSE;
	public ProductFilterPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		if (getPage().getClass().equals(CatalogPage.class)) {
			CatalogPage catalog = (CatalogPage) this.getParent();
			filterState = catalog.getFilterState();
			condition = filterState.getConditionByField("category");
			if(condition!=null){
			categoryId = (Long) condition.value;
			manufacturers = productService.getListModelsAndManufacturers(categoryId, "manufacturer");
			models = productService.getListModelsAndManufacturers(categoryId, "model");
			}
			condition = filterState.getConditionByField("manufacturer");
			if (condition != null) {
				manufacturerChoice = (String) condition.value;
			}
			condition = filterState.getConditionByField("available");
			if (condition != null) {
	                checkChoice=Boolean.TRUE;
			}
		}

		Form<Void> generalForm = new Form<Void>("generalForm");
		add(generalForm);
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		add(feedback);

		// Manufacturer combo-box

		final ComboBox<String> comboboxManufacturer = new ComboBox<String>("comboboxManufacturer",
				new Model<String>(manufacturerChoice), manufacturers);
		generalForm.add(comboboxManufacturer);

		generalForm.add(new AjaxButton("confirmManufacturerChoice") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (manufacturerChoice != null) {
					filterState.removeCondition("manufacturer");
				}
				String manufacturerChoice = comboboxManufacturer.getModelObject();
				condition = new Condition.Builder().setComparison(Comparison.eq).setField("manufacturer")
						.setValue(manufacturerChoice).build();
				if (null == manufacturerChoice || "".equals(manufacturerChoice)) {
					filterState.removeCondition("manufacturer");
				} else {
					filterState.addCondition(condition);
				}
				setResponsePage(new CatalogPage(filterState));
				ProductFilterPanel.this.info(comboboxManufacturer);
				target.add(feedback);
			}
		});
		
		// checkbox Available//
		formCheck = new Form<Boolean>("formCheck", Model.of(checkChoice));
		this.add(this.formCheck);
		
		CheckBox checkbox = new CheckBox("check", this.formCheck.getModel());
		Label labelCheck = new Label("labelCheck", "Есть в наличии", checkbox);
		this.formCheck.add(checkbox, labelCheck);

		// buttons //
		this.formCheck.add(this.newAjaxButton("checkAvailable"));

		// Price Range slider with modal window
		ModalWindow modalWindow = new ModalWindow("modalPrice");
		add(modalWindow);
		modalWindow.setInitialHeight(90);
		modalWindow.setInitialWidth(463);
		modalWindow.setResizable(false);
		add(new AjaxLink("openModalPrice") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				modalWindow.setContent(new InputRangeSliderPanel(modalWindow, filterState));
				modalWindow.show(target);
			}
		});

		modalWindow.setWindowClosedCallback(new WindowClosedCallback() {

			@Override
			public void onClose(AjaxRequestTarget target) {
				setResponsePage(new CatalogPage(filterState));

			}
		});
	}

	private AjaxButton newAjaxButton(String id) {
		return new AjaxButton(id) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> unused) {

				if (formCheck.getModelObject().equals(Boolean.TRUE)) {

					filterState.addCondition(new Condition.Builder().setComparison(Comparison.gt).setField("available")
							.setValue(0).build());
				} else {
					filterState.removeCondition("available");
				}
				setResponsePage(new CatalogPage(filterState));
				target.add(formCheck);

			}

		};
	}
}
