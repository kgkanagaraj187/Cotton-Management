package com.example.calpyte.masterservice.controller;


import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.entity.Crop;
import com.example.calpyte.masterservice.entity.Variety;
import com.example.calpyte.masterservice.masterexception.CustomException;
import com.example.calpyte.masterservice.service.VarietyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/variety")
public class VarietyController {

    @Autowired
    private VarietyService varietyService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveVariety(@RequestBody Variety variety) throws CustomException {
        varietyService.saveVariety(variety);
        return new ResponseEntity<Variety>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/varieties", method = RequestMethod.GET)
    public ResponseEntity<List<Variety>> getAllVarieties() {
        List<Variety> varietyList = varietyService.getAllVarieties();
        return new ResponseEntity<>(varietyService.getAllVarieties(), HttpStatus.OK);
    }

    @GetMapping(value = "/by-id")
    public ResponseEntity<Variety> findById(@RequestParam("id") String id) {
        Variety variety = varietyService.findById(id);
        return new ResponseEntity<>(variety, HttpStatus.OK);
    }

    @GetMapping(value = "/by-ids")
    public ResponseEntity<List<Variety>> findByIds(@RequestParam("ids") List<String> ids) {
        List<Variety> varietyList = varietyService.findAllById(ids);
        return new ResponseEntity<>(varietyList, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity<Variety> delete(@RequestParam("id") String id) throws CustomException  {
        varietyService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<TableResponse> getVarieties(@RequestBody PaginationDTO pagination){
        return new ResponseEntity<>(varietyService.getVarieties(pagination), HttpStatus.ACCEPTED);
    }
}