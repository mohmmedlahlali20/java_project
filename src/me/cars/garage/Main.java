package me.cars.garage;

import me.cars.garage.models.Car;
import me.cars.garage.services.GarageService;
import me.cars.garage.utils.FileManager;
import me.cars.garage.utils.InvalideCarDataException;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InvalideCarDataException {
        System.out.println("--- Welcome to Garage Management System ---");
        Scanner scanner = new Scanner(System.in);
        GarageService garageService = new GarageService();
        FileManager fileManager = new FileManager();
        List<Car> loadCars = fileManager.loadFromFile();
        for (Car c: loadCars){
            garageService.addCar(c);
        }
        System.out.println("Systeme: laoded " + loadCars.size() + " cars");


        while (true) {
            System.out.println("\n--- Garage Manager ---");
            System.out.println("1. Add Car");
            System.out.println("2. View All Cars");
            System.out.println("3. Search by Plate");
            System.out.println("4. Update Price ");
            System.out.println("5. Remove car");
            System.out.println("6. Exit");
            System.out.print("Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();


            switch (choice) {
                case 1:
                    System.out.println("Enter Plate: ");
                    String plate = scanner.nextLine();
                    System.out.println("Enter Brand: ");
                    String brand = scanner.nextLine();
                    System.out.println("Enter Price: ");
                    int price = scanner.nextInt();
                    if (price < 0) throw new InvalideCarDataException("Price cannot be negative!");
                    if (plate.trim().isEmpty() || brand.trim().isEmpty()){
                        System.out.println("Plate and brand cannot be empty");
                    }
                    scanner.nextLine();

                    Car newCar = new Car(plate, brand, price);
                    garageService.addCar(newCar);
                    break;
                case 2:
                    garageService.showAllCars();
                    break;
                case 3:
                    System.out.println("Enter Plate to search");
                    String searchPlate = scanner.nextLine();
                    garageService.findCarByPlate(searchPlate);
                    break;
                case 4:
                    System.out.println("Enter Plate: ");
                    String uPlate = scanner.nextLine();
                    System.out.println("Enter New Price");
                    int newPrice = scanner.nextInt();
                    garageService.updateCar(uPlate, newPrice);
                    break;
                case 5:
                    System.out.println("Enter Plate to Delete");
                    String dPlate = scanner.nextLine();
                    garageService.removeCar(dPlate);
                    break;
                case 6:
                    System.out.println("Saving data...");
                    fileManager.saveToFile(garageService.getAllCarsList());
                    System.out.println("bye bye");
                    return;

            }


        }

    }
}