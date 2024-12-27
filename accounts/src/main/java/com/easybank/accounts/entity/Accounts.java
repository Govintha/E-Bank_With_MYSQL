package com.easybank.accounts.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@Entity
public class Accounts  extends BaseEntity{


    @Column(name = "customer_id") // this is optional JPA remove the _
    private  Long customerId;
    /*
     I am not generating auto increment because
     I will generated Account number in my logic i just i mention
     @ID is my primary ID
     */
    @Id
    @Column(name = "account_number")
    private Long accountNumber;
    @Column(name = "account_type")
    private String accountType;
    @Column(name = "branch_address")
    private String branchAddress;

}
