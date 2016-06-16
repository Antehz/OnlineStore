package by.hrychanok.training.shop.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import by.hrychanok.training.shop.repository.filter.Filter;
import by.hrychanok.training.shop.model.Customer;
import by.hrychanok.training.shop.model.PageText;
import by.hrychanok.training.shop.repository.PageTextRepository;
import by.hrychanok.training.shop.repository.filter.Filter;
import by.hrychanok.training.shop.service.PageTextService;

@Service
@Transactional
public class PageTextServiceImpl extends BasicServiceImpl<PageText, PageTextRepository, Long>
		implements PageTextService {

	private static Logger LOGGER = LoggerFactory.getLogger(PageTextServiceImpl.class);

	@Override
	public List<PageText> findByTitle(String title) {
		return repository.findByTitle(title);
	}

	@Override
	public List<PageText> findAll(Filter filter, Sort sort) {
		if (filter.existCondition()) {
			return repository.findAll(filter, sort);
		}
		return repository.findAll(sort);

	}

	@Override
	public Long count(Filter filter) {
		if (filter.existCondition()) {
			return repository.count(filter);
		}
		return repository.count();

	}

}
