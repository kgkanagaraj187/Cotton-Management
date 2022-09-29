package com.example.calpyte.masterservice.service.impl;


import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.dto.pagination.SearchCriteria;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.entity.Variety;
import com.example.calpyte.masterservice.masterexception.CustomException;
import com.example.calpyte.masterservice.repo.VarietyRepository;
import com.example.calpyte.masterservice.service.VarietyService;
import com.example.calpyte.masterservice.specification.CountrySpecification;
import com.example.calpyte.masterservice.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VarietyServiceImpl implements VarietyService {

    @Autowired
    private VarietyRepository varietyRepository;

    @Override
    public void saveVariety(Variety variety) throws CustomException {

        validate(variety);
        Mapper.setAuditable(variety);
        varietyRepository.save(variety);

    }

    @Override
    public List<Variety> getAllVarieties() {
        return varietyRepository.findAll();
    }

    @Override
    public Variety findById(String id) {
        Optional<Variety> varietyOptional = varietyRepository.findById(id);
        if (varietyOptional.isPresent()) {
            Variety variety = new Variety();
            variety.setId(varietyOptional.get().getId());
            variety.setName(varietyOptional.get().getName());
            return variety;
        }
        return null;
    }

    @Override
    public List<Variety> findAllById(List<String> ids) {
        return varietyRepository.findAllById(ids);

    }

    @Override
    public void delete(String id) throws CustomException {
        Optional<Variety> varietyOpt = varietyRepository.findById(id);
        if (varietyOpt.isPresent()) {
            Variety variety = varietyOpt.get();
            variety.setIsDeleted(true);
            varietyRepository.save(variety);
        }
    }

    @Override
    public TableResponse getVarieties(PaginationDTO pagination) {
        TableResponse response;
        Pageable paging = PageRequest.of(pagination.getPageNo() - 1, pagination.getPageSize());
        Page<Variety> varietyPaged = varietyRepository.findAll(getSpecifications(pagination) , paging);
        if (varietyPaged.hasContent()) {
            List<Variety> varietyList = varietyPaged.getContent();
            response = new TableResponse(pagination.getDraw(), (int) varietyPaged.getTotalElements(), (int) varietyPaged.getTotalElements(),
                    varietyList);
        } else {
            response = new TableResponse(pagination.getDraw(), (int) varietyPaged.getTotalElements(), (int) varietyPaged.getTotalElements(),
                    new ArrayList<>());
        }
        return response;
    }




    private Specification<Variety> getSpecifications(PaginationDTO pagination) {
        List<SearchCriteria> params = new ArrayList<>(pagination.getFilter());

        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
                .map(CountrySpecification::new)
                .collect(Collectors.toList());

        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i)
                    .isOrPredicate()
                    ? Specification.where(result)
                    .or(specs.get(i))
                    : Specification.where(result)
                    .and(specs.get(i));
        }

        return result;
    }

    private void validate(Variety variety) throws CustomException {
        Variety eVariety = varietyRepository.findByName(variety.getName());
        if (eVariety != null && (!Objects.equals(eVariety.getId(), variety.getId()))) {
            throw new CustomException("Duplicate Crop name");
        }
    }
}