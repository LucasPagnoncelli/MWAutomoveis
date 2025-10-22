package model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
public class daoAutomovel {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MWAutomoveisPU");
    EntityManager em = emf.createEntityManager();
    private List<automovel> automoveis = new ArrayList<automovel>();

    public boolean cadastrar(automovel a) {
        em.getTransaction().begin();
        try {
            em.persist(a);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        }
    }

    public boolean editar(automovel a) {
        em.getTransaction().begin();
        try {
            em.merge(a);
            em.getTransaction().commit();
            return true;
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            em.getTransaction().commit();
            return false;
        }
    }

    public boolean excluir(automovel a) {
        em.getTransaction().begin();
        try {
            em.remove(a);
            em.getTransaction().commit();
            return true;
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            em.getTransaction().commit();
            return false;
        }
    }

    public List<automovel> listar() {
        Query consulta = em.createQuery("select a from automovel a");
        return consulta.getResultList();
    }
    public List<automovel> listarAutomoveisDeCliente(cliente c) {
        Query consulta = em.createQuery("select a from automovel a where a.cliente = :c");
        consulta.setParameter("c", c);
        return consulta.getResultList();
    }
}
