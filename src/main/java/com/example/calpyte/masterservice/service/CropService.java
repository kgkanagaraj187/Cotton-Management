package com.example.calpyte.masterservice.service;

import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.entity.Country;
import com.example.calpyte.masterservice.entity.Crop;
import com.example.calpyte.masterservice.masterexception.CustomException;

import java.util.List;

public interface CropService {
    List<Crop> getAllCrops();
    Crop findById(String id);

    List<Crop> findAllById(List<String> ids);

    void delete(String id) throws CustomException;
    void saveCrop(Crop crop) throws CustomException;

    TableResponse getCrops(PaginationDTO pagination);



}
