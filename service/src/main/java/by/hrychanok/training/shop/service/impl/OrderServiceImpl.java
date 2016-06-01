package by.hrychanok.training.shop.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import by.hrychanok.training.shop.model.CartContent;
import by.hrychanok.training.shop.model.Customer;
import by.hrychanok.training.shop.model.Order;
import by.hrychanok.training.shop.model.OrderContent;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.model.ShippingMethod;
import by.hrychanok.training.shop.model.StatusOrder;
import by.hrychanok.training.shop.repository.CartContentRepository;
import by.hrychanok.training.shop.repository.CustomerRepository;
import by.hrychanok.training.shop.repository.OrderContentRepository;
import by.hrychanok.training.shop.repository.OrderRepository;
import by.hrychanok.training.shop.repository.ProductRepository;
import by.hrychanok.training.shop.repository.filter.Filter;
import by.hrychanok.training.shop.service.CustomerService;
import by.hrychanok.training.shop.service.OrderService;
import by.hrychanok.training.shop.service.exeption.ServiceException;

@Service
@Transactional
public class OrderServiceImpl extends BasicServiceImpl<Order, OrderRepository, Long> implements OrderService {

	private static Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	MailService mail;
	@Autowired
	OrderContentRepository orderContentRepository;
	@Autowired
	CustomerService customerService;
	@Autowired
	CartContentRepository cartContentRepository;
	@Autowired
	ProductRepository productRepository;

	@Override
	public Order createOrder(Order order) {
		Customer customer = order.getCustomer();
		order.setStartDate(new Date());
		order.setStatus(StatusOrder.Making);
		List<CartContent> cartContent = customer.getCartContent();
		if (cartContent.isEmpty()) {
			throw new ServiceException(String.format("Cart of customer has id: %s is empty", customer.getId()));
		}
		System.out.println(order);
		repository.save(order);
		transferFromCartToOrder(order, cartContent);
		order.setStatus(StatusOrder.Pending);
		List<OrderContent> orderContent = getOrderContentById(order.getId());
		// mail.sendOrderConfirmationMail(order);
		return order;

	}

	public void transferFromCartToOrder(Order order, List<CartContent> cartContent) {
		Integer priceTotal = 0;
		Product product;
		for (CartContent item : cartContent) {
			OrderContent orderContent = new OrderContent();
			orderContent.setProduct(item.getProduct());
			orderContent.setAmount(item.getAmount());
			Integer price = orderContent.getProduct().getPrice() * orderContent.getAmount();
			orderContent.setPrice(price);
			orderContent.setOrder(order);
			changeProductData(item, orderContent);
			orderContentRepository.save(orderContent);
			priceTotal = priceTotal + price;
		}
		cartContentRepository.clearCustomerCartContent(order.getCustomer().getId());
	}

	private void changeProductData(CartContent item, OrderContent orderContent) {
		Product product;
		product = productRepository.findOne(item.getProduct().getId());
		Integer currentAvailable = product.getAvailable();
		product.setAvailable(currentAvailable - orderContent.getAmount());
		Integer currentCountOrder = product.getCountOrder()+orderContent.getAmount();
		product.setCountOrder(currentCountOrder++);
		productRepository.save(product);
	}

	@Override
	public Customer getCustomerByOrderId(Long id) {
		return repository.findOne(id).getCustomer();

	}

	@Override
	public List<OrderContent> getOrderContentById(Long id) {

		return repository.findOne(id).getOrderContent();

	}

	@Override
	public List<Order> getOrdersByCustomer(Long id) {

		return repository.getOrderListByCustomer(id);
	}

	@Override
	public void delete(Long id) {
		LOGGER.info(String.format("Delete order id: ", id));
		Order order = repository.findOne(id);
		if (order == null) {
			LOGGER.info(String.format("Order id: %s not found", id));
		} else {
			StatusOrder statusOrder = order.getStatus();
			boolean deleteOrderCondition = statusOrder.equals(StatusOrder.Cancelled)
					|| statusOrder.equals(StatusOrder.Pending);
			if (deleteOrderCondition) {
				repository.delete(id);
				LOGGER.info(String.format("Order id: %s has been deleted", id));
			} else {
				throw new ServiceException("This order can not be deleted, cause - order has status confirmed");
			}
		}

	}

	@Override
	public Order changeStatusOrder(Long id, StatusOrder statusOrder) {
		Order order = repository.findOne(id);
		order.setStatus(statusOrder);
		return repository.save(order);
	}

	@Override
	public List<Order> findOrdersContainGivenProduct(Long productId) {

		return orderContentRepository.findByProductId(productId);
	}

	@Override
	public List<Order> getByStatus(StatusOrder status) {
		return repository.getByStatus(status);
	}

	@Override
	public Long count(Filter filter) {
		if (filter.existCondition()) {
			return repository.count(filter);
		} else {
			return repository.count();
		}
	}

	@Override
	public List<Order> findAll(Filter filter, Pageable page) {
		if (filter.existCondition()) {
			return repository.findAll(filter, page).getContent();
		} else {
			return repository.findAll(page).getContent();
		}
	}

	@Override
	public List<Order> findAll(Pageable page) {
		return repository.findAll(page).getContent();
	}

	@Override
	public List<OrderContent> findAllContent(Filter filter, Pageable page) {
		return orderContentRepository.findAll(filter, page).getContent();
	}

	@Override
	public Long countContent(Filter filter) {
		if (filter.existCondition()) {
			return orderContentRepository.count(filter);
		} else {
			return orderContentRepository.count();
		}
	}

}
