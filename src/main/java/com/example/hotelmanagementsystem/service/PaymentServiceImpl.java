package com.example.hotelmanagementsystem.service;

import com.example.hotelmanagementsystem.config.EmailSender;
import com.example.hotelmanagementsystem.entity.*;
import com.example.hotelmanagementsystem.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final ReserveRepository reserveRepository;
    private final EmailSender emailSender;

    private final InvoiceService invoiceService;

    private final InvoiceRepository invoiceRepository;

    private final BranchRepository branchRepository;

    private final BookingRepository bookingRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, ReserveRepository reserveRepository, EmailSender emailSender, InvoiceService invoiceService, InvoiceRepository invoiceRepository, BranchRepository branchRepository, BookingRepository bookingRepository) {
        this.paymentRepository = paymentRepository;
        this.reserveRepository = reserveRepository;
        this.emailSender = emailSender;
        this.invoiceService = invoiceService;
        this.invoiceRepository = invoiceRepository;
        this.branchRepository = branchRepository;
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    @Override
    public Payment createPayement(PaymentType paymentType, Reserve reserve) {
        Reserve newReserve = reserveRepository.findById(reserve.getReserveId()).get();
        User user = newReserve.getUser();
        Payment payment = new Payment();
        payment.setPaymentDate(new Date());
        payment.setPaymentType(paymentType);
        payment.setUser(user);
        payment.setReserve(newReserve);
        newReserve.setPaid(true);
//        emailSender.sendPaymentEmail(user,newReserve);
        Invoice invoice = new Invoice();
        Branch branch = reserve.getRoom().getBranch();
        invoice.setBranch(branch);
        invoice.setInvoiceNo(checkNo(branch));
        invoice.setIssuedAt(new Date());
        invoice.setReserve(newReserve);
        invoiceRepository.save(invoice);
        Reserve reserve1 = reserveRepository.save(newReserve);
        Booking booking = new Booking();
        booking.setReserve(reserve1);
        booking.setAdmit(false);
        booking.setPenalty(0.0);
        booking.setCheckOut(false);
        bookingRepository.save(booking);
        return paymentRepository.save(payment);
    }

    @Override
    public String checkNo(Branch branch){
        String invoiceNo = invoiceService.generateInvoiceNo(branch);
        Invoice invoice1 = invoiceRepository.findByInvoiceNo(invoiceNo);
        if(invoice1 !=null){
            checkNo(branch);
        }
        return  invoiceNo;
    }
}
