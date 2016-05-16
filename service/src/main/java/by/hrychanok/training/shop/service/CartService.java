package by.hrychanok.training.shop.service;

import java.util.List;

import by.hrychanok.training.shop.model.CartContent;
import by.hrychanok.training.shop.model.Customer;

public interface CartService    {
	
	CartContent addProductToCart(Long productId, Long customerId, Integer amount);
	void deleteProductFromCart(Long id);
	List<CartContent> getCustomerCartContent(Long id);
	void clearCustomerCartContent(Long id);
	CartContent findById(Long id);
	
}
