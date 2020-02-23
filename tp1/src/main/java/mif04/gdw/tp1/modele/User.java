package mif04.gdw.tp1.modele;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by ecoquery on 25/09/2016.
 */
@Entity
@Table(name="USER")
public class User {

	@Id
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="PSEUDO")
	private String pseudo;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

    
}
