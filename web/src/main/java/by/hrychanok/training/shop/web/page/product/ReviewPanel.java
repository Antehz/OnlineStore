package by.hrychanok.training.shop.web.page.product;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.util.string.ComponentRenderer;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
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
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.googlecode.wicket.kendo.ui.console.FeedbackConsole;
import com.googlecode.wicket.kendo.ui.form.TextField;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;

import by.hrychanok.training.shop.model.CartContent;
import by.hrychanok.training.shop.model.CustomerCredentials;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.model.ProductComment;
import by.hrychanok.training.shop.model.UserRole;
import by.hrychanok.training.shop.service.ProductService;
import by.hrychanok.training.shop.web.app.AuthorizedSession;
import by.hrychanok.training.shop.web.app.MySession;
import by.hrychanok.training.shop.web.page.AbstractPage;

public class ReviewPanel extends Panel {

	@SpringBean
	ProductService productService;

	private CustomerCredentials customer;

	private List<ProductComment> productCommentList;
	private Long productId;
	boolean visibleForOnlyAdmin = AuthorizedSession.get().isSignedIn()
			&& AuthorizedSession.get().getLoggedUser().getRole().equals(UserRole.admin);
	boolean visibleForOnlyCustomer = AuthorizedSession.get().isSignedIn();

	public ReviewPanel(String id, Long productId) {
		super(id);
		this.productId = productId;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		if (visibleForOnlyCustomer) {
			customer = AuthorizedSession.get().getLoggedUser();
		}
		productCommentList = productService.getCommentByProductId(productId);

		final DataView dataView = new DataView("simple", new ListDataProvider(productCommentList)) {
			public void populateItem(final Item item) {
				ProductComment productComment = (ProductComment) item.getModelObject();

				item.add(new Label("customerName", Model.of(productComment.getCustomer().getFirstName())));
				item.add(DateLabel.forDatePattern("added", Model.of(productComment.getDate()), "dd-MM-yyyy"));
				item.add(new Label("comment", Model.of(productComment.getComment())));

				final Form<Void> formDelete = new Form<Void>("formDelete");
				item.add(formDelete);

				// Buttons //
				AjaxButton deleteButton = new AjaxButton("deleteButton") {

					private static final long serialVersionUID = 1L;

					@Override
					protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
						productService.deleteCommentById(productComment.getId());
						PageParameters parameters = new PageParameters();
						parameters.add("id", productId);
						setResponsePage(new ProductPage(parameters));
					}
				};
				deleteButton.setVisible(visibleForOnlyAdmin);
				formDelete.add(deleteButton);
			}
		};

		dataView.setItemsPerPage(10);

		add(dataView);

		add(new PagingNavigator("navigator", dataView));

		final FeedbackConsole console = new FeedbackConsole("console") {

			private static final long serialVersionUID = 1L;

			@Override
			protected String format(Serializable message, boolean error) {
				if (error) {
					return String.format("<font>%s</font>", super.format(message, error));
				}

				return super.format(message, error);
			}
		};
		this.add(console);
		// Form //
		final Form<Void> formAdd = new Form<Void>("formAdd");
		this.add(formAdd);

		// TextField //
		String enterMessage = getString("enterMessage");
		final TextField<String> textField = new TextField<String>("message", Model.of(enterMessage));
		formAdd.add(textField.setRequired(true));

		// Buttons //
		AjaxButton buttonAdd = new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				String message = textField.getModelObject();
				console.info(message);
				productService.addCommentForProduct(productId, customer.getId(), message);
				PageParameters parameters = new PageParameters();
				parameters.add("id", productId);
				setResponsePage(new ProductPage(parameters));

			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				console.refresh(target);
			}
		};
		formAdd.setEnabled(visibleForOnlyCustomer);
		formAdd.add(buttonAdd);
	}
}
