package classesIOC;

import java.io.Serializable;

/**
 *
 * @author josep
 */
public class Producte implements Serializable {
    private Article article;
    private String marca;
    private double preu;

    public Producte() {
    }

    public Producte(Article article, String marca, double preu) {
        this.article = article;
        this.preu = preu;
        this.marca=marca;
    }
    
    @Override
    public String toString() {
        return "PRODUCTE: " + article.toString()+ ", marca=" + marca 
                                                    + ", preu=" + preu;
    }

    /**
     * @return the article
     */
    public Article getArticle() {
        return article;
    }

    /**
     * @param article the article to set
     */
    public void setArticle(Article article) {
        this.article = article;
    }

    /**
     * @return the preu
     */
    public double getPreu() {
        return preu;
    }

    /**
     * @param preu the preu to set
     */
    public void setPreu(double preu) {
        this.preu = preu;
    }

    /**
     * @return the marca
     */
    public String getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }
}
