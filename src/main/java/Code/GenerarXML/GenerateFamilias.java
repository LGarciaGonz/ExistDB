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

public class GenerateFamilias {

    // Variable estática para almacenar la colección XML
    static Collection col = null;

    // Método para cargar datos desde una base de datos XML y generar un archivo XML
    public static void CargarDatos() {

        // Conectar a la colección XML
        col = CollectionConexion.conectar();

        // Consulta XQuery para extraer datos de un archivo y estructurarlos en otro archivo XML
        String Consulta = "let $b := doc(\"familias.xml\")\n" +
                "return\n" +
                "<familias>\n" +
                "{\n" +
                "    for $a in $b//option\n" +
                "    return\n" +
                "        <familia>\n" +
                "            <nombre>{ $a/data() }</nombre>\n" +
                "            <codigo>{$a/@value}</codigo>\n" +
                "        </familia>\n" +
                "}\n" +
                "</familias>";

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
                FileWriter fw = new FileWriter("target/Familias.xml");
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
                    StreamResult result1 = new StreamResult(new File("target/Familias.xml"));

                    // Configurar propiedades de salida para la transformación XML
                    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    transformer.setOutputProperty("{http:xml.apache.org/xslt}indent-amount", "4");

                    // Realizar la transformación XML y escribir el resultado en el archivo
                    transformer.transform(source, result1);
                } catch (TransformerException e) {
                    throw new RuntimeException(e);
                }

                // Mensaje de éxito
                System.out.println("\t\n>>> Datos FAMILIAS generados");

                // Cerrar la conexión con la colección
                col.close();

            } catch (XMLDBException e) {
                // Capturar excepción relacionada con la base de datos XML
                System.out.println("\n>>> ERROR AL CONSULTAR DOCUMENTO.");
                e.printStackTrace();
            } catch (IOException e) {
                // Capturar excepción relacionada con operaciones de entrada/salida (IO)
                System.err.println("\n>>> Error: " + e.getMessage());
            }
        }
    }

}
