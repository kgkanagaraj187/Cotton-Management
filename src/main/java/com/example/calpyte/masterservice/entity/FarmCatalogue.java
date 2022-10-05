package com.example.calpyte.masterservice.entity;


import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "farmCatalogue")
@Where(clause = "is_deleted = false")

public class FarmCatalogue extends AuditableBase implements Serializable {
    private String name;

    private Integer type;
   @OneToMany(mappedBy = "catalogue_value")
    private List<CatalogueValue> catalogueValue;
}



