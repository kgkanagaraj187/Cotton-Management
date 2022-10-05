package com.example.calpyte.masterservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "certification")
@Where(clause = "is_deleted = false")
public class Certification extends AuditableBase implements Serializable {
    private String name;

//    @OneToMany(mappedBy = "certification")
//    private List<Variety> variety;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "variety_id", referencedColumnName = "id")
    @JsonIgnoreProperties("certification")
    private Variety variety;
}
