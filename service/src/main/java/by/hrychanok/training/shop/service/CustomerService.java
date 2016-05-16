package by.hrychanok.training.shop.service;



import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import by.hrychanok.training.shop.model.CartContent;
import by.hrychanok.training.shop.model.Customer;
import by.hrychanok.training.shop.model.CustomerCredentials;
import by.hrychanok.training.shop.model.Order;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.model.ProductComment;
import by.hrychanok.training.shop.repository.filter.CustomerFilter;
import by.hrychanok.training.shop.repository.filter.Filter;
public interface CustomerService extends BasicService<Customer, Long> {

	Customer getCustomerByCredentials(String login, String password);
	Customer registerCustomer(Customer customer, CustomerCredentials customerCredentials);
	List<Customer> find(CustomerFilter filter);
	String getText();
}
