package by.hrychanok.training.shop.web.page.product;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;

import by.hrychanok.training.shop.model.Category;

/**
 * Tree content factory for the {@link org.apache.wicket.examples.tree.AdvancedTreePage}.
 * 
 * Note: This indirection is used for demonstration purposes only! Don't jump through similar hoops
 * if you're just using one type of content for your application's trees.
 * 
 * @author Sven Meier
 */
public abstract class Content implements IDetachable
{

    /**
     * Create new content.
     */
    public abstract Component newContentComponent(String id, AbstractTree<Category> tree,
        IModel<Category> model);

    @Override
    public void detach()
    {
    }
}