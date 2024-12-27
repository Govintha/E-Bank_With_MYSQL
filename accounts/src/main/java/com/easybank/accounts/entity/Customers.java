package com.easybank.accounts.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
//@Table(name = "customer") optinal when class name same as table name
public class Customers extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genrate Id
    private Long customerId;

    private String name;

    private String email;

    private String mobileNumber;


}
