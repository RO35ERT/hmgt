package com.example.hotelmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomInvoiceDto {
    private String roomNumber;
    private String numberOfDays;
    private Boolean status;
    private String branch;
    private Boolean issued;
    private String invoiceNo;
    private Boolean checkedIn;
    private Boolean checkedOut;
}
