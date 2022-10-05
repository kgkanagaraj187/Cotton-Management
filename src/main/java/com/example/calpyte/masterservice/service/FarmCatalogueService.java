package com.example.calpyte.masterservice.service;

import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.entity.Crop;
import com.example.calpyte.masterservice.entity.FarmCatalogue;
import com.example.calpyte.masterservice.masterexception.CustomException;

import java.util.List;

public interface FarmCatalogueService {
    void saveFarmCatalogue(FarmCatalogue farmCatalogue) throws CustomException;

    List<FarmCatalogue> getAllFarmCatalogue();
    FarmCatalogue findById(String id);

    List<FarmCatalogue> findAllById(List<String> ids);

    void delete(String id) throws CustomException;


    TableResponse getFarmCatalogs(PaginationDTO pagination);


}
