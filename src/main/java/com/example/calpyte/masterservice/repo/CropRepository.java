package com.example.calpyte.masterservice.repo;

import com.example.calpyte.masterservice.entity.Country;
import com.example.calpyte.masterservice.entity.Crop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CropRepository extends JpaRepository<Crop, String>, JpaSpecificationExecutor<Crop> {

        Crop findByName(String name);

        Crop save(Crop crop);

    Page<Crop> findAll(Specification<Crop> specifications, Pageable paging);
}
