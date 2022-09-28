package com.example.calpyte.masterservice.specification;

import com.example.calpyte.masterservice.dto.pagination.SearchCriteria;
import com.example.calpyte.masterservice.entity.Village;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class VillageSpecification extends BaseSpecification implements Specification<Village> {

    private SearchCriteria criteria;

    public VillageSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Village> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return getPredicate(criteria, root, query, builder);
    }
}
