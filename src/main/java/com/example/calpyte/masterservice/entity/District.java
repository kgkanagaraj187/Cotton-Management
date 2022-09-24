package com.example.calpyte.masterservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "district")
@Where(clause = "is_deleted = false")
public class District extends AuditableBase  implements Serializable {
    private String name;

//    @Transient
//    private List<String> names;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id", referencedColumnName = "id")
    @JsonIgnoreProperties("district")
    private State state;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "district", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<City> cities;
  /*  @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "country", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private  Country country;*/
//
//    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
//    @JoinColumn(name = "state_id",nullable = false)
//    @JsonManagedReference
//    private State state;
}
