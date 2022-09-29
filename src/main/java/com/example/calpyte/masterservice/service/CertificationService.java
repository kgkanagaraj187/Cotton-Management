package com.example.calpyte.masterservice.service;

import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.entity.Certification;
import com.example.calpyte.masterservice.entity.Variety;
import com.example.calpyte.masterservice.masterexception.CustomException;

import java.util.List;

public interface CertificationService {

    void saveCertification(Certification certification) throws CustomException;

    List<Certification> getAllCertification();


    Certification findById(String id);

    List<Certification> findAllById(List<String> ids);

    void delete(String id) throws CustomException;

    TableResponse getCertification(PaginationDTO pagination);


    List<Certification> getAllCertifications();
}
