package by.hrychanok.training.shop.web.page.product;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.tree.DefaultNestedTree;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import by.hrychanok.training.shop.model.Category;
import by.hrychanok.training.shop.service.CategoryService;
import by.hrychanok.training.shop.web.component.leftMenu.LeftMenuPanel;
import by.hrychanok.training.shop.web.component.leftMenu.ProductCategoryPanel;
import by.hrychanok.training.shop.web.page.AbstractPage;

public class BeginnersTreePage extends LeftMenuPanel {
	private static final long serialVersionUID = 1L;

	public BeginnersTreePage(String id) {
		super(id);
		add(new DefaultNestedTree<Category>("tree", new CategoryProvider()) {

			/**
			 * To use a custom component for the representation of a node's
			 * content we would override this method.
			 */
			@Override
			protected Component newContentComponent(String id, IModel<Category> node) {
				return new ProductCategoryPanel(id, node);

				
			}
		});
	}
}