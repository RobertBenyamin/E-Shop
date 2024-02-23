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
        when(mockCar.getCarId()).thenReturn("1");
        Car createdCar = carRepository.create(mockCar);
        assertNotNull(createdCar);
        assertNotNull(createdCar.getCarId());
    }

    @Test
    void testFindAll() {
        List<Car> carData = new ArrayList<>();
        carData.add(mockCar);

        Iterator<Car> iterator = carData.iterator();
        when(mockCar.getCarId()).thenReturn("1");

        assertEquals(iterator.next().getCarId(), "1");
    }

    @Test
    void testFindById() {
        when(mockCar.getCarId()).thenReturn("1");
        carRepository.create(mockCar);
        assertEquals(carRepository.findById("1"), mockCar);
    }

    @Test
    public void testUpdateExistingCar() {
        // Arrange
        Car initialCar = new Car();
        initialCar.setCarId("1");
        initialCar.setCarName("Toyota");
        initialCar.setCarColor("Red");
        initialCar.setCarQuantity(5);
        carRepository.create(initialCar);

        Car updatedCar = new Car();
        updatedCar.setCarId("1");
        updatedCar.setCarName("Toyota Camry");
        updatedCar.setCarColor("Blue");
        updatedCar.setCarQuantity(10);

        // Act
        Car updated = carRepository.update("1", updatedCar);

        // Assert
        assertNotNull(updated);
        assertEquals("1", updated.getCarId());
        assertEquals("Toyota Camry", updated.getCarName());
        assertEquals("Blue", updated.getCarColor());
        assertEquals(10, updated.getCarQuantity());
    }

    @Test
    public void testUpdateNonExistingCar() {
        // Arrange
        Car initialCar = new Car();
        initialCar.setCarId("1");
        initialCar.setCarName("Toyota Camry");
        initialCar.setCarColor("Blue");
        initialCar.setCarQuantity(10);

        // Act
        Car updated = carRepository.update("2", initialCar);

        // Assert
        assertNull(updated);
    }

    @Test
    void testDelete() {
        when(mockCar.getCarId()).thenReturn("1");
        carRepository.create(mockCar);
        carRepository.delete("1");
        assertNull(carRepository.findById("1"));
    }
}

