package com.example.calpyte.masterservice.repo;

import com.example.calpyte.masterservice.entity.City;
import com.example.calpyte.masterservice.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, String>, JpaSpecificationExecutor<City> {
    City findByName(String name);
    City findAllById(String name);
    @Query("FROM City c WHERE c.revisionNo > :revNo")
    List<City> findByRevisionNoGreaterThan(@Param("revNo") Long revNo);
//    @Query("FROM City c WHERE c.revisionNo > :revNo")
//    List<City> findByRevisionNoGreaterThan(@Param("revNo") Long revNo);
//
//    City findByName(String name);
//
//    Page<City> findAll(Object specifications, Pageable paging);
}
