package com.example.calpyte.masterservice.specification;

import com.example.calpyte.masterservice.dto.pagination.SearchCriteria;
import com.example.calpyte.masterservice.entity.Country;
import com.example.calpyte.masterservice.entity.District;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class DistrictSpecification  extends BaseSpecification implements Specification<District> {
    private SearchCriteria criteria;

    public DistrictSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<District> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return getPredicate(criteria, root, query, builder);
    }


}
