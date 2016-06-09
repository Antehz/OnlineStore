package by.hrychanok.training.shop.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import by.hrychanok.training.shop.model.CartContent;
import by.hrychanok.training.shop.model.Customer;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.repository.filter.Filter;

public interface CartService {
	List<CartContent> getCustomerCartContent(Long customerId);

	Boolean addProductToCart(Long productId, Long customerId, Integer amount);

	void deleteProductFromCart(Long id);

	void clearCustomerCartContent(Long id);

	CartContent findById(Long id);

	public void save(CartContent cart);

	public List<CartContent> findAll(Filter filter, Pageable page);

	public Long count(Filter filter);
	
	public Integer getTotalPriceCart(Long customerId);
	
}
