package by.hrychanok.training.shop.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.hrychanok.training.shop.model.CartContent;
import by.hrychanok.training.shop.model.Customer;
import by.hrychanok.training.shop.model.CustomerCredentials;
import by.hrychanok.training.shop.repository.CartContentRepository;
import by.hrychanok.training.shop.repository.CustomerCredentialsRepository;
import by.hrychanok.training.shop.repository.CustomerRepository;
import by.hrychanok.training.shop.repository.filter.CustomerFilter;
import by.hrychanok.training.shop.repository.filter.Filter;
import by.hrychanok.training.shop.service.CustomerService;
import by.hrychanok.training.shop.service.exeption.ServiceException;
import by.hrychanok.training.shop.model.Order;
import by.hrychanok.training.shop.model.ProductComment;

@Service
@Transactional
public class CustomerServiceImpl extends BasicServiceImpl<Customer, CustomerRepository, Long>
		implements CustomerService {
	private static Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

	public String text = "This;s TEST";

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Autowired
	CustomerCredentialsRepository customerCredentialsRepository;

	@Autowired
	MailService mail;

	@Override
	public CustomerCredentials getCustomerByCredentials(String login, String password) {
		Customer customer = null;
		LOGGER.debug("Get customer by credentials  login:{}, password: {}", login, password);
		CustomerCredentials customerCredentials = customerCredentialsRepository.findByLoginAndPassword(login, password);
		if (customerCredentials != null) {
			customer = customerCredentials.getCustomer();
			LOGGER.info("Customer {} {} has been found", customer.getFirstName(), customer.getLastName());
		} else {
			LOGGER.warn("Customer with credentials login: {} , password:  {} has not been found!", login, password);
		}
		return customer.getCustomerCredentials();
	}

	@Override
	public Customer registerCustomer(Customer customer, CustomerCredentials customerCredentials) {
		boolean exist = checkExistUser(customerCredentials.getLogin(), customer.getEmail());
		System.out.println(exist);
		if (!exist) {
			customer.setCustomerCredentials(customerCredentials);
			customer = repository.save(customer);
			if (customer != null) {
				LOGGER.info("Customer succesfully registred : {}", customer);
				// mail.sendRegistrationNotificationMail(customer);
			}
		} else {
			throw new ServiceException(String.format("Customer with login: %s, or EMail: %s already exist!",
					customerCredentials.getLogin(), customer.getEmail()));
		}
		return customer;
	}

	@Override
	public List<Customer> find(CustomerFilter filter) {
		List<Customer> listFiltered = repository.find(filter);
		if (listFiltered.isEmpty()) {
			LOGGER.debug("Not found any matches ");
		}
		return listFiltered;
	}

	private Boolean checkExistUser(String login, String email) {
		CustomerFilter cFilter = new CustomerFilter();
		cFilter.setEmail(email);
		cFilter.setLogin(login);
		List<Customer> list = repository.find(cFilter);
		if (list.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public CustomerCredentials getCredentials(Long id) {
		return repository.findOne(id).getCustomerCredentials();
	}

	@Override
	public Long count(CustomerFilter filter) {
		return repository.count(filter);
	}

	@Override
	public Collection<? extends String> resolveRoles(Long id) {
		CustomerCredentials customerCredentials = customerCredentialsRepository.findOne(id);
		return Collections.singletonList(customerCredentials.getRole().name());
	}

	@Override
	public Boolean loginIsAvailable(String login) {
		if (customerCredentialsRepository.findByLogin(login) != null) {
			return false;
		} else {
			return true;

		}
	}

	@Override
	public Boolean emailIsAvailable(String email) {
		if (repository.findByEmail(email) != null) {
			return false;
		}
		return true;

	}

	@Override
	public List<Customer> findAll(Filter filter, Pageable page) {
		if (filter.existCondition()) {
			return repository.findAll(filter, page).getContent();
		}
		return repository.findAll(page).getContent();

	}

	@Override
	public Long count(Filter filter) {
		if (filter.existCondition()) {
			return repository.count(filter);
		}
		return repository.count();

	}
}
