package com.example.calpyte.masterservice.controller;


import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.entity.District;
import com.example.calpyte.masterservice.entity.State;
import com.example.calpyte.masterservice.masterexception.CustomException;
import com.example.calpyte.masterservice.repo.DistrictRepository;
import com.example.calpyte.masterservice.service.DistrictService;
import com.example.calpyte.masterservice.service.StateService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("district")
public class DistrictController {
    @Autowired
    private DistrictService districtService;


    @PostMapping(value = "/save")
    public ResponseEntity<?> saveDistrict(@RequestBody District district) throws CustomException{
        districtService.saveDistrict(district);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/districts")
    public ResponseEntity<List<District>> getAllDistrict() {
        List<District> districtList = districtService.getAllDistrict();
        return new ResponseEntity<>(districtList, HttpStatus.OK);

    }

    @GetMapping(value = "/by-id")
    public ResponseEntity<District> findById(@RequestParam("id") String id) {
        District district = districtService.findById(id);
        return new ResponseEntity<>(district, HttpStatus.OK);
    }


    @GetMapping(value = "/by-ids")
    public ResponseEntity<List<District>> findByIds(@RequestParam("ids") List<String> ids) {
        DistrictService districtService = null;
        List<District> districtList = districtService.findAllById(ids);
        return new ResponseEntity<>(districtList, HttpStatus.OK);
    }

    @GetMapping(value = "/by-rev")
    public ResponseEntity<List<District>> findByRevNo(@RequestParam("revNo") Long revNo) {
        List<District> districtList = districtService.findByRevNo(revNo);
        return new ResponseEntity<List<District>>(districtList, HttpStatus.OK);
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity<District> delete(@RequestParam("id") String id) throws CustomException  {
        districtService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<TableResponse> getAllDistricts(@org.springframework.web.bind.annotation.RequestBody PaginationDTO pagination){
        DistrictService districtService = null;
        return new ResponseEntity(districtService.getAllDistricts(pagination), HttpStatus.ACCEPTED);
    }



}















