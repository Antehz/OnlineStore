package by.hrychanok.training.shop.repository;

import java.util.List;

import by.hrychanok.training.shop.model.Customer;
import by.hrychanok.training.shop.repository.filter.CustomerFilter;

public interface CustomerRepositoryCustom {
	List<Customer> find(CustomerFilter filter);
	Long count(CustomerFilter filter);
}
