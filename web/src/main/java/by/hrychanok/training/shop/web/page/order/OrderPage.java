package by.hrychanok.training.shop.web.page.order;

import java.util.Iterator;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.data.domain.PageRequest;

import by.hrychanok.training.shop.model.CartContent;
import by.hrychanok.training.shop.model.Order;
import by.hrychanok.training.shop.model.OrderContent;
import by.hrychanok.training.shop.model.StatusOrder;
import by.hrychanok.training.shop.repository.filter.Comparison;
import by.hrychanok.training.shop.repository.filter.Condition;
import by.hrychanok.training.shop.service.OrderService;
import by.hrychanok.training.shop.web.page.AbstractPage;
import by.hrychanok.training.shop.web.page.GenericSortableTypeDataProvider;
import by.hrychanok.training.shop.web.page.product.ProductPage;

public class OrderPage extends AbstractPage {

	@SpringBean
	OrderService orderService;

	private Order order;

	public OrderPage() {
		super();
	}

	public OrderPage(Long id) {
		order = orderService.findOne(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new Label("orderId", Model.of(order.getId())));
		add(new Label("shippingMethod", Model.of(order.getShippingMethod())));
		add(new Label("totalPrice", Model.of(order.getTotalPrice())));
		defineStyleStatus();
		add(new Label("additionalInfo", Model.of(order.getAdditionalInfo())));

		// Table 2 - order content

		GenericSortableTypeDataProvider<OrderContent> dp = new GenericSortableTypeDataProvider<OrderContent>() {

			public Iterator<? extends OrderContent> returnIterator(PageRequest pageRequest) {
				filterState.addCondition(new Condition.Builder().setComparison(Comparison.eq).setField("order")
						.setValue(order.getId()).build());
				return orderService.findAllContent(filterState, pageRequest).iterator();
			}

			@Override
			public long size() {
				return orderService.countContent(filterState);
			}
		};

		final DataView<OrderContent> dataView = new DataView<OrderContent>("orderContentTable", dp) {
			private static final long serialVersionUID = 1L;
			int counter = 1;

			@Override
			protected void populateItem(Item<OrderContent> item) {
				OrderContent orderContent = item.getModelObject();

				item.add(new Label("counter", counter++));

				Link linkProductId = new Link<Void>("linkProduct") {
					@Override
					public void onClick() {
						PageParameters parameters = new PageParameters();
						parameters.add("id", orderContent.getProduct().getId());
						setResponsePage(new ProductPage(parameters));
					}
				};
				linkProductId.setBody(Model.of(orderContent.getProduct().getId()));
				item.add(linkProductId);

				item.add(new Label("amount", orderContent.getAmount()));

				item.add(new Label("price", orderContent.getPrice()));

			}
		};
		dataView.setItemsPerPage(6L);
		dataView.setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());
		add(new PagingNavigator("navigator", dataView));
		add(dataView);
	}

	private void defineStyleStatus() {
		final WebMarkupContainer wmc = new WebMarkupContainer("trStyle");
		add(wmc);
		if (order.getStatus().equals(StatusOrder.Cancelled)) {
			wmc.add(new AttributeModifier("class", "danger"));
		}
		if (order.getStatus().equals(StatusOrder.Accepted)) {
			wmc.add(new AttributeModifier("class", "active"));
		}
		if (order.getStatus().equals(StatusOrder.Done)) {
			wmc.add(new AttributeModifier("class", "success"));
		}
		if (order.getStatus().equals(StatusOrder.Making)) {
			wmc.add(new AttributeModifier("class", "info"));
		}

		wmc.add(new Label("status", Model.of(order.getStatus())));
	}
}