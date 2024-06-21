package com.dam.tfg.MotoMammiApplicationJNC.Utils;

import com.dam.tfg.MotoMammiApplicationJNC.Models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static Session session;

    /**
     * Crea la factoria de sesiones
     */
    public static void buildSessionFactory() {

        Configuration configuration = new Configuration();
        configuration.configure();
        // Se registran las clases que hay que mapear con cada tabla de la base de datos
        configuration.addAnnotatedClass(ProviderDTO.class);
        configuration.addAnnotatedClass(CustomerDTO.class);
        configuration.addAnnotatedClass(InterfaceDTO.class);
        configuration.addAnnotatedClass(TranslationDTO.class);
        configuration.addAnnotatedClass(PartsDTO.class);
        configuration.addAnnotatedClass(VehiclesDTO.class);
        configuration.addAnnotatedClass(InvoicesDTO.class);
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(
                configuration.getProperties()).buildServiceRegistry();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    /**
     * Abre una nueva sesión
     */
    public static void openSession() {
        session = sessionFactory.openSession();
    }

    /**
     * Devuelve la sesión actual
     * @return
     */
    public static Session getCurrentSession() {

        if ((session == null) || (!session.isOpen()))
            openSession();

        return session;
    }

    /**
     * Cierra Hibernate
     */
    public static void closeSessionFactory() {

        if (session != null)
            session.close();

        if (sessionFactory != null)
            sessionFactory.close();
    }
}
