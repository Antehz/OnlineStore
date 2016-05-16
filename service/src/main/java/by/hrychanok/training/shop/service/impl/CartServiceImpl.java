package by.hrychanok.training.shop.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.hrychanok.training.shop.model.CartContent;
import by.hrychanok.training.shop.model.Customer;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.repository.CartContentRepository;
import by.hrychanok.training.shop.repository.CustomerRepository;
import by.hrychanok.training.shop.service.CartService;
import by.hrychanok.training.shop.service.CustomerService;
import by.hrychanok.training.shop.service.ProductService;
import by.hrychanok.training.shop.service.exeption.ServiceException;

@Service
@Transactional
public class CartServiceImpl implements CartService {
	private static Logger LOGGER = LoggerFactory.getLogger(CartServiceImpl.class);

	@Autowired
	CartContentRepository repository;
	@Autowired
	ProductService productService;
	@Autowired
	CustomerService customerService;

	@Override
	public void clearCustomerCartContent(Long id) {
		repository.clearCustomerCartContent(id);
		LOGGER.info("Cart by customer {} has been cleared", id);
	}

	@Override
	public CartContent addProductToCart(Long productId, Long customerId, Integer amount) {

		List<CartContent> contentCartCustomer = getCustomerCartContent(customerId);
		CartContent cartContent = new CartContent();
		Product product = productService.findOne(productId);
		Customer customer = customerService.findOne(customerId);
		if (product.getAvailable() == 0) {
			LOGGER.debug("Product is missing on the warehouse, id: " + product.getId());
			throw new ServiceException("Given product isn't available");
		}
		cartContent.setProduct(product);
		cartContent.setCustomer(customer);
		cartContent.setAmount(amount);
		cartContent.setDateAdd(new Date());
		cartContent.setPrice(product.getPrice() * amount);

		for (CartContent temp : contentCartCustomer) {
			boolean existItem = temp.getProduct().getId().equals(cartContent.getProduct().getId());
			if (existItem) {
				temp.setAmount(amount);
				temp.setDateAdd(new Date());
				temp.setPrice(temp.getPrice() * amount);
				cartContent = repository.save(temp);
				LOGGER.info("Amount of items {} was changed for customer {} cart", product.getId(), customer.getId());
			}
		}
		cartContent = repository.save(cartContent);
		LOGGER.info("Item {} has been added to cart customer id:", product.getId(), customer.getId());
		return cartContent;
	}
	@Override
	public List<CartContent> getCustomerCartContent(Long id) {
		return repository.getCartContentById(id);
	}
	@Override
	public void deleteProductFromCart(Long id) {
		repository.delete(id);
	}
	@Override
	public CartContent findById(Long id) {
		return repository.findOne(id);
	}
}
