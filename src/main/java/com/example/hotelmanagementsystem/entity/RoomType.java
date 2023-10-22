package com.example.hotelmanagementsystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "room_types")
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomTypeId;

    private String roomType;

    public RoomType() {
    }

    public RoomType(Long roomTypeId, String roomType) {
        this.roomTypeId = roomTypeId;
        this.roomType = roomType;
    }

    public Long getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return  roomType;
    }
}
