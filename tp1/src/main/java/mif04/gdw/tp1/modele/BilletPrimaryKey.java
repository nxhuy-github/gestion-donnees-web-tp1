package mif04.gdw.tp1.modele;

import java.io.Serializable;

import javax.persistence.Embeddable;


@Embeddable
public class BilletPrimaryKey implements Serializable{

	private static final long serialVersionUID = 1L;

	public Categorie categorie;
    public String titre;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
        	return true;
        }
        if (o == null || getClass() != o.getClass()) {
        	return false;
        }
        BilletPrimaryKey billetPK = (BilletPrimaryKey) o;
        if (!categorie.equals(billetPK.categorie)) {
        	return false;
        }
        return titre.equals(billetPK.titre);

    }

    @Override
    public int hashCode() {
        return 31 * categorie.hashCode() + titre.hashCode();
    }

}
