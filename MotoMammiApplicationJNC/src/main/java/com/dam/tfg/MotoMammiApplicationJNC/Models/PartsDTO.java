package com.dam.tfg.MotoMammiApplicationJNC.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "MM_PARTS")
public class PartsDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "Dni", nullable = false, length = 10)
    private String dni;

    @Column(name = "ClaimNumber", unique = true, length = 20)
    private String claimNumber;

    @Column(name = "PolicyNumber", length = 20)
    private String policyNumber;

    @Column(name = "ClaimDate")
    @Temporal(TemporalType.DATE)
    private Date claimDate;

    @Column(name = "Description", length = 255)
    private String description;

    @Column(name = "Status", length = 20)
    private String status;

    @Column(name = "Amount")
    private Double amount;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getClaimNumber() {
        return claimNumber;
    }

    public void setClaimNumber(String claimNumber) {
        this.claimNumber = claimNumber;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public Date getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(Date claimDate) {
        this.claimDate = claimDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
