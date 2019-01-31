package tema4_ioc;

import classesIOC.Article;
import classesIOC.Envas;
import classesIOC.Estoc;
import classesIOC.Magatzem;
import classesIOC.Producte;
import classesIOC.UnitatDeMesura;
import com.db4o.Db4oEmbedded;
import com.db4o.EmbeddedObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.ext.Db4oException;
import com.db4o.ext.Db4oIOException;
import com.db4o.query.Predicate;
import com.db4o.query.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class GestorProductes {

    EmbeddedObjectContainer db;

    public GestorProductes(String dbFile) {
        obrir(dbFile);
    }

    public GestorProductes(EmbeddedConfiguration configuracio, String dbFile) {
        obrir(configuracio, dbFile);
    }

    private void obrir(String dbFile) {
        EmbeddedConfiguration configuracio = Db4oEmbedded.newConfiguration();
        configuracio.common().objectClass(Magatzem.class).cascadeOnUpdate(true);
        configuracio.common().objectClass(Magatzem.class).cascadeOnDelete(true);
        configuracio.common().activationDepth(10);
        configuracio.common().updateDepth(10);
        this.db = Db4oEmbedded.openFile(configuracio, dbFile);
    }

    public void obrir(EmbeddedConfiguration configuracio, String dbFile) {
        this.db = Db4oEmbedded.openFile(configuracio, dbFile);
    }

    public void tancar() {
        if (!db.ext().isClosed()) {
            db.commit();
            db.close();
        }
    }

    public void eliminar(Object obj) {
        try {
            db.delete(obj);
        } catch (Db4oIOException ex) {
            db.rollback();
        }
        db.commit();
    }

    public void actualitzar() {
        db.commit(); 
    }

    public void actualitzar(Object obj) {
        db.store(obj);
        db.commit();
    }

    public void contarObjetos() {
        Query q = db.query();
        List l = q.execute();
        System.out.println("OBJECTES ========================>> " + l.size());
    }

    public void mostrarInformacioLLista(List list) {
        for (Object o : list) {
            System.out.println(o);
        }
    }

    public EmbeddedObjectContainer getDb() {
        return this.db;
    }

    // ----------------------------------------------
    // -------------- OBTENIR OBJECTES --------------
    // ----------------------------------------------
    private Object obtenirUnObjecte(Object obj) throws Db4oException {
        ObjectSet llista = null;

        llista = db.queryByExample(obj);
        if (llista.hasNext()) {
            obj = llista.next();
            if (llista.hasNext()) {
                throw new Db4oException("Patró poc identificatiu");
            }
        } else {
            this.actualitzar(obj);
        }
        return obj;
    }

    public Article obtenirObjecte(Article obj) throws Db4oException {
        return (Article) obtenirUnObjecte(obj);
    }

    public Envas obtenirObjecte(Envas obj) throws Db4oException {
        return (Envas) obtenirUnObjecte(obj);
    }

    public Magatzem obtenirObjecte(Magatzem obj) throws Db4oException {
        return (Magatzem) obtenirUnObjecte(obj);
    }

    public Producte obtenirObjecte(Producte obj) throws Db4oException {
        return (Producte) obtenirUnObjecte(obj);
    }

    public UnitatDeMesura obtenirObjecte(UnitatDeMesura obj)
            throws Db4oException {
        return (UnitatDeMesura) obtenirUnObjecte(obj);
    }

    public List<Article> obtenirArticles() {
        return db.queryByExample(new Article());
    }

    public List<Envas> obtenirEnvasos() {
        return db.queryByExample(new Envas());
    }

    public List<Magatzem> obtenirMagatzems() {
        return db.queryByExample(new Magatzem());
    }

    public List<Producte> obtenirProductes() {
        return db.queryByExample(new Producte());
    }

    public List<UnitatDeMesura> obtenirUnitats() {
        return db.queryByExample(new UnitatDeMesura());
    }

    // ------------------------------------------------------------------------------
    // -------------- OBTENIR PRODUCTES QUE CUMPLEIXEN UNES CONDICIONS --------------
    // ------------------------------------------------------------------------------
    public List<Producte> obtenirProductePerArticleNQ(Article article) {
        List<Producte> productes = db.query(new Predicate<Producte>() {                        
            @Override
            public boolean match(Producte producte) {
                return producte.getArticle().getId().equalsIgnoreCase(article.getId());
            }
        });
        return productes;
    }

    public List<Producte> obtenirProductePerArticleSODA(Article article) {
        Query query = db.query();
        query.constrain(Producte.class);
        query.descend("article").descend("id").constrain(article.getId());
        ObjectSet<Producte> set = query.execute();
        return set;
    }

    public List<Producte> obtenirProductesDunArticlePerPreuNQ(final String iniciId, final double minim, final double maxim) {

        List<Producte> productes = db.query(new Predicate<Producte>() {
            @Override
            public boolean match(Producte producte) {
                int lletres = iniciId.length();
                String id = producte.getArticle().getId().substring(0, lletres);

                double preu = producte.getPreu();
                return ((id.equalsIgnoreCase(iniciId)) && (preu >= minim && preu <= maxim));
            }
        });
        return productes;
    }

    public List<Producte> obtenirProductesDunArticlePerPreuSODA(final String iniciId, final double minim, final double maxim) {

        Query q1 = db.query();
        q1.constrain(Producte.class);
        q1.descend("article").descend("id").constrain(iniciId).startsWith(true);
        q1.descend("preu").constrain(minim).equal().greater();
        q1.descend("preu").constrain(maxim).equal().smaller();
        return q1.execute();
    }

    public void veureEstocPerProductesMagatzem(List<Producte> llista, Magatzem m) {
        Collection<Estoc> estocs = m.getEstoc().values();
        Iterator i = estocs.iterator();
        while (i.hasNext()) {
            Estoc estoc = (Estoc)i.next();
            for (Producte p : llista) {
                if (estoc.getProducte() == p){
                    System.out.println("PRODUCTE= " + estoc.getProducte());
                    System.out.println("STOCK= " + estoc.getQuantitat());
                }
            }
        }
    }

    public List<Producte> obtenirProductesEstocMenorNQ(final String idMagatzem, final double quantitat) {
        final Magatzem m;
        ObjectSet<Producte> ret = null;
        ObjectSet<Magatzem> subQuery = db.query(new Predicate<Magatzem>() {
            @Override
            public boolean match(Magatzem et) {
                return et.getId().equalsIgnoreCase(idMagatzem);
            }
        });

        if (subQuery.hasNext()) {
            m = subQuery.next();
        } else {
            return new ArrayList();
        }

        ret = db.query(new Predicate<Producte>() {

            @Override
            public boolean match(Producte producte) {
                return m.getEstoc(producte).getQuantitat() <= quantitat;
            }
        });
        return ret;
    }

    public List<Producte> obtenirProductesEstocMenorSODA(final String idMagatzem, final double quantitat) {
        Collection<Estoc> estocs;
        Magatzem m;
        List<Producte> productes;

        // Selecciono el almacen con idMagatzem
        Query q1 = db.query();
        q1.constrain(Magatzem.class);
        q1.descend("id").constrain(idMagatzem);
        List<Magatzem> list = q1.execute();

        if (list.size() == 1) { // Si encuentra el magatzem...
            // Hacemos una lista (Collection) con los estocs de ese almacén
            m = list.get(0);

            // Los objetos Map estan formados por pares clave/valor
            // Los almacenes tienen Map<Producte,Estoc> Producte es la clave y Estoc el valor
            // El método values() sobre un objeto Map devuelve una Collection con los vaslores que contiene el Map, en este caso los objetos Estoc
            estocs = m.getEstoc().values();
        } else {
            System.out.println("No s'ha trobat UN magatzem ÚNIC amb id = " + idMagatzem);
            return new ArrayList();
        }

        // Una 2a query nos devuelve objetos Producte
        Producte p;
        Query q2 = db.query();
        q2.constrain(Producte.class);

        // Recorremos la lista de estocs del almacen
        for (Estoc e : estocs) {
            p = (Producte) this.obtenirObjecte(e.getProducte());
            // Para cada objeto Estoc de la lista, comprobamos si la cantidad de producto se corresponde con la cantidad indicada como parámetro
            if (e.getQuantitat() <= quantitat) {
                // Si es así añadimos una constraint OR
                // El valor de la constraint (el Producto que está dentro del rango) se acepta                
                q2.constraints().or(q2.constrain(p));
            } else {
                // Si NO, añadimos una constraint AND NOT
                // El valor de la constraint (el Producto que está dentro del rango) NO se aceptan                
                q2.constraints().and(q2.constrain(p).not());
            }
        }

        productes = q2.execute();

        if (productes.isEmpty()) {
            System.out.println("Tots els productes tenen un estoc superior a " + quantitat);
        }

        return productes;
    }
}
