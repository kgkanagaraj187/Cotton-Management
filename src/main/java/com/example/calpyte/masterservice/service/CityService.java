package com.example.calpyte.masterservice.service;


import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.entity.City;
import com.example.calpyte.masterservice.masterexception.CustomException;

import java.util.List;

public interface CityService {

    void saveCity(City city) throws CustomException;

    List<City> getAllCities();

    City findById(String id);

    List<City> findAllById(List<String> ids);

    List<City> findByRevNo(Long revNo);

    void delete(String id)  throws CustomException;

    TableResponse getCities(PaginationDTO pagination);
}



