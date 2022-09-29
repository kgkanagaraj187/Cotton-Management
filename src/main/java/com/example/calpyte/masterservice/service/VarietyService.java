package com.example.calpyte.masterservice.service;

import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.entity.Crop;
import com.example.calpyte.masterservice.entity.Variety;
import com.example.calpyte.masterservice.masterexception.CustomException;

import java.util.List;

public interface VarietyService {

    void saveVariety(Variety variety) throws CustomException;

    List<Variety> getAllVarieties();

    Variety findById(String id);

    List<Variety> findAllById(List<String> ids);

    void delete(String id) throws CustomException;

    TableResponse getVarieties(PaginationDTO pagination);

}
