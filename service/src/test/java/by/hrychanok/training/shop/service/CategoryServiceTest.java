package by.hrychanok.training.shop.service;

import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import by.hrychanok.training.shop.model.Category;
import by.hrychanok.training.shop.service.configuration.TestDataBaseConfig;
import junit.framework.Assert;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestDataBaseConfig.class)
@WebAppConfiguration
public class CategoryServiceTest {

	@Autowired
	CategoryService categoryService;

	@Test
	public void findRootes() {
		Category result = categoryService.findRoot();
		Assert.assertFalse(result==null);

	}

	public void createCategory() {
		Category root = new Category(null, "root", "description root");
		categoryService.save(root);
		Category child1 = new Category(root, "child1", "description of child1");
		categoryService.save(child1);
		Category child2 = new Category(root, "child2", "description of child2");
		categoryService.save(child2);
	}

	@Test
	public void findChildrenAndRootes() {
		Category root = categoryService.findRoot();
		List<Category> children = root.getChildren();
		Assert.assertFalse(children.isEmpty());

	}

	@Test
	public void deleteBranch() {
		Category category = categoryService.findByName("root");
		Assert.assertNotNull(category);
		categoryService.deleteCategoryBransh("root");
		category = categoryService.findByName("root");
		Assert.assertNull(category);
	}
	@After
	public void deleteRootes(){
		categoryService.deleteCategoryBransh("root");
	}
}
