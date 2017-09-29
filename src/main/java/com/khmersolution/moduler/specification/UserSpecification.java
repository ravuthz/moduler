package com.khmersolution.moduler.specification;

import com.khmersolution.moduler.domain.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserSpecification implements Specification<User> {

    private SearchCriteria criteria;

    public UserSpecification(SearchCriteria searchCriteria) {
        this.criteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        String key = criteria.getKey();
        String val = criteria.getValue().toString();

        switch (criteria.getOperation()) {
            case "<":
                return builder.lessThanOrEqualTo(root.get(key), val);

            case ">":
                return builder.greaterThanOrEqualTo(root.get(key), val);

            case ":":
                if (root.get(key).getJavaType() != String.class) {
                    return builder.equal(root.<String> get(key), val);
                }
                return builder.like(root.get(key), "%" + val + "%");

            default:
                return null;
        }

    }

}
