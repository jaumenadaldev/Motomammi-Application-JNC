package com.dam.tfg.MotoMammiApplicationJNC.Repositories;

import com.dam.tfg.MotoMammiApplicationJNC.Models.InterfaceDTO;
import com.dam.tfg.MotoMammiApplicationJNC.Utils.HibernateUtil;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class InterfaceRepository implements ObjectRepository<InterfaceDTO> {

    @Transactional
    public void storeList(List<InterfaceDTO> interfaceDTOList) {
        Session session = null;
        try {
            HibernateUtil.buildSessionFactory();
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            for (InterfaceDTO dataInterfaceDTO : interfaceDTOList) {
                session.save(dataInterfaceDTO);
            }
            session.getTransaction().commit();
            System.out.println("Guardat a la taula INTERFACE");
        } catch (Exception e) {
            if (session != null && session.getTransaction() != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            System.out.println("Error al guardar a la base de dades");
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
                HibernateUtil.closeSessionFactory();
            }
        }
    }

    @Override
    public void store(InterfaceDTO interfaceDTO) {
        Session session = null;
        try {
            HibernateUtil.buildSessionFactory();
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            session.save(interfaceDTO);
            session.getTransaction().commit();
            System.out.println("Guardat a la taula INTERFACE");
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
    public List<InterfaceDTO> retrieve(String pSource, String date) {
        HibernateUtil.buildSessionFactory();
        List<InterfaceDTO> interfaceDTOS = (List<InterfaceDTO>) HibernateUtil.getCurrentSession()
                .createQuery("from InterfaceDTO where statusProcess in ('N', 'E') and resource = :pSource")
                .setParameter("pSource", pSource).list();
        System.out.println("Consulta en Interface");
        return interfaceDTOS;
    }

    @Override
    public InterfaceDTO search(String codExternal, String codProv, String pSource) {
        HibernateUtil.buildSessionFactory();
        InterfaceDTO interfaceDTO = (InterfaceDTO) HibernateUtil.getCurrentSession()
                .createQuery("from InterfaceDTO where codExternal = :codigoExterno " +
                        "and codProv = :codigoProveedor and resource = :pSource " +
                        "order by creationDate desc") // Suponiendo que tienes un campo creationDate para ordenar
                .setParameter("codigoExterno", codExternal)
                .setParameter("codigoProveedor", codProv)
                .setParameter("pSource", pSource)
                .setMaxResults(1)
                .uniqueResult();
        return interfaceDTO;
    }

    @Override
    public List<InterfaceDTO> searchList(String codExternal, String codProv) {
        HibernateUtil.buildSessionFactory();
        List<InterfaceDTO> interfaces = (List<InterfaceDTO>) HibernateUtil.getCurrentSession()
                .createQuery("from InterfaceDTO where codExternal = :codigoExterno " +
                        "and codProv = :codigoProveedor order by creationDate desc")
                .setParameter("codigoExterno", codExternal).setParameter("codigoProveedor", codProv).list();
        return interfaces;
    }

    @Override
    public InterfaceDTO delete(int id) {
        return null;
    }

    @Override
    public InterfaceDTO update(InterfaceDTO interfaceDTO) {
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();

            // Obtenir l'entitat des de la base de dades usant l'ID
            List<InterfaceDTO> results = session.createQuery("FROM InterfaceDTO WHERE codExternal = :codExternal order by creationDate desc")
                    .setParameter("codExternal", interfaceDTO.getCodExternal())
                    .list();

            if (results.isEmpty()) {
                // Si no es troba l'entitat, llençar una excepció
                throw new EntityNotFoundException("L'entitat amb ID " + interfaceDTO.getCodExternal() + " no ha estat trobada.");
            }

            // Obtenir l'entitat existent
            InterfaceDTO existingEntity = results.get(0);

            // Actualitzar els atributs de l'entitat amb els valors de interfaceDTO
            existingEntity.setStatusProcess(interfaceDTO.getStatusProcess());
            existingEntity.setUpdatedBy(interfaceDTO.getUpdatedBy());
            existingEntity.setLastUpdate(interfaceDTO.getLastUpdate());
            existingEntity.setCodError(interfaceDTO.getCodError());
            existingEntity.setErrorMessage(interfaceDTO.getErrorMessage());

            session.update(existingEntity);
            session.getTransaction().commit();
            return existingEntity;
        } catch (Exception e) {
            if (session != null && session.getTransaction() != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            System.out.println("Error al actualitzar a la base de dades");
            e.printStackTrace();
            // Gestionar o rellançar l'excepció segons correspongui en la teva aplicació
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
