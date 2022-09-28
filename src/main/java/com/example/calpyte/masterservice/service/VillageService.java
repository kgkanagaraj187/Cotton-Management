package com.example.calpyte.masterservice.service;

import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.entity.Village;
import com.example.calpyte.masterservice.masterexception.CustomException;

import java.util.List;

public interface VillageService {
    void saveVillage(Village village) throws CustomException;

   TableResponse getAllVillage(PaginationDTO pagination);

    Village findById(String id);

    List<Village> findAllById(List<String> ids);


    void delete(String id) throws CustomException;

    List<Village> findByRevNo(Long revNo);

    List<Village> getAllVillage();
    List<Village> getAllVillages();
    List<Village> getAllvillages();
    TableResponse getVillages(PaginationDTO pagination);

    Object getAllVillages(PaginationDTO pagination);
}
