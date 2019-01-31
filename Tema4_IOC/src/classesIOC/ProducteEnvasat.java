package classesIOC;

/**
 *
 * @author josep
 */
public class ProducteEnvasat extends Producte {

    private Envas envas;

    public ProducteEnvasat() {
    }

    public ProducteEnvasat(Article article, String marca, double preu) {
        super(article, marca, preu);
    }

    public ProducteEnvasat(Article article, String marca, double preu, Envas envas) {
        super(article, marca, preu);
        this.envas = envas;
    }

    @Override
    public String toString() {
        if (envas == null) {
            return "PRODUCTE ENVASAT --> " + super.toString();
        }
        return "PRODUCTE ENVASAT (" + envas.toString() + ") --> " + super.toString();
    }

    /**
     * @return the envas
     */
    public Envas getEnvas() {
        return envas;
    }

    /**
     * @param envas the envas to set
     */
    public void setEnvas(Envas envas) {
        this.envas = envas;
    }
}
