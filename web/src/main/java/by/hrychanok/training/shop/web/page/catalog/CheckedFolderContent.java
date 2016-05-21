package by.hrychanok.training.shop.web.page.catalog;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.extensions.markup.html.repeater.tree.content.CheckedFolder;
import org.apache.wicket.extensions.markup.html.repeater.util.ProviderSubset;
import org.apache.wicket.model.IModel;

import by.hrychanok.training.shop.model.Category;

public class CheckedFolderContent extends Content
{

    private static final long serialVersionUID = 1L;

    private ProviderSubset<Category> checked;

    public CheckedFolderContent(ITreeProvider<Category> provider)
    {
        checked = new ProviderSubset<>(provider, false);
    }

    @Override
    public void detach()
    {
        checked.detach();
    }

    protected boolean isChecked(Category category)
    {
        return checked.contains(category);
    }

    protected void check(Category category, boolean check)
    {
        if (check)
        {
            checked.add(category);
        }
        else
        {
            checked.remove(category);
        }
    }

    @Override
    public Component newContentComponent(String id, final AbstractTree<Category> tree, IModel<Category> model)
    {
        return new CheckedFolder<Category>(id, tree, model)
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected IModel<Boolean> newCheckBoxModel(final IModel<Category> model)
            {
                return new IModel<Boolean>()
                {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public Boolean getObject()
                    {
                        return isChecked(model.getObject());
                    }

                    @Override
                    public void setObject(Boolean object)
                    {
                        check(model.getObject(), object);
                    }

                    @Override
                    public void detach()
                    {
                    }
                };
            }
        };
    }
}