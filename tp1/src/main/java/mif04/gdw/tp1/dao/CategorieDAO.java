package mif04.gdw.tp1.dao;

import mif04.gdw.tp1.modele.Categorie;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.Collection;
import java.util.List;

/**
 * Created by ecoquery on 25/09/2016.
 */
public class CategorieDAO {
	
	private final EntityManager em;
	
    public CategorieDAO(EntityManager em) {
        this.em = em;
    }

    public List<Categorie> getAllCategories() {
    	return em.createQuery("SELECT cat FROM Categorie cat", Categorie.class).getResultList();
    }

    public Categorie getOrCreate(String nomCategorie) {
        TypedQuery<Categorie> q = em.createQuery("SELECT cat "
				+ "FROM Categorie cat "
				+ "WHERE cat.nom = ?1 ", Categorie.class);
		q.setParameter(1, nomCategorie);
		Collection<Categorie> results = q.getResultList();
        if (results.size() > 0) {
            return results.iterator().next();
        } else {
        	Categorie cat= new Categorie();
            cat.setNom(nomCategorie);
            em.persist(cat);
            return cat;
        }
    }

}
