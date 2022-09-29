package com.example.calpyte.masterservice.service.impl;

import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.dto.pagination.SearchCriteria;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.entity.Country;
import com.example.calpyte.masterservice.entity.Crop;
import com.example.calpyte.masterservice.masterexception.CustomException;
import com.example.calpyte.masterservice.repo.CropRepository;
import com.example.calpyte.masterservice.service.CropService;
import com.example.calpyte.masterservice.specification.CountrySpecification;
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
public class CropServiceImpl implements CropService {
    @Autowired
    private CropRepository cropRepository;

    @Override
    public List<Crop> getAllCrops() {
        return cropRepository.findAll();
    }


    @Override
    public void saveCrop(Crop crop) throws CustomException {

        validate(crop);
        Mapper.setAuditable(crop);
        cropRepository.save(crop);

    }


    @Override
    public Crop findById(String id) {
        Optional<Crop> cropOptional = cropRepository.findById(id);
        if (cropOptional.isPresent()) {
            Crop crop = new Crop();
            crop.setId(cropOptional.get().getId());
            crop.setName(cropOptional.get().getName());
            return crop;
        }
        return null;
    }

    @Override
    public List<Crop> findAllById(List<String> ids) {
        return cropRepository.findAllById(ids);

    }

    @Override
    public void delete(String id) throws CustomException {
        Optional<Crop> cropOpt = cropRepository.findById(id);
        if (cropOpt.isPresent()) {
            Crop crop = cropOpt.get();
             crop.setIsDeleted(true);
             cropRepository.save(crop);
        }
    }

    @Override
    public TableResponse getCrops(PaginationDTO pagination) {
        TableResponse response;
        Pageable paging = PageRequest.of(pagination.getPageNo() - 1, pagination.getPageSize());
        Page<Crop> cropPaged = cropRepository.findAll(getSpecifications(pagination) , paging);
        if (cropPaged.hasContent()) {
            List<Crop> countryList = cropPaged.getContent();
            response = new TableResponse(pagination.getDraw(), (int) cropPaged.getTotalElements(), (int) cropPaged.getTotalElements(),
                    countryList);
        } else {
            response = new TableResponse(pagination.getDraw(), (int) cropPaged.getTotalElements(), (int) cropPaged.getTotalElements(),
                    new ArrayList<>());
        }
        return response;
    }




    private Specification<Crop> getSpecifications(PaginationDTO pagination) {
        List<SearchCriteria> params = new ArrayList<>(pagination.getFilter());

        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
                .map(CountrySpecification::new)
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

    private void validate(Crop crop) throws CustomException {
        Crop eCrop = cropRepository.findByName(crop.getName());
        if (eCrop != null && (!Objects.equals(eCrop.getId(), crop.getId()))) {
            throw new CustomException("Duplicate Crop name");
        }
    }

}
