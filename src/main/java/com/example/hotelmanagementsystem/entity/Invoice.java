package com.example.hotelmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "invoices")
public class Invoice {

    @Id
    private String invoiceNo;
    private Date issuedAt;

    @ManyToOne
    @JoinColumn(
            name = "branch_id"
    )
    private Branch branch;

    @OneToOne
    @JoinColumn(
            name = "reserve_id"
    )
    private Reserve reserve;
}
