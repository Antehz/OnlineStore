package by.hrychanok.training.shop.web.component.adminPanel;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.data.domain.PageRequest;
import com.googlecode.wicket.jquery.ui.form.button.ConfirmAjaxButton;
import com.googlecode.wicket.kendo.ui.form.dropdown.AjaxDropDownList;
import by.hrychanok.training.shop.model.Customer;
import by.hrychanok.training.shop.model.CustomerCredentials;
import by.hrychanok.training.shop.model.UserRole;
import by.hrychanok.training.shop.repository.filter.Filter;
import by.hrychanok.training.shop.service.CustomerService;
import by.hrychanok.training.shop.web.page.AbstractPage;
import by.hrychanok.training.shop.web.page.GenericSortableTypeDataProvider;
import by.hrychanok.training.shop.web.page.personalCabinet.CustomerProfile;

@AuthorizeInstantiation(value = { "admin" })
public class CustomerManagerPage extends AbstractPage {

	@SpringBean
	CustomerService customerService;
	private Filter filterState = new Filter();

	@Override
	protected void onInitialize() {
		super.onInitialize();

		GenericSortableTypeDataProvider<Customer> dp = new GenericSortableTypeDataProvider<Customer>(filterState) {

			public Iterator<? extends Customer> returnIterator(PageRequest pageRequest) {

				return customerService.findAll(filterState, pageRequest).iterator();
			}

			@Override
			public long size() {
				Long size = customerService.count(filterState);
				return size;
			}
		};

		final DataView<Customer> dataView = new DataView<Customer>("customerTable", dp) {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			protected void populateItem(final Item<Customer> item) {

				Customer customer = item.getModelObject();
				CustomerCredentials customerCredentials = customer.getCustomerCredentials();

				Link linkId = new Link<Void>("linkId") {
					@Override
					public void onClick() {
						PageParameters parameters = new PageParameters();
						parameters.add("id", customer.getId());
						setResponsePage(new CustomerProfile(parameters));
					}
				};
				linkId.setBody(Model.of(customer.getId()));
				item.add(linkId);

				item.add(new Label("login", Model.of(customer.getCustomerCredentials().getLogin())));

				item.add(new Label("email", Model.of(customer.getEmail())));

				item.add(new Label("firstName", Model.of(customer.getFirstName())));

				item.add(new Label("lastName", Model.of(customer.getLastName())));

				item.add(DateLabel.forDatePattern("created", Model.of(customer.getCreated()), "dd-MM-yyyy"));

				Label userRoleLabel = new Label("role", Model.of(customer.getCustomerCredentials().getRole()));

				userRoleLabel.setOutputMarkupId(true);
				item.add(userRoleLabel);

				Form formManageCustomerList = new Form("formManageCustomerList",
						new CompoundPropertyModel<CustomerCredentials>(customerCredentials));
				item.add(formManageCustomerList);

				// ComboBox //
				List<UserRole> roleList = Arrays.asList(UserRole.values());
				final AjaxDropDownList<UserRole> dropdown = new AjaxDropDownList<UserRole>("select",
						new Model<UserRole>(), Model.ofList(roleList)) {

					private static final long serialVersionUID = 1L;

					@Override
					public void onSelectionChanged(AjaxRequestTarget target) {
						UserRole choice = this.getModelObject();
						if (choice != null) {
							customerCredentials.setRole(choice);
							customer.setCustomerCredentials(customerCredentials);
							customerService.save(customer);
							userRoleLabel.setDefaultModelObject(customerCredentials.getRole());
							target.add(userRoleLabel);
							String changeRole = getString("changeRoleSuc");
							success(changeRole);
							target.add(CustomerManagerPage.this.feedback);
						}

					}
				};

				formManageCustomerList.add(dropdown);
				// Buttons //
				String deleleteConfirm = getString("delete");
				String confirm = getString("confirm");
				String changeRole = getString("changeRoleSuc");
				String confirmMessage = getString("confirmMessage");

				ConfirmAjaxButton deleteOrderbutton = new ConfirmAjaxButton("deleteButton", deleleteConfirm, confirm,
						confirmMessage) {

					private static final long serialVersionUID = 1L;

					@Override
					protected void onError(AjaxRequestTarget target, Form<?> form) {
						String userDeleteFail = getString("userDeleteFail");
						this.error(userDeleteFail);
						target.add(CustomerManagerPage.this.feedback);
					}

					@Override
					protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
						customerService.delete(customer);
						String userDeleteSuc = getString("userDeleteSuc");
						this.success(userDeleteSuc);
						target.add(CustomerManagerPage.this.feedback);

						try {
							Thread.sleep(1200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						setResponsePage(new CustomerManagerPage());

					}
				};
				formManageCustomerList.add(deleteOrderbutton);

			}
		};
		dataView.setItemsPerPage(18L);
		dataView.setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());

		add(new OrderByBorder("orderById", "id", dp) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});

		add(new OrderByBorder("orderByEmail", "email", dp) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});

		add(new OrderByBorder("orderByFirstName", "firstName", dp) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});

		add(new OrderByBorder("orderByLastName", "lastName", dp) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});

		add(new OrderByBorder("orderByCreated", "created", dp) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});

		add(dataView);
		add(new PagingNavigator("navigator", dataView));
	}
}
