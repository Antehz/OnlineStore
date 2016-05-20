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

	@Override
	public Order createOrder(Long customerId, ShippingMethod shippingMethod, String additionalInfo) {
		Order order = new Order();
		Customer customer = customerService.findOne(customerId);
		if (customer == null) {
			throw new ServiceException(String.format("Customer has id: %s not found", customerId));
		}
		order.setCustomer(customer);
		order.setStartDate(new Date());
		order.setShippingMethod(shippingMethod);
		order.setAdditionalInfo(additionalInfo);
		order.setStatus(StatusOrder.Making);
		List<CartContent> cartContent = customer.getCartContent();
		if (cartContent.isEmpty()) {
			throw new ServiceException(String.format("Cart of customer has id: %s is empty", customerId));
		}
		repository.save(order);
		Integer priceTotal = transferFromCartToOrder(order, cartContent);
		priceTotal = shippingInfluenceOnPrice(priceTotal, shippingMethod);
		order.setTotalPrice(priceTotal);
		order.setStatus(StatusOrder.Pending);
		List<OrderContent> orderContent = getOrderContentById(order.getId());
		mail.sendOrderConfirmationMail(order);
		return repository.save(order);

	}

	public Integer transferFromCartToOrder(Order order, List<CartContent> cartContent) {
		Integer priceTotal = 0;
		for (CartContent item : cartContent) {
			OrderContent orderContent = new OrderContent();
			orderContent.setProduct(item.getProduct());
			orderContent.setAmount(item.getAmount());
			Integer price = orderContent.getProduct().getPrice() * orderContent.getAmount();
			orderContent.setPrice(price);
			orderContent.setOrder(order);
			orderContentRepository.save(orderContent);
			priceTotal = priceTotal + price;
		}
		cartContentRepository.clearCustomerCartContent(order.getCustomer().getId());
		return priceTotal;
	}

	public Integer shippingInfluenceOnPrice(Integer priceTotal, ShippingMethod shippingMethod) {
		int costCourierDelivery = 70000;
		boolean freeShip = ShippingMethod.Courier.equals(shippingMethod) && priceTotal > 500000
				|| ShippingMethod.Pickup.equals(shippingMethod);
		if (freeShip) {
			return priceTotal;
		} else {
			return priceTotal + costCourierDelivery;
		}
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
	public Page<Order> findAllPage(Filter filter, Pageable page) {
		if (filter.existCondition()) {
			return repository.findAll(filter, page);
		} else {
			return repository.findAll(page);
		}
	}

}
