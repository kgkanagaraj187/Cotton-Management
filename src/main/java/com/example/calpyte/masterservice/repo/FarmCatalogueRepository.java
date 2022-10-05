package com.example.calpyte.masterservice.repo;

import com.example.calpyte.masterservice.entity.FarmCatalogue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmCatalogueRepository extends JpaRepository<FarmCatalogue, String>, JpaSpecificationExecutor<FarmCatalogue>{

    FarmCatalogue findByName(String name);

    FarmCatalogue save(FarmCatalogue farmCatalogue);

    Page<FarmCatalogue> findAll(Specification<FarmCatalogue> specifications, Pageable paging);
}