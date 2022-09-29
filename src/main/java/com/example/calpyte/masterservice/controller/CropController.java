package com.example.calpyte.masterservice.controller;


import com.example.calpyte.masterservice.dto.CropDTO;
import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.entity.Country;
import com.example.calpyte.masterservice.entity.Crop;
import com.example.calpyte.masterservice.masterexception.CustomException;
import com.example.calpyte.masterservice.service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crop")
public class CropController {

    @Autowired
    private CropService cropService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveCrop(@RequestBody Crop crop) throws CustomException {
        cropService.saveCrop(crop);
        return new ResponseEntity<Crop>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/crops", method = RequestMethod.GET)
    public ResponseEntity<List<Crop>> getAllCrops() {
        List<Crop> cropList = cropService.getAllCrops();
        return new ResponseEntity<>(cropService.getAllCrops(), HttpStatus.OK);
    }

    @GetMapping(value = "/by-id")
    public ResponseEntity<Crop> findById(@RequestParam("id") String id) {
        Crop crop = cropService.findById(id);
        return new ResponseEntity<>(crop, HttpStatus.OK);
    }

    @GetMapping(value = "/by-ids")
    public ResponseEntity<List<Crop>> findByIds(@RequestParam("ids") List<String> ids) {
        List<Crop> cropList = cropService.findAllById(ids);
        return new ResponseEntity<>(cropList, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity<Crop> delete(@RequestParam("id") String id) throws CustomException  {
        cropService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<TableResponse> getCrops(@RequestBody PaginationDTO pagination){
        return new ResponseEntity<>(cropService.getCrops(pagination), HttpStatus.ACCEPTED);
    }

}