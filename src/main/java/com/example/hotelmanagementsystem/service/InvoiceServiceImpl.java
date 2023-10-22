package com.example.hotelmanagementsystem.service;

import com.example.hotelmanagementsystem.entity.Branch;
import com.example.hotelmanagementsystem.entity.Invoice;
import com.example.hotelmanagementsystem.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public String generateInvoiceNo(Branch branch) {
        String branchInit = branch.getBranch().substring(0,3);
        Date date = new Date();
        String dateYear = date.toString();
        String year = dateYear.substring(dateYear.length()-4);

        int rand = (int)(Math.floor(Math.random()*(99999-10000+1)+10000));
        return branchInit+rand+year;
    }

    @Override
    public List<Invoice> getInvoice(Branch branch) {
        return null;
    }

    @Override
    public Double getSumPerMonth(String date) {
        Double sum = 0.0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
           Date start =  dateFormat.parse(date+"-01");
            Date end =  dateFormat.parse(date+"-31");
            sum = invoiceRepository.getTotalBetweenStartAndEnd(start,end);
        }catch (Exception e){
            System.out.println(e);
        }
        return sum;
    }

    @Override
    public Double getOccupiedPerMonth(String data) {
        return null;
    }


}
