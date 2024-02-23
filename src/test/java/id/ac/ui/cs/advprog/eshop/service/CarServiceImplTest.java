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
        car.setCarId("1");
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(5);

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
        car1.setCarId("1");
        car1.setCarName("Toyota");
        car1.setCarColor("Red");
        car1.setCarQuantity(5);

        Car car2 = new Car();
        car2.setCarId("2");
        car2.setCarName("Honda");
        car2.setCarColor("Blue");
        car2.setCarQuantity(3);

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
        car.setCarId("1");
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(5);

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
        car.setCarId("1");
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(5);

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
