package com.example.calpyte.masterservice.service;


import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.entity.Country;
import com.example.calpyte.masterservice.masterexception.CustomException;

import java.util.List;

public interface CountryService {

    void saveCountry(Country country) throws CustomException;

    List<Country> getAllCountries();

    Country findById(String id);

    List<Country> findAllById(List<String> ids);

    void delete(String id) throws CustomException;

    TableResponse getCountries(PaginationDTO pagination);

    List<Country> findByRevNo(Long revNo);
}
