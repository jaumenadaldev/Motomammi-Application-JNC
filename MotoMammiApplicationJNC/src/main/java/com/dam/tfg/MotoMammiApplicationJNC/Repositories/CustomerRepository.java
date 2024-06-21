package com.dam.tfg.MotoMammiApplicationJNC.Repositories;

import com.dam.tfg.MotoMammiApplicationJNC.Models.CustomerDTO;
import com.dam.tfg.MotoMammiApplicationJNC.Utils.HibernateUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepository implements ObjectRepository<CustomerDTO> {
    @Override
    public void storeList(List<CustomerDTO> t) {
        // Implementació per emmagatzemar una llista de clients
    }

    @Override
    public void store(CustomerDTO customerDTO) {
        Session session = null;
        try {
            HibernateUtil.buildSessionFactory();
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            session.save(customerDTO);
            session.getTransaction().commit();
            System.out.println("Guardat a la taula CUSTOMERS");
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
    public List<CustomerDTO> retrieve(String pSource, String date) {
        return null;
    }

    @Override
    public CustomerDTO search(String dni, String codProv, String pSource) {
        Session session = null;
        try {
            HibernateUtil.buildSessionFactory();
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();

            List<CustomerDTO> customers = (List<CustomerDTO>) HibernateUtil.getCurrentSession()
                    .createQuery("from CustomerDTO where Dni = :dni")
                    .setParameter("dni", dni).list();

            session.getTransaction().commit();

            if (customers.isEmpty()) {
                return null;
            } else {
                return customers.get(0); // Retorna el primer resultat que coincideixi
            }

        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Error al buscar a la base de dades a la taula CUSTOMERS");
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;

    }

    @Override
    public List<CustomerDTO> searchList(String codExternal, String codProv) {
        return null;
    }

    @Override
    public CustomerDTO delete(int id) {
        return null;
    }

    @Override
    public CustomerDTO update(CustomerDTO customerDTO) {
        return null;
    }

    public boolean searchDNI(String dni) {
        Session session = null;
        try {
            HibernateUtil.buildSessionFactory();
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();

            List<CustomerDTO> customers = (List<CustomerDTO>) HibernateUtil.getCurrentSession()
                    .createQuery("from CustomerDTO where Dni = :dni")
                    .setParameter("dni", dni).list();

            session.getTransaction().commit();

            if (customers.isEmpty()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Error al buscar a la base de dades a la taula CUSTOMERS");
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return false;
    }

    public boolean searchPlate(String plate) {
        Session session = null;
        try {
            HibernateUtil.buildSessionFactory();
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();

            List<CustomerDTO> customers = (List<CustomerDTO>) HibernateUtil.getCurrentSession()
                    .createQuery("from CustomerDTO where CarPlate = :plate")
                    .setParameter("plate", plate).list();

            session.getTransaction().commit();

            if (customers.isEmpty()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Error al buscar a la base de dades a la taula CUSTOMERS");
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return false;
    }
}
