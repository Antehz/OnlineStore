package by.hrychanok.training.shop.web.page.catalog;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import by.hrychanok.training.shop.model.Order;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.repository.filter.Filter;
import by.hrychanok.training.shop.service.OrderService;
import by.hrychanok.training.shop.service.ProductService;

public class SortableProductDataProvider extends SortableDataProvider<Product, String> implements IFilterStateLocator<Filter> {

	private static final long serialVersionUID = 1L;
	
	@SpringBean
	ProductService productService;
	
	Filter filterState = new Filter();
	
	PageRequest page ;

	public SortableProductDataProvider() {
		Injector.get().inject(this); 
		setSort("id", SortOrder.DESCENDING);
	}

	@Override
	public Iterator<? extends Product> iterator(long first, long count) {
		
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
         
         return productService.findAll(filterState, new PageRequest(numberPage, (int)count, direction, property)).iterator();
	}

	@Override
	public long size() {
		return productService.count(filterState);
	}

	@Override
	public IModel<Product> model(Product object) {
		return new Model<Product>(object);
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