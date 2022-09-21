package com.example.calpyte.masterservice.service.impl;

import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.dto.pagination.SearchCriteria;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.entity.Country;
import com.example.calpyte.masterservice.masterexception.CustomException;
import com.example.calpyte.masterservice.repo.CountryRepository;
import com.example.calpyte.masterservice.service.CountryService;
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
public class CountryServiceImpl implements CountryService {
    @Autowired
    private CountryRepository countryRepository;

    @Override
    public void saveCountry(Country country) throws CustomException {
        if(country.getNames()!=null && country.getNames().size() > 0){
            List<Country> countryList =  country.getNames().stream().map(countryName -> {
                country.setName(countryName);
                return country;
            }).collect(Collectors.toList());
            countryRepository.saveAll(countryList);
        }else{
            validate(country);
            Mapper.setAuditable(country);
            countryRepository.save(country);
        }
    }

    private void validate(Country country) throws CustomException {
        Country eCountry = countryRepository.findByName(country.getName());
        if (eCountry != null && (!Objects.equals(eCountry.getId(), country.getId()))) {
            throw new CustomException("Duplicate Country name");
        }
    }

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Country findById(String id) {
        Optional<Country> countryOptional = countryRepository.findById(id);
        if (countryOptional.isPresent()) {
            Country country = new Country();
            country.setId(countryOptional.get().getId());
            country.setName(countryOptional.get().getName());
            return country;
        }
        return null;
    }

    @Override
    public List<Country> findAllById(List<String> ids) {
        return countryRepository.findAllById(ids);

    }

    @Override
    public void delete(String id) throws CustomException {
        Optional<Country> countryOpt = countryRepository.findById(id);
        if (countryOpt.isPresent()) {
            Country country = countryOpt.get();
            /*if (country.getStates() != null && !country.getStates().isEmpty()) {
                throw new CustomException("Sorry!! Country " + country.getName()
                        + " can't be deleted since it is mapped with " + country.getStates().size() + " states");
            } else {
                country.setIsDeleted(true);
                countryRepository.save(country);
            }*/
        }
    }

    @Override
    public TableResponse getCountries(PaginationDTO pagination) {
        TableResponse response = null;
        return response;
    }

    /*@Override
    public TableResponse getCountries(PaginationDTO pagination) {
        TableResponse response;
        Pageable paging = PageRequest.of(pagination.getPageNo() - 1, pagination.getPageSize());
        Page<Country> countryPaged = countryRepository.findAll(getSpecifications(pagination) , paging);
        if (countryPaged.hasContent()) {
            List<Country> countryList = countryPaged.getContent();
            response = new TableResponse(pagination.getDraw(), (int) countryPaged.getTotalElements(), (int) countryPaged.getTotalElements(),
                    countryList);
        } else {
            response = new TableResponse(pagination.getDraw(), (int) countryPaged.getTotalElements(), (int) countryPaged.getTotalElements(),
                    new ArrayList<>());
        }
        return response;
    }*/

    @Override
    public List<Country> findByRevNo(Long revNo) {
        List<Country> countryList = countryRepository.findByRevisionNoGreaterThan(revNo);
        return countryList.stream().map(this::copyToDTO).collect(Collectors.toList());
    }

    private Country copyToDTO(Country country) {
        Country eCountry = Mapper.map(country, Country.class);
        eCountry.setId(country.getId());
        eCountry.setName(country.getName());
        return eCountry;
    }

   /* private Specification<> getSpecifications(PaginationDTO pagination) {
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
    }*/

}