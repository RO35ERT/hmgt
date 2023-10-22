package com.example.hotelmanagementsystem.dto;

import com.example.hotelmanagementsystem.entity.RoomType;
import org.springframework.web.multipart.MultipartFile;

public class ToRoomDto {
    private String roomNumber;
    private Boolean status;
    private Double price;
    private String image;
    private RoomType roomType;

    private String branch;

    public ToRoomDto() {
    }

    public ToRoomDto(String roomNumber, Boolean status, Double price, String image, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.status = status;
        this.price = price;
        this.image = image;
        this.roomType = roomType;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "ToRoomDto{" +
                "roomNumber='" + roomNumber + '\'' +
                ", status=" + status +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", roomType='" + roomType + '\'' +
                '}';
    }
}
