package me.cars.garage.services;

import me.cars.garage.dao.CarDAO;
import me.cars.garage.dao.impl.CarDAOImpl;
import me.cars.garage.models.Car;
import me.cars.garage.utils.DatabaseConnection;

import java.sql.*; // Import s-SQL kamel
import java.util.ArrayList;
import java.util.List;

public class GarageService {
    private CarDAO carDAO = new CarDAOImpl();

    // 1. ADD CAR
    public void addCar(Car car) {
        try {
            if (car.getPrice() < 100) {
                System.out.println("❌ Car too cheap for our luxury garage!");
                return;
            }
            carDAO.save(car);
            System.out.println("Car saved via DAO!");
        } catch (SQLException e) {
            System.err.println(" Service Error: " + e.getMessage());
        }
    }

    // 2. SHOW ALL CARS
    public void showAllCars() {
        try {
            List<Car> cars = carDAO.findAll();
            if (cars.isEmpty()) System.out.println("Garage empty.");
            else cars.forEach(Car::displayDetails);
        } catch (SQLException e) {
            System.err.println(" Error loading cars.");
        }
    }

    // 3. FIND BY PLATE
    public void findCarByPlate(String plate) {
        try {
            Car found = carDAO.findByPlate(plate);

            if (found != null) {
                System.out.println(" Car Found:");
                found.displayDetails();
            } else {
                System.out.println("Sorry, no car found with plate: [" + plate + "]");
            }

        } catch (SQLException e) {
            System.err.println("Error while searching for car: " + e.getMessage());
        }
    }

    public void updateCar(String plate, int price) {
        try {
            carDAO.update(plate, price);
            System.out.println("Price updated successfully!");
        } catch (SQLException e) {
            System.err.println("Error updating price: " + e.getMessage());
        }
    }

    // 5. REMOVE CAR
    public void removeCar(String plate) {
        try {
            carDAO.delete(plate);
            System.out.println("Car removed successfully.");
        } catch (SQLException e) {
            System.err.println("Error deleting car: " + e.getMessage());
        }
    }
}