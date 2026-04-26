package me.cars.garage;

import me.cars.garage.models.Car;
import me.cars.garage.services.GarageService;
import me.cars.garage.utils.DatabaseConnection;
import me.cars.garage.utils.InvalideCarDataException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InvalideCarDataException {

        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                System.out.println("Database connection established successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            System.out.println("Check your .env file and PostgreSQL service.");
            return;
        }

        System.out.println("--- Welcome to Garage Management System (SQL Edition) ---");
        Scanner scanner = new Scanner(System.in);
        GarageService garageService = new GarageService();



        while (true) {
            System.out.println("\n--- Garage Manager ---");
            System.out.println("1. Add Car");
            System.out.println("2. View All Cars");
            System.out.println("3. Search by Plate");
            System.out.println("4. Update Price ");
            System.out.println("5. Remove car");
            System.out.println("6. Exit");
            System.out.print("Choice: ");

            int choice = 0;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("❌ Please enter a valid menu number.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter Plate: ");
                    String plate = scanner.nextLine();
                    System.out.print("Enter Brand: ");
                    String brand = scanner.nextLine();

                    int price = 0;
                    boolean validPrice = false;
                    while (!validPrice) {
                        System.out.print("Enter price (positive number): ");
                        try {
                            price = scanner.nextInt();
                            if (price > 0) validPrice = true;
                            else System.out.println("Price must be positive!");
                        } catch (InputMismatchException e) {
                            System.out.println("Error: enter valid number");
                            scanner.nextLine();
                        }
                    }
                    scanner.nextLine();

                    Car newCar = new Car(plate, brand, price);
                    garageService.addCar(newCar);
                    break;

                case 2:
                    garageService.showAllCars();
                    break;

                case 3:
                    System.out.print("Enter Plate to search: ");
                    String searchPlate = scanner.nextLine();
                    garageService.findCarByPlate(searchPlate);
                    break;

                case 4:
                    System.out.print("Enter Plate: ");
                    String uPlate = scanner.nextLine();
                    System.out.print("Enter New Price: ");
                    int newPrice = scanner.nextInt();
                    scanner.nextLine();
                    garageService.updateCar(uPlate, newPrice);
                    break;

                case 5:
                    System.out.print("Enter Plate to Delete: ");
                    String dPlate = scanner.nextLine();
                    garageService.removeCar(dPlate);
                    break;

                case 6:
                    System.out.println("All data is safe in PostgreSQL. Bye bye!");
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}