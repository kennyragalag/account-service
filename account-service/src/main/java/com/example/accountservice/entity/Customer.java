package com.example.accountservice.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table (name = "customer")
@Getter
@Setter 
@NoArgsConstructor
public class Customer {
    
    @Id
    @SequenceGenerator (name = "customer_sequence", sequenceName = "customer_sequence", initialValue = 10000001, allocationSize = 1)
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "customer_sequence")
    private Long customerNumber;

    @Column (name = "customer_name", nullable = false, length = 50)
    private String customerName;

    @Column (name = "customer_mobile", nullable = false, length = 20)
    private String customerMobile;

    @Column (name = "customer_email", nullable = false, length = 50)
    private String customerEmail;

    @Column (name = "address1", nullable = false, length = 100)
    private String address1;

    @Column (name = "address2", length = 100)
    private String address2;

    @OneToMany (mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts = new ArrayList<>();
    
    public void addAccount(Account account) {
        accounts.add(account);
        account.setCustomer(this);
    }
}
