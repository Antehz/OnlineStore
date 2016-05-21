package by.hrychanok.training.shop.web.page.catalog;

import java.util.List;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import by.hrychanok.training.shop.model.Category;
import by.hrychanok.training.shop.web.page.AbstractPage;
import by.hrychanok.training.shop.web.page.product.CategoryProvider;

/**
 * @author Sven Meier
 */

public abstract class AdvancedTreePage extends AbstractPage {

	private static final long serialVersionUID = 1L;

	private Behavior theme;

	private AbstractTree<Category> tree;

	private CategoryProvider provider = new CategoryProvider();

	private Content content;

	private List<Content> contents;

	private List<Behavior> themes;

	/**
	 * Construct.
	 */

	public AdvancedTreePage() {
		Form<Void> form = new Form<>("form");
		add(form);

		tree = createTree(provider, new FooExpansionModel());
		tree.add(new Behavior() {
			private static final long serialVersionUID = 1L;

			@Override
			public void onComponentTag(Component component, ComponentTag tag) {
				theme.onComponentTag(component, tag);
			}

			@Override
			public void renderHead(Component component, IHeaderResponse response) {
				theme.renderHead(component, response);
			}
		});
		form.add(tree);

	}

	protected abstract AbstractTree<Category> createTree(CategoryProvider provider, IModel<Set<Category>> state);

	protected Component newContentComponent(String id, IModel<Category> model) {
		return content.newContentComponent(id, tree, model);
	}

	private class FooExpansionModel extends AbstractReadOnlyModel<Set<Category>> {
		@Override
		public Set<Category> getObject() {
			return FooExpansion.get();
		}
	}
}