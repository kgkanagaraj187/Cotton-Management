package com.example.calpyte.masterservice.service.impl;


import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.dto.pagination.SearchCriteria;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.entity.Country;
import com.example.calpyte.masterservice.entity.District;
import com.example.calpyte.masterservice.entity.State;
import com.example.calpyte.masterservice.masterexception.CustomException;
import com.example.calpyte.masterservice.repo.DistrictRepository;
import com.example.calpyte.masterservice.service.DistrictService;
import com.example.calpyte.masterservice.specification.CountrySpecification;
import com.example.calpyte.masterservice.specification.DistrictSpecification;
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
public class DistrictServiceImpl implements DistrictService {

    @Autowired
    private DistrictRepository districtRepository;



    @Override
    public void saveDistrict(District district) throws CustomException {
        validate(district);
        Mapper.setAuditable(district);
        districtRepository.save(district);

    }

    @Override
    public TableResponse getAllDistricts(PaginationDTO pagination) {
        return null;
    }

    private void validate(District district) throws CustomException {
        District eDistrict = districtRepository.findByName(district.getName());
        if (eDistrict != null && (!Objects.equals(eDistrict.getId(), district.getId()))) {
            throw new CustomException("Duplicate District name");
        }
    }

    @Override
    public List<District> getAllDistricts() {
        return districtRepository.findAll();
    }


//        if (district.getNames() != null && district.getNames().size() > 0) {
//            List<District> districtList = district.getNames().stream().map(districtName -> {
//                district.setName(districtName);
//                return district;
//            }).collect(Collectors.toList());
//            districtRepository.saveAll(districtList);
//        }else{
//            validate(district);
//            Mapper.setAuditable(district);
//            districtRepository.save(district);
//        }
//    }


    @Override
    public District findById(String id) {
        Optional<District> districtOptional = districtRepository.findById(id);
        if (districtOptional.isPresent()) {
            District district = new District();
            district.setId(districtOptional.get().getId());
            district.setName(districtOptional.get().getName());
            return district;
        }
        return null;
    }

    @Override
    public List<District> findAllById(List<String> ids) {
        return districtRepository.findAllById(ids);

    }

    @Override
    public void delete(String id) throws CustomException {
        Optional<District> districtOpt = districtRepository.findById(id);
        if (districtOpt.isPresent()) {
            District district = districtOpt.get();
        }}

    @Override
    public List<District> getAllDistrict() {
        return null;
    }

    @Override
    public TableResponse getDistrict(PaginationDTO pagination) {
        TableResponse response;
        Pageable paging = PageRequest.of(pagination.getPageNo() - 1, pagination.getPageSize());
        Page<District> districtPaged = districtRepository.findAll(getSpecifications(pagination) , paging);
        if (districtPaged.hasContent()) {
            List<District> districtList = districtPaged.getContent();
            response = new TableResponse(pagination.getDraw(), (int) districtPaged.getTotalElements(), (int) districtPaged.getTotalElements(), districtList);
        } else {
            response = new TableResponse(pagination.getDraw(), (int) districtPaged.getTotalElements(), (int) districtPaged.getTotalElements(),
                    new ArrayList<>());
        }
        return response;
    }

    @Override
    public List<District> findByRevNo(Long revNo) {
        List<District> districtList = districtRepository.findByRevisionNoGreaterThan(revNo);
        return districtList.stream().map(this::copyToDTO).collect(Collectors.toList());
    }

    private District copyToDTO(District district) {
        District eDistrict = Mapper.map(district, District.class);
        eDistrict.setId(district.getId());
        eDistrict.setName(district.getName());
        return eDistrict;
    }

    private Specification<District> getSpecifications(PaginationDTO pagination) {
        List<SearchCriteria> params = new ArrayList<>(pagination.getFilter());

        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
                .map(DistrictSpecification::new)
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
