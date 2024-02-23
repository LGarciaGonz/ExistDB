package Conexiones;

import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;

public class CollectionConexion {
    static Collection colCollection = null; // Declaración de variable para la colección principal.
    static Collection colDATOS = null; // Declaración de variable para la colección de datos.

    // Método para establecer la conexión con la base de datos eXist.
    public static Collection conectar() {

        // Establecer la conexión con la base de datos eXist utilizando el método de la clase ConexionExist.
        colCollection = ConexionExist.conexionExistDb();

        if (colCollection != null) {
            try {

                // Obtener el servicio de gestión de colecciones para crear una nueva colección.
                CollectionManagementService service = (CollectionManagementService) colCollection.getService("CollectionManagementService", "1.0");

                // Crear una nueva colección llamada "colecionXML".
                colDATOS = service.createCollection("colecionXML");
            } catch (XMLDBException e) {

                // Manejar el error en caso de que falle la inicialización de la base de datos eXist.
                System.err.println("\n>>> Error al inicializar la BD eXist.");
            }
        }

        // Devolver la colección de datos.
        return colDATOS;
    }

    // Método para desconectar la colección de datos.
    public static void desconectar() throws XMLDBException {
        // Cerrar la colección de datos.
        colDATOS.close();
    }
}
