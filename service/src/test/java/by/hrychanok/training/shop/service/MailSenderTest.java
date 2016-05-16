package by.hrychanok.training.shop.service;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import by.hrychanok.training.shop.model.CartContent;
import by.hrychanok.training.shop.model.Category;
import by.hrychanok.training.shop.model.Customer;
import by.hrychanok.training.shop.model.CustomerCredentials;
import by.hrychanok.training.shop.model.Gender;
import by.hrychanok.training.shop.model.Order;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.model.ShippingMethod;
import by.hrychanok.training.shop.model.UserRole;
import by.hrychanok.training.shop.service.configuration.TestDataBaseConfig;
import by.hrychanok.training.shop.service.impl.MailService;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestDataBaseConfig.class)
@WebAppConfiguration
public class MailSenderTest {

	@Resource
	private ProductService productService;
	@Resource
	private CartService cartService;
	@Resource
	private OrderService orderService;
	@Resource
	MailService mailService;
	@Resource
	CustomerService customerService;
	Customer customer;
	Product product;
	Order order;
	@Test
	public void registerCustomer(){
		customerService.deleteAll();
		Customer cTest = new Customer();
		CustomerCredentials cCredentials = new CustomerCredentials();
		cCredentials.setLogin(String.format("ASnte"));
		cCredentials.setPassword("mts5402283");
		cCredentials.setRole(UserRole.Customer);
		cTest.setFirstName("Anton");
		cTest.setLastName("Hrychanok");
		cTest.setEmail(String.format("ardasss@ya.ru"));
		Random x = new Random();
		int s = x.nextInt(6);
		cTest.setAddress("Gorkogo " + s);
		cTest.setCity("Hrodno");
		cTest.setCountry("Belarus " + s);
		cTest.setDateBirth(new Date(12 - 12 - 2000));
		cTest.setCreated(new Date());
		cTest.setGender(Gender.UNKNOWN);
		cTest.setZipCode("230020");
		customer = customerService.registerCustomer(cTest, cCredentials);
	}
	@Test
	public void orderCreateNotification(){
		orderService.deleteAll();
		productService.deleteAll();
		customerService.deleteAll();
		orderService.deleteAll();
		
		product = new Product();
		product.setName("name");
		product.setManufacturer("manufacturer");
		product.setModel("model");
		product.setImageURL("image");
		product.setDescription("description product");
		product.setCountRecommended(0);
		product.setAvailable(5);
		Category cat = new Category();
		product.setCategory(cat);
		product.setCountOrder(7);
		product.setPrice(100000);
		productService.save(product);

		CustomerCredentials customerCredentials = new CustomerCredentials();
		customer = new Customer();
		customerCredentials.setLogin(String.format("Antehz"));
		customerCredentials.setPassword("testPassword");
		customerCredentials.setRole(UserRole.Customer);
		customer.setFirstName("Anton");
		customer.setLastName("Lastname");
		customer.setEmail(String.format("ardasss@ya.ru"));
		customer.setAddress("Gorkogo 75");
		customer.setCity("Hrodno");
		customer.setCountry("Belarus");
		customer.setDateBirth(new Date(1970, 1, 1));
		customer.setCreated(new Date());
		customer.setGender(Gender.UNKNOWN);
		customer.setZipCode("230020");
		customerService.registerCustomer(customer, customerCredentials);

		CartContent cart = new CartContent();
		int amount =5;
		cartService.addProductToCart(product.getId(), customer.getId(), amount);
		order = orderService.createOrder(customer.getId(), ShippingMethod.Courier, "Test additional information");
		
	}
	@Test
	public void mailTest(){
		mailService.sendMail("ardasss@ya.ru", "Hello", "This's the body of message"); 
	}
}
