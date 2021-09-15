/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tfm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

/**
 *
 * @author Carlos
 */
public class Algoritmo {
    // Numero de variables

    private int nVariables;
    // Numero de operaciones no escalares (tamanio eficaz)
    private int L;
    // Tamanio de la poblacion
    private int tamPoblacion;
    // Tamanio del individuo
    private int tamIndividuo;
    // Rango minimo
    private double lower;
    // Rango maximo
    private double upper;
    // Ruta del fichero
    private String ruta;
    // Tipo de seleccion
    private int selection;
    // Tipo de cruce
    private int cross;
    // Tipo de mutacion
    private int mutation;
    // Tipo de reemplazamiento
    private int replacement;
    // Probabilidad de cruce
    private double crossProb;
    // Probabilidad de mutacion
    private double mutationProb;
    // Test de parada
    private int test;
    // Elitismo
    private boolean elitism;
    // Contador
    private int contador = 0;
    // K-Torneo
    private int kTorneo;
    // Variables que indicaran los individuos que han resultado elegidos en el proceso de seleccion
    private int father1;
    private int father2;
    // Variable que indicara el individuo elegido para mutar
    private int fatherM;
    // Variable que contendra la probabilidad aleatoria de cruce y mutacion
    private double probability;

    public Algoritmo() {
    }

    public void inicializar(PanelPrincipalBase vista) {
        this.cross = vista.getCross();
        this.crossProb = vista.getCrossProb();
        this.elitism = vista.getElitism();
        this.L = vista.getL();
        this.lower = vista.getLower();
        this.mutation = vista.getMutation();
        this.mutationProb = vista.getMutationProb();
        this.nVariables = vista.getnVariables();
        this.replacement = vista.getReplacement();
        this.ruta = vista.getRoute();
        this.selection = vista.getSelection();
        this.tamPoblacion = vista.getTamanioPoblacion();
        this.test = vista.getTest();
        this.upper = vista.getUpper();
        this.kTorneo = vista.getkTorneo();
    }

