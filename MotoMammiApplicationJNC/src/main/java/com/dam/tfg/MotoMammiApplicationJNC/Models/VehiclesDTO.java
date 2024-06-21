package com.dam.tfg.MotoMammiApplicationJNC.Models;

import javax.persistence.*;

@Entity
@Table(name = "MM_VEHICLES")
public class VehiclesDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "CarPlate", unique = true, length = 10)
    private String carPlate;

    @Column(name = "VehicleType", length = 50)
    private String vehicleType;

    @Column(name = "Brand", length = 50)
    private String brand;

    @Column(name = "Model", length = 50)
    private String model;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
