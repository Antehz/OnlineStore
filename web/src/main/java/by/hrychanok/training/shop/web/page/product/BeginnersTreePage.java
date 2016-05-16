package by.hrychanok.training.shop.web.page.product;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.tree.DefaultNestedTree;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import by.hrychanok.training.shop.model.Category;
import by.hrychanok.training.shop.service.CategoryService;
import by.hrychanok.training.shop.web.page.AbstractPage;

public class BeginnersTreePage extends AbstractPage
{
	@SpringBean
	CategoryService categoryService;
    private static final long serialVersionUID = 1L;

    /**
     * Construct.
     */
    public BeginnersTreePage()
    {
        add(new DefaultNestedTree<Category>("tree", new CategoryProvider(categoryService))
        {

            /**
             * To use a custom component for the representation of a node's content we would
             * override this method.
             */
            @Override
            protected Component newContentComponent(String id, IModel<Category> node)
            {
                return super.newContentComponent(id, node);
            }
        });
    }
}