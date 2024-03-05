package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentRepositoryTest {
    @InjectMocks
    PaymentRepository paymentRepository;
    List<Payment> payments;

    @Mock
    private Payment mockPayment;

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
        when(mockPayment.getId()).thenReturn("1");
        Payment result = paymentRepository.save(mockPayment);
        assertNotNull(result);
        assertNotNull(result.getId());
    }

    @Test
    void testSaveUpdate() {
        Payment payment = mock(Payment.class);
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234567890");
        Order order = mock(Order.class);
        when(payment.getId()).thenReturn("1");
        when(payment.getMethod()).thenReturn("VOUCHER_CODE");

        Payment savedPayment = paymentRepository.save(payment);
        Payment newPayment = new Payment(savedPayment.getId(), savedPayment.getMethod(), paymentData, order);
        Payment updatedPayment = paymentRepository.save(newPayment);

        assertEquals(newPayment.getId(), updatedPayment.getId());
        assertEquals(newPayment.getMethod(), updatedPayment.getMethod());
        assertEquals(newPayment.getStatus(), updatedPayment.getStatus());
        assertEquals(newPayment.getPaymentData(), updatedPayment.getPaymentData());
        assertSame(newPayment.getOrder(), updatedPayment.getOrder());
    }

    @Test
    void testFindByIdIfIdFound() {
        Payment newPayment = paymentRepository.save(payments.get(1));
        Payment findResult = paymentRepository.findById(newPayment.getId());

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