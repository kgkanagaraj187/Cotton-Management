package com.example.calpyte.masterservice.service.impl;


import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.dto.pagination.SearchCriteria;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.entity.Farmer;
import com.example.calpyte.masterservice.masterexception.CustomException;
import com.example.calpyte.masterservice.repo.FarmerRepository;
import com.example.calpyte.masterservice.service.FarmerService;
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
public class FarmerServiceImpl implements FarmerService {

    @Autowired
    private FarmerRepository farmerRepository;


    @Override
    public void saveFarmer(Farmer farmer) throws CustomException {

        validate(farmer);
        Mapper.setAuditable(farmer);
        farmerRepository.save(farmer);

    }

    @Override
    public List<Farmer> getAllFarmers() {
        return null;
    }

    private void validate(Farmer farmer) throws CustomException {
        Farmer eFarmer = farmerRepository.findByName(farmer.getName());
        if (eFarmer != null && (!Objects.equals(eFarmer.getId(), farmer.getId()))) {
            throw new CustomException("Duplicate Farmer name");
        }
    }

   @Override
    public List<Farmer> getFarmers() {
        return farmerRepository.findAll();
    }

    @Override
    public Farmer findById(String id) {
        Optional<Farmer> farmerOptional = farmerRepository.findById(id);
        if (farmerOptional.isPresent()) {
            Farmer farmer = new Farmer();
            farmer.setId(farmerOptional.get().getId());
            farmer.setName(farmerOptional.get().getName());
            return farmer;
        }
        return null;
    }
    @Override
    public List<Farmer> findAllById(List<String> ids) {
        return farmerRepository.findAllById(ids);

    }

    @Override
    public List<Farmer> findByRevNo(Long revNo) {
        List<Farmer> farmerList = farmerRepository.findByRevisionNoGreaterThan(revNo);
        return farmerList.stream().map(this::copyToDTO).collect(Collectors.toList());
    }

    private Farmer copyToDTO(Farmer farmer) {
        Farmer eFarmer = Mapper.map(farmer, Farmer.class);
        eFarmer.setId(farmer.getId());
        eFarmer.setName(farmer.getName());
        return eFarmer;
    }

    @Override
    public void delete(String id) throws CustomException {
        Optional<Farmer> farmerOpt = farmerRepository.findById(id);
        if (farmerOpt.isPresent()) {
            Farmer farmer = farmerOpt.get();
        }
    }
    @Override
    public TableResponse getFarmers(PaginationDTO pagination) {
        TableResponse response;
        Pageable paging = PageRequest.of(pagination.getPageNo() - 1, pagination.getPageSize());
        Page<Farmer> farmerPaged = farmerRepository.findAll(getSpecifications(pagination) , paging);
        if (farmerPaged.hasContent()) {
            List<Farmer> farmerList = farmerPaged.getContent();
            response = new TableResponse(pagination.getDraw(), (int) farmerPaged.getTotalElements(), (int) farmerPaged.getTotalElements(),
                    farmerList);
        } else {
            response = new TableResponse(pagination.getDraw(), (int) farmerPaged.getTotalElements(), (int) farmerPaged.getTotalElements(),
                    new ArrayList<>());
        }
        return response;
    }
    private Specification<Farmer> getSpecifications(PaginationDTO pagination) {
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