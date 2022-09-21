package com.example.calpyte.masterservice.repo;

import com.example.calpyte.masterservice.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {
    Country findByName(String name);
    Country findAllById(String name);

    /*Page<Country> findAll(Specification<State> specifications, Pageable paging);*/

    @Query("FROM Country c WHERE c.revisionNo > :revNo")
    List<Country> findByRevisionNoGreaterThan(@Param("revNo") Long revNo);
}
