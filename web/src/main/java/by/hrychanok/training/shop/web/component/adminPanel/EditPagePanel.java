package by.hrychanok.training.shop.web.component.adminPanel;

import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.panel.Panel;

import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.form.button.ButtonBehavior;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.WysiwygEditor;
import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.toolbar.DefaultWysiwygToolbar;
import by.hrychanok.training.shop.model.PageText;
import by.hrychanok.training.shop.service.PageTextService;

public class EditPagePanel extends Panel {

	@SpringBean
	PageTextService pageTextService;
	private PageText pageText;

	public EditPagePanel(ModalWindow modalWindow, PageText pageText) {
		super(modalWindow.getContentId());
		this.pageText = pageText;
		pageText.setDate(new Date());
		pageText.setTitle(modalWindow.getPage().getClass().getSimpleName());
	}

	@SuppressWarnings("unused")
	private String text;

	@Override
	protected void onInitialize() {
		super.onInitialize();
		// Form //
		final Form<Void> form = new Form<Void>("form");
		add(form.setOutputMarkupId(true));

		// Wysiwyg //
		DefaultWysiwygToolbar toolbar = new DefaultWysiwygToolbar("toolbar");
		final WysiwygEditor editor = new WysiwygEditor("wysiwyg", new PropertyModel<String>(pageText, "text"), toolbar);

		form.add(toolbar, editor);

		// Feedback//
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback);

		Label htmlLabel = new Label("htmlLabel", Model.of(""));
		htmlLabel.setOutputMarkupId(true);
		htmlLabel.setEscapeModelStrings(false);
		add(htmlLabel);

		// Buttons //
		this.add(new AjaxButton("display", form) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> unused) {
				String html = editor.getModelObject();
				if (html != null) {
					pageText.setText(html);
					pageTextService.save(pageText);
					form.info(html);
				}

				target.add(feedback);
			}
		});

		this.add(new AjaxLink<Void>("disable") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onInitialize() {
				super.onInitialize();

				this.add(new ButtonBehavior(IJQueryWidget.JQueryWidget.getSelector(this)));
			}

			@Override
			public void onClick(AjaxRequestTarget target) {
				target.add(form.setEnabled(!form.isEnabled()));
			}
		});

	}

}
