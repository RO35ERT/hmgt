package com.example.hotelmanagementsystem.controller;

import com.example.hotelmanagementsystem.service.InvoiceService;
import com.example.hotelmanagementsystem.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UniversalController {

    @Autowired
    public InvoiceService invoiceService;

    @Autowired
    public ReserveService reserveService;
    @GetMapping("/getIncomePerMonth/{date}")
    public Double getIncomePerMonth(@PathVariable("date") String date){
        if(invoiceService.getSumPerMonth(date) == null){
            return 0.0;
        }
        return invoiceService.getSumPerMonth(date);
    }

    @GetMapping("/getPendingReservePerMonth/{date}")
    public Double getPendingReservePerMonth(@PathVariable("date") String date){
        if(reserveService.countPendingReserveByMonth(date) == null){
            return 0.0;
        }
        return reserveService.countPendingReserveByMonth(date);
    }

    @GetMapping("/getReservePerMonth/{date}")
    public Double getReservePerMonth(@PathVariable("date") String date){
        if(reserveService.countAllReserveByMonth(date) == null){
            return 0.0;
        }
        return reserveService.countAllReserveByMonth(date);
    }
}
