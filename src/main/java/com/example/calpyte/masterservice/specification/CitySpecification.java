package com.example.calpyte.masterservice.specification;

import com.example.calpyte.masterservice.dto.pagination.SearchCriteria;
import com.example.calpyte.masterservice.entity.City;
import com.example.calpyte.masterservice.entity.Country;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CitySpecification extends BaseSpecification implements Specification<City> {

    private SearchCriteria criteria;

    public CitySpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<City> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return getPredicate(criteria, root, query, builder);
    }
}
