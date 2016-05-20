package by.hrychanok.training.shop.web.page.personalCabinet;

import java.util.Iterator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import by.hrychanok.training.shop.model.Order;
import by.hrychanok.training.shop.repository.filter.Filter;
import by.hrychanok.training.shop.service.OrderService;

public class SortableTypeDataProvider extends SortableDataProvider<Order, String> implements IFilterStateLocator<Filter> {

	private static final long serialVersionUID = 1L;
	
	@SpringBean
	OrderService orderService;
	
	Filter filterState = new Filter();
	
	PageRequest page ;

	public SortableTypeDataProvider() {
		Injector.get().inject(this); 
		setSort("id", SortOrder.DESCENDING);
	}

	@Override
	public Iterator<? extends Order> iterator(long first, long count) {
		
		/**
		 * get current number page for load into pageable object 
		 */
         int numberPage = (int) (first / count);
       
		 String property = getSort().getProperty();
         SortOrder propertySortOrder = getSortState().getPropertySortOrder(property);
		
         Direction direction;
         
         if (propertySortOrder.equals(SortOrder.ASCENDING)) {
        	 direction = Direction.DESC;
         }
         else {
        	 direction = Direction.ASC;
         }
         
         return orderService.findAll(filterState, new PageRequest(numberPage, (int)count, direction, property)).iterator();
	}

	@Override
	public long size() {
		return orderService.count(filterState);
	}

	@Override
	public IModel<Order> model(Order object) {
		return new Model<Order>(object);
	}

	@Override
	public Filter getFilterState() {
		return filterState;
	}

	@Override
	public void setFilterState(Filter filterState) {
		this.filterState=filterState;
	}


}
