package by.hrychanok.training.shop.service.impl;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import by.hrychanok.training.shop.model.Customer;
import by.hrychanok.training.shop.service.BasicService;

/**
 * T -target DTO type DAO - DaoImpl class ID - primary key value
 * (id)
 */
public class BasicServiceImpl<T, REPOSITORY extends JpaRepository<T, ID>, ID extends Serializable>
		implements BasicService<T, ID> {

	private static Logger LOGGER = LoggerFactory.getLogger(BasicServiceImpl.class);
	@Autowired
	protected REPOSITORY repository;

	@Override
	@Transactional
	public T save(T entity) {
		LOGGER.debug("Save entity "+entity);
		return (T) repository.save(entity);
	}

	@Override
	@Transactional
	public T findOne(ID id) {
		LOGGER.debug("Find entity by id "+id);
		return (T) repository.findOne(id);
	}

	@Override
	@Transactional
	public boolean exists(ID id) {
		return repository.exists(id);
	}

	@Override
	@Transactional
	public List<T> findAll() {
		LOGGER.debug("Find all");
		return (List<T>) repository.findAll();
	}

	@Override
	@Transactional
	public long count() {
		return repository.count();
	}

	@Override
	@Transactional
	public void delete(ID id) {
		repository.delete(id);
	}

	@Override
	@Transactional
	public void delete(T entity) {
		repository.delete(entity);
	}

	@Override
	@Transactional
	public void deleteAll() {
		LOGGER.debug("Delete all");
		repository.deleteAll();
	}

	@Override
	public
	<S extends T> S saveAndFlush(S entity){
		return repository.saveAndFlush(entity);
	}

}
