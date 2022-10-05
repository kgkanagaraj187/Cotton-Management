package com.example.calpyte.masterservice.service.impl;

import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.dto.pagination.SearchCriteria;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.entity.Certification;
import com.example.calpyte.masterservice.masterexception.CustomException;
import com.example.calpyte.masterservice.repo.CertificationRepository;
import com.example.calpyte.masterservice.service.CertificationService;
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
public class CertificationServiceImpl implements CertificationService {

    @Autowired
    private CertificationRepository certificationRepository;

    @Override
    public void saveCertification(Certification certification) throws CustomException {

        validate(certification);
        Mapper.setAuditable(certification);
        certificationRepository.save(certification);
    }

    @Override
    public List<Certification> getAllCertification() {
        return certificationRepository.findAll();
    }

    @Override
    public Certification findById(String id) {
        Optional<Certification> certificationOptional = certificationRepository.findById(id);
        if (certificationOptional.isPresent()) {
            Certification certification = new Certification();
            certification.setId(certificationOptional.get().getId());
            certification.setName(certificationOptional.get().getName());
            return certification;
        }
        return null;
    }

    @Override
    public List<Certification> findAllById(List<String> ids) {
        return certificationRepository.findAllById(ids);

    }

    @Override
    public void delete(String id) throws CustomException {
        Optional<Certification> certificationOpt = certificationRepository.findById(id);
        if (certificationOpt.isPresent()) {
            Certification certification = certificationOpt.get();
            certification.setIsDeleted(true);
            certificationRepository.save(certification);
        }
    }

    @Override
    public TableResponse getCertification(PaginationDTO pagination) {
        TableResponse response;
        Pageable paging = PageRequest.of(pagination.getPageNo() - 1, pagination.getPageSize());
        Page<Certification> certificationPaged = certificationRepository.findAll(getSpecifications(pagination) , paging);
        if (certificationPaged.hasContent()) {
            List<Certification> varietyList = certificationPaged.getContent();
            response = new TableResponse(pagination.getDraw(), (int) certificationPaged.getTotalElements(), (int) certificationPaged.getTotalElements(),
                    varietyList);
        } else {
            response = new TableResponse(pagination.getDraw(), (int) certificationPaged.getTotalElements(), (int) certificationPaged.getTotalElements(),
                    new ArrayList<>());
        }
        return response;
    }

    @Override
    public List<Certification> getAllCertifications() {
        return null;
    }

    @Override
    public TableResponse getCertifications(PaginationDTO pagination) {
        return null;
    }


    private Specification<Certification> getSpecifications(PaginationDTO pagination) {
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

    private void validate (Certification certification) throws CustomException {
        Certification eCertification = certificationRepository.findByName(certification.getName());
        if(eCertification != null && (!Objects.equals(eCertification.getId(), certification.getId()))){
            throw new CustomException("Duplicate Crop name");
        }
    }
}
