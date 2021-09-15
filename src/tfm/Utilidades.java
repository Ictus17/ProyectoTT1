/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tfm;

import java.io.File;
import java.util.Vector;

/**
 *
 * @author Carlos
 */
public class Utilidades {

    static public double numerosAleatoriosEntre01() {
        return Math.random();
    }

    public static double numerosAleatoriosEntreAB(double limiteInferior, double limiteSuperior) {
        return (double) (limiteSuperior * Math.random()) + limiteInferior;
    }

    public static void rellenaVector(Vector<Double> vector, int size) {
        for (int i = 0; i < size; i++) {
            vector.add(0.0);
        }
    }

    public static String conseguirRutaRelativa(String ruta) {
        File file = new File(ruta);
//create bufferreader to wrap the file
        String path = file.getAbsolutePath();
        String filePath = path.substring(0, path.lastIndexOf(File.separator) + 1);
        return filePath;
    }
}
