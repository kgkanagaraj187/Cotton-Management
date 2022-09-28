package com.example.calpyte.masterservice.service.impl;

import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.dto.pagination.SearchCriteria;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.entity.City;
import com.example.calpyte.masterservice.entity.Country;
import com.example.calpyte.masterservice.entity.Village;
import com.example.calpyte.masterservice.masterexception.CustomException;
import com.example.calpyte.masterservice.repo.VillageRepository;
import com.example.calpyte.masterservice.service.VillageService;
import com.example.calpyte.masterservice.specification.VillageSpecification;
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
public class VillageServiceImpl implements VillageService {

    @Autowired
    private VillageRepository villageRepository;



    @Override
    public void saveVillage(Village village) throws CustomException {
        validate(village);
        Mapper.setAuditable(village);
        villageRepository.save(village);

    }

    @Override
    public TableResponse getAllVillages(PaginationDTO pagination) {
        return null;
    }


//    @Override
//    public List<Villege> getAllVillege(PaginationDTO pagination) {
//        return null;
//    }

    private void validate(Village village) throws CustomException {
        Village eVillage = villageRepository.findByName(village.getName());
        if (eVillage != null && (!Objects.equals(eVillage.getId(), village.getId()))) {
            throw new CustomException("Duplicate Village name");
        }
    }
    @Override
    public List<Village> getAllvillages() {
        return villageRepository.findAll();
    }




    @Override
    public TableResponse getAllVillage(PaginationDTO pagination) {
        return null;
    }

    @Override
    public Village findById(String id) {
        Optional<Village> villageOptional = villageRepository.findById(id);
        if (villageOptional.isPresent()) {
            Village village = new Village();
            village.setId(villageOptional.get().getId());
            village.setName(villageOptional.get().getName());
            return village;
        }
        return null;
    }

    @Override
    public List<Village> findAllById(List<String> ids) {
        return villageRepository.findAllById(ids);

    }


    @Override
    public void delete(String id) throws CustomException {
        Optional<Village> villageOpt = villageRepository.findById(id);
        if (villageOpt.isPresent()) {
            Village village = villageOpt.get();
        }
    }

    @Override
    public List<Village> getAllVillage() {
        return null;
    }

    @Override
    public List<Village> getAllVillages() {
        return null;
    }
//
//    @Override
//    public List<Village> getAllVillage() {
//        return null;
//    }
//
//    @Override
//    public List<Village> getAllVillages() {
//        return null;
//    }
//
//    @Override
//    public List<Village> getAllVillage() {
//        return null;
//    }
////
//    @Override
//    public List<Village> getAllVillage() {
//        return null;
//    }

    @Override
    public TableResponse getVillages(PaginationDTO pagination) {
        TableResponse response;
        Pageable paging = PageRequest.of(pagination.getPageNo() - 1, pagination.getPageSize());
        Page<Village> villagePage = villageRepository.findAll(getSpecifications(pagination) , paging);
        if (villagePage.hasContent()) {
            List<Village> villageList = villagePage.getContent();
            response = new TableResponse(pagination.getDraw(), (int) villagePage.getTotalElements(), (int) villagePage.getTotalElements(),
                    villageList);
        } else {
            response = new TableResponse(pagination.getDraw(), (int) villagePage.getTotalElements(), (int) villagePage.getTotalElements(),
                    new ArrayList<>());
        }
        return response;
    }
//
//
    @Override
    public List<Village> findByRevNo(Long revNo) {
        List<Village> villageList = villageRepository.findByRevisionNoGreaterThan(revNo);
        return villageList.stream().map(this::copyToDTO).collect(Collectors.toList());
    }
    private Village copyToDTO(Village village) {
        Village eVillage = Mapper.map(village, Village.class);
        eVillage.setId(village.getId());
        eVillage.setName(village.getName());
        return eVillage;
    }

    private Specification<Village> getSpecifications(PaginationDTO pagination) {
        List<SearchCriteria> params = new ArrayList<>(pagination.getFilter());

        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
                .map(VillageSpecification::new)
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
