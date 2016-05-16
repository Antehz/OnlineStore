package by.hrychanok.training.shop.web.page;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import by.hrychanok.training.shop.model.AbstractModel;

public class BasePageForTable<T extends AbstractModel> extends AbstractPage {

	private T selected;

	public BasePageForTable() {
		add(new Label("selectedLabel", new PropertyModel<>(this, "selectedContactLabel")));
		add(new FeedbackPanel("feedback"));
	}

	/**
	 * @return string representation of selected contact property
	 */
	public String getSelectedContactLabel() {
		if (selected == null) {
			return "Nothing selected";
		} else {
			return selected.getId().toString();
		}
	}

	/**
	 * 
	 */
	public class ActionPanel extends Panel {
		/**
		 * @param id
		 *            component id
		 * @param model
		 *            model for contact
		 */
		public ActionPanel(String id, IModel<T> model) {
			super(id, model);
			add(new Link("select") {
				@Override
				public void onClick() {
					selected = (T) getParent().getDefaultModelObject();
				}
			});
		}
	}

	/**
	 * @return selected contact
	 */
	public T getSelected() {
		return selected;
	}

	/**
	 * sets selected contact
	 * 
	 * @param selected
	 */
	public void setSelected(T selected) {
		addStateChange();
		this.selected = selected;
	}

}
