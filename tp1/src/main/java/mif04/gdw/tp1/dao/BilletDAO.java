package mif04.gdw.tp1.dao;

import java.util.Collection;

import mif04.gdw.tp1.modele.Billet;
import mif04.gdw.tp1.modele.Categorie;
import mif04.gdw.tp1.modele.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Created by ecoquery on 25/09/2016.
 */
public class BilletDAO {
	
	private final EntityManager em;

    public BilletDAO(EntityManager em) {
    	this.em = em;
    }

    public Billet getBilletByTitreAndCategorie(String titre, String categorie) {
    	TypedQuery<Billet> q =
                em.createQuery("SELECT b "
                		+ "FROM Billet b JOIN b.categorie c "
                		+ "WHERE b.titre = ?1 AND c.nom = ?2", Billet.class);
        q.setParameter(1,titre);
        q.setParameter(2,categorie);
        Collection<Billet> results = q.getResultList();
        if (results.size() > 0) {
            return results.iterator().next();
        } else {
            return null;
        }

    }

    public Billet create(String titre, Categorie categorie, String contenu, User user) {
    	Billet billet = new Billet();
        billet.setTitre(titre);
        billet.setCategorie(categorie);
        billet.setContenu(contenu);
        billet.setUser(user);
        categorie.getBillets().add(billet);
        em.persist(billet);
        return billet;
    }
}
