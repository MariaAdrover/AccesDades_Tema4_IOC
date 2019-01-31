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
public class Envas implements Serializable {
    private String tipus;
    private double quantitat;
    private UnitatDeMesura unitat;

    public Envas() {
    }

    public Envas(String tipus, double quantitat, UnitatDeMesura unitat) {
        this.tipus = tipus;
        this.quantitat = quantitat;
        this.unitat = unitat;
    }
    
    /**
     * @return the tipus
     */
    public String getTipus() {
        return tipus;
    }

    /**
     * @param tipus the tipus to set
     */
    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    /**
     * @return the quantitat
     */
    public double getQuantitat() {
        return quantitat;
    }

    /**
     * @param quantitat the quantitat to set
     */
    public void setQuantitat(double quantitat) {
        this.quantitat = quantitat;
    }

    /**
     * @return the unitat
     */
    public UnitatDeMesura getUnitat() {
        return unitat;
    }

    /**
     * @param unitat the unitat to set
     */
    public void setUnitat(UnitatDeMesura unitat) {
        this.unitat = unitat;
    }

    @Override
    public String toString() {
        return "ENVAS: " + tipus + " de " + quantitat + " " + unitat.getSimbol();
    }    
}
