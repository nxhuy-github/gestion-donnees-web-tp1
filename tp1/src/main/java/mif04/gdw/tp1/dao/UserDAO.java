package mif04.gdw.tp1.dao;

import java.util.Collection;

import mif04.gdw.tp1.modele.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Created by ecoquery on 25/09/2016.
 */
public class UserDAO {
	
	private final EntityManager em;
	
    public UserDAO(EntityManager em) {
    	this.em = em;
    }

    /**
     * Renvoie l'utilisateur correspondant à cet email
     * @param email l'email de l'utilisateur
     * @return l'utilisateur ou null s'il n'existe pas
     */
    public User getUserByEmail(String email) {
    	if (email == null) {
            return null;
        }
        return em.find(User.class, email);
    }

    /**
     * Créée un nouvel utilisateur ou met à jour son pseudo
     * @param email
     * @param pseudo
     * @return l'utilisateur créé ou lis à jour
     */
    public User createOrUpdate(String email, String pseudo) {
    	User user = getUserByEmail(email);
        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setPseudo(pseudo);
            em.persist(user);
        } else {
            user.setPseudo(pseudo);
        }
        return user;

    }
}
