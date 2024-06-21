package com.dam.tfg.MotoMammiApplicationJNC.Repositories;

import com.dam.tfg.MotoMammiApplicationJNC.Models.ProviderDTO;
import com.dam.tfg.MotoMammiApplicationJNC.Utils.HibernateUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProviderRepository implements ObjectRepository<ProviderDTO> {

    @Override
    public void storeList(List<ProviderDTO> providersDTOList) {
        // Implementació per emmagatzemar una llista de proveïdors
    }

    public void store(ProviderDTO providersDTO) {
        // Implementació per emmagatzemar un proveïdor
    }

    @Override
    public List<ProviderDTO> retrieve(String pSource, String date) {
        HibernateUtil.buildSessionFactory();
        List<ProviderDTO> proveidors = (List<ProviderDTO>) HibernateUtil.getCurrentSession()
                .createQuery("from ProviderDTO where SwiAct = true " +
                        "and ifnull(:pDate,current_date()) between DateIni and DateEnd")
                .setParameter("pDate", null).list();
        System.out.println("Consulta per nom de proveïdor");
        return proveidors;
    }

    @Override
    public ProviderDTO search(String codExternal, String codProvs, String pSource) {
        return null;
    }

    @Override
    public List<ProviderDTO> searchList(String codExternal, String codProv) {
        return null;
    }

    @Override
    public ProviderDTO delete(int id) {
        return null;
    }

    @Override
    public ProviderDTO update(ProviderDTO providersDTO) {
        return null;
    }
}
