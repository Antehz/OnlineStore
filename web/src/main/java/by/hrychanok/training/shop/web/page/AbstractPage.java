package by.hrychanok.training.shop.web.page;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.data.domain.Sort;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import by.hrychanok.training.shop.model.PageText;
import by.hrychanok.training.shop.model.UserRole;
import by.hrychanok.training.shop.repository.filter.Comparison;
import by.hrychanok.training.shop.repository.filter.Condition;
import by.hrychanok.training.shop.repository.filter.Filter;
import by.hrychanok.training.shop.service.PageTextService;
import by.hrychanok.training.shop.web.app.AuthorizedSession;
import by.hrychanok.training.shop.web.component.adminPanel.EditPagePanel;
import by.hrychanok.training.shop.web.component.footer.FooterPanel;
import by.hrychanok.training.shop.web.component.header.HeaderPanel;
import by.hrychanok.training.shop.web.component.leftMenu.InfoPanel;
import by.hrychanok.training.shop.web.component.leftMenu.PersonalCabinetPanel;
import by.hrychanok.training.shop.web.component.localization.LanguageSelectionComponent;
import by.hrychanok.training.shop.web.component.productFilter.ProductFilterPanel;
import by.hrychanok.training.shop.web.component.productFilter.SearchProduct;
import by.hrychanok.training.shop.web.page.catalog.CatalogPage;
import by.hrychanok.training.shop.web.page.info.NewsPage;

public abstract class AbstractPage extends WebPage {

	private Component headerPanel;
	private Component leftMenuPanel;
	private Component footerPanel;
	private Component filterProductPanel;
	public static KendoFeedbackPanel feedback;

	@SpringBean
	PageTextService pageTextService;

	public AbstractPage(PageParameters parameters) {
		super(parameters);
	}

	public AbstractPage() {
		super();
	}

	boolean visibleForOnlyAdmin = AuthorizedSession.get().isSignedIn()
			&& AuthorizedSession.get().getLoggedUser().getRole().equals(UserRole.admin);

	private List<PageText> listNews;

	private Filter filter = new Filter();

	@Override
	protected void onInitialize() {
		super.onInitialize();

		String title = getPage().getClass().getSimpleName();

		filter.addCondition(
				new Condition.Builder().setComparison(Comparison.eq).setField("title").setValue(title).build());
		listNews = pageTextService.findAll(filter, new Sort(Sort.Direction.DESC, "date"));

		add(new LanguageSelectionComponent("language-select"));
		feedback = new KendoFeedbackPanel("feedback");
		add(feedback);

		if (AuthenticatedWebSession.get().isSignedIn()) {

			add(leftMenuPanel = new PersonalCabinetPanel("leftMenuPanel"));
		} else {
			add(leftMenuPanel = new InfoPanel("leftMenuPanel"));
		}
		add(new SearchProduct("searchProduct"));

		filterProductPanel = new ProductFilterPanel("filterProductPanel");
		filterProductPanel.setVisible(false);
		if (getPage().getClass().equals(CatalogPage.class)) {
			filterProductPanel.setVisible(true);
		}
		add(filterProductPanel);
		add(footerPanel = new FooterPanel("footerPanel"));
		add(headerPanel = new HeaderPanel("headerPanel"));

		ModalWindow modalWindow = new ModalWindow("modal");
		add(modalWindow);

		// Generate Page text content
		WebMarkupContainer wmc = new WebMarkupContainer("wmc");
		wmc.setOutputMarkupId(true);
		add(wmc);
		final DataView dataView = new DataView("simple", new ListDataProvider(listNews)) {
			public void populateItem(final Item item) {
				PageText pageText = (PageText) item.getModelObject();
				DateLabel dateLabel = DateLabel.forDatePattern("date", Model.of(pageText.getDate()), "dd-MM-yyyy");
				dateLabel.setVisible(getPage().getClass().equals(NewsPage.class));
				item.add(dateLabel);
				item.add(new Label("text", Model.of(pageText.getText())).setEscapeModelStrings(false));

				final Form<Void> formEdit = new Form<Void>("formEdit");
				item.add(formEdit);

				// Buttons //
				AjaxButton deleteButton = new AjaxButton("deleteButton") {

					private static final long serialVersionUID = 1L;

					@Override
					protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
						pageTextService.delete(pageText);
						String deleteItem = getString("deleteItem");
						info(deleteItem);
						target.add(AbstractPage.feedback);

						setResponsePage(getPage().getClass());
					}
				};
				formEdit.add(deleteButton);

				// Buttons //
				AjaxButton editButton = new AjaxButton("editButton") {

					private static final long serialVersionUID = 1L;

					@Override
					protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
						modalWindow.setInitialWidth(900);
						modalWindow.setInitialHeight(300);
						modalWindow.setAutoSize(false);
						modalWindow.setContent(new EditPagePanel(modalWindow, pageText));
						modalWindow.show(target);

					}
				};
				formEdit.add(editButton);
				formEdit.setVisible(visibleForOnlyAdmin);
			}
		};

		dataView.setItemsPerPage(10);
		wmc.add(dataView);

		final Form<Void> formAdd = new Form<Void>("formAdd");
		add(formAdd);

		// Buttons //
		AjaxButton addButton = new AjaxButton("addButton") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				modalWindow.setInitialWidth(900);
				modalWindow.setInitialHeight(300);
				modalWindow.setAutoSize(false);
				modalWindow.setContent(new EditPagePanel(modalWindow, new PageText()));
				modalWindow.show(target);
			}
		};
		formAdd.add(addButton);
		formAdd.setVisible(visibleForOnlyAdmin);
		modalWindow.setWindowClosedCallback(new WindowClosedCallback() {

			@Override
			public void onClose(AjaxRequestTarget target) {
				setResponsePage(getPage().getClass());

			}
		});

	}

}
