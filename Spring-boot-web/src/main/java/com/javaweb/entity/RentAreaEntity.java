package com.javaweb.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "rentarea")
@Getter
@Setter
public class RentAreaEntity extends BaseEntity {

    @Column(name = "value")
    private Long value;

    @ManyToOne
    @JoinColumn(name = "buildingid", nullable = false)
    @JsonBackReference
    private BuildingEntity buildingEntity;
}
