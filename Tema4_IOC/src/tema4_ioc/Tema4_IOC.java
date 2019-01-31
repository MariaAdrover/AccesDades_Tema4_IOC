package tema4_ioc;

import classesIOC.Article;
import classesIOC.Envas;
import classesIOC.Estoc;
import classesIOC.Magatzem;
import classesIOC.Producte;
import classesIOC.ProducteAGranel;
import classesIOC.ProducteEnvasat;
import classesIOC.UnitatDeMesura;
import com.db4o.ext.Db4oIOException;
import java.io.File;
import java.util.List;

/**
 *
 * @author miaad
 */
public class Tema4_IOC {

    private void crearMagatzem(GestorProductes gestor) {
        Magatzem m = gestor.obtenirObjecte(new Magatzem("1", "Magatzem 1"));

        System.out.println("Crear MAGATZEM");
        System.out.println(m);
        System.out.println("");
    }

    private void crearUnitatsMesura(GestorProductes gestor) {
        List list;

        gestor.obtenirObjecte(new UnitatDeMesura("l", "litre"));
        gestor.obtenirObjecte(new UnitatDeMesura("kg", "quilogram"));
        gestor.obtenirObjecte(new UnitatDeMesura("g", "gram"));

        System.out.println("Crear UNITATS DE MESURA");
        list = gestor.obtenirUnitats();
        gestor.mostrarInformacioLLista(list);
        System.out.println("");
    }

    private void crearEnvasos(GestorProductes gestor) {
        UnitatDeMesura mesura;
        List list;

        mesura = gestor.obtenirObjecte(new UnitatDeMesura("l", "litre"));
        gestor.obtenirObjecte(new Envas("ampolla", 0.75, mesura));
        mesura = gestor.obtenirObjecte(new UnitatDeMesura("g", "gram"));
        gestor.obtenirObjecte(new Envas("brick", 350, mesura));
        gestor.obtenirObjecte(new Envas("bossa", 150, mesura));

        System.out.println("Crear ENVASOS");
        list = gestor.obtenirEnvasos();
        gestor.mostrarInformacioLLista(list);
        System.out.println("");
    }

    private void crearArticles(GestorProductes gestor) {
        List list;

        gestor.obtenirObjecte(new Article("vino tinto"));
        gestor.obtenirObjecte(new Article("vino blanco"));
        gestor.obtenirObjecte(new Article("patatilla"));
        gestor.obtenirObjecte(new Article("salsa tomate"));
        gestor.obtenirObjecte(new Article("poma"));
        gestor.obtenirObjecte(new Article("tomatiga"));

        System.out.println("Crear ARTICLES");
        list = gestor.obtenirArticles();
        gestor.mostrarInformacioLLista(list);
        System.out.println("");
    }

    private void crearProductes(GestorProductes gestor) {
        UnitatDeMesura mesura;
        Envas envas;
        Article article;
        List list;

        mesura = gestor.obtenirObjecte(new UnitatDeMesura("l", "litre"));
        article = gestor.obtenirObjecte(new Article("vino tinto"));
        envas = gestor.obtenirObjecte(new Envas("ampolla", 0.75, mesura));
        gestor.obtenirObjecte(new ProducteEnvasat(article, "ViNegre 1", 5.00, envas));
        gestor.obtenirObjecte(new ProducteEnvasat(article, "ViNegre 2", 9.99, envas));
        gestor.obtenirObjecte(new ProducteEnvasat(article, "ViNegre 3", 20.00, envas));

        article = gestor.obtenirObjecte(new Article("vino blanco"));
        gestor.obtenirObjecte(new ProducteEnvasat(article, "ViBlanc 1", 2.95, envas));
        gestor.obtenirObjecte(new ProducteEnvasat(article, "ViBlanc 2", 18.50, envas));

        mesura = gestor.obtenirObjecte(new UnitatDeMesura("g", "gram"));
        article = gestor.obtenirObjecte(new Article("patatilla"));
        envas = gestor.obtenirObjecte(new Envas("bossa", 150, mesura));
        gestor.obtenirObjecte(new ProducteEnvasat(article, "Matutano", 1.99, envas));
        gestor.obtenirObjecte(new ProducteEnvasat(article, "Ruffles", 1.50, envas));

        article = gestor.obtenirObjecte(new Article("salsa tomate"));
        envas = gestor.obtenirObjecte(new Envas("brick", 350, mesura));
        gestor.obtenirObjecte(new ProducteEnvasat(article, "orlando", 0.99, envas));

        mesura = gestor.obtenirObjecte(new UnitatDeMesura("kg", "quilogram"));
        article = gestor.obtenirObjecte(new Article("poma"));
        gestor.obtenirObjecte(new ProducteAGranel(article, 1.23, mesura));
        article = gestor.obtenirObjecte(new Article("tomatiga"));
        gestor.obtenirObjecte(new ProducteAGranel(article, 0.89, mesura));

        System.out.println("Crear PRODUCTES");
        list = gestor.obtenirProductes();
        gestor.mostrarInformacioLLista(list);
        System.out.println("");
    }

