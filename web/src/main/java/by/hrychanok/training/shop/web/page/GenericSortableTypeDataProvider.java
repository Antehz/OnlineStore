package by.hrychanok.training.shop.web.page;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import by.hrychanok.training.shop.model.AbstractModel;
import by.hrychanok.training.shop.model.CartContent;
import by.hrychanok.training.shop.model.CustomerCredentials;
import by.hrychanok.training.shop.repository.filter.Comparison;
import by.hrychanok.training.shop.repository.filter.Condition;
import by.hrychanok.training.shop.repository.filter.Filter;
import by.hrychanok.training.shop.service.CartService;
import by.hrychanok.training.shop.service.OrderService;
import by.hrychanok.training.shop.service.ProductService;
import by.hrychanok.training.shop.web.app.AuthorizedSession;

public abstract class GenericSortableTypeDataProvider<T extends AbstractModel> extends SortableDataProvider<T, String> implements IFilterStateLocator<Filter> {

	private static final long serialVersionUID = 1L;
	
	@SpringBean
	CartService cartService;
	
	@SpringBean
	OrderService orderService;
	
	@SpringBean
	ProductService productService;
	
	
	protected CustomerCredentials customer;
	protected Filter filterState;
    
	public GenericSortableTypeDataProvider(Filter filterState) {
		customer = AuthorizedSession.get().getLoggedUser();
		Injector.get().inject(this); 
		this.filterState=filterState;
		setSort("id", SortOrder.DESCENDING);
	}

	@Override
	public Iterator<? extends T> iterator(long first, long count) {
		
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
         PageRequest pageRequest =  new PageRequest(numberPage, (int)count, direction, property);
         
         return returnIterator(pageRequest);
	}

	public abstract Iterator<? extends T> returnIterator(PageRequest pageRequest);
	
	@Override
	public abstract long size() ;

	@Override
	public IModel<T> model(T object) {
		return new Model<T>(object);
	}

	@Override
	public Filter getFilterState() {
		return filterState;
	}

	@Override
	public void setFilterState(Filter filterState) {
		this.filterState=filterState;
	}
	
	public void addConditionToFilterState(List<Condition> listConditions){
		for (Condition condition : listConditions) {
			filterState.addCondition(condition);
		}
	}

}