package com.dam.tfg.MotoMammiApplicationJNC.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "MM_TRANSLATIONS")
public class TranslationDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "CodProv")
    private String codProv;

    @Column(name = "InternalCode")
    private String internalCode;

    @Column(name = "ExternalCode")
    private String externalCode;

    @Column(name = "DateIni")
    @Temporal(TemporalType.DATE)
    private Date dateIni;

    @Column(name = "DateEnd")
    @Temporal(TemporalType.DATE)
    private Date dateEnd;

    public TranslationDTO() {
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodProv() {
        return codProv;
    }

    public void setCodProv(String codProv) {
        this.codProv = codProv;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
    }

    public String getExternalCode() {
        return externalCode;
    }

    public void setExternalCode(String externalCode) {
        this.externalCode = externalCode;
    }

    public Date getDateIni() {
        return dateIni;
    }

    public void setDateIni(Date dateIni) {
        this.dateIni = dateIni;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
}
