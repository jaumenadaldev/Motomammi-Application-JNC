package com.dam.tfg.MotoMammiApplicationJNC.Models;

import java.util.Date;

public class InvoiceDTO {

    private String dniCustomer;
    private String nameCustomer;
    private String firstNameCustomer;
    private String lastNameCustomer;
    private String modelVehicle;
    private String carPlateVehicle;
    private Date issueDate;

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    private double cost;
    private String invoiceNumber;

    public InvoiceDTO() {
    }

    public String getDniCustomer() {
        return dniCustomer;
    }

    public void setDniCustomer(String dniCustomer) {
        this.dniCustomer = dniCustomer;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getfirstNameCustomer() {
        return firstNameCustomer;
    }

    public void setfirstNameCustomer(String firstNameCustomer) {
        this.firstNameCustomer = firstNameCustomer;
    }

    public String getlastNameCustomer() {
        return lastNameCustomer;
    }

    public void setlastNameCustomer(String lastNameCustomer) {
        this.lastNameCustomer = lastNameCustomer;
    }

    public String getModelVehicle() {
        return modelVehicle;
    }

    public void setModelVehicle(String modelVehicle) {
        this.modelVehicle = modelVehicle;
    }

    public String getcarPlateVehicle() {
        return carPlateVehicle;
    }

    public void setcarPlateVehicle(String carPlateVehicle) {
        this.carPlateVehicle = carPlateVehicle;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
}
