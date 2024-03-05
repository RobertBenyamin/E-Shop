package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;

import java.util.ArrayList;
import java.util.List;

public class PaymentRepository {
    private List<Payment> paymentData = new ArrayList<>();

    public Payment save(Payment payment) {
        int i = 0;
        for (Payment savedPayment : paymentData) {
            if (savedPayment.getId().equals(payment.getId())) {
                paymentData.remove(i);
                paymentData.add(i, payment);
                return payment;
            }
            i += 1;
        }
        paymentData.add(payment);
        return payment;
    }

    public Payment findById(String id) {
        for (Payment savedPayment : paymentData) {
            if (savedPayment.getId().equals(id)) {
                return savedPayment;
            }
        }
        return null;
    }

    public List<Payment> findAllByVoucherCode() {
        List<Payment> result = new ArrayList<>();
        for (Payment savedPayment : paymentData) {
            if (savedPayment.getMethod().equals("VOUCHER_CODE")) {
                result.add(savedPayment);
            }
        }
        return result;
    }

    public List<Payment> findAllByCashOnDelivery() {
        List<Payment> result = new ArrayList<>();
        for (Payment savedPayment : paymentData) {
            if (savedPayment.getMethod().equals("CASH_ON_DELIVERY")) {
                result.add(savedPayment);
            }
        }
        return result;
    }

    public List<Payment> getAllPayment() {
        return paymentData;
    }
}
