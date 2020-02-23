package mif04.gdw.tp1.metier;

import mif04.gdw.tp1.dao.BilletDAO;
import mif04.gdw.tp1.dao.CategorieDAO;
import mif04.gdw.tp1.dao.UserDAO;
import mif04.gdw.tp1.modele.Billet;
import mif04.gdw.tp1.modele.Categorie;
import mif04.gdw.tp1.modele.User;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by ecoquery on 25/09/2016.
 */
public class Blog {
    private BilletDAO billetDAO;
    private CategorieDAO categorieDAO;
    private UserDAO userDAO;
    private EntityManager em;

    public Blog(EntityManager em) {
        this.em = em;
        this.billetDAO = new BilletDAO(this.em);
        this.categorieDAO = new CategorieDAO(em);
        this.userDAO = new UserDAO(em);
    }

    public List<Categorie> getCategories() {
        return categorieDAO.getAllCategories();
    }

    public User getUser(String user) {
        return userDAO.getUserByEmail(user);
    }

    public Billet getBillet(String titre, String categorie) {
        return billetDAO.getBilletByTitreAndCategorie(titre, categorie);
    }

    public User newUser(String email, String pseudo) {
        em.getTransaction().begin();
        User user = userDAO.createOrUpdate(email, pseudo);
        em.getTransaction().commit();
        return user;
    }

    /**
     * Créée un nouveau billet
     * @param titre
     * @param nomCategorie
     * @param contenu
     * @param user
     * @return le billet créé
     */
    public Billet nouveauBillet(String titre, String nomCategorie, String contenu, User user) {
        em.getTransaction().begin();
        Categorie categorie = categorieDAO.getOrCreate(nomCategorie);
        Billet billet = billetDAO.create(titre, categorie, contenu, user);
        em.getTransaction().commit();
        return billet;
    }

    public void changeBillet(Billet billet, String contenu) {
        em.getTransaction().begin();
        billet.setContenu(contenu);
        em.getTransaction().commit();
    }
}
