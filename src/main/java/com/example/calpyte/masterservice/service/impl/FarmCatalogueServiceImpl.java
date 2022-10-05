package com.example.calpyte.masterservice.service.impl;

import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.dto.pagination.SearchCriteria;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.entity.FarmCatalogue;
import com.example.calpyte.masterservice.masterexception.CustomException;
import com.example.calpyte.masterservice.service.FarmCatalogueService;
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
public class FarmCatalogueServiceImpl implements FarmCatalogueService {

    @Autowired
    private com.example.calpyte.masterservice.repo.FarmCatalogueRepository farmCatalogueRepository;
    @Override


    public void saveFarmCatalogue(FarmCatalogue farmCatalogue) throws CustomException {
        validate(farmCatalogue);
        Mapper.setAuditable(farmCatalogue);
        farmCatalogueRepository.save(farmCatalogue);
    }

    @Override
    public List<FarmCatalogue> getAllFarmCatalogue() {
        return farmCatalogueRepository.findAll();
    }

    @Override
    public FarmCatalogue findById(String id) {
        Optional<FarmCatalogue> farmCatalogueOptional = farmCatalogueRepository.findById(id);
        if (farmCatalogueOptional.isPresent()) {
            FarmCatalogue farmCatalogue = new FarmCatalogue();
            farmCatalogue.setId(farmCatalogueOptional.get().getId());
            farmCatalogue.setName(farmCatalogueOptional.get().getName());
            return farmCatalogue;
        }
        return null;
    }

    @Override
    public List<FarmCatalogue> findAllById(List<String> ids) {
        return farmCatalogueRepository.findAllById(ids);

    }

    @Override
    public void delete(String id) throws CustomException {
        Optional<FarmCatalogue> farmCatalogueOpt = farmCatalogueRepository.findById(id);
        if (farmCatalogueOpt.isPresent()) {
            FarmCatalogue farmCatalogue = farmCatalogueOpt.get();
            farmCatalogue.setIsDeleted(true);
            farmCatalogueRepository.save(farmCatalogue);
        }
    }

    @Override
    public TableResponse getFarmCatalogs(PaginationDTO pagination) {
        TableResponse response;
        Pageable paging = PageRequest.of(pagination.getPageNo() - 1, pagination.getPageSize());
        Page<FarmCatalogue> farmCataloguePaged = farmCatalogueRepository.findAll(getSpecifications(pagination) , paging);
        if (farmCataloguePaged.hasContent()) {
            List<FarmCatalogue> farmCatalogueList = farmCataloguePaged.getContent();
            response = new TableResponse(pagination.getDraw(), (int) farmCataloguePaged.getTotalElements(), (int) farmCataloguePaged.getTotalElements(),
                    farmCatalogueList);
        } else {
            response = new TableResponse(pagination.getDraw(), (int) farmCataloguePaged.getTotalElements(), (int) farmCataloguePaged.getTotalElements(),
                    new ArrayList<>());
        }
        return response;
    }




    private Specification<FarmCatalogue> getSpecifications(PaginationDTO pagination) {
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

    private void validate(FarmCatalogue farmCatalogue) throws CustomException {
        FarmCatalogue eFarmCatalogue = farmCatalogueRepository.findByName(farmCatalogue.getName());
        if (eFarmCatalogue != null && (!Objects.equals(eFarmCatalogue.getId(), farmCatalogue.getId()))) {
            throw new CustomException("Duplicate FarmCatalogue name");
        }
    }
}

