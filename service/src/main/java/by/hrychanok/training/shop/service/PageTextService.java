package by.hrychanok.training.shop.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import by.hrychanok.training.shop.model.PageText;
import by.hrychanok.training.shop.repository.filter.Filter;

public interface PageTextService extends BasicService<PageText, Long> {

	List<PageText> findByTitle(String title);

	Long count(Filter filter);

	List<PageText> findAll(Filter filter, Sort sort);

}
