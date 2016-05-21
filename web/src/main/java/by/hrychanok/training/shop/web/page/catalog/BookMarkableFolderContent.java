package by.hrychanok.training.shop.web.page.catalog;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.content.Folder;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import by.hrychanok.training.shop.model.Category;
import by.hrychanok.training.shop.service.CategoryService;

public class BookMarkableFolderContent extends Content {
	@SpringBean
	CategoryService categoryService;

	public BookMarkableFolderContent(final AbstractTree<Category> tree) {
		String stringid = tree.getRequest().getRequestParameters().getParameterValue("category").toString();
		Long id = Long.parseLong(stringid);
		if (id != null) {
			Category category = categoryService.findOne(id);
			while (category != null) {
				tree.getModel().getObject().add(category);
				category = category.getParent();
			}
		}
	}

	@Override
	public Component newContentComponent(String id, final AbstractTree<Category> tree, IModel<Category> model) {
		return new Folder<Category>(id, tree, model) {
			private static final long serialVersionUID = 1L;

			@Override
			protected MarkupContainer newLinkComponent(String id, IModel<Category> model) {
				Category category = model.getObject();

				if (tree.getProvider().hasChildren(category)) {
					return super.newLinkComponent(id, model);
				} else {
					PageParameters parameters = new PageParameters();
					parameters.add("category", category.getId());

					return new BookmarkablePageLink<>(id, tree.getPage().getClass(), parameters);
				}
			}
		};
	}
}
