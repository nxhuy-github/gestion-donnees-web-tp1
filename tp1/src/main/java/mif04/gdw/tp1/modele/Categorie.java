package mif04.gdw.tp1.modele;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Created by ecoquery on 25/09/2016.
 */
@Entity
@Table(name="CATEGORIE")
public class Categorie {

    private static final Logger LOG = LoggerFactory.getLogger(Categorie.class);
    
    
    @Id
    @GeneratedValue()
    @Column(name="ID_CAT")
    private int id;
    
    @Column(name="NOM")
    private String nom;
    
    @OneToMany(mappedBy="categorie")
    private Collection<Billet> billets = new ArrayList<Billet>();

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Collection<Billet> getBillets() {
        return billets;
    }

    public String getNomEncode() {
        try {
            return URLEncoder.encode(getNom(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error("erreur d'encodage", e);
            return "erreur";
        }
    }
}
