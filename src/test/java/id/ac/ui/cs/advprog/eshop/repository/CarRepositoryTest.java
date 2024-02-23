package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarRepositoryTest {
    @InjectMocks
    CarRepository carRepository;

    @Mock
    private Car mockCar;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreate() {
        when(mockCar.getId()).thenReturn("1");
        Car createdCar = carRepository.create(mockCar);
        assertNotNull(createdCar);
        assertNotNull(createdCar.getId());
    }

    @Test
    void testFindAll() {
        List<Car> carData = new ArrayList<>();
        carData.add(mockCar);

        Iterator<Car> iterator = carData.iterator();
        when(mockCar.getId()).thenReturn("1");

        assertEquals(iterator.next().getId(), "1");
    }

    @Test
    void testFindById() {
        when(mockCar.getId()).thenReturn("1");
        carRepository.create(mockCar);
        assertEquals(carRepository.findById("1"), mockCar);
    }

    @Test
    public void testUpdateExistingCar() {
        // Arrange
        Car initialCar = new Car();
        initialCar.setId("1");
        initialCar.setName("Toyota");
        initialCar.setColor("Red");
        initialCar.setQuantity(5);
        carRepository.create(initialCar);

        Car updatedCar = new Car();
        updatedCar.setId("1");
        updatedCar.setName("Toyota Camry");
        updatedCar.setColor("Blue");
        updatedCar.setQuantity(10);

        // Act
        Car updated = carRepository.update("1", updatedCar);

        // Assert
        assertNotNull(updated);
        assertEquals("1", updated.getId());
        assertEquals("Toyota Camry", updated.getName());
        assertEquals("Blue", updated.getColor());
        assertEquals(10, updated.getQuantity());
    }

    @Test
    public void testUpdateNonExistingCar() {
        // Arrange
        Car initialCar = new Car();
        initialCar.setId("1");
        initialCar.setName("Toyota Camry");
        initialCar.setColor("Blue");
        initialCar.setQuantity(10);

        // Act
        Car updated = carRepository.update("2", initialCar);

        // Assert
        assertNull(updated);
    }

    @Test
    void testDelete() {
        when(mockCar.getId()).thenReturn("1");
        carRepository.create(mockCar);
        carRepository.delete("1");
        assertNull(carRepository.findById("1"));
    }
}
