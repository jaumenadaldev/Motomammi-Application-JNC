package com.dam.tfg.MotoMammiApplicationJNC.Repositories;

import com.dam.tfg.MotoMammiApplicationJNC.Models.InvoicesDTO;
import com.dam.tfg.MotoMammiApplicationJNC.Utils.HibernateUtil;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InvoicesRepository implements ObjectRepository<InvoicesDTO> {
    @Override
    public void storeList(List<InvoicesDTO> t) {

    }

    @Override
    public void store(InvoicesDTO invoicesDTO) {

    }

    @Override
    public List<InvoicesDTO> retrieve(String codProv, String date) {
        Session session = null;
        try {

            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(4));

            HibernateUtil.buildSessionFactory();
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();

            List<InvoicesDTO> invoices = (List<InvoicesDTO>) HibernateUtil.getCurrentSession()
                    .createQuery("from InvoicesDTO where CodProv = :codProv and YEAR(IssueDate) = :year and MONTH(issueDate) = :month and send = false")
                    .setParameter("codProv", codProv)
                    .setParameter("year", year)
                    .setParameter("month", month)
                    .list();

            session.getTransaction().commit();
            return invoices;

        }catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Error en cercar en la base de dades a la taula mm_invoices");
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;

    }

    @Override
    public InvoicesDTO search(String date, String codProv, String pSource) {
        return null;
    }

    @Override
    public List<InvoicesDTO> searchList(String codExternal, String codProv) {
        return null;
    }

    @Override
    public InvoicesDTO delete(int id) {
        return null;
    }

    @Override
    public InvoicesDTO update(InvoicesDTO invoicesDTO) {
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();

            // Obtenir l'entitat desde la base de dades emprant l'ID
            List<InvoicesDTO> results = session.createQuery("FROM InvoicesDTO WHERE InvoiceNumber = :invoiceNumber")
                    .setParameter("InvoiceNumber", invoicesDTO.getInvoiceNumber())
                    .list();

            if (results.isEmpty()) {
                // Si no es troba l'entitat, llançar una excepció
                throw new EntityNotFoundException("La entitat amb ID " + invoicesDTO.getInvoiceNumber() + " no s'ha trobat.");
            }

            // Obtenir l'entidad existent
            InvoicesDTO existingEntity = results.get(0);

            // Actualitzar els atributs de l'entitat amb els valors d'interfaceDTO
            existingEntity.setSend(true);

            session.update(existingEntity);
            session.getTransaction().commit();
            return existingEntity;
        } catch (Exception e) {
            if (session != null && session.getTransaction() != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            System.out.println("Error en actualitzar a la base de dades");
            e.printStackTrace();
            // Manetjar o rellançar l'excepció segons correspongui a la teva aplicació
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}