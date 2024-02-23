package Code;

import com.aspose.cells.Workbook;

public class ConvCSVtoXML {

    // Metodo para pasar de CSV a XML utilizando la dependencia AsposeJavaAPI
    public static void conversion() {

        try {
            // Creamos el workbook de cada csv y luego lo guardamos donde queremos que se situe
            Workbook workbook = new Workbook("src/main/resources/CentrosCFGMyS.csv");
            Workbook workbook2 = new Workbook("src/main/resources/proyectos.csv");

            // Nombre y ruta donde se van a guardar.
            workbook.save("src/main/resources/CentrosCFGMyS.xml");
            workbook2.save("src/main/resources/proyectos.xml");

        } catch (Exception e) {
            // Manejar el error.
            System.err.println("\n>>> Error al realizar la conversión de CSV a XML.");
        }

    }

}
