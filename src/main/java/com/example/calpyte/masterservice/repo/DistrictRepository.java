package com.example.calpyte.masterservice.repo;

import com.example.calpyte.masterservice.entity.District;
import com.example.calpyte.masterservice.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District,String> {

    District findByName(String name);
    @Query("FROM District c WHERE c.revisionNo > :revNo")
    List<District> findByRevisionNoGreaterThan(@Param("revNo") Long revNo);

}
