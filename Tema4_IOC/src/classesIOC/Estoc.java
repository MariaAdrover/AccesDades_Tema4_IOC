/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classesIOC;

import java.io.Serializable;

/**
 *
 * @author josep
 */
public class Estoc implements Serializable {
    private Producte producte=null;
    private Double quantitat=0.0;

    public Estoc() {
    }

    public Estoc(Producte prod, Double quantitat) {
        this.producte = prod;
        this.quantitat = quantitat;
    }
    
    /**
     * @return the quantitat
     */
    public Double getQuantitat() {
        return quantitat;
    }

    /**
     * @param quantitat the quantitat to set
     */
    public void setQuantitat(Double quantitat) {
        this.quantitat = quantitat;
    }
    
    @Override
    public String toString(){
        return producte.toString() + "[Qant: " + getQuantitat() +"]";
    }

    /**
     * @return the producte
     */
    public Producte getProducte() {
        return producte;
    }

    /**
     * @param producte the producte to set
     */
    public void setProducte(Producte producte) {
        this.producte = producte;
    }
}
