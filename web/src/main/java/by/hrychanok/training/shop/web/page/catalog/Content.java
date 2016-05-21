package by.hrychanok.training.shop.web.page.catalog;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;

import by.hrychanok.training.shop.model.Category;

public abstract class Content implements IDetachable {

	/**
	 * Create new content.
	 */
	public abstract Component newContentComponent(String id, AbstractTree<Category> tree, IModel<Category> model);

	@Override
	public void detach() {
	}
}