    public double ejecutar() {
        // Se crea antes de nada el SLP
        SLP straightLineProgram = new SLP(this.nVariables, this.L);
        // Aquí se tendra la estructura de datos representada en el diccionario llamado slp de nuestro SLP, el vector de valores de cada Uk
        // asi como del Output y una serie de funciones para manejar dicha estructura. En realidad, esta estructura va a ser una plantilla en
        // la que sustituir los valores de cada individuo de la poblacion del algoritmo genetico, y nos servira de patron a la hora de realizar
        // calculos sobre los individuos.
        // Ahora se calculara el numero de parametros totales para el SLP, que no es mas que calcular el tamaño que va tener cada
        // individuo de la poblacion (con sus valores)
        this.tamIndividuo = straightLineProgram.calculaParametros(this.nVariables, this.L);
        /////////////////////////////////////////////////////////////////////////////////////
        //////////////// Comienzo del Algoritmo Genetico ////////////////
        ////////////////////////////////////////////////////////////////////////////////////
        // 1) Generacion de una poblacion inicial
        // Seguidamente, con la informacion relativa al tamanio de la poblacion y del individuo, asi como del rango de valores que podran
        // tomar los parametros escalares, se generara una poblacion con valores generados aleatoriamente para cada individuo. La
        // poblacion no sera mas que un vector que almacena individuos
        Poblacion hominum = new Poblacion(this.tamPoblacion, this.tamIndividuo, this.L, this.lower, this.upper);
        // 2) Evaluacion de la poblacion inicial (calculo del fitness)
        hominum.calculaFitnessPoblacion(this.ruta, this.nVariables, straightLineProgram);

        // Aqui todos los individuos de la poblacion tienen asociado un valor fitness.
        // 3) Ejecucion de iteraciones
        while (contador < test) {
            // Creacion de una poblacion auxiliar
            Poblacion hominumAux = new Poblacion();
            // Contador de individuos de la nueva poblacion
            int nIndividuosNuevos = 0;
            // 3.1) Algoritmo elitista -> Se inserta el mejor
            if (this.elitism == true) {
                // El algoritmo es elitista y tenemos que meter en la poblacion
                // auxiliar
                // nueva el mejor
                if (nIndividuosNuevos >= this.tamPoblacion) {
                    break;// Continua;
                } else {
                    nIndividuosNuevos++;
                }
                hominumAux.getPopulation().add(hominum.mejorIndividuo());
            }
            // Se aplica el algoritmo general evolutivo mientras que la poblacion nueva auxiliar sea menor que la original

            Continua:
            while (hominumAux.getPopulation().size() < tamPoblacion) {
                // 3.2) Seleccion de padres
                switch (selection) {
                    case AlgoritmoConstantes.RULETA:
                        // Se obtienen los padres por seleccion por ruleta
                        father1 = hominum.ruleta();
                        father2 = hominum.ruleta();
                        fatherM = hominum.ruleta();
                        // Comprobacion de que los individuos padre son distintos
                        while (father1 == father2) {
                            father2 = hominum.ruleta();
                        }
                        break;
                    case AlgoritmoConstantes.K_TORNEO:
                        // Se obtienen los padres por seleccion por torneo
                        father1 = hominum.kTorneo(this.kTorneo);
                        father2 = hominum.kTorneo(this.kTorneo);
                        fatherM = hominum.kTorneo(this.kTorneo);
                        // Comprobacion de que los individuos padre son distintos
                        while (father1 == father2) {
                            father2 = hominum.kTorneo(this.kTorneo);
                        }
                        break;
                }
                // 3.3) Generacion de un numero al azar para la probabilidad de
                // cruce y mutacion
                probability = Utilidades.numerosAleatoriosEntre01();
                // 3.4) Determinar si se produce cruce
                if (probability <= this.crossProb) {
                    // 3.4.1) Creacion de los hijos
                    Individuo hijo1 = new Individuo();
                    Individuo hijo2 = new Individuo();
                    // 3.4.2) Determinar el tipo de cruce
                    switch (this.cross) {
                        case AlgoritmoConstantes.CRUCE_1_PUNTO:
                            hominum.cruce1Punto(hominum.getPopulation()
                                    .get(father1),
                                    hominum.getPopulation().get(father2), hijo1,
                                    hijo2);
                            break;
                        case AlgoritmoConstantes.CRUCE_1_PUNTO_SELECTIVO:
                            hominum.cruce1PuntoSelectivo(hominum.getPopulation()
                                    .get(father1),
                                    hominum.getPopulation().get(father2), hijo1,
                                    hijo2, straightLineProgram);
                            break;

                        case AlgoritmoConstantes.CRUCE_UNIFORME:
                            hominum.cruceUniforme(
                                    hominum.getPopulation().get(father1), hominum
                                    .getPopulation().get(father2), hijo1,
                                    hijo2);
                            break;
                        case AlgoritmoConstantes.CRUCE_UNIFORME_SELECTIVO:
                            hominum.cruceUniformeSelectivo(hominum.getPopulation()
                                    .get(father1),
                                    hominum.getPopulation().get(father2), hijo1,
                                    hijo2, straightLineProgram);
                            break;
                    }
                    // 3.4.3) Determinar el tipo de insercion
                    switch (this.replacement) {
                        case AlgoritmoConstantes.REPLACE_HIJOS:
                            if (nIndividuosNuevos >= tamPoblacion) {
                                break Continua;
                            } else {
                                nIndividuosNuevos++;
                            }
                            // Se inserta el primer hijo
                            hominumAux.getPopulation().add(hijo1);
                            if (nIndividuosNuevos >= tamPoblacion) {
                                break Continua;
                            } else {
                                nIndividuosNuevos++;
                            }
                            // Se inserta el segundo hijo
                            hominumAux.getPopulation().add(hijo2);
                            break;
                        case AlgoritmoConstantes.REPLACE_MEJORES:
                            // Poblacion Auxiliar
                            Poblacion k = new Poblacion();
                            k.getPopulation().add(
                                    hominum.getPopulation().get(father1));
                            k.getPopulation().add(
                                    hominum.getPopulation().get(father2));
                            k.getPopulation().add(hijo1);
                            k.getPopulation().add(hijo2);
                            k.calculaFitnessPoblacion(this.ruta, this.nVariables,
                                    straightLineProgram);

                            // Vector que almacenara los fitness de padres e hijos
                            Vector<Double> f = new Vector<Double>();
                            f.add(hominum.getPopulation().get(father1).getFitness());
                            f.add(hominum.getPopulation().get(father2).getFitness());
                            f.add(hijo1.getFitness());
                            f.add(hijo2.getFitness());
                            // Variable que almacenara el mejor de padres e hijos
                            double mejor;
                            mejor = Collections.min(f);
                            for (int i = 0; i < k.getPopulation().size(); i++) {
                                if (k.getPopulation().get(i).getFitness() == mejor)
;
                                {
                                    if (nIndividuosNuevos >= this.tamPoblacion) {
                                        break Continua;
                                    } else {
                                        nIndividuosNuevos++;
                                    }
                                    hominumAux.getPopulation().add(
                                            k.getPopulation().get(i));
                                }
                            }
                            // Eliminacion del vector de fitness el mejor
                            f.remove(mejor);
                            while (f.size() > 3) {
                                f.removeElement(f.lastElement());
                            }
                            // Calculo del mejor de entre el padre
                            mejor = Collections.min(f);
                            for (int i = 0; i < k.getPopulation().size(); i++) {
                                if (k.getPopulation().get(i).getFitness() == mejor)
;
                                {
                                    if (nIndividuosNuevos >= this.tamPoblacion) {
                                        break Continua;
                                    } else {
                                        nIndividuosNuevos++;
                                    }
                                    hominumAux.getPopulation().add(
                                            k.getPopulation().get(i));
                                }
                            }
                            break;
                    }
                }

                // 3.5) Determinar si se produce mutacion
                if (probability <= this.mutationProb) {
                    // 3.5.1) Determinar el tipo de mutacion
                    switch (mutation) {
                        case AlgoritmoConstantes.MUTACION_NORMAL:
                            // Se muta el individuo
                            hominum.mutacion(hominum.getPopulation().get(fatherM));
                            if (nIndividuosNuevos >= this.tamPoblacion) {
                                break Continua;
                            } else {
                                nIndividuosNuevos++;
                            }
                            // Se inserta en la nueva poblacion
                            hominumAux.getPopulation().add(
                                    hominum.getPopulation().get(fatherM));
                            break;
                        case AlgoritmoConstantes.MUTACION_SESGADA:
                            // Se muta el individuo
                            hominum.mutacionSesgada(hominum.getPopulation().get(
                                    fatherM));

                            if (nIndividuosNuevos >= this.tamPoblacion) {
                                break Continua;
                            } else {
                                nIndividuosNuevos++;
                            }
                            // Se inserta en la nueva poblacion
                            hominumAux.getPopulation().add(
                                    hominum.getPopulation().get(fatherM));
                            break;
                    }
                }
            }
            int diferencia = 0;
            if (hominumAux.getPopulation().size() > hominum.getPopulation()
                    .size()) {
                diferencia = hominumAux.getPopulation().size()
                        - hominum.getPopulation().size();
            }
            for (int i = 0; i < diferencia; i++) {
                hominumAux.getPopulation().remove(i);
            }
            hominumAux.calculaFitnessPoblacion(this.ruta, this.nVariables,
                    straightLineProgram);

            // Se aumenta el contador de generaciones
            contador++;
            // Se asigna la poblacion auxiliar nueva a la anterior
            hominum = hominumAux;
        }

        // Se obtiene el mejor individuo de la poblacion final
        Individuo mejor = hominum.mejorIndividuo();
        // Se muestra el error final cometido en el algoritmo, que no es mas que
        // el
        // fitness de ese mejor individuo
        System.out.println("Fitness del Mejor Individuo (Error cometido) : ");
        System.out.println(mejor.getFitness());
        System.out.println(Arrays.toString(mejor.getOperacionEscalar().toArray()));
        System.out.println(Arrays.toString(mejor.getParametrosReales().toArray()));
        
        
        
        System.out.println("Presione una tecla para finalizar.");
        // Generacion de graficas si el ejercicio tiene 1 o 2 variables
        if ((this.nVariables == 1) || (this.nVariables == 2)) {
            generaSalida(straightLineProgram, mejor);
        }
        return mejor.getFitness();
    }

