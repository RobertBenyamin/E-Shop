package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    List<Payment> payments;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        // Make products
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setName("Sampo Cap Bambang");
        product1.setQuantity(2);
        products.add(product1);

        // Make orders
        List<Order> orders = new ArrayList<>();
        Order order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b", products, 1708560000L, "Safira Sudrajat");
        orders.add(order1);
        Order order2 = new Order("7f9e15bb-4b15-42f4-aebc-c3af385fb878", products, 1708570000L, "Safira Sudrajat");
        orders.add(order2);
        Order order3 = new Order("e334ef48-9eff-4da8-9487-8ee697ecbf1e", products, 1708570000L, "Bambang Sudrajat");
        orders.add(order3);

        // Make payments
        payments = new ArrayList<>();

        Map<String, String> paymentData1 = new HashMap<String, String>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment1 = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "VOUCHER_CODE", paymentData1,
                orders.getFirst());
        payments.add(payment1);

        Map<String, String> paymentData2 = new HashMap<String, String>();
        paymentData2.put("address", "Jalan Anggur");
        paymentData2.put("deliveryFee", "12000");
        Payment payment2 = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2d2", "CASH_ON_DELIVERY", paymentData2,
                orders.getFirst());
        payments.add(payment2);
    }

    @Test
    void testSaveCreate() {
        Payment payment = payments.get(1);
        Payment result = paymentRepository.save(payment);
        Payment findResult = paymentRepository.findById(payments.get(1).getId());

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getStatus(), findResult.getStatus());
        assertEquals(payment.getPaymentData(), findResult.getPaymentData());
        assertSame(payment.getOrder(), findResult.getOrder());
    }

    @Test
    void testSaveUpdate() {
        // create new payment
        Payment payment = payments.get(1);
        paymentRepository.save(payment);

        // change payment data
        Payment newPayment = new Payment(payment.getId(), payment.getMethod(), payments.get(0).getPaymentData(),
                payment.getOrder());
        Payment result = paymentRepository.save(newPayment);
        Payment findResult = paymentRepository.findById(payments.get(1).getId());

        assertEquals(result.getId(), findResult.getId());
        assertEquals(result.getId(), findResult.getId());
        assertEquals(result.getStatus(), findResult.getStatus());
        assertEquals(result.getMethod(), findResult.getMethod());
        assertEquals(payments.getFirst().getPaymentData(), findResult.getPaymentData());
        assertSame(result.getOrder(), findResult.getOrder());

    }

    @Test
    void testFindByIdIfIdFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }
        Payment findResult = paymentRepository.findById(payments.get(1).getId());

        assertEquals(payments.get(1).getId(), findResult.getId());
        assertEquals(payments.get(1).getMethod(), findResult.getMethod());
        assertEquals(payments.get(1).getStatus(), findResult.getStatus());
        assertEquals(payments.get(1).getPaymentData(), findResult.getPaymentData());
        assertSame(payments.get(1).getOrder(), findResult.getOrder());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }
        Payment findResult = paymentRepository.findById("zczc");
        assertNull(findResult);
    }

    @Test
    void testFindAllByMethodVoucherCode() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }
        List<Payment> paymentList = paymentRepository.findAllByVoucherCode();
        assertEquals(1, paymentList.size());
    }

    @Test
    void testFindAllByMethodCashOnDelivery() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }
        List<Payment> paymentList = paymentRepository.findAllByCashOnDelivery();
        assertEquals(1, paymentList.size());
    }

    @Test
    void testFindAll() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }
        List<Payment> paymentList = paymentRepository.getAllPayment();
        assertEquals(2, paymentList.size());
    }
}