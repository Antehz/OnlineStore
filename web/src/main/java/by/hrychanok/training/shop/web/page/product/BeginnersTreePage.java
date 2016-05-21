package by.hrychanok.training.shop.web.page.product;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.markup.html.repeater.tree.DefaultNestedTree;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import by.hrychanok.training.shop.model.Category;
import by.hrychanok.training.shop.service.CategoryService;
import by.hrychanok.training.shop.web.component.leftMenu.LeftMenuPanel;
import by.hrychanok.training.shop.web.component.leftMenu.ProductCategoryPanel;
import by.hrychanok.training.shop.web.page.AbstractPage;
import by.hrychanok.training.shop.web.page.testPage;
import by.hrychanok.training.shop.web.page.catalog.CatalogPage;
import by.hrychanok.training.shop.web.page.catalog.CatalogPanel;
import by.hrychanok.training.shop.web.page.home.HomePage;

public class BeginnersTreePage extends LeftMenuPanel {
	private static final long serialVersionUID = 1L;

	public BeginnersTreePage(String id) {
		super(id);

		DefaultNestedTree<Category> tree = new DefaultNestedTree<Category>("tree", new CategoryProvider()) {
			@Override
			protected Component newContentComponent(String id, IModel<Category> node) {
				Category category = node.getObject();
				if (category.getChildren().isEmpty()) {
					System.out.println(category.getName());
					PageParameters parameters = new PageParameters();
					parameters.add("id", category.getId());
					BookmarkablePageLink bookLink = new BookmarkablePageLink<>(id, CatalogPanel.class,
							parameters);
					bookLink.setBody(Model.of(category.getName()));
					return bookLink;
			}
				
					return new ProductCategoryPanel(id, node);
				

			}
		};
		add(tree);

	}
}