package me.cars.garage.services;

import me.cars.garage.models.Car;
import me.cars.garage.utils.FileManager;

import javax.tools.JavaFileManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GarageService {
    private Map<String, Car> cars = new HashMap<>();

    public void addCar(Car car) {
        if (cars.containsKey(car.getPlate())) {
            System.out.println("car with this plate already exist");
        } else {
            cars.put(car.getPlate(), car);
            System.out.println("Car added successfully");
        }
    }

    public void showAllCars() {
        if (cars.isEmpty()) {
            System.out.println("Garage is empty");
        } else {
            cars.values().forEach(Car::displayDetails);
        }
    }

    public List<Car> getAllCarsList() {
        return new ArrayList<>(cars.values());
    }


    public void findCarByPlate(String plate) {
        Car found = cars.get(plate);

        if (found != null) {
            System.out.println("car Found: ");
            found.displayDetails();
        } else {
            System.out.println("sorry, no car found with plate: " + plate);
        }
    }


    public void updateCar(String plate, int price) {
        if (cars.containsKey(plate)) {
            Car car = cars.get(plate);
            car.setPrice(price);
//            car.setBrand(brand);
            car.setPlate(plate);
            System.out.println("Price updated successfully!");
        } else {
            System.out.println("Car not found");
        }
    }


    public void removeCar(String plate) {

        if(plate == null || plate.trim().isEmpty()){
            System.out.println("Error: Invalid plate provided");
            return;
        }

        if (cars.containsKey(plate)) {
            Car removedCar = cars.remove(plate);
            System.out.println("Car with plate [" + plate + "] removed successfully.");
        } else {
            System.out.println("Cannot delete: No car found with plate [" + plate + "].");
        }
    }


    public void saveGarage(FileManager fm) {
        List<Car> list = new ArrayList<>(cars.values());
        fm.saveToFile(list);

    }
}
