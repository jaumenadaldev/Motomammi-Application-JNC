package com.dam.tfg.MotoMammiApplicationJNC.Models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "MM_INTERFACE")
public class InterfaceDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "CodExternal", length = 10)
    private String codExternal;

    @Column(name = "CodProv", length = 5)
    private String codProv;

    @Column(name = "ContJson", columnDefinition = "LONGTEXT")
    private String contJson;

    @Column(name = "CreationDate")
    private Timestamp creationDate;

    @Column(name = "LastUpdate")
    private Timestamp lastUpdate;

    @Column(name = "CreatedBy", length = 255)
    private String createdBy;

    @Column(name = "UpdatedBy", length = 255)
    private String updatedBy;

    @Column(name = "CodError")
    private Integer codError;

    @Column(name = "ErrorMessage", length = 4000)
    private String errorMessage;

    @Column(name = "StatusProcess", columnDefinition = "varchar(10) default 'N'")
    private String statusProcess;

    @Column(name = "Operation")
    private String operation;

    @Column(name = "Resource")
    private String resource;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodExternal() {
        return codExternal;
    }

    public void setCodExternal(String codExternal) {
        this.codExternal = codExternal;
    }

    public String getCodProv() {
        return codProv;
    }

    public void setCodProv(String codProv) {
        this.codProv = codProv;
    }

    public String getContJson() {
        return contJson;
    }

    public void setContJson(String contJson) {
        this.contJson = contJson;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getCodError() {
        return codError;
    }

    public void setCodError(Integer codError) {
        this.codError = codError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getStatusProcess() {
        return statusProcess;
    }

    public void setStatusProcess(String statusProcess) {
        this.statusProcess = statusProcess;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
