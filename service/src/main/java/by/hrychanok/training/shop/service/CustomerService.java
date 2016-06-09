package by.hrychanok.training.shop.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
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

	CustomerCredentials getCustomerByCredentials(String login, String password);

	Customer registerCustomer(Customer customer, CustomerCredentials customerCredentials);

	List<Customer> find(CustomerFilter filter);

	CustomerCredentials getCredentials(Long id);

	String getText();

	Long count(CustomerFilter filter);

	Collection<? extends String> resolveRoles(Long id);
	
	Boolean loginIsAvailable(String login);
	Boolean emailIsAvailable(String email);
	
	public List<Customer> findAll(Filter filter, Pageable page);

	public Long count(Filter filter);
}
