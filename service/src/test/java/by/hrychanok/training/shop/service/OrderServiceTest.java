package by.hrychanok.training.shop.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import by.hrychanok.training.shop.model.CartContent;
import by.hrychanok.training.shop.model.Category;
import by.hrychanok.training.shop.model.Customer;
import by.hrychanok.training.shop.model.CustomerCredentials;
import by.hrychanok.training.shop.model.Gender;
import by.hrychanok.training.shop.model.Order;
import by.hrychanok.training.shop.model.OrderContent;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.model.Season;
import by.hrychanok.training.shop.model.ShippingMethod;
import by.hrychanok.training.shop.model.StatusOrder;
import by.hrychanok.training.shop.model.Tire;
import by.hrychanok.training.shop.model.TireDestination;
import by.hrychanok.training.shop.model.UserRole;
import by.hrychanok.training.shop.repository.filter.Comparison;
import by.hrychanok.training.shop.repository.filter.Condition;
import by.hrychanok.training.shop.repository.filter.Filter;
import by.hrychanok.training.shop.service.configuration.TestDataBaseConfig;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestDataBaseConfig.class)
@WebAppConfiguration
public class OrderServiceTest {

	@Resource
	private ProductService productService;
	@Resource
	private CustomerService customerService;
	@Resource
	private CartService cartService;
	@Resource
	CategoryService categoryService;
	@Resource
	private OrderService orderService;
	Product product;
	Customer customer;
	Order order;
	List<Order> orderList;

	/*@Before*/
	public void preparation() {
		
		orderService.deleteAll();
		productService.deleteAll();
		customerService.deleteAll();
		orderService.deleteAll();
		int count = 7;
		for (int i = 0; i < count; i++) {
			Tire tire = new Tire();
			tire.setName(i + "Product");
			tire.setManufacturer(i + "manufacturerProduct");
			tire.setModel(i + "modelProduct");
			tire.setImageURL("imageProduct ");
			tire.setDescription("description product Product");
			tire.setCountRecommended(i);
			tire.setAvailable(i + 10);
			Category category = categoryService.findByName("Зимние");
			tire.setCategory(category);
			tire.setCountOrder(3 * i);
			tire.setPrice(5000000 * i);
			tire.setProfileHeight(22 + i * 2);
			tire.setProfileWidth(20 + i * 4);
			tire.setRimSize(4);
			tire.setSpikes(false);
			tire.setTireDestination(TireDestination.car);
			tire.setTireSeason(Season.summer);
			product = productService.save(tire);

			CustomerCredentials customerCredentials = new CustomerCredentials();
			customer = new Customer();
			customerCredentials.setLogin(String.format(i + "testUser"));
			customerCredentials.setPassword("testPassword");
			customerCredentials.setRole(UserRole.Customer);
			customer.setFirstName(i + "testFirstName");
			customer.setLastName(i + "testLastName");
			customer.setEmail(String.format(i + "testEmail@ya.ru"));
			customer.setAddress("Gorkogo" + 50 + i);
			customer.setCity("Hrodno");
			customer.setCountry("Belarus");
			customer.setDateBirth(new Date(1970, 1, 1));
			customer.setCreated(new Date());
			customer.setGender(Gender.UNKNOWN);
			customer.setZipCode("230020");
			customerService.registerCustomer(customer, customerCredentials);

			CartContent cart = new CartContent();
			int amount = i;
			cartService.addProductToCart(product.getId(), customer.getId(), amount);
			order = orderService.createOrder(customer.getId(), ShippingMethod.Courier, "Test additional information");
		}
		orderList = orderService.findAll();
	}

	@Test
	public void createOrder() {
		Random random = new Random();
		int randomIndex = random.nextInt(7);
		Order order = orderList.get(randomIndex);
		Assert.assertNotNull(orderService.findOne(order.getId()));
		List<OrderContent> listContent = orderService.getOrderContentById(order.getId());
		Assert.assertNotNull(listContent);
	}

	@Test
	public void deleteOrderAndGetOrderContent() {
		Random random = new Random();
		int randomIndex = random.nextInt(7);
		Order order = orderList.get(randomIndex);
		List<OrderContent> listOrderContent = orderService.getOrderContentById(order.getId());
		Assert.assertFalse(listOrderContent.isEmpty());
		orderService.delete(order.getId());
		Assert.assertNull(orderService.findOne(order.getId()));
	}

	@Test
	public void getOrderById() {
		Random random = new Random();
		int randomIndex = random.nextInt(7);
		Order order = orderList.get(randomIndex);
		Long orderId = order.getId();
		Assert.assertNotNull(orderService.findOne(orderId));
	}

	@Test
	public void changeStatusOrder() {
		Random random = new Random();
		int randomIndex = random.nextInt(7);
		Order order = orderList.get(randomIndex);
		StatusOrder statusOrderBefore = order.getStatus();
		orderService.changeStatusOrder(order.getId(), StatusOrder.Cancelled);
		StatusOrder statusOrderAfter = orderService.findOne(order.getId()).getStatus();
		Assert.assertNotEquals(statusOrderBefore, statusOrderAfter);
	}

	@Test
	public void filterOrder() {
		Filter filter = new Filter();
	    PageRequest page1 = new PageRequest(0, 4, Direction.DESC, "totalPrice");
	    
		Page<Order> orderPages = orderService.findAllPage(filter, new PageRequest(0, 5, Direction.DESC, "totalPrice"));
		for (Order order : orderPages) {
			System.out.println(order);
		}
		/*int countFirstFilter = orderPages.size();
		Assert.assertFalse(orderPages.isEmpty());
		filter.addCondition(new Condition.Builder().setComparison(Comparison.between).setField("totalPrice")
				.setValue(10000000).setLimitValue(40000000).build());
		orderPages = orderService.findAll(filter, page1);
		int countSecondFilter = orderPages.size();
		Assert.assertTrue(countFirstFilter > countSecondFilter);*/
	}

	@Test
	public void getOrdersByStatus() {
		List<Order> list = orderService.getByStatus(StatusOrder.Pending);
		for (Order order : list) {
			System.out.println(order);
		}
	}

	public void clearAll() {
		orderService.deleteAll();
		productService.deleteAll();
		customerService.deleteAll();
		orderService.deleteAll();
	}

}
