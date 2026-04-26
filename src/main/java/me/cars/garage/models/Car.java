package me.cars.garage.models;

public class Car {
    private String plate;
    private String brand;
    private int price;

    public Car(String plate, String brand, int price ){
        this.plate = plate;
        this.brand = brand;
        this.price = price;
    }

    public void displayDetails(){
        System.out.println("plate : " + plate + " | Brande: " + brand + " | Price: " + price + " DH");
    }

    public String getPlate(){return plate;}
    public String getBrand() {return brand;}
    public  int getPrice() {return price;}
    public void  setPrice(int price ){
        this.price = price;
    }
    public  void setPlate(String plate){
        this.plate = plate;
    }
    public void setBrand(String brand){
        this.brand = brand;
    }

}
