package by.hrychanok.training.shop.service;

import java.util.List;

import by.hrychanok.training.shop.model.Category;

public interface CategoryService {
 
	Category  findByName(String name);
	Category findRoot();
	List<Category> findByChildren(Category cat);
	void saveCategory (Long parentId, String name, String description);
	void deleteCategoryBransh(String name);
	Category save(Category category);
	Category findOne(Long id);
	List<Category> findAll();
	List<String> findAllName();
}
