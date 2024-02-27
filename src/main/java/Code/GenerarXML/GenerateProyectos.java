package Code.GenerarXML;

import Conexiones.CollectionConexion;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XPathQueryService;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

public class GenerateProyectos {

    // Variable estática para almacenar la colección XML
    static Collection col = null;

    // Método para cargar datos desde una base de datos XML y generar un archivo XML
    public static void CargarDatos() {

        // Conectar a la colección XML
        col = CollectionConexion.conectar();

        // Consulta XQuery para extraer datos de un archivo XML y estructurarlos en otro archivo XML
        String Consulta = "let $xml := doc(\"proyectosFP.xml\")\n" +
                "return\n" +
                "<Proyectos>{\n" +
                "    for $row in $xml//Row\n" +
                "    return\n" +
                "        <Proyecto>\n" +
                "            <CENTRO_COORDINADOR>{data($row/CENTROCOORDINADOR)}</CENTRO_COORDINADOR>\n" +
                "            <TÍTULO_DEL_PROYECTO>{data($row/TÍTULODELPROYECTO)}</TÍTULO_DEL_PROYECTO>\n" +
                "            <FECHA_INICIO>{data($row/AUTORIZACIÓN)}</FECHA_INICIO>\n" +
                "            <FECHA_FIN>{data($row/CONTINUIDAD)}</FECHA_FIN>\n" +
                "            <USER_MANAGER>{data($row/COORDINACIÓN)}</USER_MANAGER>\n" +
                "            <USER_EMAIL>{data($row/CONTACTO)}</USER_EMAIL>\n" +
                "            <CENTROS_ANEXIONADOS>{data($row/CENTROSANEXIONADOS)}</CENTROS_ANEXIONADOS>\n" +
                "        </Proyecto>\n" +
                "}</Proyectos>";

        if (col != null) {
            try {
                // Realizar la consulta XQuery
                XPathQueryService facturasCod1;
                facturasCod1 = (XPathQueryService) col.getService("XPathQueryService", "3.0");

                // Establecer propiedades para la salida XML
                col.setProperty("indent", "yes");
                facturasCod1.setProperty("indent", "yes");

                // Ejecutar la consulta XQuery y obtener el resultado
                ResourceSet result = facturasCod1.query(Consulta);
                ResourceIterator i;
                i = result.getIterator();

                // Verificar si hay resultados
                if (!i.hasMoreResources()) {
                    System.err.println(">>> LA CONSULTA NO DEVUELVE NADA O ESTÁ MAL ESCRITA");
                }

                // Escribir los resultados en un archivo XML
                FileWriter fw = new FileWriter("target/Proyectos.xml");
                Resource r = null;
                while (i.hasMoreResources()) {
                    r = i.nextResource();
                    fw.write(r.getContent().toString());
                }
                fw.close();

                // Formatear el archivo XML generado
                try {
                    Transformer transformer = TransformerFactory.newInstance().newTransformer();
                    Source source = new StreamSource(new StringReader(r.getContent().toString()));
                    StreamResult result1 = new StreamResult(new File("target/Proyectos.xml"));

                    // Configurar propiedades de salida para la transformación XML
                    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    transformer.setOutputProperty("{http:xml.apache.org/xslt}indent-amount", "4");

                    // Realizar la transformación XML y escribir el resultado en el archivo
                    transformer.transform(source, result1);
                } catch (TransformerException e) {
                    System.err.println("\n>>> Error en la transformación.");
                }

                // Mensaje de éxito
                System.out.println("\t\n>>> Datos PROYECTOS generados");

                // Cerrar la conexión con la colección
                col.close();

            } catch (XMLDBException e) {
                // Capturar excepción relacionada con la base de datos XML
                System.err.println("\n>>> ERROR AL CONSULTAR DOCUMENTO.");
                e.printStackTrace();
            } catch (ClassCastException e) {
                // Capturar excepción si hay un problema al castear tipos
                System.err.println(">>> Error: No se ha podido castear");
            } catch (IOException e) {
                // Capturar excepción relacionada con operaciones de entrada/salida (IO)
                System.out.println("\n>>> Error: " + e.getMessage());
            }
        }
    }
}