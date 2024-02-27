package Code;

import Conexiones.CollectionConexion;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;

import java.io.File;

public class InsertarXML {

    static Collection col = null;

    // Método para insertar los datos XML a la colección de ExistDb
    public static void insertXML() {

        // Archivos XML que se van a insertar
        File XmlFamilia = new File("src/main/resources/familias.xml");
        File XmlProyectosFP = new File("src/main/resources/proyectosFP.xml");
        File XmlCentros = new File("src/main/resources/CentrosCFGMyS.xml");
        File XmlProyectos = new File("src/main/resources/proyectos.xml");

        // Conexión a la colección ExistDb
        col = CollectionConexion.conectar();

        if (col != null) {

            // Se logró la conexión
            System.out.println("\t>>> Éxito de conexión");

            // Insertar el archivo familias.xml si es posible leerlo
            if (!XmlFamilia.canRead()) {
                System.err.println("\n>>> Error al intentar leer el documento familias.xml");
            } else {
                try {
                    // Crear un recurso y establecer su contenido con el archivo XML
                    Resource resource = col.createResource(XmlFamilia.getName(), "XMLResource");
                    resource.setContent(XmlFamilia);
                    // Almacenar el recurso en la colección
                    col.storeResource(resource);
                } catch (XMLDBException e) {
                    // Manejar el error en caso de excepción
                    System.err.println("\n>>> Error: " + e.getMessage());
                }
            }

            // Insertar el archivo proyectosFP.xml si es posible leerlo
            if (!XmlProyectosFP.canRead()) {
                System.err.println("\n>>> Error al intentar leer el documento proyectosFP.xml");
            } else {
                try {
                    // Crear un recurso y establecer su contenido con el archivo XML
                    Resource resource = col.createResource(XmlProyectosFP.getName(), "XMLResource");
                    resource.setContent(XmlProyectosFP);
                    // Almacenar el recurso en la colección
                    col.storeResource(resource);
                } catch (XMLDBException e) {
                    // Manejar el error en caso de excepción
                    System.err.println("\n>>> Error: " + e.getMessage());
                }
            }

            // Insertar el archivo CentrosCFGMyS.xml si es posible leerlo
            if (!XmlCentros.canRead()) {
                System.err.println("\n>>> Error al intentar leer el documento centrosCFGMyS.csv");
            } else {
                try {
                    // Crear un recurso y establecer su contenido con el archivo XML
                    Resource resource = col.createResource(XmlCentros.getName(), "XMLResource");
                    resource.setContent(XmlCentros);
                    // Almacenar el recurso en la colección
                    col.storeResource(resource);
                } catch (XMLDBException e) {
                    // Manejar el error en caso de excepción
                    System.err.println("\n>>> Error: " + e.getMessage());
                }
            }

            // Insertar el archivo proyectos.xml si es posible leerlo
            if (!XmlProyectos.canRead()) {
                System.err.println("\n>>> Error al intentar leer el documento proyectos.csv");
            } else {
                try {
                    // Crear un recurso y establecer su contenido con el archivo XML
                    Resource resource = col.createResource(XmlProyectos.getName(), "XMLResource");
                    resource.setContent(XmlProyectos);
                    // Almacenar el recurso en la colección
                    col.storeResource(resource);
                } catch (XMLDBException e) {
                    // Manejar el error en caso de excepción
                    System.err.println("\n>>> Error: " + e.getMessage());
                }
            }
        }
    }
}
