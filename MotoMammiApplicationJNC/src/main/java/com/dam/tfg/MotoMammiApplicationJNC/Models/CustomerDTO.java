package com.dam.tfg.MotoMammiApplicationJNC.Models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MM_CUSTOMERS")
public class CustomerDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "Name", length = 50)
    private String name;

    @Column(name = "FirstName", length = 50)
    private String firstName;

    @Column(name = "LastName", length = 50)
    private String lastName;

    @Column(name = "BirthDate")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(name = "PostalCode", length = 20)
    private String postalCode;

    @Column(name = "StreetType", length = 50)
    private String streetType;

    @Column(name = "City", length = 50)
    private String city;

    @Column(name = "NumberStreet")
    private int numberStreet;

    @Column(name = "PhoneNumber", length = 50)
    private String phoneNumber;

    @Column(name = "Dni", unique = true, nullable = false, length = 10)
    private String dni;

    @Column(name = "LicenseType", length = 10)
    private String licenseType;

    @Column(name = "CarPlate", unique = true, length = 10)
    private String carPlate;

    @Column(name = "Email", length = 100)
    private String email;

    @Column(name = "Gender", length = 20)
    private String gender;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreetType() {
        return streetType;
    }

    public void setStreetType(String streetType) {
        this.streetType = streetType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getNumberStreet() {
        return numberStreet;
    }

    public void setNumberStreet(int numberStreet) {
        this.numberStreet = numberStreet;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
