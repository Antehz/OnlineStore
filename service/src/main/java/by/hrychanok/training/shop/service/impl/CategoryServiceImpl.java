package by.hrychanok.training.shop.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.hrychanok.training.shop.model.Category;
import by.hrychanok.training.shop.repository.CategoryRepository;
import by.hrychanok.training.shop.service.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	private static Logger LOGGER = LoggerFactory.getLogger(CartServiceImpl.class);

	@Autowired
	CategoryRepository repository;

	@Override
	public Category findRoot() {
		Category root = repository.findByParentIsNull();
		if (root==null) {
			LOGGER.debug("No found any rootes");
		}
		return root;
	}

	@Override
	public List<Category> findByChildren(Category cat) {
		List<Category> childrenList = repository.findByParentIs(cat);
		if (childrenList.isEmpty()) {
			LOGGER.debug("Given category id %s ,  doesn't have any children", cat);
		}
		return childrenList;
	}

	@Override
	public void saveCategory(Long parentId, String name, String description) {
		Category parent = repository.findOne(parentId);
		Category newCategory = new Category(parent, name, description);
		repository.save(newCategory);

	}

	@Override
	public Category findByName(String name) {

		return repository.findByName(name);
	}

	@Override
	public void deleteCategoryBransh(String name) {

		repository.deleteByName(name);

	}

	@Override
	public Category save(Category category) {
		return repository.save(category);

	}

	@Override
	public Category findOne(Long id) {
		return repository.findOne(id);
	}

	@Override
	public List<Category> findAll() {
		return repository.findAll();
	}

}
