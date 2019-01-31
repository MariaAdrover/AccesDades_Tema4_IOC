package classesIOC;


/**
 *
 * @author josep
 */
public class ProducteAGranel extends Producte {
    private static final long serialVersionUID = 1L;
    private UnitatDeMesura unitat;

    public ProducteAGranel() {
    }

    public ProducteAGranel(Article article, double preu) {
        super(article, null, preu);
    }
    
    public ProducteAGranel(Article article, double preu, UnitatDeMesura unitat) {
        super(article, null, preu);
        this.unitat = unitat;
    }
    
    @Override
    public String toString() {
        return "PRODUCTE A GRANEL: " + getArticle().toString()  
                                     + ", preu=" + getPreu() + "/" 
                                     + unitat.getSimbol() + "]";        
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
    
}
