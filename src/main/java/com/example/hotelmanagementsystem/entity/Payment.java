package com.example.hotelmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payment_id;
    private Date paymentDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "payment_type_id",
            referencedColumnName = "type_id"
    )
    private PaymentType paymentType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    User user;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(
            name = "reserve_id",
            referencedColumnName = "reserveId"
    )
    Reserve reserve;
}
