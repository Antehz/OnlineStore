package by.hrychanok.training.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import by.hrychanok.training.shop.model.Category;

public interface CategoryRepositoryCustom {
	
	void addCategory (Category parent, Category child);

	

}
