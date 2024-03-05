package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;

import java.util.ArrayList;
import java.util.List;

public class PaymentRepository {
    private List<Payment> paymentData = new ArrayList<>();

    public Payment save(Payment payment) {
        return payment;
    }
    public Payment findById(String id){
        return null;
    }
    public List<Payment> findAllByVoucherCode(){
        return null;
    }
    public List<Payment> findAllByCashOnDelivery(){
        return null;
    }
    public List<Payment> getAllPayment(){
        return null;
    }
}
