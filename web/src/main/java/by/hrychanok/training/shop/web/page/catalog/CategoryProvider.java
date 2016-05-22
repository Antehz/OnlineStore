package by.hrychanok.training.shop.web.page.catalog;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import by.hrychanok.training.shop.model.Category;
import by.hrychanok.training.shop.service.CategoryService;
import by.hrychanok.training.shop.service.CustomerService;

public class CategoryProvider implements ITreeProvider<Category>
{
	@SpringBean
	CategoryService categoryService;
    private static final long serialVersionUID = 1L;

    public CategoryProvider()
    {
		Injector.get().inject(this);
    }

    @Override
    public void detach()
    {
    }

    @Override
    public Iterator<Category> getRoots()
    {
       Category root = categoryService.findRoot();
        
        return root.getChildren().iterator();
    }

    @Override
    public boolean hasChildren(Category category)
    {
        return category.getParent() == null || !category.getChildren().isEmpty();
    }

    @Override
    public Iterator<Category> getChildren(final Category category)
    {
        return category.getChildren().iterator();
    }

    /**
     * Creates a {@link FooModel}.
     */
    @Override
    public IModel<Category> model(Category category)
    {
        return new CategoryModel(category);
    }

    /**
     * A {@link Model} which uses an id to load its {@link Foo}.
     * 
     * If {@link Foo}s were {@link Serializable} you could just use a standard {@link Model}.
     * 
     * @see #equals(Object)
     * @see #hashCode()
     */
    private class CategoryModel extends LoadableDetachableModel<Category>
    {
        private static final long serialVersionUID = 1L;
        @SpringBean
    	CategoryService categoryService;
    	
        private final Long id;

        public CategoryModel(Category category)
        {
            super(category);
            Injector.get().inject(this); 
            id = category.getId();
        }

        @Override
        protected Category load()
        {
            return categoryService.findOne(id);
        }

        /**
         * Important! Models must be identifyable by their contained object.
         */
        @Override
        public boolean equals(Object obj)
        {
            if (obj instanceof CategoryModel)
            {
                return ((CategoryModel)obj).id.equals(id);
            }
            return false;
        }

        /**
         * Important! Models must be identifyable by their contained object.
         */
        @Override
        public int hashCode()
        {
            return id.hashCode();
        }
    }
}