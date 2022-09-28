package com.example.calpyte.masterservice.service;

import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.entity.Form;
import com.example.calpyte.masterservice.masterexception.CustomException;

import java.util.List;

public interface FormService {
 void saveForm(Form form) throws CustomException;



    List<Form> getAllForms();

    Form findById(String id);

    List<Form> findAllById(List<String> ids);

    List<Form> findByRevNo(Long revNo);

    void delete(String id) throws CustomException;
    List<Form> getForms();
    Object getForms(PaginationDTO pagination);
}
