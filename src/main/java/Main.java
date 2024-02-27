import Code.ConvCSVtoXML;
import Code.GenerarXML.GenerateCentros;
import Code.GenerarXML.GenerateFamilias;
import Code.GenerarXML.GenerateProyectos;
import Code.InsertarXML;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // DECLARACIONES
        Scanner sc = new Scanner(System.in);
        boolean salir = false;
        String opcion = "";

        // BUCLE PARA MOSTRAR EL MENÚ DE OPCIONES
        while (!salir) {

            System.out.println("""
                    \n----------------------------------------
                    1. Insertar archivos a la colección.
                    2. Generar archivos XML.
                    0. Salir
                    ----------------------------------------""");

            opcion = sc.nextLine();                                                                                 // Leer y guardar la opción del usuario.

            // ESTRUCTURA PARA LA LLAMADA A LOS MÉTODOS
            switch (opcion) {
                case "0" -> salir = true;

                case "1" -> {
                    ConvCSVtoXML.conversion();
                    InsertarXML.insertXML();
                }

                case "2" -> {
                    GenerateCentros.CargarDatos();
                    GenerateFamilias.CargarDatos();
                    GenerateProyectos.CargarDatos();
                }

                default ->
                        System.err.println("\n>>> OPCIÓN NO VÁLIDA: Introduzca una opción del menú");             // Informar al usuario de un error cometido.

            }
        }
    }
}