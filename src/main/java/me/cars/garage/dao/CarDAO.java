package me.cars.garage.dao;

import me.cars.garage.models.Car;

import java.sql.SQLException;
import java.util.List;

public interface CarDAO {
    void save(Car car) throws SQLException;
    List<Car> findAll() throws SQLException;
    void delete(String plate) throws SQLException;
    void update(String plate, int newPrice) throws SQLException;
    Car findByPlate(String plate) throws SQLException;
}
