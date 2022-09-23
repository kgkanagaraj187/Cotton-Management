package com.example.calpyte.masterservice.repo;


import com.example.calpyte.masterservice.entity.Country;
import com.example.calpyte.masterservice.entity.State;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepository extends JpaRepository<State,String> {



    State findByName(String name);
    @Query("FROM State c WHERE c.revisionNo > :revNo")
    List<State> findByRevisionNoGreaterThan(@Param("revNo") Long revNo);


}
