package com.example.calpyte.masterservice.controller;

import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.entity.Village;
import com.example.calpyte.masterservice.masterexception.CustomException;
import com.example.calpyte.masterservice.service.VillageService;
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
@RequestMapping("/village")
public class VillageController {
    @Autowired
    private VillageService villageService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveVillage(@RequestBody Village village) throws  CustomException{
        villageService.saveVillage(village);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/villages")
    public ResponseEntity<List<Village>> getAllVillages() {
        List<Village> villageList = villageService.getAllVillage();
        return new ResponseEntity<>(villageList, HttpStatus.OK);

    }

    @GetMapping(value = "/by-id")
    public ResponseEntity<Village> findById(@RequestParam("id") String id) {
        Village village = villageService.findById(id);
        return new ResponseEntity<>(village, HttpStatus.OK);
    }

    @GetMapping(value = "/by-ids")
    public ResponseEntity<List<Village>> findByIds(@RequestParam("ids") List<String> ids) {
        List<Village> villageList = villageService.findAllById(ids);
        return new ResponseEntity<>(villageList, HttpStatus.OK);
    }

    @GetMapping(value = "/by-rev")
    public ResponseEntity<List<Village>> findByRevNo(@RequestParam("revNo") Long revNo) {
        List<Village> villageList = villageService.findByRevNo(revNo);
        return new ResponseEntity<List<Village>>(villageList, HttpStatus.OK);
    }
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity<Village> delete(@RequestParam("id") String id) throws CustomException  {
        villageService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping(value = "/")
    public ResponseEntity<TableResponse> getStates(@org.springframework.web.bind.annotation.RequestBody PaginationDTO pagination){
        return new ResponseEntity(villageService.getAllVillages(pagination), HttpStatus.ACCEPTED);
    }

}
