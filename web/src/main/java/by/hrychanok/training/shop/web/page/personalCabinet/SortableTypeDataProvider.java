package by.hrychanok.training.shop.web.page.personalCabinet;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import by.hrychanok.training.shop.model.Order;
import by.hrychanok.training.shop.repository.filter.Filter;

public class SortableTypeDataProvider extends SortableDataProvider<Order, String> implements IFilterStateLocator<Filter> {

	private static final long serialVersionUID = 1L;
	private List<Order> listType;

	public SortableTypeDataProvider() {
		this(Collections.<Order> emptyList());
	}

	public SortableTypeDataProvider(List<Order> listType) {

		if (listType == null) {
			throw new IllegalArgumentException("argument [list] cannot be null");
		}

		this.listType = listType;

		setSort("id", SortOrder.ASCENDING);
	}

	public List<Order> getData() {
		return listType;
	}

	public void setListType(List<Order> listType) {
		this.listType = listType;
	}

	@Override
	public Iterator<? extends Order> iterator(long first, long count) {

		List<Order> listType = getData();

		final SortParam sp = getSort();

		Collections.sort(getData(), getComparator(sp));

		long toIndex = first + count;
		if (toIndex > listType.size()) {
			toIndex = listType.size();
		}
		return listType.subList((int) first, (int) toIndex).listIterator();
	}

	@Override
	public long size() {
		return listType.size();
	}


	@Override
	public Filter getFilterState() {
		return null;
	}

	@Override
	public void setFilterState(Filter state) {
	}

	protected Comparator<Order> getComparator(final SortParam sp) {
		return new Comparator<Order>() {
			@Override
			public int compare(Order arg0, Order arg1) {

				int result;

				PropertyModel<Comparable> model0 = new PropertyModel<Comparable>(arg0, (String) sp.getProperty());
				PropertyModel<Comparable> model1 = new PropertyModel<Comparable>(arg1, (String) sp.getProperty());

				if (model0.getObject() == null && model1.getObject() == null) {
					result = 0;
				} else if (model0.getObject() == null) {
					result = -1;
				} else if (model1.getObject() == null) {
					result = 1;
				} else {
					result = model0.getObject().compareTo(model1.getObject());
				}
				if (!getSort().isAscending()) {
					result = -result;
				}
				return result;

			}
		};
	}

	@Override
	public IModel<Order> model(Order object) {
		return new Model<Order>(object);
	}


}
