package by.hrychanok.training.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import by.hrychanok.training.shop.model.Category;
import by.hrychanok.training.shop.model.Product;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	Category findByName(String name);
	List<Category> findByParentIs(Category cat);
	Category findByParentIsNull();
	void deleteByName(String name);

}
