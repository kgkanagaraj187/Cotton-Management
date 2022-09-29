package com.example.calpyte.masterservice.service.impl;

import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.dto.pagination.SearchCriteria;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.entity.Farmer;
import com.example.calpyte.masterservice.entity.Form;
import com.example.calpyte.masterservice.entity.Village;
import com.example.calpyte.masterservice.masterexception.CustomException;
import com.example.calpyte.masterservice.repo.FormRepository;
import com.example.calpyte.masterservice.service.FormService;
import com.example.calpyte.masterservice.specification.FarmerSpecification;
import com.example.calpyte.masterservice.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FormServiceImpl implements FormService {
    
    @Autowired
    private FormRepository formRepository;

    @Override
    public void saveForm(Form form) throws CustomException {
        validate(form);
        Mapper.setAuditable(form);
        formRepository.save(form);

    }

    @Override
    public List<Form> getAllForms() {
        return null;
    }

    private void validate(Form form) throws CustomException {
        Form eForm = formRepository.findByName(form.getName());
        if (eForm != null && (!Objects.equals(eForm.getId(), form.getId()))) {
            throw new CustomException("Duplicate Form name");
        }
    }

    @Override
    public List<Form> getForms() {
        return formRepository.findAll();
    }

    @Override
    public Form findById(String id) {
        Optional<Form> formOptional = formRepository.findById(id);
        if (formOptional.isPresent()) {
            Form form = new Form();
            form.setId(formOptional.get().getId());
            form.setName(formOptional.get().getName());
            return form;
        }
        return null;
    }
    @Override
    public List<Form> findAllById(List<String> ids) {
        return formRepository.findAllById(ids);

    }

    @Override
    public List<Form> findByRevNo(Long revNo) {
        List<Form> formList = formRepository.findByRevisionNoGreaterThan(revNo);
        return formList.stream().map(this::copyToDTO).collect(Collectors.toList());
    }

    private Form copyToDTO(Form form) {
        Form eForm = Mapper.map(form, Form.class);
        eForm.setId(form.getId());
        eForm.setName(form.getName());
        return eForm;
    }

    @Override
    public void delete(String id) throws CustomException {
        Optional<Form> formOpt = formRepository.findById(id);
        if (formOpt.isPresent()) {
            Form form = formOpt.get();
        }
    }
    @Override
    public TableResponse getForms(PaginationDTO pagination) {
        TableResponse response;
        Pageable paging = PageRequest.of(pagination.getPageNo() - 1, pagination.getPageSize());
        Page<Form> formPaged = formRepository.findAll(getSpecifications(pagination) , paging);
        if (formPaged.hasContent()) {
            List<Form> formList = formPaged.getContent();
            response = new TableResponse(pagination.getDraw(), (int) formPaged.getTotalElements(), (int) formPaged.getTotalElements(),
                    formList);
        } else {
            response = new TableResponse(pagination.getDraw(), (int) formPaged.getTotalElements(), (int) formPaged.getTotalElements(),
                    new ArrayList<>());
        }
        return response;
    }
    private Specification<Form> getSpecifications(PaginationDTO pagination) {
        List<SearchCriteria> params = new ArrayList<>(pagination.getFilter());

        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
                .map(FarmerSpecification::new)
                .collect(Collectors.toList());

        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i)
                    .isOrPredicate()
                    ? Specification.where(result)
                    .or(specs.get(i))
                    : Specification.where(result)
                    .and(specs.get(i));
        }

        return result;
    }
}
