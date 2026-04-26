package me.cars.garage.services;

import me.cars.garage.models.Car;
import me.cars.garage.utils.DatabaseConnection;
import java.sql.*; // Import s-SQL kamel
import java.util.ArrayList;
import java.util.List;

public class GarageService {

    // 1. ADD CAR
    public void addCar(Car car) {
        String sql = "INSERT INTO cars (plate, brand, price) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, car.getPlate());
            pstmt.setString(2, car.getBrand());
            pstmt.setInt(3, car.getPrice());
            pstmt.executeUpdate();
            System.out.println("✅ Car saved to DB!");
        } catch (SQLException e) {
            System.out.println("❌ DB Error (Add): " + e.getMessage());
        }
    }

    // 2. SHOW ALL CARS
    public void showAllCars() {
        String sql = "SELECT * FROM cars";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                Car car = new Car(rs.getString("plate"), rs.getString("brand"), rs.getInt("price"));
                car.displayDetails();
            }
            if (!hasData) System.out.println("Garage is empty.");
        } catch (SQLException e) {
            System.out.println("❌ DB Error (Show): " + e.getMessage());
        }
    }

    // 3. FIND BY PLATE
    public void findCarByPlate(String plate) {
        String sql = "SELECT * FROM cars WHERE plate = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, plate);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Car found = new Car(rs.getString("plate"), rs.getString("brand"), rs.getInt("price"));
                    found.displayDetails();
                } else {
                    System.out.println("No car found with plate: " + plate);
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ DB Error (Find): " + e.getMessage());
        }
    }

    // 4. UPDATE PRICE
    public void updateCar(String plate, int price) {
        String sql = "UPDATE cars SET price = ? WHERE plate = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, price);
            pstmt.setString(2, plate);
            int rows = pstmt.executeUpdate();

            if (rows > 0) System.out.println("Price updated successfully! ✅");
            else System.out.println("Car not found.");
        } catch (SQLException e) {
            System.out.println("❌ DB Error (Update): " + e.getMessage());
        }
    }

    // 5. REMOVE CAR
    public void removeCar(String plate) {
        String sql = "DELETE FROM cars WHERE plate = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, plate);
            int rows = pstmt.executeUpdate();

            if (rows > 0) System.out.println("Car removed successfully. ✅");
            else System.out.println("Cannot delete: Car not found.");
        } catch (SQLException e) {
            System.out.println("❌ DB Error (Delete): " + e.getMessage());
        }
    }
}