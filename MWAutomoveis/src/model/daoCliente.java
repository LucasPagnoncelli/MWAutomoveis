package model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

public class daoCliente {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MWAutomoveisPU");
    EntityManager em = emf.createEntityManager();
    private List<cliente> clientes = new ArrayList<cliente>();
    
    public boolean cadastrar (cliente c) {
        em.getTransaction().begin();
        try{
            em.persist(c);
            em.getTransaction().commit();
            return true;
        }catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        }
    }
    public boolean editar (cliente c) {
        em.getTransaction().begin();
        try {
            em.merge(c);
            em.getTransaction().commit();
            return true;
        }catch (PersistenceException e) {
            em.getTransaction().rollback();
            em.getTransaction().commit();
            return false;
        }
    }
    
    public boolean excluir (cliente c) {
        em.getTransaction().begin();
        try {
            em.remove(c);
            em.getTransaction().commit();
            return true;
        }catch (PersistenceException e){
            em.getTransaction().rollback();
            em.getTransaction().commit();
            return false;
        }
    }
    public List<cliente> listar(){
        Query consulta= em.createQuery("select c from cliente c");
        return consulta.getResultList();
    }
    
    
}
