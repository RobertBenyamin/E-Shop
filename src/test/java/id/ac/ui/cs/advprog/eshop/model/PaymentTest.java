package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unused")
public class PaymentTest {
    private List<Order> orders;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        this.orders = new ArrayList<>();
        this.products = new ArrayList<>();

        Product product1 = new Product();
        product1.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setName("Sampo Cap Bambang");
        product1.setQuantity(2);

        Product product2 = new Product();
        product2.setId("a2c62328-4a37-4664-83c7-f32db8620155");
        product2.setName("Sabun Cap Usep");
        product2.setQuantity(1);

        this.products.add(product1);
        this.products.add(product2);

        Order order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b", this.products, 1708560000L, "Safira Sudrajat");
        Order order2 = new Order("65525631-a210-074c-45b6-54eb1396d79b", this.products, 1708570000L, "Bambang Bambang");

        this.orders.add(order1);
        this.orders.add(order2);
    }

    @Test
    void testCreatePaymentEmptyMethod() {
        Map<String, String> paymentData = new HashMap<String, String>();
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "", paymentData,
                    this.orders.getFirst());
        });
    }

    @Test
    void testCreatePaymentInvalidMethod() {
        Map<String, String> paymentData = new HashMap<String, String>();
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "PAYLATER", paymentData,
                    this.orders.getFirst());
        });
    }

    @Test
    void testCreatePaymentEmptyPaymentData() {
        Map<String, String> paymentData = new HashMap<String, String>();
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "CASH_ON_DELIVERY", paymentData,
                    this.orders.getFirst());
        });
    }

    @Test
    void testCreatePaymentEmptyOrder() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("address", "Jalan Anggur");
        paymentData.put("deliveryFee", "12000");
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "CASH_ON_DELIVERY", paymentData,
                    null);
        });
    }

    // Voucher Code
    @Test
    void testCreatePaymentVoucherCodeSuccess() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "VOUCHER_CODE",
                paymentData, this.orders.getFirst());
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeRejectedNot16Characters() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("voucherCode", "ESHOP1234ABC56789");
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "VOUCHER_CODE",
                paymentData, this.orders.getFirst());
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeRejectedNotStartedWithESHOP() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("voucherCode", "ESSHOP1234AB5678");
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "VOUCHER_CODE",
                paymentData, this.orders.getFirst());
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeRejectedNotContain8NumericalCharacters() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("voucherCode", "ESHOP1234ABC567D");
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "VOUCHER_CODE",
                paymentData, this.orders.getFirst());
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetStatusPaymentVoucherCodeSucess() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("voucherCode", "ESHOP1234ABC567D");
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "VOUCHER_CODE",
                paymentData, this.orders.getFirst());
        payment.setStatus("SUCCESS");
        assertEquals("SUCCESS", payment.getOrder().getStatus());
    }

    @Test
    void testSetStatusPaymentVoucherCodeRejected() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("voucherCode", "ESHOP1234ABC567D");
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "VOUCHER_CODE",
                paymentData, this.orders.getFirst());
        payment.setStatus("REJECTED");
        assertEquals("FAILED", payment.getOrder().getStatus());
    }

    // Cash on Delivery
    @Test
    void testCreatePaymentCashOnDeliverySuccess() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("address", "Jalan Anggur");
        paymentData.put("deliveryFee", "12000");
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de",
                "CASH_ON_DELIVERY", paymentData, this.orders.getFirst());
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentCashOnDeliveryRejectedMissingAddress() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("deliveryFee", "12000");
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de",
                "CASH_ON_DELIVERY", paymentData, this.orders.getFirst());
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentCashOnDeliveryRejectedMissingDeliveryFee() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("address", "Jalan Anggur");
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de",
                "CASH_ON_DELIVERY", paymentData, this.orders.getFirst());
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetStatusPaymentCashOnDeliverySuccess() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("address", "Jalan Anggur");
        paymentData.put("deliveryFee", "12000");
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de",
                "CASH_ON_DELIVERY", paymentData, this.orders.getFirst());
        payment.setStatus("SUCCESS");
        assertEquals("SUCCESS", payment.getOrder().getStatus());
    }

    @Test
    void testSetStatusPaymentCashOnDeliveryRejected() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("address", "Jalan Anggur");
        paymentData.put("deliveryFee", "12000");
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de",
                "CASH_ON_DELIVERY", paymentData, this.orders.getFirst());
        payment.setStatus("REJECTED");
        assertEquals("FAILED", payment.getOrder().getStatus());
    }
}
