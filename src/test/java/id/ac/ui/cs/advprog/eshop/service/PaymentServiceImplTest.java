package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.*;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SuppressWarnings("unused")
@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {
    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    List<Map<String, String>> paymentDatas;
    List<Order> orders;

    @BeforeEach
    void setUp() {

        // Make product
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setName("Sampo Cap Bambang");
        product1.setQuantity(2);
        products.add(product1);

        // Make order
        orders = new ArrayList<>();
        Order order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b", products, 1708560000L, "Safira Sudrajat");
        orders.add(order1);
        Order order2 = new Order("7f9e15bb-4b15-42f4-aebc-c3af385fb078", products, 1708570000L, "Safira Sudrajat");
        orders.add(order2);

        // Make payment datas
        paymentDatas = new ArrayList<>();

        Map<String, String> paymentData1 = new HashMap<String, String>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");
        paymentDatas.add(paymentData1);

        Map<String, String> paymentData2 = new HashMap<String, String>();
        paymentData2.put("address", "Jalan Anggur");
        paymentData2.put("deliveryFee", "12000");
        paymentDatas.add(paymentData2);
    }

    @Test
    void testCreatePayment() {
        String id = "75c64e96-d4d7-454b-8ee5-7086efff516c";
        Payment payment = new Payment(id, "VOUCHER_CODE", paymentDatas.get(0), orders.get(1));

        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(orders.get(1), "VOUCHER_CODE", paymentDatas.get(0));

        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testCreateInvalidPaymentMethod() {
        String id = "75c64e96-d4d7-454b-8ee5-7086efff516c";
        Payment payment = new Payment(id, "VOUCHER_CODE", paymentDatas.get(0), orders.get(1));

        assertThrows(IllegalArgumentException.class,
                () -> paymentService.addPayment(orders.get(1), "PAYLATER", paymentDatas.get(0)));

        verify(paymentRepository, times(0)).save(payment);
    }

    @Test
    void testSetStatus() {
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "CASH_ON_DELIVERY", paymentDatas.get(0),
                orders.get(1));

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());

        Payment newPayment = new Payment(payment.getId(), "VOUCHER_CODE", paymentDatas.get(0), payment.getOrder());

        doReturn(payment).when(paymentRepository).findById(payment.getId());
        doReturn(newPayment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(payment.getId(), result.getId());
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusInvalidStatus() {
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "CASH_ON_DELIVERY", paymentDatas.get(0),
                orders.get(1));
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());

        doReturn(payment).when(paymentRepository).findById(payment.getId());

        assertThrows(IllegalArgumentException.class,
                () -> paymentService.setStatus(payment, "MEOW"));

        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testSetStatusInvalidPaymentId() {
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "CASH_ON_DELIVERY", paymentDatas.get(0),
                orders.get(1));

        doReturn(null).when(paymentRepository).findById(payment.getId());

        assertThrows(NoSuchElementException.class,
                () -> paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue()));

        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testGetPaymentIfIdFound() {
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "CASH_ON_DELIVERY", paymentDatas.get(0),
                orders.get(1));

        doReturn(payment).when(paymentRepository).findById(payment.getId());

        Payment result = paymentService.getPayment(payment.getId());
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testGetPaymentIfIdNotFound() {
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "CASH_ON_DELIVERY", paymentDatas.get(0),
                orders.get(1));

        assertNull(paymentService.getPayment(payment.getId()));
    }

    @Test
    void testGetAllPayments() {
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "CASH_ON_DELIVERY", paymentDatas.get(0),
                orders.get(1));
        List<Payment> payments = new ArrayList<>();
        payments.add(payment);

        doReturn(payments).when(paymentRepository).getAllPayment();

        List<Payment> results = paymentService.getAllPayments();
        assertEquals(payments, results);

        assertEquals(1, results.size());
    }
}