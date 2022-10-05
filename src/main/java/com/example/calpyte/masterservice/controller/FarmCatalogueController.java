package com.example.calpyte.masterservice.controller;

import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.entity.FarmCatalogue;
import com.example.calpyte.masterservice.masterexception.CustomException;
import com.example.calpyte.masterservice.service.FarmCatalogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/farm_catalogue")
public class FarmCatalogueController {


    @Autowired
    private FarmCatalogueService farmCatalogueService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveFarmCatalogue(@RequestBody FarmCatalogue  farmCatalogue) throws CustomException {
        farmCatalogueService.saveFarmCatalogue( farmCatalogue);
        return new ResponseEntity<FarmCatalogue>(HttpStatus.CREATED);
   }

    @RequestMapping(value = "/farmCatalogue", method = RequestMethod.GET)
    public ResponseEntity<List<FarmCatalogue>> getAllFarmCatalogue() {
        List<FarmCatalogue> farmCatalogueList = farmCatalogueService.getAllFarmCatalogue();
        return new ResponseEntity<>(farmCatalogueService.getAllFarmCatalogue(), HttpStatus.OK);
    }

    @GetMapping(value = "/by-id")
    public ResponseEntity<FarmCatalogue> findById(@RequestParam("id") String id) {
        FarmCatalogue farmCatalogue = farmCatalogueService.findById(id);
        return new ResponseEntity<>(farmCatalogue, HttpStatus.OK);
    }

    @GetMapping(value = "/by-ids")
    public ResponseEntity<List<FarmCatalogue>> findByIds(@RequestParam("ids") List<String> ids) {
        List<FarmCatalogue> farmCatalogueList = farmCatalogueService.findAllById(ids);
        return new ResponseEntity<>(farmCatalogueList, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity<FarmCatalogue> delete(@RequestParam("id") String id) throws CustomException  {
        farmCatalogueService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<TableResponse> getFarmCatalogs(@RequestBody PaginationDTO pagination){
        return new ResponseEntity<>(farmCatalogueService.getFarmCatalogs(pagination), HttpStatus.ACCEPTED);
    }


}
