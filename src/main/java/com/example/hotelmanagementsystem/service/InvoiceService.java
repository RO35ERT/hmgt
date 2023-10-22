package com.example.hotelmanagementsystem.service;

import com.example.hotelmanagementsystem.entity.Branch;
import com.example.hotelmanagementsystem.entity.Invoice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InvoiceService {
    public String generateInvoiceNo(Branch branch);

    List<Invoice> getInvoice(Branch branch);

    Double getSumPerMonth(String date);

    Double getOccupiedPerMonth(String data);
}
