package by.hrychanok.training.shop.repository.filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class Filter implements Specification, Serializable {
	
	List<Condition> conditions;

	public Filter() {
		conditions = new ArrayList<>();
	}

	public boolean existCondition() {
		return conditions.isEmpty() ? false : true;
	}

	public void addCondition(Condition condition) {
		this.conditions.add(condition);
	}

	public void removeCondition(String field) {
		for (int i = 0; i < conditions.size(); i++) {
			if (field.equals(conditions.get(i).field)) {
				conditions.remove(i);
			}
		}
	}

	public Condition getConditionByField(String field) {
		for (int i = 0; i < conditions.size(); i++) {
			if (field.equals(conditions.get(i).field)) {
				return conditions.get(i);
			}
		}
		return null;
	}

	@Override
	public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = buildPredicates(root, criteriaQuery, criteriaBuilder);
		return predicates.size() > 1 ? criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]))
				: predicates.get(0);
	}

	private List<Predicate> buildPredicates(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		conditions
				.forEach(condition -> predicates.add(buildPredicate(condition, root, criteriaQuery, criteriaBuilder)));
		return predicates;
	}

	public Predicate buildPredicate(Condition condition, Root root, CriteriaQuery criteriaQuery,
			CriteriaBuilder criteriaBuilder) {
		switch (condition.comparison) {
		case eq:
			return buildEqualsPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
		case gt:
			return buildGreaterThanPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
		case lt:
			return buildLowerThanPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
		case ne:
			return buildNotEqualsPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
		case isnull:
			return buildIsNullPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
		case or:
			return buildOrPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
		case between:
			return buildBeetwenPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
		default:
			return buildEqualsPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);

		}
	}

	private Predicate buildEqualsPredicateToCriteria(Condition condition, Root root, CriteriaQuery criteriaQuery,
			CriteriaBuilder criteriaBuilder) {
		return criteriaBuilder.equal(root.get(condition.field), condition.value);
	}

	private Predicate buildGreaterThanPredicateToCriteria(Condition condition, Root root, CriteriaQuery criteriaQuery,
			CriteriaBuilder criteriaBuilder) {
		return criteriaBuilder.gt(root.<Integer> get(condition.field), (Integer) condition.value);
	}

	private Predicate buildNotEqualsPredicateToCriteria(Condition condition, Root root, CriteriaQuery criteriaQuery,
			CriteriaBuilder criteriaBuilder) {
		return criteriaBuilder.notEqual(root.get(condition.field), condition.value);
	}

	private Predicate buildIsNullPredicateToCriteria(Condition condition, Root root, CriteriaQuery criteriaQuery,
			CriteriaBuilder criteriaBuilder) {
		return criteriaBuilder.isNull(root.get(condition.field));
	}

	private Predicate buildBeetwenPredicateToCriteria(Condition condition, Root root, CriteriaQuery criteriaQuery,
			CriteriaBuilder criteriaBuilder) {
		return criteriaBuilder.between(root.<Integer> get(condition.field), (Integer) condition.value,
				(Integer) condition.limitValue);
	}

	private Predicate buildLowerThanPredicateToCriteria(Condition condition, Root root, CriteriaQuery criteriaQuery,
			CriteriaBuilder criteriaBuilder) {
		return criteriaBuilder.lt(root.<Integer> get(condition.field), (Integer) condition.value);
	}

	private Predicate buildOrPredicateToCriteria(Condition condition, Root root, CriteriaQuery criteriaQuery,
			CriteriaBuilder criteriaBuilder) {

		Predicate condition1 = criteriaBuilder.like(root.get(condition.field), (String) condition.value);
		Predicate condition2 = criteriaBuilder.like(root.get(condition.field2), (String) condition.value);
		return criteriaBuilder.or(condition1, condition2);
	}
}
