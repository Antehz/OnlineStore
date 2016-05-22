package by.hrychanok.training.shop.web.page.cart;

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

import by.hrychanok.training.shop.model.CartContent;
import by.hrychanok.training.shop.repository.filter.Comparison;
import by.hrychanok.training.shop.repository.filter.Condition;
import by.hrychanok.training.shop.repository.filter.Filter;
import by.hrychanok.training.shop.service.CartService;

public class SortableCartDataProvider extends SortableDataProvider<CartContent, String> implements IFilterStateLocator<Filter> {

	private static final long serialVersionUID = 1L;
	
	@SpringBean
	CartService cartService;
	
	Filter filterState = new Filter();
	PageRequest page ;
    Long customerId = 1580L; //!!!!!
    
	public SortableCartDataProvider() {
		Injector.get().inject(this); 
		setSort("product", SortOrder.DESCENDING);
	}

	@Override
	public Iterator<? extends CartContent> iterator(long first, long count) {
		
         int numberPage = (int) (first / count);
         filterState.addCondition(
 				new Condition.Builder().setComparison(Comparison.eq).setField("customer").setValue(customerId).build());
		 String property = getSort().getProperty();
         SortOrder propertySortOrder = getSortState().getPropertySortOrder(property);
		
         Direction direction;
         
         if (propertySortOrder.equals(SortOrder.ASCENDING)) {
        	 direction = Direction.DESC;
         }
         else {
        	 direction = Direction.ASC;
         }
         
         return cartService.findAll(filterState, new PageRequest(numberPage, (int)count, direction, property)).iterator();
	}

	@Override
	public long size() {
		return cartService.count(filterState);
	}

	@Override
	public IModel<CartContent> model(CartContent object) {
		return new Model<CartContent>(object);
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