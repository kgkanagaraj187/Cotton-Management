package com.example.calpyte.masterservice.controller;

import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.entity.Farmer;
import com.example.calpyte.masterservice.entity.Form;
import com.example.calpyte.masterservice.masterexception.CustomException;
import com.example.calpyte.masterservice.service.FormService;
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
@RequestMapping("/form")
public class FormController {

    @Autowired
    private FormService formService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveForm(@RequestBody Form form) throws CustomException{
        formService.saveForm(form);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }
    @GetMapping(value = "/forms")
    public ResponseEntity<List<Form>> getAllForms(){
            List<Form> formList= formService.getAllForms();
                return new ResponseEntity<>(formList,HttpStatus.OK);
    }

    @GetMapping(value = "/by-id")
    public ResponseEntity<Form> findById(@RequestParam("id") String id) {
        Form form = formService.findById(id);
        return new ResponseEntity<>(form, HttpStatus.OK);
    }

    @GetMapping(value = "/by-ids")
    public ResponseEntity<List<Form>> findByIds(@RequestParam("ids") List<String> ids) {
        List<Form> formList = formService.findAllById(ids);
        return new ResponseEntity<>(formList, HttpStatus.OK);
    }
    @GetMapping(value = "/by-rev")
    public ResponseEntity<List<Form>> findByRevNo(@RequestParam("revNo") Long revNo) {
        List<Form> formList = formService.findByRevNo(revNo);
        return new ResponseEntity<List<Form>>(formList, HttpStatus.OK);
    }
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity<Form> delete(@RequestParam("id") String id) throws CustomException  {
        formService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping(value = "/")
    public ResponseEntity<TableResponse> getForms(@org.springframework.web.bind.annotation.RequestBody PaginationDTO pagination){
        return new ResponseEntity(formService.getForms(pagination), HttpStatus.ACCEPTED);
    }

}
