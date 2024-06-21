package com.dam.tfg.MotoMammiApplicationJNC.Repositories;

import com.dam.tfg.MotoMammiApplicationJNC.Models.VehiclesDTO;
import com.dam.tfg.MotoMammiApplicationJNC.Utils.HibernateUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VehicleRepository implements ObjectRepository<VehiclesDTO> {
    @Override
    public void storeList(List<VehiclesDTO> t) {
        // Implementació per emmagatzemar una llista de vehicles
    }

    @Override
    public void store(VehiclesDTO vehicleDTO) {

        CustomerRepository customerRepository = new CustomerRepository();

        if (customerRepository.searchPlate(vehicleDTO.getCarPlate())) {
            throw new IllegalArgumentException("La matricula no existeix");
        }

        Session session = null;
        try {
            HibernateUtil.buildSessionFactory();
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            session.save(vehicleDTO);
            session.getTransaction().commit();
            System.out.println("Guardat a la taula VEHICLE");
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
    public List<VehiclesDTO> retrieve(String pSource, String date) {
        return null;
    }

    @Override
    public VehiclesDTO search(String carPlate, String codProv, String pSource) {
        HibernateUtil.buildSessionFactory();
        List<VehiclesDTO> vehicle = (List<VehiclesDTO>) HibernateUtil.getCurrentSession()
                .createQuery("from VehiclesDTO where CarPlate = :carPlate")
                .setParameter("carPlate", carPlate).list();
        if (vehicle.isEmpty()) {
            return null;
        } else {
            return vehicle.get(0); // Retorna el primer resultat que coincideixi
        }
    }

    @Override
    public List<VehiclesDTO> searchList(String codExternal, String codProv) {
        return null;
    }

    @Override
    public VehiclesDTO delete(int id) {
        return null;
    }

    @Override
    public VehiclesDTO update(VehiclesDTO vehicleDTO) {
        return null;
    }
}
