package by.hrychanok.training.shop.web.page;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

import by.hrychanok.training.shop.model.AbstractModel;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.service.ProductService;
import by.hrychanok.training.shop.web.page.catalog.CatalogPanel;
import by.hrychanok.training.shop.web.page.product.ProductPage;

public class BasePageForTable<T extends AbstractModel>  extends AbstractPage  {

	public T selected;
	

	public BasePageForTable(PageParameters parameters) {
		super(parameters);
		// TODO Auto-generated constructor stub
	}

	public BasePageForTable() {
		/*super(id);*/

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

		public void goResponsePage() {
		}

		public ActionPanel(String id, IModel<T> model) {
			super(id, model);
			Product selectedProduct = (Product) model.getObject();
			Image image = new Image("imagetest",
					new PackageResourceReference(CatalogPanel.class, selectedProduct.getImageURL()));

			Link link = new Link("select") {
				@Override
				public void onClick() {
					selected = (T) getParent().getDefaultModelObject();
					goResponsePage();
				}
			};
			
			link.add(image);
			add(link);
			
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
