package by.hrychanok.training.shop.web.page;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import by.hrychanok.training.shop.model.AbstractModel;

public class BasePageForTable<T extends AbstractModel>  extends AbstractPage  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public T selected;
	

	public BasePageForTable(PageParameters parameters) {
		super(parameters);
	}

	public BasePageForTable() {

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
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
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void createLink(IModel<T> model) {
			
		}

		public ActionPanel(String id, IModel<T> model) {
			super(id, model);
			createLink(model);
			
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
