package com.example.hotelmanagementsystem.dto;

import org.springframework.web.multipart.MultipartFile;

public class RoomDto {

    private String roomNumber;
    private Boolean status;
    private Double price;
    private MultipartFile image;
    private String roomType;

    private String branch;

    private String ammenities;

    public RoomDto() {
    }

    public RoomDto(String roomNumber, Boolean status, Double price, MultipartFile image, String roomType, String branch, String ammenities) {
        this.roomNumber = roomNumber;
        this.status = status;
        this.price = price;
        this.image = image;
        this.roomType = roomType;
        this.branch = branch;
        this.ammenities = ammenities;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getAmmenities() {
        return ammenities;
    }

    public void setAmmenities(String ammenities) {
        this.ammenities = ammenities;
    }
}
