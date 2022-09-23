package com.example.calpyte.masterservice.service;

import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.entity.District;
import com.example.calpyte.masterservice.entity.State;
import com.example.calpyte.masterservice.masterexception.CustomException;

import java.util.List;

public interface DistrictService {
    void saveDistrict(District district) throws CustomException;


    List<District> getAllDistricts(PaginationDTO pagination);

    List<District> findAllById(List<String> ids);

    District findById(String id);

    List<District> findByRevNo(Long revNo);

    void delete(String id) throws CustomException;

    List<District> getAllDistrict();
}