    private void asignarEstoc(GestorProductes gestor, Magatzem m) {
        List<Producte> llistaProductes = gestor.obtenirProductes();
        double[] quantitat = new double[]{20, 20, 20, 15, 9, 30, 35, 40, 15.5, 19.0};

        for (int i = 0; i < llistaProductes.size(); i++) {
            Estoc stc;
            Producte p = llistaProductes.get(i);
            stc = new Estoc(p, quantitat[i]);
            m.assignarEstoc(stc);
        }

        System.out.println("Assignar STOCK");
        gestor.veureEstocPerProductesMagatzem(llistaProductes, m);
    }

    public void instanciarObjectes() {
        GestorProductes gestor = null;
        try {
            gestor = new GestorProductes("tema4DB4O.data");

            // Crear magatzem
            crearMagatzem(gestor);

            // Crear unitats de mesura
            crearUnitatsMesura(gestor);

            // Envasos
            crearEnvasos(gestor);

            // Articles
            crearArticles(gestor);

            // Productes
            crearProductes(gestor);

            // Actualitzar els canvis            
            Magatzem m = gestor.obtenirObjecte(new Magatzem("1", "Magatzem 1"));
            gestor.actualitzar(m);

            // Assignació de l’estoc per cada producte al magatzem
            asignarEstoc(gestor, m);

            // Actualitzar els canvis
            gestor.actualitzar(m);

        } catch (Db4oIOException ex) {
            System.err.println("Error:" + ex.getMessage());

        } finally {
            if (gestor != null) {
                gestor.tancar();
            }
        }
    }

    private void incrementarPreus(GestorProductes gestor) {
        List<Producte> llista;

        System.out.println("==========>>  Incrementar preu");
        System.out.println("PRECIOS ORIGINALES");
        llista = gestor.obtenirProductePerArticleSODA(new Article("vino tinto"));
        gestor.mostrarInformacioLLista(llista);
        System.out.println("");
        System.out.println(">> INCREMENTO EL PRECIO EN UN 5%");
        System.out.println("");
        for (Producte p : llista) {
            p.setPreu(p.getPreu() * 1.05);
        }

        System.out.println("PRECIOS INCREMENTADOS 5%");
        gestor.mostrarInformacioLLista(llista);
        System.out.println("");
    }

    private void incrementarEstoc(GestorProductes gestor, Magatzem m) {
        List<Producte> llista;
        System.out.println("==========>> Incremento stock en 2 ud");
        System.out.println("STOCK ACTUAL de vino tinto");
        llista = gestor.obtenirProductePerArticleNQ(new Article("vino tinto"));
        gestor.veureEstocPerProductesMagatzem(llista, m);
        System.out.println("");
        System.out.println(">> AUMENTO EL STOCK EN 2 UD");
        System.out.println("");
        for (Producte p : llista) {
            m.incrementarEstocProducte(p, 2.0);
        }
        System.out.println("STOCK INCREMENTADOS en 2 ud");
        gestor.veureEstocPerProductesMagatzem(llista, m);
        System.out.println("");
    }

    private void decrementarEstoc(GestorProductes gestor, Magatzem m) {
        List<Producte> llista;
        System.out.println("==========>> Decrementar stock en 5 ud");
        System.out.println("STOCK ACTUAL de vino tinto");
        llista = gestor.obtenirProductePerArticleSODA(new Article("vino tinto"));
        gestor.veureEstocPerProductesMagatzem(llista, m);
        System.out.println("");
        System.out.println(">> DECREMENTO EL STOCK EN 5 UD");
        System.out.println("");
        for (Producte p : llista) {
            m.decrementarEstocProducte(p, 5.0);
        }
        System.out.println("STOCK decrementados en 5 ud");
        gestor.veureEstocPerProductesMagatzem(llista, m);
    }

