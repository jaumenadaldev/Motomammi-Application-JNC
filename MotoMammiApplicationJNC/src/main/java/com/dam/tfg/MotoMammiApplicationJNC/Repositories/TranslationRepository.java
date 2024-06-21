package com.dam.tfg.MotoMammiApplicationJNC.Repositories;

import com.dam.tfg.MotoMammiApplicationJNC.Models.TranslationDTO;
import com.dam.tfg.MotoMammiApplicationJNC.Utils.HibernateUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TranslationRepository implements ObjectRepository<TranslationDTO> {

    @Override
    public void storeList(List<TranslationDTO> t) {
        // Implementació per emmagatzemar una llista de traduccions
    }

    @Override
    public void store(TranslationDTO translationDTO) {
        // Implementació per emmagatzemar una traducció
    }

    @Override
    public List<TranslationDTO> retrieve(String pSource, String date) {
        return null;
    }

    @Override
    public TranslationDTO search(String codProv, String externalCode, String pSource) {
        HibernateUtil.buildSessionFactory();
        List<TranslationDTO> translations = (List<TranslationDTO>) HibernateUtil.getCurrentSession()
                .createQuery("from TranslationDTO where CodProv = :codProv and ExternalCode = :externalCode " +
                        "and current_date() between DateIni and DateEnd")
                .setParameter("codProv", codProv)
                .setParameter("externalCode", externalCode)
                .list();
        if (translations.isEmpty()) {
            return null;
        } else {
            return translations.get(0); // Retorna el primer resultat que coincideixi
        }
    }

    @Override
    public List<TranslationDTO> searchList(String codExternal, String codProv) {
        return null;
    }

    @Override
    public TranslationDTO delete(int id) {
        return null;
    }

    @Override
    public TranslationDTO update(TranslationDTO translationDTO) {
        return null;
    }
}
