package com.dam.tfg.MotoMammiApplicationJNC.Models;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MM_INVOICES")
public class InvoicesDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "InvoiceNumber", unique = true, nullable = false, length = 20)
    private String invoiceNumber;

    @Column(name = "IssueDate")
    @Temporal(TemporalType.DATE)
    private Date issueDate;

    @Column(name = "CodProv", length = 5)
    private String codProv;

    @Column(name = "Dni", length = 10)
    private String dni;

    @Column(name = "CarPlate", length = 10)
    private String carPlate;

    @Column(name = "Cost")
    private double cost;

    @Column(name = "Send", nullable = false, columnDefinition = "boolean default false")
    private boolean send;

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getCodProv() {
        return codProv;
    }

    public void setCodProv(String codProv) {
        this.codProv = codProv;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }
}
