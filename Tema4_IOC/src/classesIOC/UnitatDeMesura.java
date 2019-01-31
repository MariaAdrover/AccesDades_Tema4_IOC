package classesIOC;

import java.io.Serializable;

/**
 *
 * @author josep
 */
public class UnitatDeMesura implements Serializable {
    private static final long serialVersionUID = 1L;
    private String simbol=null;
    private String descripcio=null;

    public UnitatDeMesura() {
    }

    public UnitatDeMesura(String abreviacio) {
        this.simbol = abreviacio;
    }
    
    public UnitatDeMesura(String abreviacio, String descripcio) {
        this.simbol = abreviacio;
        this.descripcio = descripcio;
    }
    
    @Override
    public String toString() {
        return getSimbol() + "(" +getDescripcio()+")";
    }

    /**
     * @return the simbol
     */
    public String getSimbol() {
        return simbol;
    }

    /**
     * @param simbol the simbol to set
     */
    public void setSimbol(String abreviacio) {
        this.simbol = abreviacio;
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
    
}
