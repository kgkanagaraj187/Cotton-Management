package com.example.calpyte.masterservice.controller;


import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.entity.Certification;
import com.example.calpyte.masterservice.entity.Variety;
import com.example.calpyte.masterservice.masterexception.CustomException;
import com.example.calpyte.masterservice.service.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certification")
public class CertificationController {

    @Autowired
    private CertificationService certificationService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveCertification(@RequestBody Certification certification) throws CustomException {
        certificationService.saveCertification(certification);
        return new ResponseEntity<Certification>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/certifications", method = RequestMethod.GET)
    public ResponseEntity<List<Certification>> getAllCertifications() {
        List<Certification> certificationList = certificationService.getAllCertifications();
        return new ResponseEntity<>(certificationService.getAllCertifications(), HttpStatus.OK);
    }

    @GetMapping(value = "/by-id")
    public ResponseEntity<Certification> findById(@RequestParam("id") String id) {
        Certification certification = certificationService.findById(id);
        return new ResponseEntity<>(certification, HttpStatus.OK);
    }

    @GetMapping(value = "/by-ids")
    public ResponseEntity<List<Certification>> findByIds(@RequestParam("ids") List<String> ids) {
        List<Certification> certificationList = certificationService.findAllById(ids);
        return new ResponseEntity<>(certificationList, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity<Certification> delete(@RequestParam("id") String id) throws CustomException  {
        certificationService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<TableResponse> getCertification(@RequestBody PaginationDTO pagination){
        return new ResponseEntity<>(certificationService.getCertification(pagination), HttpStatus.ACCEPTED);
    }
}

