package com.example.calpyte.masterservice.controller;

import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.entity.Country;
import com.example.calpyte.masterservice.entity.Farmer;
import com.example.calpyte.masterservice.masterexception.CustomException;
import com.example.calpyte.masterservice.service.FarmerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/farmer")
public class FarmerController {

@Autowired
    private FarmerService farmerService;

@PostMapping(value = "/save")
    public ResponseEntity<?> saveFarmer(@RequestBody Farmer farmer) throws CustomException{
    farmerService.saveFarmer(farmer);
    return new ResponseEntity<>(HttpStatus.CREATED);

}

@GetMapping(value = "/farmers")
    public ResponseEntity<List<Farmer>> getAllFarmers(){
    List<Farmer> farmerList= farmerService.getAllFarmers();
    return new ResponseEntity<>(farmerList,HttpStatus.OK);

}

    @GetMapping(value = "/by-id")
    public ResponseEntity<Farmer> findById(@RequestParam("id") String id) {
        Farmer farmer = farmerService.findById(id);
        return new ResponseEntity<>(farmer, HttpStatus.OK);
    }


    @GetMapping(value = "/by-ids")
    public ResponseEntity<List<Farmer>> findByIds(@RequestParam("ids") List<String> ids) {
        List<Farmer> farmerList = farmerService.findAllById(ids);
        return new ResponseEntity<>(farmerList, HttpStatus.OK);
    }


    @GetMapping(value = "/by-rev")
    public ResponseEntity<List<Farmer>> findByRevNo(@RequestParam("revNo") Long revNo) {
        List<Farmer> farmerList = farmerService.findByRevNo(revNo);
        return new ResponseEntity<List<Farmer>>(farmerList, HttpStatus.OK);
    }
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity<Farmer> delete(@RequestParam("id") String id) throws CustomException  {
        farmerService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<TableResponse> getFarmers(@org.springframework.web.bind.annotation.RequestBody PaginationDTO pagination){
        return new ResponseEntity(farmerService.getFarmers(pagination), HttpStatus.ACCEPTED);
    }
}
