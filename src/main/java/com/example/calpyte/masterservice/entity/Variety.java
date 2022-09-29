package com.example.calpyte.masterservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name="variety")
@Where(clause = "is_deleted = false")

public class Variety extends AuditableBase  implements Serializable {
    private String name;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "crop_id", referencedColumnName = "id")
    @JsonIgnoreProperties("variety")
    private Crop crop;

}
