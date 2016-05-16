package by.hrychanok.training.shop.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.hrychanok.training.shop.model.Category;
import by.hrychanok.training.shop.model.Customer;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.model.ProductComment;
import by.hrychanok.training.shop.repository.CategoryRepository;
import by.hrychanok.training.shop.repository.CustomerRepository;
import by.hrychanok.training.shop.repository.ProductCommentRepository;
import by.hrychanok.training.shop.repository.ProductRepository;
import by.hrychanok.training.shop.repository.filter.Filter;
import by.hrychanok.training.shop.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl extends BasicServiceImpl<Product, ProductRepository, Long> implements ProductService {
	private static Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ProductCommentRepository productCommentRepository;
	@Autowired
	CustomerRepository customerRepository;

/*	@Override
	public Category findCategoryByProductId(Long id) {
		Category category = categoryRepository.findCategoryById(id);
		if (category == null) {
			LOGGER.debug(String.format("Category for id: %s not found", id));
		}
		return category;
	}*/

	@Override
	public List<Product> findProductByCategoryId(Long id) {
		List<Product> list = repository.getProductByCategoryId(id);
		LOGGER.debug("Get list of products by caregory id {} has been done", id);
		return list;
	}

	@Override
	public List<Product> findAll(Filter filter, Pageable page) {
		return repository.findAll(filter, page).getContent();
	}

	@Override
	public ProductComment addCommentForProduct(Long productId, Long customerId, String message) {
		Customer customer = customerRepository.findOne(customerId);
		Product product = repository.findOne(productId);
		ProductComment productComment = new ProductComment();
		productComment.setComment(message);
		productComment.setCustomer(customer);
		productComment.setProduct(product);
		productComment.setDate(new Date());
		productComment = productCommentRepository.save(productComment);
		LOGGER.info(String.format("Commentary has been added customer id: %s", customerId));
		return productComment;
	}

	@Override
	public List<ProductComment> getCommentByProductId(Long id) {
		return productCommentRepository.getByProductId(id);
	}

	@Override
	public void deleteAllComment() {
		productCommentRepository.deleteAll();
		LOGGER.info("All products was deleted");
	}

	@Override
	public List<ProductComment> getCommentByCustomerId(Long id) {
	      List<ProductComment> listComment = productCommentRepository.getByCustomerId(id);
	      if(listComment.isEmpty()){
	    	  LOGGER.debug(String.format("Customer id: %s doesn't have comments for any products", id));
	      }
		return listComment;
	}

	@Override
	public void deleteCommentById(Long id) {
		productCommentRepository.delete(id);
		
	}

	@Override
	public ProductComment getCommentById(Long id) {
		return productCommentRepository.findOne(id);
	}

	@Override
	public List<Product> findProductByCategoryName(String name) {
		return repository.getProductByCategoryName(name);
	}

}
