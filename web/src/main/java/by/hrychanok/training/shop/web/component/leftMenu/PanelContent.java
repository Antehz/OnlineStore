package by.hrychanok.training.shop.web.component.leftMenu;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;

import by.hrychanok.training.shop.model.Category;

public class PanelContent implements IDetachable
{
    private static final long serialVersionUID = 1L;

    public Component newContentComponent(String id, AbstractTree<Category> tree, IModel<Category> model)
    {
        return new ProductCategoryPanel(id, model);
    }

	@Override
	public void detach() {
	}
}