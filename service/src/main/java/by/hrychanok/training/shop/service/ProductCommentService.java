package by.hrychanok.training.shop.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import by.hrychanok.training.shop.model.ProductComment;
import by.hrychanok.training.shop.repository.filter.Filter;

public interface ProductCommentService extends BasicService<ProductComment, Long> {
	
	List<ProductComment> findAll(Filter filter, Sort sort);

}
