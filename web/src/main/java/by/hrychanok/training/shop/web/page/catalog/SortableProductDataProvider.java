package by.hrychanok.training.shop.web.page.catalog;

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
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.repository.filter.Filter;

public class SortableProductDataProvider extends SortableDataProvider<Product, String> implements IFilterStateLocator<Filter> {

	private static final long serialVersionUID = 1L;
	private List<Product> listType;

	public SortableProductDataProvider() {
		this(Collections.<Product> emptyList());
	}

	public SortableProductDataProvider(List<Product> listType) {

		if (listType == null) {
			throw new IllegalArgumentException("argument [list] cannot be null");
		}

		this.listType = listType;

		setSort("id", SortOrder.ASCENDING);
	}

	public List<Product> getData() {
		return listType;
	}

	public void setListType(List<Product> listType) {
		this.listType = listType;
	}

	@Override
	public Iterator<? extends Product> iterator(long first, long count) {

		List<Product> listType = getData();

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

	protected Comparator<Product> getComparator(final SortParam sp) {
		return new Comparator<Product>() {
			@Override
			public int compare(Product arg0, Product arg1) {

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
	public IModel<Product> model(Product object) {
		return new Model<Product>(object);
	}


}
