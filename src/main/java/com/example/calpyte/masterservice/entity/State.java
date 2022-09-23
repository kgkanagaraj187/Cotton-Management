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
import java.util.Collection;
import java.util.List;

@Table(name = "state")
@Data
@Entity
@Where(clause = "is_deleted = false")
public class State extends AuditableBase implements Serializable  {
    private String name;

//    @Transient
//    private List<String> names;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    @JsonIgnoreProperties("state")
    private Country country;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "state", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<District> districts;


}