    public void modificarDades() {
        GestorProductes gestor = null;
        try {
            gestor = new GestorProductes("tema4DB4O.data");
            Magatzem m = gestor.obtenirObjecte(new Magatzem("1"));

            // Incrementam un 5% el preu de tots els vins negres 
            incrementarPreus(gestor);
            gestor.actualitzar(m);

            // Incrementar l'estoc de vi negre
            incrementarEstoc(gestor, m);
            gestor.actualitzar(m);

            // Decrementar l'estoc de vi negre
            decrementarEstoc(gestor, m);
            gestor.actualitzar(m);

        } catch (Db4oIOException ex) {
            System.err.println("Error:" + ex.getMessage());

        } finally {
            if (gestor != null) {
                gestor.tancar();
            }
        }
    }

    public void ferConsulta() {
        GestorProductes gestor = null;
        try {
            gestor = new GestorProductes("tema4DB4O.data");

            List<Producte> llista = gestor.obtenirProductesEstocMenorNQ("1", 20);
            System.out.println("Productes amb estoc menor o igual que 20 -- consulta NQ");
            gestor.veureEstocPerProductesMagatzem(llista, gestor.obtenirObjecte(new Magatzem("1")));

            System.out.println("");

            llista = gestor.obtenirProductesEstocMenorSODA("1", 20);
            System.out.println("Productes amb estoc menor o igual que 20 -- consulta SODA");
            gestor.veureEstocPerProductesMagatzem(llista, gestor.obtenirObjecte(new Magatzem("1")));
        } finally {
            if (gestor != null) {
                gestor.tancar();
            }
        }
    }

    public void pruebaQueries1(GestorProductes gestor) {
        List llistaProd;

        System.out.println(">> Mostrar tots els productes als que l'article comença per 'vi', amb preu>=5 i preu<=20");
        System.out.println("---  NQ ---");
        llistaProd = gestor.obtenirProductesDunArticlePerPreuNQ("vi", 5, 20);
        gestor.mostrarInformacioLLista(llistaProd);
        System.out.println("");
    }

    public void pruebaQueries2(GestorProductes gestor) {
        List llistaProd;

        System.out.println(">> Mostrar tots els productes als que l'article comença per 'vi', amb preu>=5 i preu<=20");
        System.out.println("---  SODA ---");
        llistaProd = gestor.obtenirProductesDunArticlePerPreuSODA("vi", 5, 20);
        gestor.mostrarInformacioLLista(llistaProd);
        System.out.println("");
    }

    public void pruebaEliminar(GestorProductes gestor, Object p) {
        System.out.println(">> ELIMINAM EL PRODUCTE:");
        System.out.println(p);

        gestor.eliminar(p);
        System.out.println("");
    }

    public void altresProves() {        
        GestorProductes gestor = new GestorProductes("tema4DB4O.data");
        // Feim la 1ª consulta
        pruebaQueries1(gestor);
        
        //Obtenim la referència a l'article que volem eliminar
        Article article = gestor.obtenirObjecte(new Article("vino tinto"));
        //Producte p = gestor.obtenirObjecte(new ProducteEnvasat(article, "ViNegre 1", 5));
        
        // Eliminam l'article
        pruebaEliminar(gestor,article);
        
        // Tornam a fer la consulta
        pruebaQueries2(gestor);
        gestor.getDb().close();
    }
    
    public void eliminarDB(String fileName) {
        new File("tema4DB4O.data").delete();
        System.out.println("<<<< DB " + fileName + " eliminated >>>>");        
    }

    public static void main(String[] args) {
        Tema4_IOC db4o = new Tema4_IOC();

        /* Eliminar la base de dades*/
        //db4o.eliminarDB("tema4DB4O.data");

        /* Acxtivitat 2 - part 1 
        System.out.println("====================================== EJERCICIO 2.1 ======================================");
        db4o.instanciarObjectes();
        System.out.println("");
        System.out.println("");*/

        /* Acxtivitat 2 - part 2 
        System.out.println("====================================== EJERCICIO 2.2 ======================================");
        db4o.modificarDades();
        System.out.println("");
        System.out.println("");*/

        /* Activitat 2 - part 3 
        System.out.println("====================================== EJERCICIO 2.3 ======================================");
        db4o.ferConsulta();
        System.out.println("");
        System.out.println("");*/

        /* QUERIES 
        System.out.println("===================================== Altres mètodes ======================================");
        db4o.altresProves();*/
    }

}
