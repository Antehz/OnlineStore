package by.hrychanok.training.shop.web.component.leftMenu;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.markup.html.repeater.tree.DefaultNestedTree;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.googlecode.wicket.kendo.ui.widget.tooltip.TooltipBehavior;

import by.hrychanok.training.shop.model.Category;
import by.hrychanok.training.shop.service.CategoryService;
import by.hrychanok.training.shop.web.page.AbstractPage;
import by.hrychanok.training.shop.web.page.catalog.CatalogPage;
import by.hrychanok.training.shop.web.page.catalog.CategoryProvider;
import by.hrychanok.training.shop.web.page.home.HomePage;

public class CatalogTreePanel extends Panel {
	private static final long serialVersionUID = 1L;

	public CatalogTreePanel(String id) {
		super(id);

		DefaultNestedTree<Category> tree = new DefaultNestedTree<Category>("tree", new CategoryProvider()) {
			@Override
			protected Component newContentComponent(String id, IModel<Category> node) {
				Category category = node.getObject();
				if (category.getChildren().isEmpty()) {
					PageParameters parameters = new PageParameters();
					parameters.add("id", category.getId());
					MultiLineLabel  tooltipe = new MultiLineLabel("tooltipe", category.getDescription());
					BookmarkablePageLink bookLink = new BookmarkablePageLink<>(id, CatalogPage.class,
							parameters);
					bookLink.add(new TooltipBehavior(tooltipe));
					bookLink.setBody(Model.of(category.getName()));
					return bookLink;
			}
				return super.newContentComponent(id, node);
			}
		};
		add(tree);

	}
}