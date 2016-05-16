package by.hrychanok.training.shop.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;

import by.hrychanok.training.shop.model.Customer;
import by.hrychanok.training.shop.model.CustomerCredentials_;
import by.hrychanok.training.shop.model.Customer_;
import by.hrychanok.training.shop.repository.CustomerRepositoryCustom;
import by.hrychanok.training.shop.repository.filter.CustomerFilter;

public class CustomerRepositoryImpl implements CustomerRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;

	protected EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public List<Customer> find(CustomerFilter filter) {
		EntityManager em = getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);

		Root<Customer> from = cq.from(Customer.class);

		if (filter.getUserName() != null) {
			Predicate fNameEqualCondition = cb.equal(from.get(Customer_.firstName), filter.getUserName());
			Predicate lNameEqualCondition = cb.equal(from.get(Customer_.lastName), filter.getUserName());
			cq.where(cb.or(fNameEqualCondition, lNameEqualCondition));
		}
		if (filter.getEmail() != null) {
			Predicate emailEqualCondition = cb.equal(from.get(Customer_.email), filter.getEmail());
			cq.where(cb.and(emailEqualCondition));
		}
		if (filter.getAddress() != null) {
			Predicate addressEqualCondition = cb.equal(from.get(Customer_.address), filter.getAddress());
			cq.where(cb.and(addressEqualCondition));
		}
		if (filter.getLogin() != null) {
			Predicate emailEqualCondition = cb.equal(from.get(Customer_.email), filter.getEmail());
			Predicate loginEqualCondition = cb.equal(from.get(Customer_.customerCredentials).get(CustomerCredentials_.login), filter.getLogin());
			cq.where(cb.or(loginEqualCondition,emailEqualCondition ));
		}
		if (filter.getCity() != null) {
			Predicate cityEqualCondition = cb.equal(from.get(Customer_.city), filter.getCity());
			cq.where(cb.and(cityEqualCondition));
		}

		if (filter.getCountry() != null) {
			Predicate countryEqualCondition = cb.equal(from.get(Customer_.country), filter.getCountry());
			cq.where(cb.and(countryEqualCondition));
		}
		if (filter.isFetchCredentials()) {
			from.fetch(Customer_.customerCredentials, JoinType.LEFT);
		}
		if (filter.getSortProperty() != null) {
			cq.orderBy(new OrderImpl(from.get(filter.getSortProperty()), filter.isSortOrder()));
		}
		if (filter.getCreated()!=null){
			Predicate createdEqualCondition = cb.equal(from.get(Customer_.created), filter.getCreated());
			cq.where(cb.and(createdEqualCondition));
		}
		TypedQuery<Customer> q = em.createQuery(cq);
		if (filter.getOffset() != null && filter.getLimit() != null) {
			q.setFirstResult(filter.getOffset());
			q.setMaxResults(filter.getLimit());
		}
		List<Customer> allitems = q.getResultList();
		return allitems;
	}

}
