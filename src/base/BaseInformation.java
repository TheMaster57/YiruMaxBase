package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Maxime Blaise
 */
public class BaseInformation implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Map qui gère toutes les données
     */
    private HashMap<String, String> map = new HashMap<>();

    /**
     * Constructeur qui initialise d'office la map.
     * @param maplu .
     */
    public BaseInformation(HashMap<String, String> maplu) {
    	this.map = maplu;
    }

    public BaseInformation(String driver, String dbname, String url) {
        map.put("driver", driver);

        switch(driver) {
            case "mysql":
                map.put("namedriver", "com.mysql.jdbc.Driver");
                break;
            default:
                map.put("namedriver", "com.mysql.jdbc.Driver");
                break;
        }
        
        map.put("dbname", dbname);
        map.put("login", "root");
        map.put("password", "");
        map.put("url", url);
    }

    /**
     * Constructeur vide.
     * Se contente de remplir avec les valeurs par défaut.
     */
    public BaseInformation() {
    	map.put("driver", "mysql");
    	map.put("namedriver", "com.mysql.jdbc.Driver");
    	map.put("dbname", "dbname");
    	map.put("login", "root");
    	map.put("password", "");
    	map.put("url", "//localhost");
    }

   
    /**
     * Method which read all informations from infobdd.idb file.
     *
     * @param filename nom du fichier.
     * @return les informations utiles
     * @throws java.io.IOException .
     * @throws java.lang.ClassNotFoundException .
     */
    @SuppressWarnings("unchecked")
	public static BaseInformation lectureInformations(String filename) throws IOException, ClassNotFoundException {
        BaseInformation res = null;

        try {
            //On récupère simplement l'objet
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
                //On récupère simplement l'objet
                res = new BaseInformation((HashMap<String, String>) ois.readObject());
            }
        } catch(NullPointerException e) {
           // System.out.println("Null pointer exception");
        }

        return res;
    }

    /**
     * Modification d'une valeur de la HashMap
     * @param cle Clé
     * @param valeur Valeur
     */
    public void modifierValeur(String cle, String valeur) {
        this.map.replace(cle, valeur);
    }

    /**
     * This method is the most important in this class. It writes database
     * information in the file.
     *
     * @param filename nom du fichier
     * @throws java.io.FileNotFoundException .
     */
    public void ecrireInformations(String filename) throws FileNotFoundException, IOException {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this.map);
        }
    }

    public HashMap<String, String> getMap() {
        return map;
    }

    public void setMap(HashMap<String, String> map) {
        this.map = map;
    }
}
