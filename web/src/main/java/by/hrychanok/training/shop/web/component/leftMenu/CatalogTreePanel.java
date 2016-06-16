package by.hrychanok.training.shop.web.component.leftMenu;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.markup.html.repeater.tree.DefaultNestedTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.content.Folder;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import com.googlecode.wicket.jquery.ui.JQueryIcon;
import com.googlecode.wicket.jquery.ui.widget.menu.ContextMenu;
import com.googlecode.wicket.jquery.ui.widget.menu.ContextMenuBehavior;
import com.googlecode.wicket.jquery.ui.widget.menu.IMenuItem;
import com.googlecode.wicket.jquery.ui.widget.menu.MenuItem;
import com.googlecode.wicket.kendo.ui.widget.tooltip.TooltipBehavior;
import by.hrychanok.training.shop.model.CarBattery;
import by.hrychanok.training.shop.model.Category;
import by.hrychanok.training.shop.model.Coolant;
import by.hrychanok.training.shop.model.Lamp;
import by.hrychanok.training.shop.model.Oil;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.model.ScreenWash;
import by.hrychanok.training.shop.model.Tire;
import by.hrychanok.training.shop.model.UserRole;
import by.hrychanok.training.shop.model.Wheel;
import by.hrychanok.training.shop.service.CategoryService;
import by.hrychanok.training.shop.web.app.AuthorizedSession;
import by.hrychanok.training.shop.web.component.adminPanel.CategoryCreatePanel;
import by.hrychanok.training.shop.web.page.catalog.CatalogPage;
import by.hrychanok.training.shop.web.page.catalog.CategoryProvider;
import by.hrychanok.training.shop.web.page.product.ProductCombinePanel;

public class CatalogTreePanel extends Panel {
	
	private static final long serialVersionUID = 1L;
	private ContextMenu menu;
	private Category category;
	private ModalWindow modalWindow;
	private final static String IMAGELINKPATTERN = "/images/product/{name_image}.jpg";

	@SpringBean
	CategoryService categoryService;
	boolean visibleForOnlyAdmin = AuthorizedSession.get().isSignedIn()
			&& AuthorizedSession.get().getLoggedUser().getRole().equals(UserRole.admin);

	public CatalogTreePanel(String id) {
		super(id);
		modalWindow = new ModalWindow("modal");
		add(modalWindow);
		Form form = new Form<>("form");

		menu = new ContextMenu("contextMenu", newMenuItemList()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onContextMenu(AjaxRequestTarget target, Component component) {

				category = (Category) component.getDefaultModelObject();
			}

			@Override
			public void onClick(AjaxRequestTarget target, IMenuItem item) {
			}
		};
		menu.setOutputMarkupId(true);
		menu.setVisible(visibleForOnlyAdmin);
		add(menu);

		DefaultNestedTree<Category> tree = new DefaultNestedTree<Category>("tree", new CategoryProvider()) {
			@Override
			protected Component newContentComponent(String id, IModel<Category> node) {
				Category category = node.getObject();
				Folder fold = new Folder<Category>(id, this, node) {
					@Override
					protected IModel<?> newLabelModel(IModel<Category> model) {
						if (Session.get().getLocale().toString().equals("en_US")) {
							return new PropertyModel<String>(model, "nameEng");
						} else {
							return new PropertyModel<String>(model, "name");
						}
					}

					@Override
					protected boolean isClickable() {
						return true;
					}

					@Override
					protected void onClick(AjaxRequestTarget target) {
						super.onClick(target);
						if (category.getChildren().isEmpty()) {
							PageParameters parameters = new PageParameters();
							parameters.add("id", category.getId());
							setResponsePage(new CatalogPage(parameters));
						}
					}
				};
				MultiLineLabel tooltipe = new MultiLineLabel("tooltipe", category.getDescription());
				fold.add(new TooltipBehavior(tooltipe));
				fold.add(new ContextMenuBehavior(menu));
				return fold;
			}
		};
		form.add(tree);
		add(form);
	}

