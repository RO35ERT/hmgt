package com.example.hotelmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {
   @Id
    private String roomNumber;
    private Boolean status;
    private Double price;

    private String ammenities;
    @Lob
    @Column(length = 1000)
    private byte[] image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "room_type_id",
            referencedColumnName = "roomTypeId"
    )
    private RoomType roomType;

    @OneToMany
    private List<Reserve> reserve;

    @ManyToOne
    @JoinColumn(
            name = "branch_id"
    )
    private Branch branch;

    public Room(String roomNumber, Boolean status, Double price, byte[] image) {
        this.roomNumber = roomNumber;
        this.status = status;
        this.price = price;
        this.image = image;
    }

    public Room() {
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public List<Reserve> getReserve() {
        return reserve;
    }

    public void setReserve(List<Reserve> reserve) {
        this.reserve = reserve;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Branch getBranch() {
        return branch;
    }

    public String getAmmenities() {
        return ammenities;
    }

    public void setAmmenities(String ammenities) {
        this.ammenities = ammenities;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber='" + roomNumber + '\'' +
                ", status=" + status +
                ", price=" + price +
                ", image=" + Arrays.toString(image) +
                ", roomType="+ roomType+
                '}';
    }

}
