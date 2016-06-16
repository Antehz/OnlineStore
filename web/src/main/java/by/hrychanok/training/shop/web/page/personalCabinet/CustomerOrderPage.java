package by.hrychanok.training.shop.web.page.personalCabinet;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.widget.tabs.AjaxTab;
import com.googlecode.wicket.kendo.ui.widget.tabs.TabbedPanel;
import by.hrychanok.training.shop.model.StatusOrder;
import by.hrychanok.training.shop.repository.filter.Comparison;
import by.hrychanok.training.shop.repository.filter.Condition;
import by.hrychanok.training.shop.web.page.AbstractPage;

public class CustomerOrderPage extends AbstractPage {

	public CustomerOrderPage() {
		super();

	}

	public CustomerOrderPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		Options options = new Options();
		options.set("collapsible", true);
		add(new TabbedPanel("tabs", this.newTabList(), options));
	}

	private List<ITab> newTabList() {
		List<ITab> tabs = new ArrayList<ITab>();
		String allOrder = getString("allOrder");
		tabs.add(new AjaxTab(Model.of(allOrder)) {

			private static final long serialVersionUID = 1L;

			@Override
			public WebMarkupContainer getLazyPanel(String panelId) {
				try {
					// sleep the thread for a half second to simulate a long
					// load
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return new OrderHistoryPanel(panelId);
			}
		});
		String currentOrder = getString("currentOrder");
		tabs.add(new AjaxTab(Model.of(currentOrder)) {

			private static final long serialVersionUID = 1L;

			@Override
			public WebMarkupContainer getLazyPanel(String panelId) {
				try {
					// sleep the thread for a half second to simulate a long
					// load
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				List<Condition> conditionsList = new ArrayList<Condition>();
				Condition condition = new Condition.Builder().setComparison(Comparison.eq).setField("status")
						.setValue(StatusOrder.Pending).build();
				conditionsList.add(condition);
				return new OrderHistoryPanel(panelId, conditionsList);
			}
		});
		String doneOrder = getString("doneOrder");
		tabs.add(new AjaxTab(Model.of(doneOrder)) {

			private static final long serialVersionUID = 1L;

			@Override
			public WebMarkupContainer getLazyPanel(String panelId) {
				try {
					// sleep the thread for a half second to simulate a long
					// load
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				List<Condition> conditionsList = new ArrayList<Condition>();
				Condition condition = new Condition.Builder().setComparison(Comparison.eq).setField("status")
						.setValue(StatusOrder.Done).build();
				conditionsList.add(condition);
				return new OrderHistoryPanel(panelId, conditionsList);
			}
		});

		return tabs;
	}
}
