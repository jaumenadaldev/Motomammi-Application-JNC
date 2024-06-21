package com.dam.tfg.MotoMammiApplicationJNC.Repositories;

import com.dam.tfg.MotoMammiApplicationJNC.Models.CustomerDTO;
import com.dam.tfg.MotoMammiApplicationJNC.Models.PartsDTO;
import com.dam.tfg.MotoMammiApplicationJNC.Utils.HibernateUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PartsRepository implements ObjectRepository<PartsDTO> {
    @Override
    public void storeList(List<PartsDTO> t) {
        // Implementació per emmagatzemar una llista de parts
    }

    @Override
    public void store(PartsDTO partsDTO) {

        CustomerRepository customerRepository = new CustomerRepository();
        if (customerRepository.searchDNI(partsDTO.getDni())) {
            throw new IllegalArgumentException("El client no existeix");
        }

        Session session = null;
        try {
            HibernateUtil.buildSessionFactory();
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            session.save(partsDTO);
            session.getTransaction().commit();
            System.out.println("Guardat a la taula PARTS");
        } catch (Exception e) {
            if (session != null && session.getTransaction() != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            System.out.println("Error al guardar a la base de dades");
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<PartsDTO> retrieve(String pSource, String date) {
        return null;
    }

    @Override
    public PartsDTO search(String dni, String claimNumber, String pSource) {
        Session session = null;
        try {
            HibernateUtil.buildSessionFactory();
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();

            List<PartsDTO> partsDTOS = session.createQuery("from PartsDTO where Dni = :dni and ClaimNumber = :claimNumber")
                    .setParameter("dni", dni)
                    .setParameter("claimNumber", claimNumber)
                    .list();

            session.getTransaction().commit();

            if (partsDTOS.isEmpty()) {
                return null;
            } else {
                return partsDTOS.get(0); // Retorna el primer resultat que coincideixi
            }
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Error al buscar a la base de dades a la taula PARTS");
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    @Override
    public List<PartsDTO> searchList(String codExternal, String codProv) {
        return null;
    }

    @Override
    public PartsDTO delete(int id) {
        return null;
    }

    @Override
    public PartsDTO update(PartsDTO partsDTO) {
        return null;
    }
}
