package me.cars.garage.dao.impl;

import me.cars.garage.dao.CarDAO;
import me.cars.garage.models.Car;
import me.cars.garage.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAOImpl implements CarDAO {

    @Override
    public void save(Car car) throws SQLException {
        String sql = "INSERT INTO cars (plate, brand, price) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, car.getPlate());
            pstmt.setString(2, car.getBrand());
            pstmt.setInt(3, car.getPrice());
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<Car> findAll() throws SQLException {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                cars.add(new Car(rs.getString("plate"), rs.getString("brand"), rs.getInt("price")));
            }
        }
        return cars;
    }

    @Override
    public void delete(String plate) throws SQLException {
        String sql = "DELETE FROM cars WHERE plate = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, plate);
            pstmt.executeUpdate();
        }
    }

    @Override
    public void update(String plate, int newPrice) throws SQLException {
        String sql = "UPDATE cars SET price = ? WHERE plate = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newPrice);
            pstmt.setString(2, plate);
            pstmt.executeUpdate();
        }
    }

    @Override
    public Car findByPlate(String plate) throws SQLException {
        String sql = "SELECT * FROM cars WHERE plate = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, plate);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Car(
                            rs.getString("plate"),
                            rs.getString("brand"),
                            rs.getInt("price")
                    );
                }
            }
        }
        return null;
    }
}