    public void generaSalida(SLP slp, Individuo ind) {
        String rutaRelativa = Utilidades.conseguirRutaRelativa(this.ruta);
        try {
            // Puntos muestra
            FileReader file = new FileReader(this.ruta);
            BufferedReader fileStream = new BufferedReader(file);
            // Puntos Salida
            // Ahora se abre el archivo con esa ruta
            PrintWriter puntosMuestraSalida = new PrintWriter(rutaRelativa
                    + "p_salida.dat");
            if (fileStream.ready()) {
                // Se procesa la primera componente
                String temp = fileStream.readLine();
                while (temp != null) {
                    temp = temp.replaceAll("\t", " ");
                    // Vector que almacenara las cada conjunto de componentes
                    Vector<Double> componentes = new Vector<Double>();
                    // Variable que recogera el output del individuo i asociado
                    // a Ui
                    double output;
                    String[] contenido = temp.split(" ");
                    String salida = "";
                    for (int j = 0; j < nVariables; j++) {
                        componentes.add(j, Double.parseDouble(contenido[j]));
                        salida = salida + contenido[j] + "\t";
                    }
                    output = slp.calculaOutput(componentes,
                            ind.getOperacionEscalar(),
                            ind.getParametrosReales());
                    // Se escribe el valor del outut (que es el valor
                    // aproximado)
                    salida = salida + output;
                    puntosMuestraSalida.println(salida);
                    // Se lee el siguiente conjunto de puntos
                    temp = fileStream.readLine();
                }
                fileStream.close();
                puntosMuestraSalida.flush();
                puntosMuestraSalida.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Poblacion redimensiona(Poblacion original, Poblacion auxiliar) {
        return original;
    }
}
