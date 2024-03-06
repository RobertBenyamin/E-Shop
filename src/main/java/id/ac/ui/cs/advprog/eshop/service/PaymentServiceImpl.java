package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        Payment payment = new Payment("", method, paymentData, order);
        payment = paymentRepository.save(payment);
        return payment;
    }

    public Payment setStatus(Payment payment, String status) {
        Payment savedPayment = paymentRepository.findById(payment.getId());
        if (savedPayment != null) {
            savedPayment.setStatus(status);
            paymentRepository.save(savedPayment);
            return savedPayment;
        } else {
            throw new NoSuchElementException();
        }
    }

    public Payment getPayment(String paymentId) {
        Payment payment = paymentRepository.findById(paymentId);
        if (payment != null) {
            return payment;
        } else {
            throw new NoSuchElementException("Payment not found");
        }
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.getAllPayment();
    }
}
