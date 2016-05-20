package by.hrychanok.training.shop.service.impl;

import java.util.List;

import org.springframework.data.domain.Sort;

import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.model.ProductComment;
import by.hrychanok.training.shop.repository.ProductCommentRepository;
import by.hrychanok.training.shop.repository.filter.Filter;
import by.hrychanok.training.shop.service.ProductCommentService;

public class ProductCommentServiceImpl extends BasicServiceImpl<ProductComment, ProductCommentRepository, Long>
		implements ProductCommentService {

	@Override
	public List<ProductComment> findAll(Filter filter, Sort sort) {
		return repository.findAll(filter, sort);
	}

}
