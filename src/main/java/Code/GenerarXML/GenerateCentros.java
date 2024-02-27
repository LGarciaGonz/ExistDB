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

public class GenerateCentros {

    // Declaramos una variable estática para almacenar la colección XML
    static Collection col = null;

    // Método para cargar los datos desde una base de datos XML y generar un archivo XML
    public static void CargarDatos() {
        // Conectarse a la colección XML
        col = CollectionConexion.conectar();

        // Consulta XQuery para extraer datos de un archivo y estructurarlos en otro archivo XML
        String Consulta = "declare namespace ss=\"urn:schemas-microsoft-com:office:spreadsheet\";\n" +
                "<Centros>{\n" +
                "    for $xml in doc(\"CentrosCFGMyS.xml\")//ss:Row\n" +
                "    return\n" +
                "    <Centro>\n" +
                "        <nombre>{$xml/ss:Cell[6]/ss:Data/data()}</nombre>\n" +
                "        <codigo>{$xml/ss:Cell[4]/ss:Data/data()}</codigo>\n" +
                "        <web>{$xml/ss:Cell[11]/ss:Data/data()}</web>\n" +
                "        <correoElectronico>{$xml/ss:Cell[10]/ss:Data/data()}</correoElectronico>\n" +
                "    </Centro>}\n" +
                "</Centros>";

        if (col != null) {
            try {
                // Realizar la consulta XQuery
                XPathQueryService facturasCod1;
                facturasCod1 = (XPathQueryService) col.getService("XPathQueryService", "3.0");

                // Configurar propiedades de formato
                col.setProperty("indent", "yes");
                facturasCod1.setProperty("indent", "yes");

                // Ejecutar la consulta y obtener el resultado
                ResourceSet result = facturasCod1.query(Consulta);
                ResourceIterator i;
                i = result.getIterator();

                // Verificar si hay resultados
                if (!i.hasMoreResources()) {
                    System.out.println(">>> LA CONSULTA NO DEVUELVE NADA O ESTÁ MAL ESCRITA");
                }

                // Escribir los resultados en un archivo XML
                FileWriter fw = new FileWriter("target/Centros.xml");
                Resource r = null;
                while (i.hasMoreResources()) {
                    r = i.nextResource();
                    fw.write(r.getContent().toString());
                }
                fw.close();

                // Formatear el archivo XML generado
                try {

                    Transformer transformer = TransformerFactory.newInstance().newTransformer();

                    // Leer el contenido del recurso XML como fuente
                    Source source = new StreamSource(new StringReader(r.getContent().toString()));
                    StreamResult result1 = new StreamResult(new File("target/Centros.xml"));

                    // Configurar propiedades de salida del transformador
                    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    transformer.setOutputProperty("{http:xml.apache.org/xslt}indent-amount", "4");

                    // Realizar la transformación y escribir el resultado en el archivo
                    transformer.transform(source, result1);

                } catch (TransformerException e) {
                    System.err.println("\n>>> Error en la transformación.");
                }

                // Mensaje de éxito
                System.out.println("\t\n>>> Datos CENTROS generados");

                // Cerrar la conexión con la colección
                col.close();
            } catch (XMLDBException e) {

                // Capturar excepción relacionada con la base de datos XML
                System.err.println("\n>>> ERROR AL CONSULTAR DOCUMENTO.");
                e.printStackTrace();

            } catch (IOException e) {
                // Capturar excepción relacionada con operaciones de entrada/salida (IO)
                System.err.println("\n>>> Error: " + e.getMessage());
            }
        }
    }
}