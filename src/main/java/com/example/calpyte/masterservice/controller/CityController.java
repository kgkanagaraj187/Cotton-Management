package com.example.calpyte.masterservice.controller;


import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.entity.City;
import com.example.calpyte.masterservice.entity.Country;
import com.example.calpyte.masterservice.masterexception.CustomException;
import com.example.calpyte.masterservice.service.CityService;
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
@RequestMapping("/city")
public class CityController {
@Autowired
    private CityService cityService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveCity(@RequestBody City city) throws CustomException {
        cityService.saveCity(city);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/cities")
    public ResponseEntity<List<City>> getAllCities() {
        List<City> cityList = cityService.getAllCities();
        return new ResponseEntity<>(cityList, HttpStatus.OK);
    }

    @GetMapping(value = "/by-id")
    public ResponseEntity<City> findById(@RequestParam("id") String id) {
        City city = cityService.findById(id);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @GetMapping(value = "/by-ids")
    public ResponseEntity<List<City>> findByIds(@RequestParam("ids") List<String> ids) {
        List<City> cityList = cityService.findAllById(ids);
        return new ResponseEntity<>(cityList, HttpStatus.OK);
    }


    @GetMapping(value = "/by-rev")
    public ResponseEntity<List<City>> findByRevNo(@RequestParam("revNo") Long revNo) {
        List<City> cityList = cityService.findByRevNo(revNo);
        return new ResponseEntity<List<City>>(cityList, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity<City> delete(@RequestParam("id") String id) throws CustomException  {
        cityService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping(value = "/")
    public ResponseEntity<TableResponse> getCities(@RequestBody PaginationDTO pagination){
        return new ResponseEntity<>(cityService.getCities(pagination), HttpStatus.ACCEPTED);
    }
}
