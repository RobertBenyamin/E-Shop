package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceImplTest {
    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateCar() {
        // Arrange
        Car car = new Car();
        car.setId("1");
        car.setName("Toyota");
        car.setColor("Red");
        car.setQuantity(5);

        // Act
        carService.create(car);

        // Assert
        verify(carRepository, times(1)).create(car);
    }

    @Test
    void testFindAllCars() {
        // Arrange
        List<Car> cars = new ArrayList<>();
        Car car1 = new Car();
        car1.setId("1");
        car1.setName("Toyota");
        car1.setColor("Red");
        car1.setQuantity(5);

        Car car2 = new Car();
        car2.setId("2");
        car2.setName("Honda");
        car2.setColor("Blue");
        car2.setQuantity(3);

        cars.add(car1);
        cars.add(car2);

        when(carRepository.findAll()).thenReturn(cars.iterator());

        // Act
        List<Car> foundCars = carService.findAll();

        // Assert
        assertEquals(cars.size(), foundCars.size());
    }

    @Test
    void testFindCarById() {
        // Arrange
        Car car = new Car();
        car.setId("1");
        car.setName("Toyota");
        car.setColor("Red");
        car.setQuantity(5);

        when(carRepository.findById("1")).thenReturn(car);

        // Act
        Car foundCar = carService.findById("1");

        // Assert
        assertEquals(car, foundCar);
    }

    @Test
    void testUpdateCar() {
        // Arrange
        Car car = new Car();
        car.setId("1");
        car.setName("Toyota");
        car.setColor("Red");
        car.setQuantity(5);

        // Act
        carService.update("1", car);

        // Assert
        verify(carRepository, times(1)).update("1", car);
    }

    @Test
    void testDeleteCarById() {
        // Act
        carService.deleteCarById("1");

        // Assert
        verify(carRepository, times(1)).delete("1");
    }
}
