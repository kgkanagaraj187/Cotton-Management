package com.example.calpyte.masterservice.service;

import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.entity.Farmer;
import com.example.calpyte.masterservice.masterexception.CustomException;

import java.util.List;

public interface FarmerService {
    void saveFarmer(Farmer farmer) throws CustomException;

    List<Farmer> getAllFarmers();

    Farmer findById(String id);

    List<Farmer> findAllById(List<String> ids);

    List<Farmer> findByRevNo(Long revNo);

    void delete(String id) throws CustomException;

    Object getFarmers(PaginationDTO pagination);
    List<Farmer> getFarmers();
}
