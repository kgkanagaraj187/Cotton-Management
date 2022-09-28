package com.example.calpyte.masterservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name = "form")
@Where(clause = "is_deleted = false")
public class Form extends AuditableBase implements Serializable {
    private String name;
    private String latitude;
    private String longitude;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
   @JoinColumn(name = "farmer_id",referencedColumnName = "id")
    @JsonIgnoreProperties("form")
    private Farmer farmer;

;
}
