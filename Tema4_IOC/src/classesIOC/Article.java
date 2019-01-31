package classesIOC;

import java.io.Serializable;

/**
 * Classe que representa una article de venda de l'aplicació comercial
 * @author josep
 */
public class Article implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id; //denominació de l'article
    private String descripcio;

    /**
     * Constructor per defecte
     */
    public Article() {
    }

    /**
     * Constructor que inicializa la descripció de l'article
     * @param id inicalitza la denominació de l'article.
     */
    public Article(String id) {
        this.id = id;
    }

    /**
     * Constructor que inicializa la denominació i la descripció de l'article. 
     * @param id és la denominació de l'article i fa d'identificador .
     * @param descripcio És la descripció que caldrà assignar a l'article 
     */
    public Article(String id, String descripcio) {
        this.id=id;
        this.descripcio = descripcio;
    }
    
    /**
     * Obté la denominació de l'article
     * @return denominació de l'article.
     */
    public String getId() {
        return id;
    }

    /**
     * Assigna la denominació de l'article que servirà també d'identificador
     * @param id Valor que s'assignarà a l'id de l'article
     */
    public void setId(String id) {
        this.id = id;
    }

//    /**
//     * Indica si l'objecte on es fa la crida i el que es passa per paràmetre són 
//     * iguals.
//     * @param object a comparar
//     * @return cert si són iguals. Fals en cas contrari.
//     */
//    @Override
//    public boolean equals(Object object) {
//        if (!(object instanceof Article)) {
//            return false;
//        }
//        Article other = (Article) object;
//        if ((this.id == null && other.id != null) 
//                || (this.id != null && !this.id.equals(other.id))) {
//            return false;
//        }
//        return true;
//    }

    /**
     * Obté una cadena representativa de l'objecte on es fa la crida
     * @return cadena representativa de l'objecte 
     */
    @Override
    public String toString() {
        return "ARTICLE: id=" + id + " / descripcio: " + descripcio;
    }

    /**
     * Obté la descripció de l'article
     * @return la descripcio
     */
    public String getDescripcio() {
        return descripcio;
    }

    /**
     * Assigna un valor a la descripció de l'article
     * @param descripcio És el valor a assignar.
     */
    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }
    
}
