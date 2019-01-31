package classesIOC;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author josep
 */
public class Magatzem implements Serializable {
    private String id;
    private String descripcio;
    private Map<Producte, Estoc> estoc = new HashMap<Producte, Estoc>();

    public Magatzem() {
    }

    public Magatzem(String id) {
        this.id = id;
    }
    
    public Magatzem(String id, String descripcio) {
        this.id = id;
        this.descripcio = descripcio;
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Magatzem: " + id + " / Estoc: ";
    }

    /**
     * @return the descripcio
     */
    public String getDescripcio() {
        return descripcio;
    }

    /**
     * @param descripcio the descripcio to set
     */
    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    /**
     * @return the estoc
     */
    public Map<Producte, Estoc> getEstoc() {
        return estoc;
    }

    /**
     * @param estoc the estoc to set
     */
    protected void setEstoc(Map<Producte, Estoc> estoc) {
        this.estoc = estoc;
    }
    
    public void assignarEstoc(Producte producte, Double quant){
        this.estoc.put(producte, new Estoc(producte, quant));
    }

    public void assignarEstoc(Estoc estocPr){
        this.estoc.put(estocPr.getProducte(), estocPr);
    }

    public void eliminarProducte(Producte producte){
        this.estoc.remove(producte);
    }
    
    public void incrementarEstocProducte(Producte producte, Double inc){
        Estoc estocProd;
        if(!estoc.containsKey(producte)){
            estoc.put(producte, new Estoc(producte, 0.0));
            
        }
        estocProd = estoc.get(producte);
        Double quant = estocProd.getQuantitat();
        quant+=inc;
        estocProd.setQuantitat(quant);
    }

    public void decrementarEstocProducte(Producte producte, Double inc){
        incrementarEstocProducte(producte, -inc);
    }
    
    public Estoc getEstoc(Producte producte){
        return estoc.get(producte);
    }
    
    public Iterator<Producte> getProductes(){
        return estoc.keySet().iterator();
    }
}
