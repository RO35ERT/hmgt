package com.example.hotelmanagementsystem.repository;

import com.example.hotelmanagementsystem.entity.Branch;
import com.example.hotelmanagementsystem.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DateTimeException;
import java.util.Date;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,String> {
    public Invoice findByInvoiceNo(String invoiceNo);
    public List<Invoice> findAllByBranch(Branch branch);

    public List<Invoice> findAllByInvoiceNoContaining(String invoiceNo);

    @Query(value = "select sum(i.reserve.totalAmount) from invoices i where i.issuedAt between :start and :end")
    public Double getTotalBetweenStartAndEnd(Date start,Date end);

    @Query("SELECT count(*) from Reserve r where r.paid=true and r.createdAt between :start and :end")
    Double countAllByBookingDate(Date start, Date end);
}
