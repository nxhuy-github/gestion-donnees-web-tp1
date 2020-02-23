package mif04.gdw.tp1.itf;

import mif04.gdw.tp1.modele.Billet;
import mif04.gdw.tp1.modele.Categorie;
import mif04.gdw.tp1.modele.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by ecoquery on 25/09/2016.
 */
public class ViewContext {
    private static final Logger LOG = LoggerFactory.getLogger(ViewContext.class);

    private User user;
    private Billet billet;
    private List<Categorie> categories;
    private String titre;

    public ViewContext() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Billet getBillet() {
        return billet;
    }

    public void setBillet(Billet billet) {
        this.billet = billet;
    }

    public List<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getLoggedEmail() {
        try {
            return user == null ? "" : URLEncoder.encode(user.getEmail(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error("Erreur d'encodage",e);
            return "error";
        }
    }
}
