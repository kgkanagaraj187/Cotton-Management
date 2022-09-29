package com.example.calpyte.masterservice.repo;

import com.example.calpyte.masterservice.entity.Farmer;
import com.example.calpyte.masterservice.entity.Form;
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
public interface FormRepository extends JpaRepository<Form,String> {

    @Query("FROM Form c WHERE c.revisionNo > :revNo")
    List<Form> findByRevisionNoGreaterThan(@Param("revNo") Long revNo);

    Form findByName(String name);
    Form findAllById(String name);

    Page<Form> findAll(Specification<Form> specifications, Pageable paging);
}
