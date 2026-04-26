package me.cars.garage.utils;

import me.cars.garage.models.Car;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public void saveToFile(List<Car> garage) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Garage.txt"))) {
            for (Car c : garage) {
                writer.write(c.getPlate() + "," + c.getBrand() + "," + c.getPrice());
                writer.newLine();
            }
            System.out.println("DATA SAVED SUCCESS");
        } catch (IOException e) {
            System.out.println("ERROR:" + e.getMessage());
        }
    }

    public List<Car> loadFromFile() {
        List<Car> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("garage.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String plate = parts[0];
                    String brand = parts[1];
                    int price = Integer.parseInt(parts[2]);
                    list.add(new Car(plate, brand, price));
                }
            }
        } catch (IOException e) {
            System.out.println("No existing garage file found. Starting fresh!");
        }
        return list;
    }
}
