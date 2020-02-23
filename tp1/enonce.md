# TP1 - ORM

L'objectif de ce TP est de ce familiariser avec les technologies de mapping objet-relationnel au travers de l'API JPA de Java. Pour cela il est demandé de compléter le code de l'application fournie afin de la rendre fonctionnelle.

Le TP peut être réalisé seul ou en binôme. Les groupes de 3 étudiants ou plus sont interdits.

Le projet indiqué dans tomuss (_c.f._ ci dessous) sera utlisé pour récupérer le code source produit pour ce TP.
Il est donc demandé de pousser le code final du TP sur le dépôt forge que vous avez créé précédement.

La date limite est fixée au **vendredi 7 octobre 2016 à 23h59**.

## Mise en route

Créer un projet sur la [forge](http://forge.univ-lyon1.fr) et partagez le avec votre binôme.
Cloner votre projet (on supposera que l'identifiant du projet est `pxxxx-gdw-tp1`):
    
    hg clone https://forge.univ-lyon1.fr/hg/pxxxx-gdw-tp1
    
Tirer ensuite le contenu de l'application depuis le dépôt de l'énoncé, puis pousser le tout vers votre dépôt sur la forge.

    cd pxxxx-gdw-tp1
    hg pull https://forge.univ-lyon1.fr/hg/inf1090m-2016-tp1
    hg update
    hg push
    
Enregistrez la valeur `pxxxx-gdw-tp1` dans la case `TP1_Forge` de l'UE _Gestion De Données Pour Le Web_ dans [tomuss](http://tomuss.univ-lyon1.fr). A noter que les 2 membres du binôme doivent saisir cette même valeur qui sera utilisée pour identifier les groupes et les rendus.

Charger le projet dans votre IDE favori.

## Mise en place de la base de données

La base de données relationnelle utilisée dans ce TP est [H2](http://h2database.com/). 
Télécharger la base de données, puis lancer la console:

    cd ..../h2/bin
    ./h2.sh -web -webAllowOthers -tcp -tcpAllowOthers -browser
    
Vérifier les paramètres dans la page web qui s'ouvre (enregistrer la connexion pour plus tard au cas où):

  -  Pilote JDBC: `org.h2.Driver`
  -  URL JDBC: `jdbc:h2:tcp://localhost/~/gdw-db`
  -  Nom d'utilisateur: _vide_
  -  Mot de passe: _vide_

Utiliser le script SQL `src/main/resources/microblog.sql` pour générer les tables de la base de données.

Laisser la base de données démarrée.

Vérifier la connexion entre Java et la base H2 en lançant le test unitaire `JPATest`.

## Tour d'horizon de l'application "microblog"

Pour lancer l'application, deux possibilités:

  - exécuter la classe `mif04.gdw.tp1.App` depuis l'IDE
  - lancer la commance `mvn exec:java` depuis la racine du projet
  
L'application lance une interface dans un navigateur. Cette interface n'est pour le moment pas réellement fonctionnelle, c'est à vous de prendre la main pour la compléter.

Le code comporte 3 packages d'intérêt:

  - `modele` contient les classes que l'on fera correspondre à des tables de la base de données (à annoter avec JPA)
  - `dao` contient le code des classes faisant l'interface entre le support de persistance (_i.e._ JPA) et le code métier
  - `metier` contient une seule classe, `Blog` qui contient du code métier utilisé par l'interface. Les transactions sont gérées dans cette classe et ne doivent pas être gérées au niveau des DAOs.
  
Par ailleurs, le package `itf` contient le code qui gère l'interface Web. Il n'est à priori pas nécessaire de comprendre en profondeur ce code.

## Travail demandé

  1. Comprendre le schéma relationnel en particulier les clés utilisées
  2. Annoter les classes du package `modele` avec JPA, en procédant par exemple comme suit:
     - Compléter et annoter la classe `User`
     - Compléter et annoter la classe `Categorie` sauf ce qui concerne la collection des billets. Attention à la gestion de la clé primaire.
     - Compléter et annoter la classe `Billet` en faisant attention aux clés primaires et étrangères définies sur la table `BILLET`
     - Terminer de compléter et d'annoter la classe `Categorie`
  3. Compléter le code des classes DAO. S'il est nécessaire de créer des requêtes dans le langage JPA (JPQL), ces requêtes pourront être définies dans les classes entités correspondantes via l'annotation `@NamedQueries`.
  4. Ecrire des tests unitaires pour les différents DAO.
  5. Vérifier le bon fonctionnement de l'application.

**Remarque:** Le test unitaire `JPATest` permet de vérifier que le mapping défini par les annotation est cohérent avec le schéma de la base H2.

### Contraintes
Les modifications apportées au projet doivent respecter les contraintes suivantes:
  - Pas de modification du schéma de la base H2
  - Pas de suppression ou de modification des méthodes/constructeurs publics des classes fournies. Il est cependant possible d'ajouter d'autres méthodes dans ces classes.
  
## Indications

### Liens utiles

  * [API JEE 7](https://docs.oracle.com/javaee/7/api/) -> package ''javax.persistence''
  * [JPA](http://en.wikipedia.org/wiki/Java_Persistence_API)
  * [Wikibook sur JPA](https://en.wikibooks.org/wiki/Java_Persistence)
  * [Hibernate](https://www.hibernate.org/)
  * [Tutoriel JPA](http://docs.oracle.com/javaee/7/tutorial/partpersist.htm#BNBPY) en anglais
  * [Ancient TP sur les ORMS](http://liris.cnrs.fr/ecoquery/dokuwiki/doku.php?id=enseignement:tp:bd:tp-orm)
  
### Remarques sur les annotations

  * Lorsqu’une entité/classe possède un identifiant portant sur plusieurs attributs, il faut créer une classe spécifique (qui n'est pas une entité) pour regrouper ces champs, et utiliser un champ de cette classe en lieu et place du groupe de champs servant d'identifiants dans votre modèle objet. Voir `@EmbeddedId`, `@Embbedable`, `@IdClass`. Voir également `@ElementCollection`/`@CollectionTable` si les éléments de la collection ne sont pas des entités.
  * Les annotations `@Column`, `@JoinColumn` peuvent être dans le mapping
  * Avec le paramètre `mappedBy`, il est possible d'utiliser une valeur du type `maCle.unChamp` pour indiquer que la relation inverse est référencée à partir d'un champ dans une clé composée (i.e. en présence de `@Embbedable`/`@EmbbeddedId`).
  * L'annotation `@Lob` peut être utilisée pour les champs contenant de grandes données (type TEXT, CLOB ou BLOB).
  
 
