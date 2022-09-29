package com.example.calpyte.masterservice.repo;


import com.example.calpyte.masterservice.entity.Crop;
import com.example.calpyte.masterservice.entity.Variety;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VarietyRepository extends JpaRepository<Variety, String>,JpaSpecificationExecutor<Variety> {

    Variety findByName(String name);

    Variety save(Variety variety);

    Page<Variety> findAll(Specification<Variety> specifications, Pageable paging);
}
