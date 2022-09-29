package com.example.calpyte.masterservice.specification;

import com.example.calpyte.masterservice.dto.pagination.SearchCriteria;
import com.example.calpyte.masterservice.entity.Country;
import com.example.calpyte.masterservice.entity.State;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class StateSpecification  extends BaseSpecification implements Specification<State> {
    private SearchCriteria criteria;

    public StateSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<State> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return getPredicate(criteria, root, query, builder);
    }


}
