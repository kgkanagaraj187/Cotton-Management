package com.example.calpyte.masterservice.controller;

import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.entity.Country;
import com.example.calpyte.masterservice.entity.State;
import com.example.calpyte.masterservice.masterexception.CustomException;
import com.example.calpyte.masterservice.service.StateService;
import com.example.calpyte.masterservice.service.impl.StateServiceImpl;
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
@RequestMapping("/state")
public class StateController {

    @Autowired
   private StateService stateService;

    @PostMapping(value = "/save")
    public ResponseEntity<?>saveState(@RequestBody State state) throws CustomException {
        stateService.saveState(state);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping(value = "/states")
    public ResponseEntity<List<State>> getAllStates() {
        List<State> stateList = stateService.getAllStates();
        return new ResponseEntity<>(stateList, HttpStatus.OK);

    }

    @GetMapping(value = "/by-id")
    public ResponseEntity<State> findById(@RequestParam("id") String id) {
        State state = stateService.findById(id);
        return new ResponseEntity<>(state, HttpStatus.OK);
    }



    @GetMapping(value = "/by-ids")
    public ResponseEntity<List<State>> findByIds(@RequestParam("ids") List<String> ids) {
        List<State> stateList = stateService.findAllById(ids);
        return new ResponseEntity<>(stateList, HttpStatus.OK);
    }

    @GetMapping(value = "/by-rev")
    public ResponseEntity<List<State>> findByRevNo(@RequestParam("revNo") Long revNo) {
        List<State> stateList = stateService.findByRevNo(revNo);
        return new ResponseEntity<List<State>>(stateList, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity<State> delete(@RequestParam("id") String id) throws CustomException  {
        stateService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<TableResponse> getStates(@RequestBody PaginationDTO pagination){
        return new ResponseEntity(stateService.getAllStates(pagination), HttpStatus.ACCEPTED);
    }


}












