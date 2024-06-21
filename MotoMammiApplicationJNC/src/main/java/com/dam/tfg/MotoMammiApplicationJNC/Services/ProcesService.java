package com.dam.tfg.MotoMammiApplicationJNC.Services;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface ProcesService {
    public void readFileInfo(String pSource, String pProv, String pDate);
    public void integrateInfo(String pSource, String codProv);
    public void genInvoiceFile(String codProv, String date);
}
