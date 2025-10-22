package model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

public class daoRevisao {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MWAutomoveisPU");
    EntityManager em = emf.createEntityManager();
    private List<revisao> revisoes = new ArrayList<revisao>();

    public boolean cadastrar(revisao r) {
        em.getTransaction().begin();
        try {
            em.persist(r);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        }
    }

    public boolean editar(revisao r) {
        em.getTransaction().begin();
        try {
            em.merge(r);
            em.getTransaction().commit();
            return true;
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            em.getTransaction().commit();
            return false;
        }
    }

    public boolean excluir(revisao r) {
        em.getTransaction().begin();
        try {
            em.remove(r);
            em.getTransaction().commit();
            return true;
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            em.getTransaction().commit();
            return false;
        }
    }

    public List<revisao> listar() {
        Query consulta = em.createQuery("select r from revisao r");
        return consulta.getResultList();
    }

    public revisao pegarUltimaRevisao(automovel a) {
        try{
        Query consulta = em.createQuery("select r from revisao r where r.automovel = :a order by r.idRevisao desc");
        consulta.setMaxResults(1);
        consulta.setParameter("a", a);
        return (revisao) consulta.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }
    
    public List<revisao> pegarRevisaoAutomovel (automovel a) {
        Query consulta = em.createQuery("select r from revisao r where r.automovel = :a");
        consulta.setParameter("a", a);
        return consulta.getResultList();
    }
}
