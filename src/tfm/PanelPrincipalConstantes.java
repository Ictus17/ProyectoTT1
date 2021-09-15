/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tfm;

/**
 *
 * @author Carlos
 */
class PanelPrincipalConstantes {

    /**
     * Variable que notificará a la clase principal cuando el algoritmo está
     * listo para ejecutarse
     */
    public static final String ALGORITMO_LISTO = "readyForGenetic";
    public static final String TITULO_PRINCIPAL = "Regresión Simbólica";
    public static final String RESUMEN_PRINCIPAL = "Este proyecto aborda la Regresion Simbólica mediante algoritmos"
            + " genéticos. Se manejaran para la estructura de datos dos conjuntos: \n"
            + "\t - V (numero de variables)\n"
            + "\t - F (operaciones): viene predefinido y manejará las operaciones F = {+, -, /, *}.\n\n"
            + "Para empezar a trabajar vaya rellenando y seleccionando los parámetros que desee para la realización del problema:";
    public static final String NUMERO_VARIABLES = "Nº. variables:";
    public static final String NUMERO_MAXIMO_OPERACIONES = "Nº. máximo de operaciones distintas de {+, -}:";
    public static final String TAMANIO_POBLACION = "Tamaño población:";
    public static final String RANGO_MIN_MAX = "Rango [min, max]";
    public static final String RANGO_VALORES1 = "Rango [min, max] de valores que podrán tomar los parámetros escalares de cada individuo" + " de la población: [";
    public static final String RANGO_VALORES2 = " , ";
    public static final String RANGO_VALORES3 = " ]";
    public static final String RUTA_PUNTOS_MUESTRA = "Ruta del fichero de los puntos muestra originales:";
    public static final String RUTA_PUNTOS_MUESTRA_ABREVIADO = "Ruta del fichero";
    public static final String TIPO_SELECCION = "Defina el tipo de selección que se aplicará a los individuos de la "
            + "población para su evolución:";
    public static final String K_TORNEO_SELECCION = "Defina el valor de K (< Tamaño Población):";
    public static final String K_TORNEO = "K-Torneo";
    public static final String RULETA = "Ruleta";
    public static final String TIPO_CRUCE = "Defina el tipo de cruce al que se someterán los individuos de la población:";
    public static final String CRUCE_1_PUNTO = "Cruce en 1 punto";
    public static final String CRUCE_1_PUNTO_SELECTIVO = "Cruce en 1 punto selectivo";
    public static final String CRUCE_UNIFORME = "Cruce uniforme";
    public static final String CRUCE_UNIFORME_SELECTIVO = "Cruce uniforme selectivo";
    public static final String TIPO_MUTACION = "Escoja el tipo de mutación que sufrirán los individuos de la población:";

    public static final String MUTACION_NORMAL = "Mutación normal (igual probabilidad)";
    public static final String MUTACION_SESGADA = "Mutación sesgada (distinta probabilidad)";
    public static final String TIPO_REEMPLAZAMIENTO = "Establezca el tipo de reemplazamiento que se llevará a cabo en la población:";
    public static final String REEMPLAZAMIENTO_HIJOS = "Siempre se eligen los hijos";
    public static final String REEMPLAZAMIENTO_MEJORES = "Se escogen los mejores entre padres e hijos";
    public static final String PROBABILIDAD_CRUCE = "Probabilidad de cruce (entre [0, 1]):";
    public static final String PROBABILIDAD_MUTACION = "Probabilidad de mutación (entre [0, 1]):";
    public static final String ALGORITMO_ELITISTA = "¿El algoritmo genético es Elitista? El mejor individuo de la población se introduce" + "en cada generación/iteración:";
    public static final String TEST_PARADA = "Establezca el test de parada (nº de iteraciones) del algoritmo genético:";
    public static final String TEST_PARADA_ABREVIADO = "Test Parada";
    public static final String TEST_RESULTADO_ALGORITMO = "El algoritmo genético ha obtenido el mejor individuo con valor fitness(error cometido): ";
    /////////////////////////////////////////////////////
    /////////////// Constantes Algoritmo ////////////////
    /////////////////////////////////////////////////////

    /**
     * Constantes para el cruce
     */
    public static final int CROSS_1_PUNTO = 1;
    public static final int CROSS_1_PUNTO_SELECTIVO = 2;
    public static final int CROSS_UNIFORME = 3;
    public static final int CROSS_UNIFORME_SELECTIVO = 4;
    /**
     * Constantes para el tipo de seleccion
     */
    public static final int SELECTION_RULETA = 1;
    public static final int SELECTION_K_TORNEO = 2;
    /**
     * Constantes para el tipo de mutacion
     */
    public static final int MUTATION_NORMAL = 1;
    public static final int MUTATION_SESGADA = 2;
    /**
     * Constantes para reemplazo de indiviuos
     */
    public static final int REPLACE_HIJOS = 1;
    public static final int REPLACE_MEJORES = 2;
    /**
     * Comando para mostrar las gráficas a traves del GNUPlot
     */
    public static final String COMANDO_GNUPLOT = "/gnuplot/gnuplot";

}
