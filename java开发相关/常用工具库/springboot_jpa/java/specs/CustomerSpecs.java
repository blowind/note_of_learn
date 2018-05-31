package com.zxf.bootdata.specs;

import com.zxf.bootdata.model.Person;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import javax.persistence.criteria.*;

public class CustomerSpecs {
	public static Specification<Person> personFromHangzhou() {
		return new Specification<Person>() {
			@Nullable
			@Override
			public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.equal(root.get("address"), "杭州");
			}
		};
	}

	public static Specification<Person> personFromChangSanJiao() {
		return new Specification<Person>() {
			@Nullable
			@Override
			public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
				Path<String> addressPath = root.get("address");
				Path<Integer> agePath = root.get("age");

				/* 此处设置了查询语句的predicate，则本函数后续返回null即可，不需要重复设置 */
				criteriaQuery.where(cb.or(cb.like(addressPath, "杭州"), cb.lessThan(agePath, 25)));

				return null;
			}
		};
	}

	public static Specification<Person> youngPersonFromNanjing() {
		return new Specification<Person>() {
			@Nullable
			@Override
			public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
				Path<String> addressPath = root.get("address");
				Path<Integer> agePath = root.get("age");

				Predicate predicate = cb.and(cb.equal(addressPath, "南京"), cb.ge(agePath, 20));
				criteriaQuery.where(predicate, cb.like(root.get("name"), "c"));

				return null;
			}
		};
	}
}
