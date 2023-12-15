package Code;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;

public class Conexion {
    //URI colección
    final static String URI="xmldb:exist://localhost:8080/exist/xmlrpc/db";
    //Usuario
    final static String usu = "admin";
    //Clave
    final static String usuPwd = "";
    // Colección
    static Collection col = null;
    // Diver para existDB
    final static String driver = "org.exist.xmldb.DatabaseImpl";

    public static void conexionExistDb() {

        try {
            // Cargar el driver.
            Class cl = Class.forName(driver);

            // Instancia de la base de datos.
            Database database = (Database) cl.newInstance();

            // Registro del driver.
            DatabaseManager.registerDatabase(database);

            // Definir la colección de trabajo.
            col = DatabaseManager.getCollection(URI, usu, usuPwd);

        } catch (Exception e) {
            System.out.println("Error en la conexión de la base de datos " + e.getMessage());
        }

    }
}
