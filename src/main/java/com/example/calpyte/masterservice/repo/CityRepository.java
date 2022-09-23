package com.example.calpyte.masterservice.repo;

import com.example.calpyte.masterservice.entity.City;
import com.example.calpyte.masterservice.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City,String> {

    @Query("FROM City c WHERE c.revisionNo > :revNo")
    List<City> findByRevisionNoGreaterThan(@Param("revNo") Long revNo);
}
