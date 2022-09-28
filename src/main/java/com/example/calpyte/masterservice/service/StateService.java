package com.example.calpyte.masterservice.service;

import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.entity.Country;
import com.example.calpyte.masterservice.entity.State;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.masterexception.CustomException;

import java.util.List;

public interface StateService {

     void saveState(State state) throws CustomException;


  TableResponse getAllStates(PaginationDTO pagination);

    List<State> findAllById(List<String> ids);

    State findById(String id);

    List<State> findByRevNo(Long revNo);

    void delete(String id) throws CustomException;

    List<State> getAllStates();

  TableResponse getStates(PaginationDTO pagination);


}

