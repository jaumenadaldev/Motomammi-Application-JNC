package com.dam.tfg.MotoMammiApplicationJNC.Models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MM_PROVIDERS")
public class ProviderDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "CodProv", unique = true)
    private String codProv;

    @Column(name = "NameProv")
    private String nameProv;

    @Column(name = "DateIni")
    @Temporal(TemporalType.DATE)
    private Date dateIni;

    @Column(name = "DateEnd")
    @Temporal(TemporalType.DATE)
    private Date dateEnd;

    @Column(name = "Swiact")
    private Boolean swiAct;

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

    public String getNameProv() {
        return nameProv;
    }

    public void setNameProv(String nameProv) {
        this.nameProv = nameProv;
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

    public Boolean getSwiAct() {
        return swiAct;
    }

    public void setSwiAct(Boolean swiAct) {
        this.swiAct = swiAct;
    }
}
