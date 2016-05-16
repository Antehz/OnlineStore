package by.hrychanok.training.shop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import by.hrychanok.training.shop.model.Customer;
import by.hrychanok.training.shop.model.Order;
import by.hrychanok.training.shop.model.OrderContent;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.model.ShippingMethod;
import by.hrychanok.training.shop.model.StatusOrder;
import by.hrychanok.training.shop.repository.filter.Filter;

public interface OrderService  extends BasicService<Order, Long>  {
	Order createOrder(Long customerId, ShippingMethod shippingMethod, String additionalInfo);

	List<Order> getOrdersByCustomer(Long id);

	Customer getCustomerByOrderId(Long id);

	List<OrderContent> getOrderContentById(Long id);

	Order changeStatusOrder(Long id, StatusOrder statusOrder);

	Page<Order> findAll(Filter filter, Pageable page);
	
	List<Order> findOrdersContainGivenProduct(Long productId);
	
	List<Order>  getByStatus(StatusOrder status);
	
}
