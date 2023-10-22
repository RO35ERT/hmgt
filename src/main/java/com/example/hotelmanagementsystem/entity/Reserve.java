package com.example.hotelmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Optional;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "reserves")
public class Reserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reserveId;

    private Date checkInDate;

    private Date checkOutDate;
    private Double totalAmount;
    private Boolean paid;
    private Date createdAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name="room_number",
            referencedColumnName = "roomNumber"
    )
    private Room room;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    private User user;

}