	private List<IMenuItem> newMenuItemList() {
		List<IMenuItem> list = new ArrayList<IMenuItem>();
		
		String createCategoryMessage = getString("createCategory");
		list.add(new MenuItem(createCategoryMessage, JQueryIcon.PLUS) {
			public void onClick(AjaxRequestTarget target) {
				modalWindow.setInitialWidth(600);
				modalWindow.setInitialHeight(300);
				modalWindow.setContent(new CategoryCreatePanel(modalWindow, category));
				modalWindow.show(target);

			};
		});
		String deleteCategoryMessage = getString("deleteCategory");
		list.add(new MenuItem(deleteCategoryMessage, JQueryIcon.CANCEL) {
			@Override
			public void onClick(AjaxRequestTarget target) {
				super.onClick(target);
				categoryService.delete(category);
				setResponsePage(getPage());
			}
		});
		String editCategoryMessage = getString("editCategory");
		list.add(new MenuItem(editCategoryMessage, JQueryIcon.REFRESH) {
			@Override
			public void onClick(AjaxRequestTarget target) {
				super.onClick(target);
				modalWindow.setInitialWidth(600);
				modalWindow.setInitialHeight(300);
				modalWindow.setContent(new CategoryCreatePanel(modalWindow, category.getId()));
				modalWindow.show(target);
			}
		});

		String createProductMessage = getString("createProduct");
		list.add(new MenuItem(createProductMessage, JQueryIcon.PLUS) {
			public void onClick(AjaxRequestTarget target) {
				modalWindow.setInitialWidth(500);
				modalWindow.setInitialHeight(900);
				modalWindow.setContent(new ProductCombinePanel(modalWindow, defineProduct(category)));
				modalWindow.show(target);

			};
		});
		return list;
	}

	private <T extends Product> IModel<T> defineProduct(Category category) {
		if (categoryDefine(category, 2L)) {
			Tire tire = new Tire();
			fillNewProduct(category, tire);
			IModel<Tire> model = Model.of(tire);
			return (IModel<T>) model;
		}
		if (categoryDefine(category, 6L)) {
			Wheel wheel = new Wheel();
			fillNewProduct(category, wheel);
			IModel<Wheel> model = Model.of(wheel);
			return (IModel<T>) model;
		}
		if (categoryDefine(category, 9L)) {
			CarBattery carBattery = new CarBattery();
			fillNewProduct(category, carBattery);
			IModel<CarBattery> model = Model.of(carBattery);
			return (IModel<T>) model;
		}
		if (categoryDefine(category, 10L)) {
			Lamp lamp = new Lamp();
			fillNewProduct(category, lamp);
			IModel<Lamp> model = Model.of(lamp);
			return (IModel<T>) model;
		}
		if (categoryDefine(category, 18L)) {
			Coolant coolant = new Coolant();
			fillNewProduct(category, coolant);
			IModel<Coolant> model = Model.of(coolant);
			return (IModel<T>) model;
		}
		if (categoryDefine(category, 14L)) {
			Oil oil = new Oil();
			fillNewProduct(category, oil);
			IModel<Oil> model = Model.of(oil);
			return (IModel<T>) model;
		}
		if (categoryDefine(category, 17L)) {
			ScreenWash screenWash = new ScreenWash();
			fillNewProduct(category, screenWash);
			IModel<ScreenWash> model = Model.of(screenWash);
			return (IModel<T>) model;
		}

		return null;
	}

	private <T extends Product> void fillNewProduct(Category category, T obj) {
		obj.setName(category.getName());
		obj.setImageURL(IMAGELINKPATTERN);
		obj.setCountRecommended(0);
		obj.setCountOrder(0);
		obj.setCategory(category);
	}

	private boolean categoryDefine(Category category, Long targetId) {
		boolean find = false;
		while (true) {
			if (category.getId() != targetId && category.getId() != 1L) {
				category = categoryService.findOne(category.getParent().getId());
			} else {
				if (category.getId() == targetId) {
					find = true;
					return find;
				} else {
					return find;
				}
			}

		}
	}

}
