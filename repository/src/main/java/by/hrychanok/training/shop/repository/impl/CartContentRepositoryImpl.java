package by.hrychanok.training.shop.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import by.hrychanok.training.shop.model.CartContent;
import by.hrychanok.training.shop.repository.CartContentRepositoryCustom;
import by.hrychanok.training.shop.repository.CustomerRepository;

public class CartContentRepositoryImpl implements CartContentRepositoryCustom {

	@Autowired
	CustomerRepository customerRepository;

	@Override
	public List<CartContent> getCartContentById(Long id) {
		return customerRepository.findOne(id).getCartContent();
	}

}
