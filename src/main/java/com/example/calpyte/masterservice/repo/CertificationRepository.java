package com.example.calpyte.masterservice.repo;

import com.example.calpyte.masterservice.entity.Certification;
import com.example.calpyte.masterservice.entity.Variety;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, String>, JpaSpecificationExecutor<Certification> {

    Certification findByName(String name);

    Certification save(Certification certification);

    Page<Certification> findAll(Specification<Certification> specifications, Pageable paging);
}
