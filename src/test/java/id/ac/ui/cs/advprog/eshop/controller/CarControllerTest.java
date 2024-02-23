package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarControllerTest {

    @Mock
    private CarService carService;

    @Mock
    private Model model;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCarPage() {
        // Act
        String viewName = carController.createCarPage(model);

        // Assert
        assertEquals("createCar", viewName);
    }

    @Test
    void testCreateCarPost() {
        // Arrange
        Car car = new Car();
        
        // Act
        String viewName = carController.createCarPost(car, model);

        // Assert
        verify(carService, times(1)).create(car);
        assertEquals("redirect:listCar", viewName);
    }

    @Test
    void testCarListPage() {
        // Arrange
        List<Car> cars = new ArrayList<>();
        when(carService.findAll()).thenReturn(cars);

        // Act
        String viewName = carController.carListPage(model);

        // Assert
        verify(carService, times(1)).findAll();
        assertEquals("carList", viewName);
    }

    @Test
    void testEditCarPage() {
        // Arrange
        String carId = "1";
        Car car = new Car();
        when(carService.findById(carId)).thenReturn(car);

        // Act
        String viewName = carController.editCarPage(carId, model);

        // Assert
        verify(carService, times(1)).findById(carId);
        assertEquals("editCar", viewName);
    }

    @Test
    void testEditCarPost() {
        // Arrange
        Car car = new Car();

        // Act
        String viewName = carController.editCarPost(car, model);

        // Assert
        verify(carService, times(1)).update(car.getId(), car);
        assertEquals("redirect:listCar", viewName);
    }

    @Test
    void testDeleteCar() {
        // Arrange
        String carId = "1";

        // Act
        String viewName = carController.deleteCar(carId);

        // Assert
        verify(carService, times(1)).deleteCarById(carId);
        assertEquals("redirect:listCar", viewName);
    }
}

