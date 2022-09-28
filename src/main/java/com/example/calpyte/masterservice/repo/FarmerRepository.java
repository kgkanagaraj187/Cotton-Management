package com.example.calpyte.masterservice.repo;

import com.example.calpyte.masterservice.entity.Country;
import com.example.calpyte.masterservice.entity.Farmer;
import com.example.calpyte.masterservice.entity.Village;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer,String> {

    @Query("FROM Farmer c WHERE c.revisionNo > :revNo")
    List<Farmer> findByRevisionNoGreaterThan(@Param("revNo") Long revNo);

    Farmer findByName(String name);
    Farmer findAllById(String name);

    Page<Farmer> findAll(Specification<Farmer> specifications, Pageable paging);
}
