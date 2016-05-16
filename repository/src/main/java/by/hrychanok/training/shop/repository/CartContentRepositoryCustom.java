package by.hrychanok.training.shop.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import by.hrychanok.training.shop.model.CartContent;

public interface CartContentRepositoryCustom {
	List<CartContent> getCartContentById( Long id);

}
