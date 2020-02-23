package mif04.gdw.tp1.modele;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by ecoquery on 25/09/2016.
 */
@Entity
@IdClass(BilletPrimaryKey.class)
@Table(name="BILLET")
public class Billet {
    private static final Logger LOG = LoggerFactory.getLogger(Billet.class);
    
    @Id
    private String titre;
    
    @Id
    @ManyToOne
    @JoinColumn(name="ID_CAT") // column ID_CAT in Billet Table
    private Categorie categorie;
    
    @ManyToOne
    @JoinColumn(name="USER_EMAIL")
    private User user;
    
    @Lob
    @Column(name="CONTENU")
    private String contenu; // CLOB
    
    public User getUser() {
        return user;
    }
    
    public Categorie getCategorie() {
        return categorie;
    }

    public String getTitre() {
        return titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
    	this.contenu = contenu;
    }

    public String getTitreEncode() {
        try {
            return URLEncoder.encode(getTitre(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error("erreur d'encodage", e);
            return "erreur";
        }
    }
    
    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
