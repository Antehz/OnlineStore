package by.hrychanok.training.shop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import by.hrychanok.training.shop.model.Category;
import by.hrychanok.training.shop.model.Order;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.model.ProductComment;
import by.hrychanok.training.shop.repository.filter.Filter;

public interface ProductService extends BasicService<Product, Long> {

	List<Product> findAll(Filter filter, Pageable page);

	List<Product> findAll(Pageable page);
	
	ProductComment addCommentForProduct(Long productId, Long customerId, String message);

	List<ProductComment> getCommentByProductId(Long id);

	ProductComment getCommentById(Long id);

	void deleteAllComment();

	List<ProductComment> getCommentByCustomerId(Long id);

	void deleteCommentById(Long id);
	
	List<Product> findProductByCategoryId(Long categoryId);
	List<Product> findProductByCategoryName(String name);

	Long count(Filter filter);
	
}
