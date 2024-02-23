package Conexiones;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;

public class ConexionExist {
    //URI colección
    final static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db";
    //Usuario
    final static String usu = "admin";
    //Clave
    final static String usuPwd = "";
    // Colección
    static Collection col = null;
    // Diver para existDB
    final static String driver = "org.exist.xmldb.DatabaseImpl";

    public static Collection conexionExistDb() {

        try {
            // Cargar el driver.
            Class cl = Class.forName(driver);

            // Instancia de la base de datos.
            Database database = (Database) cl.newInstance();

            // Registro del driver.
            DatabaseManager.registerDatabase(database);

            // Definir la colección de trabajo.
            col = DatabaseManager.getCollection(URI, usu, usuPwd);
            return col;

        } catch (XMLDBException e) {
            System.err.println("\n>>> Error al inicializar la BD eXist.");
        } catch (ClassNotFoundException e) {
            System.err.println("\n>>> Error en el driver.");
        } catch (InstantiationException e) {
            System.err.println("\n>>> Error al instanciar la BD.");
        } catch (IllegalAccessException e) {
            System.err.println("\n>>> Error al instanciar la BD.");
        }

        return null;
    }

    public static void desconectar() {
        try {
            col.close();
        } catch (XMLDBException e) {
            System.err.println("\n>>> Error al inicializar la BD eXist.");
        }
    }
}
