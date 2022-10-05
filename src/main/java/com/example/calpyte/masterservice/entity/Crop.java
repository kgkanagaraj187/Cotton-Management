package com.example.calpyte.masterservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "crop")
@Where(clause = "is_deleted = false")
public class Crop extends AuditableBase implements Serializable {
    private String name;

  @OneToMany(mappedBy = "crop")
  private List<Variety> variety;
//@OneToMany(fetch = FetchType.LAZY, mappedBy = "crop", cascade = CascadeType.ALL)
//@JsonIgnoreProperties("crop")
//private List<Variety> variety;

    }
