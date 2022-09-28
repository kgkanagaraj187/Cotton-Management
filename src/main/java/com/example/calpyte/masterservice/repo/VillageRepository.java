package com.example.calpyte.masterservice.repo;

import com.example.calpyte.masterservice.entity.City;
import com.example.calpyte.masterservice.entity.Country;
import com.example.calpyte.masterservice.entity.Village;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface VillageRepository extends JpaRepository<Village,String> {

    @Query("FROM City c WHERE c.revisionNo > :revNo")
    List<Village> findByRevisionNoGreaterThan(@Param("revNo") Long revNo);

    Village findByName(String name);

    Page<Village> findAll(Specification<Village> specifications, Pageable paging);
}
