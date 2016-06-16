package by.hrychanok.training.shop.service;

import java.util.List;

import by.hrychanok.training.shop.model.Category;

public interface CategoryService  extends BasicService<Category, Long>{

	Category findRoot();

	List<Category> findByChildren(Category cat);

}
