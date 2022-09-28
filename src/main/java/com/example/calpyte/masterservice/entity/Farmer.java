package com.example.calpyte.masterservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "farmer")
@Where(clause = "is_deleted = false")
public class Farmer extends AuditableBase implements Serializable {

    private String name;
private  String dob;
     @Column(unique = false)
    private String mobileNumber;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "village_id", referencedColumnName = "id")
    @JsonIgnoreProperties("farmer")
    private Village village;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "farmer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Form> forms;


}
