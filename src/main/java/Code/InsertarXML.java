package Code;

import Conexiones.CollectionConexion;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;

import java.io.File;

public class InsertarXML {

    static Collection col = null;

    //Metodo para insertar los datos Xml a la colecion de ExistDb
    public static void insertXML() {

        File XmlFamilia = new File("src/main/resources/familias.xml");
        File XmlProyectosFP = new File("src/main/resources/proyectosFP.xml");
        File XmlCentros = new File("src/main/resources/CentrosCFGMyS.xml");
        File XmlProyectos = new File("src/main/resources/proyectos.xml");

        col = CollectionConexion.conectar();

        if (col != null) {

            //Manera de insertar el xml
            System.out.println("\t>>> Éxito de conexión");

            // Comprobar que el archivo familias.xml pueda leerse.
            if (!XmlFamilia.canRead()) {
                System.err.println("\n>>> Error al intentar leer el documento familias.xml");
            } else {

                try {
                    Resource resource = col.createResource(XmlFamilia.getName(), "XMLResource");
                    resource.setContent(XmlFamilia);
                    col.storeResource(resource);
                } catch (XMLDBException e) {
                    // Manejar el error.
                    System.err.println("\n>>> Error: " + e.getMessage());
                }

            }

            // Comprobar que el archivo proyectosFP.xml pueda leerse.
            if (!XmlProyectosFP.canRead()) {

                System.err.println("\n>>> Error al intentar leer el documento proyectosFP.xml");

            } else {

                try {
                    Resource resource = col.createResource(XmlProyectosFP.getName(), "XMLResource");
                    resource.setContent(XmlProyectosFP);
                    col.storeResource(resource);
                } catch (XMLDBException e) {
                    // Manejar el error.
                    System.err.println("\n>>> Error: " + e.getMessage());
                }

            }

            // Comprobar que el archivo CentrosCFGMyS.xml pueda leerse.
            if (!XmlCentros.canRead()) {

                System.err.println("\n>>> Error al intentar leer el documento centrosCFGMyS.csv");

            } else {

                try {
                    Resource resource = col.createResource(XmlCentros.getName(), "XMLResource");
                    resource.setContent(XmlCentros);
                    col.storeResource(resource);
                } catch (XMLDBException e) {
                    // Manejar el error.
                    System.err.println("\n>>> Error: " + e.getMessage());
                }

            }

            // Comprobar que el archivo proyectos.xml pueda leerse.
            if (!XmlProyectos.canRead()) {

                System.err.println("\n>>> Error al intentar leer el documento proyectos.csv");

            } else {

                try {
                    Resource resource = col.createResource(XmlProyectos.getName(), "XMLResource");
                    resource.setContent(XmlProyectos);
                    col.storeResource(resource);
                } catch (XMLDBException e) {
                    // Manejar el error.
                    System.err.println("\n>>> Error: " + e.getMessage());
                }

            }
        }
    }
}
