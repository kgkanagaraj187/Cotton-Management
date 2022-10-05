package com.example.calpyte.masterservice.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name = "catalogue_value")
@Where(clause = "is_deleted = false")
public class CatalogueValue  extends AuditableBase implements Serializable {
    private String name;
    private String code;

}
