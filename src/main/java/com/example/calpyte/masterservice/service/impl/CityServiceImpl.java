package com.example.calpyte.masterservice.service.impl;

import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.dto.pagination.SearchCriteria;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.entity.City;
import com.example.calpyte.masterservice.entity.Country;
import com.example.calpyte.masterservice.masterexception.CustomException;
import com.example.calpyte.masterservice.repo.CityRepository;
import com.example.calpyte.masterservice.service.CityService;
import com.example.calpyte.masterservice.specification.CitySpecification;
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
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public void saveCity(City city) throws CustomException {

        validate(city);
        Mapper.setAuditable(city);
        cityRepository.save(city);

    }

    private void validate(City city) throws CustomException {
        City eCity = cityRepository.findByName(city.getName());
        if (eCity != null && (!Objects.equals(eCity.getId(), city.getId()))) {
            throw new CustomException("Duplicate City name");
        }
    }


    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public City findById(String id) {
        Optional<City> cityOptional = cityRepository.findById(id);
        if (cityOptional.isPresent()) {
            City city = new City();
            city.setId(cityOptional.get().getId());
            city.setName(cityOptional.get().getName());
            return city;
        }
        return null;
    }
    @Override
    public List<City> findAllById(List<String> ids) {
        return cityRepository.findAllById(ids);

    }

    @Override
    public void delete(String id) throws CustomException {
        Optional<City> cityOpt = cityRepository.findById(id);
        if (cityOpt.isPresent()) {
            City city = cityOpt.get();

        }}
    @Override
    public TableResponse getCities(PaginationDTO pagination) {
        TableResponse response;
        Pageable paging = PageRequest.of(pagination.getPageNo() - 1, pagination.getPageSize());
        Page<City> cityPaged = cityRepository.findAll(getSpecifications(pagination) , paging);
        if (cityPaged.hasContent()) {
            List<City> cityList = cityPaged.getContent();
            response = new TableResponse(pagination.getDraw(), (int) cityPaged.getTotalElements(), (int) cityPaged.getTotalElements(),
                    cityList);
        } else {
            response = new TableResponse(pagination.getDraw(), (int) cityPaged.getTotalElements(), (int) cityPaged.getTotalElements(),
                    new ArrayList<>());
        }
        return response;
    }


    @Override
    public List<City> findByRevNo(Long revNo) {
        List<City> cityList = cityRepository.findByRevisionNoGreaterThan(revNo);
        return cityList.stream().map(this::copyToDTO).collect(Collectors.toList());
    }
    private City copyToDTO(City city) {
        City eCity = Mapper.map(city, City.class);
        eCity.setId(city.getId());
        eCity.setName(city.getName());
        return eCity;
    }

    private Specification<City> getSpecifications(PaginationDTO pagination) {
        List<SearchCriteria> params = new ArrayList<>(pagination.getFilter());

        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
                .map(CitySpecification::new)
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

}
