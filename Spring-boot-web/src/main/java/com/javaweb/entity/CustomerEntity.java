package com.javaweb.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
public class CustomerEntity extends BaseEntity{
    @Column(name = "fullname", nullable = false)
    private String fullName;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "companyname")
    private String companyName;

    @Column(name = "demand")
    private String demand;

    @Column(name = "status")
    private String status;

    @Column(name = "is_active")
    private Integer isActive;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "assignmentcustomer",
                joinColumns = @JoinColumn(name = "customerid", nullable = false),
                inverseJoinColumns = @JoinColumn(name = "staffid", nullable = false))
    List<UserEntity> users;

    @OneToMany(mappedBy = "customer")
    private  List<TransactionEntity> transactions;
}
