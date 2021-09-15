/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tfm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;
import java.util.Vector;

public class Poblacion {
    // Vector que albergara a la poblacion

    Vector<Individuo> population = null;

    public Poblacion() {
        population = new Vector<Individuo>();
    }

    public Poblacion(int tamanioPob, int tamanioInd, int L, double limInf, double limSup) {
        population = new Vector<Individuo>(tamanioPob);
        for (int i = 0; i < tamanioPob; i++) {
            // Se llama al constructor de individuos para construir individuos aleatorios
            Individuo ind = new Individuo(tamanioInd, L, limInf, limSup);
            population.add(i, ind);
        }
    }

    public void calculaFitnessPoblacion(String ruta, int nVariables, SLP straightLineProgram) {
        // Ahora hay que calcular el valor fitness de cada individuo de la poblacion, y
        // para ello es necesario leer todos los valores de los puntos muestra del
        // fichero para cada uno de ellos
        // La variable x leera las componentes
        // La variable f leera el valor de la funcion
        double x;
        double f;
        for (int i = 0; i < (int) population.size(); i++) {
            try {
                // Ahora se abre el archivo con esa ruta
                FileReader file = new FileReader(ruta);
                BufferedReader fileStream = new BufferedReader(file);
                if (fileStream.ready()) {
                    // Vector que recoge el numero total de puntos muestra
                    int nPuntosMuestra = 0;
                    // Valor que recogera el valor fitness del individuo i
                    double valorFitness = 0;
                    // Se procesa la primera componente
                    String temp = fileStream.readLine();
                    while (temp != null) {
                        // Vector que almacenara cada conjunto de componentes
                        Vector<Double> componentes = new Vector<Double>();
                        // Variable que recogera el output del individuo i asociado a Ui
                        double output;
                        String[] contenido = temp.split("\\s+");
                        
                        
                        for (int j = 0; j < nVariables; j++) {
                            componentes.add(j, Double.parseDouble(contenido[j]));
                        }
                        // Procesamos el valor de la funcion en esos puntos
                        f = Double.parseDouble(contenido[contenido.length - 1]);
                        output = straightLineProgram.calculaOutput(componentes, population.get(i).getOperacionEscalar(),
                                population.get(i).getParametrosReales());
                        // Se actualiza el valor del fitnes
                        double t = output - f;
                        valorFitness += Math.pow(t, 2);
                        // Incrementamos el numero de puntos muestra leidos
                        nPuntosMuestra++;
                        // Se lee el siguiente conjunto de puntos
                        temp = fileStream.readLine();
                    }
                    // Se calcula el valor final del fitness diviendolo entre el numero de
                    // puntos muestra y se asocia al individuo i
                    valorFitness /= nPuntosMuestra;
                    // Se asigna dicho valor fitness al individuo
                    population.get(i).setFitness(valorFitness);
                    fileStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("No se ha podido abrir el archivo o ha ocurrido algun error");
            }
        }
    }

    public Individuo mejorIndividuo() {
        // Vector que almacenara los fitness de los individuos
        Vector<Double> fitnessIndividuos = new Vector<Double>();
        Individuo mejor = new Individuo();
        for (int i = 0; i < (int) population.size(); i++) {
            fitnessIndividuos.add(population.get(i).getFitness());
        }
        double mejorFitness;
        // Se busca el mejor fitness del vector
        mejorFitness = Collections.min(fitnessIndividuos);
        for (int i = 0; i < (int) population.size(); i++) {
            if (mejorFitness == population.get(i).getFitness()) {
                mejor = population.get(i);
            }
        }
        return mejor;
    }

    public Vector<Individuo> getPopulation() {
        return population;
    }

    public int ruleta() {
        // Se calcula la suma de todos los fitness de los indivudos, que se sera un valor
        // al que se llamara F
        double F = 0.0;
        int elegido = 0;;
        for (int i = 0; i < (int) population.size(); i++) {
            F = F + population.get(i).getFitness();
        }
        // Se crea un vector de valores que albergaran para cada individuo i, un valor
        // F - fitness(i)
        Vector<Double> fitnessAux = new Vector<Double>();
        for (int i = 0; i < (int) population.size(); i++) {
            fitnessAux.add(F - population.get(i).getFitness());
        }
        // Es necesario establecer este "nuevo fitness" porque el método de la ruleta
        // tradicional, supone que a mayor valor del fitness, mejor es el individuo.
        // Sin embargo en nuestro problema, como el fitness es el error, lo que ocurre
        // es que los mejores individuos son los de fitness más pequeños. Esa es la razón
        // de calcular un fitnes_aux. El fitness_aux sí que verifica que cuanto mayor es,
        // mejor es el individuo. Así pues, se aplica el método de la ruleta pero
        // considerando el fitness_aux
        // Se calcula la suma de todos los fitness_aux de los indivudos, que se sera un
        // valor al que se llamara F_aux
        double FAux = 0.0;
        for (int i = 0; i < (int) fitnessAux.size(); i++) {
            FAux = FAux + fitnessAux.get(i);
        }
        // Se genera un numero al azar entre 0 y F
        double aleatorio = Utilidades.numerosAleatoriosEntreAB(0, FAux);
        // Se recorre de nuevo la poblacion acumulando los fitness. En el momento en que
        // la suma sea igual o mayor que el numero aleatorio, ese es el individuo elegido
        double contador = 0.0;
        for (int i = 0; i < (int) population.size(); i++) {
            contador = contador + fitnessAux.get(i);
            if (contador >= aleatorio) {
                elegido = i;
                break;
            }
        }
        return elegido;
    }

    public int kTorneo(int k) {
        // Se generan al azar K numeros de entre el tamaño de la poblacion, donde el
        // numero i generado estara asociado al individuo i de la poblacion
        double seleccionado = 0.0;
        int elegido = 0;
        int k_torneo = k;
        int aux;
        // El vector almacenara los indices de los Individuos seleccionables de la
        // poblacion
        Vector<Integer> seleccionables = new Vector<Integer>();
        for (int i = 0; i < k_torneo; i++) {
            // Se generan K numeros al azar
            aux = (int) Utilidades.numerosAleatoriosEntreAB(0, population.size() - 1);
            // Los insertamos en el vector
            seleccionables.add(aux);
        }
        Vector<Double> fitnessSeleccionables = new Vector<Double>();
        for (int i = 0; i < k_torneo; i++) {
            fitnessSeleccionables.add(population.get(seleccionables.get(i)).getFitness());
        }
        // Se elige el mejor de esos, que sera el que tenga menor fitness asociado
        for (int i = 0; i < (int) fitnessSeleccionables.size() - 1; i++) {
            seleccionado = Math.min(fitnessSeleccionables.get(i), fitnessSeleccionables.get(i + 1));
        }
        for (int i = 0; i < (int) seleccionables.size(); i++) {
            if (population.get(seleccionables.get(i)).getFitness() == seleccionado) {
                elegido = seleccionables.get(i);
                break;
            }
        }
        return elegido;
    }

    public void cruce1Punto(Individuo padre1, Individuo padre2, Individuo hijo1, Individuo hijo2) {
        // Se genera un numero al azar para calcular el punto de cruce de los
        // parametros booleanos de los individuos
        int tamanioBool = padre1.getOperacionEscalar().size();
        int puntoCruceBooleanos = (int) Utilidades.numerosAleatoriosEntreAB(0, tamanioBool);
        // Se cruzan ambos parametros de los individuos padre
        for (int i = 0; i < puntoCruceBooleanos; i++) {
            hijo1.getOperacionEscalar().add(padre1.getOperacionEscalar().get(i));
            hijo2.getOperacionEscalar().add(padre2.getOperacionEscalar().get(i));
        }
        for (int i = puntoCruceBooleanos; i < tamanioBool; i++) {
            hijo1.getOperacionEscalar().add(padre2.getOperacionEscalar().get(i));
            hijo2.getOperacionEscalar().add(padre1.getOperacionEscalar().get(i));
        }
        // Se genera un numero al azar para calcular el punto de cruce de los
        // parametros reales de los individuos
        int tamanio_reales = padre1.getParametrosReales().size();
        int puntoCruceReales = (int) Utilidades.numerosAleatoriosEntreAB(0, tamanio_reales);
        // Se cruzan ambos parametros de los individuos padre
        for (int i = 0; i < puntoCruceReales; i++) {
            hijo1.getParametrosReales().add(padre1.getParametrosReales().get(i));
            hijo2.getParametrosReales().add(padre2.getParametrosReales().get(i));
        }
        for (int i = puntoCruceReales; i < tamanio_reales; i++) {
            hijo1.getParametrosReales().add(padre2.getParametrosReales().get(i));
            hijo2.getParametrosReales().add(padre1.getParametrosReales().get(i));
        }

    }

    public void cruce1PuntoSelectivo(Individuo padre1, Individuo padre2, Individuo hijo1, Individuo hijo2, SLP straightLineProgram) {
        // Para los parametros booleanos la manera de cruce es exactamente igual al cruce
        // no selectivo
        // Se genera un numero al azar para calcular el punto de cruce de los
        // parametros booleanos de los individuos
        int tamanioBool = padre1.getOperacionEscalar().size();
        int puntoCruceBooleanos = (int) Utilidades.numerosAleatoriosEntreAB(0, tamanioBool);
        // Se cruzan ambos parametros de los individuos padre
        for (int i = 0; i < puntoCruceBooleanos; i++) {
            hijo1.getOperacionEscalar().add(padre1.getOperacionEscalar().get(i));
            hijo2.getOperacionEscalar().add(padre2.getOperacionEscalar().get(i));
        }
        for (int i = puntoCruceBooleanos; i < tamanioBool; i++) {
            hijo1.getOperacionEscalar().add(padre2.getOperacionEscalar().get(i));
            hijo2.getOperacionEscalar().add(padre1.getOperacionEscalar().get(i));
        }
        // Se aplica ahora el cruce selectivo, diferente al cruce no selectivo
        // Se genera un numero al azar para calcular el punto de cruce de los
        // parametros reales de los individuos
        int tamanioTotalReales = padre1.getParametrosReales().size();
        // Los ultimos parametros reales corresponden al output y da igual
        // como se crucen tambien, por lo que solo interesa hacer cruce selectivo
        // en los Ui's, y habra que calcular los parametros que tendran
        int tamanioOutput = straightLineProgram.tamanioUi(straightLineProgram.getSlp().size());
        int tamanioRealesUi = tamanioTotalReales - tamanioOutput;
        int puntoCruceReales = (int) Utilidades.numerosAleatoriosEntreAB(0, tamanioTotalReales);

        if (puntoCruceReales < tamanioRealesUi) {
            // El punto de cruce cae en algun Ui y hay que actualizarlo aplicando
            // el cruce selectivo
            // Ahora es necesario saber en que Ui se encuentra el punto generado
            int index1 = 0;
            int index2 = 0;
            for (int i = 1; i <= straightLineProgram.getUk().size(); i++) {
                index2 += straightLineProgram.tamanioUi(i);
                if ((index1 < puntoCruceReales) && (puntoCruceReales < index2)) {
                    // Se necesita una referencia para saber en que parte del Ui cae el punto
                    // generado, en este caso sera la mitad del Ui con el valor del indice
                    int mitadUi = index1 + (straightLineProgram.tamanioUi(i) / 2);
                    if (puntoCruceReales < mitadUi) {
                        // Esta partiendo una ristra de alphas
                        if ((mitadUi - puntoCruceReales) < (puntoCruceReales - index1)) // El punto esta mas cerca del inicio de la ristra de betas que del
                        // inicio de la ristra de alphas
                        {
                            puntoCruceReales = mitadUi;
                        } else {
                            puntoCruceReales = index1;
                        }
                    }
                    if (puntoCruceReales > mitadUi) {
                        // Esta partiendo una ristra de betas
                        if ((index2 - puntoCruceReales) < (puntoCruceReales - mitadUi)) // El punto esta mas cerca del inicio de la ristra de alphas que del
                        // inicio de la ristra de betas
                        {
                            puntoCruceReales = index2;
                        } else {
                            puntoCruceReales = mitadUi;
                        }
                    }
                    break;
                } else {
                    index1 += straightLineProgram.tamanioUi(i);
                }
            }
        }
        // El punto de cruce cae en los parametros correspondientes al output y
        // el cruce seria normal
        // Se cruzan ambos parametros de los individuos padre
        for (int i = 0; i < puntoCruceReales; i++) {
            hijo1.getParametrosReales().add(padre1.getParametrosReales().get(i));
            hijo2.getParametrosReales().add(padre2.getParametrosReales().get(i));
        }
        for (int i = puntoCruceReales; i < tamanioTotalReales; i++) {
            hijo1.getParametrosReales().add(padre2.getParametrosReales().get(i));
            hijo2.getParametrosReales().add(padre1.getParametrosReales().get(i));
        }

    }

    public void cruceUniforme(Individuo padre1, Individuo padre2, Individuo hijo1, Individuo hijo2) {
        // Lo primero de todo sera generar dos mascaras aleatorias, una para los
        // parametros booleanos y otro para los reales
        int tamanioBool = padre1.getOperacionEscalar().size();
        int tamanioReales = padre1.getParametrosReales().size();
        Vector<Boolean> mascaraBooleana = new Vector<Boolean>(padre1.getOperacionEscalar().size());
        Vector<Boolean> mascaraReales = new Vector<Boolean>(padre1.getParametrosReales().size());
        for (int i = 0; i < tamanioBool; i++) {
            double b = Utilidades.numerosAleatoriosEntre01();
            if (b >= 0.5) {
                mascaraBooleana.add(i, true);
            } else {
                mascaraBooleana.add(i, false);
            }
        }
        for (int i = 0; i < tamanioReales; i++) {
            double b = Utilidades.numerosAleatoriosEntre01();
            if (b >= 0.5) {
                mascaraReales.add(i, true);
            } else {
                mascaraReales.add(i, false);
            }
        }
        // En este punto se tienen generadas de forma aleatoria unas mascaras que seran
        // distintas para cada cruce.
        /* La politica de cruce a uilizar sera la siguiente:
        Si 1 en Mascara -> Parametro del padre1 para el hijo1
        Si 0 en Mascara -> Parametro del padre2 para el hijo1
        Si 1 en Mascara -> Parametro del padre2 para el hijo2
        Si 0 en Mascara -> Parametro del padre1 para el hijo2
         */
        // Se aplica el cruce selectivo con la politica anterior para crear el hijo1 y
        // el hijo 2
        for (int i = 0; i < mascaraBooleana.size(); i++) {
            if (mascaraBooleana.get(i) == true) {
                hijo1.getOperacionEscalar().add(padre1.getOperacionEscalar().get(i));
                hijo2.getOperacionEscalar().add(padre2.getOperacionEscalar().get(i));
            } else {
                hijo1.getOperacionEscalar().add(padre2.getOperacionEscalar().get(i));
                hijo2.getOperacionEscalar().add(padre1.getOperacionEscalar().get(i));
            }
        }
        for (int i = 0; i < mascaraReales.size(); i++) {
            if (mascaraReales.get(i) == true) {
                hijo1.getParametrosReales().add(padre1.getParametrosReales().get(i));
                hijo2.getParametrosReales().add(padre2.getParametrosReales().get(i));
            } else {
                hijo1.getParametrosReales().add(padre2.getParametrosReales().get(i));
                hijo2.getParametrosReales().add(padre1.getParametrosReales().get(i));
            }
        }
    }

    public void cruceUniformeSelectivo(Individuo padre1, Individuo padre2, Individuo hijo1, Individuo hijo2, SLP straightLineProgram) {
        // Lo primero de todo sera generar dos mascaras aleatorias, una para los
        // parametros booleanos y otro para los reales
        Vector<Boolean> mascaraBooleana = new Vector<Boolean>();
        Vector<Boolean> mascaraReales = new Vector<Boolean>();
        // La manera de generar la mascara para los parametros booleanos es exactamente
        // igual que en el cruce uniforme normal
        for (int i = 0; i < padre1.getOperacionEscalar().size(); i++) {
            double b = Utilidades.numerosAleatoriosEntre01();
            if (b >= 0.5) {
                mascaraBooleana.add(true);
            } else {
                mascaraBooleana.add(false);
            }
        }
        // La manera de generar la mascara de parametros reales es un tanto distinta; por
        // cada ristra de alphas y de betas se generara un 1 o un 0 aleatoriamente.
        // En cada Ui hay dos ristras de igual tamaño de alphas y de betas y luego el
        // output tiene parametros independientes. Por tanto, el tamanio de esta mascara
        // sera de (2 * nº de Ui's) + Tamanio_Output
        int nUis = straightLineProgram.getUk().size() - 1; // Tambien se almacena el output
        int tamanioOutput = straightLineProgram.tamanioUi(straightLineProgram.getSlp().size());
        int tamanioMascaraReales = (2 * nUis) + tamanioOutput;
        for (int i = 0; i < tamanioMascaraReales; i++) {
            double b = Utilidades.numerosAleatoriosEntre01();
            if (b >= 0.5) {
                mascaraReales.add(true);
            } else {
                mascaraReales.add(false);
            }
        }
        // En este punto se tienen generadas de forma aleatoria unas mascaras que seran
        // distintas para cada cruce.
        /* La politica de cruce a uilizar sera la siguiente:
            Para los parametros booleanos:
                Si 1 en Mascara -> Parametro del padre1 para el hijo1
                Si 0 en Mascara -> Parametro del padre2 para el hijo1
                Si 1 en Mascara -> Parametro del padre2 para el hijo2
                Si 0 en Mascara -> Parametro del padre1 para el hijo2
            Para los parametros reales:
                Si 1 en Mascara -> Riestra de alphas o betas del padre1 para el hijo1
                Si 0 en Mascara -> Riestra de alphas o betas del padre2 para el hijo1
                Si 1 en Mascara -> Riestra de alphas o betas del padre2 para el hijo2
                Si 0 en Mascara -> Riestra de alphas o betas del padre1 para el hijo2
                (A excepcion de que estemos en el output que se efectua cruce normal)
         */

        // Aplicacion del cruce selectivo con la politica anterior para crear los
        // parametros booleanos del hijo1 y el hijo 2
        for (int i = 0; i < mascaraBooleana.size(); i++) {
            if (mascaraBooleana.get(i) == true) {
                hijo1.getOperacionEscalar().add(padre1.getOperacionEscalar().get(i));
                hijo2.getOperacionEscalar().add(padre2.getOperacionEscalar().get(i));
            } else {
                hijo1.getOperacionEscalar().add(padre2.getOperacionEscalar().get(i));
                hijo2.getOperacionEscalar().add(padre1.getOperacionEscalar().get(i));
            }
        }
        // Aplicacion del cruce selectivo con la politica anterior para crear los
        // parametros reales del hijo1 y el hijo 2
        // Variables que nos acotaran rangos en los que se encuentran los alpas y los
        // betas de cada Ui
        int index1 = 0;
        int index2 = 0;
        int indiceMascara = 0;
        for (int i = 0; i < nUis; i++) {
            // se sabe que por cada Ui va a haber 2 valores en la mascara de parametros,
            // uno para la ristra de alphas y otra para la ristra de betas
            int tamanioUi = straightLineProgram.tamanioUi(i + 1);
            // index2 aqui tiene el tamo del ui, para acotar el rango a solo alphas o
            // betas lo dividimos entre 2
            index2 += tamanioUi / 2;
            // Tratamos la ristra de alphas
            for (int j = index1; j < index2; j++) {
                if (mascaraReales.get(indiceMascara) == true) {
                    hijo1.getParametrosReales().add(padre1.getParametrosReales().get(j));
                    hijo2.getParametrosReales().add(padre2.getParametrosReales().get(j));
                } else {
                    hijo1.getParametrosReales().add(padre2.getParametrosReales().get(j));
                    hijo2.getParametrosReales().add(padre1.getParametrosReales().get(j));
                }
            }
            // Siguiente rango, que sera el correspondiente a las betas del Ui actual
            index1 += tamanioUi / 2;
            index2 += tamanioUi / 2;
            indiceMascara++;
            // Tratamiento de la ristra de betas
            for (int j = index1; j < index2; j++) {
                if (mascaraReales.get(indiceMascara) == true) {
                    hijo1.getParametrosReales().add(padre1.getParametrosReales().get(j));
                    hijo2.getParametrosReales().add(padre2.getParametrosReales().get(j));
                } else {
                    hijo1.getParametrosReales().add(padre2.getParametrosReales().get(j));
                    hijo2.getParametrosReales().add(padre1.getParametrosReales().get(j));
                }
            }
            index1 += tamanioUi / 2;
            indiceMascara++;
        }
        // Y ahora se ha de tratar el output como un cruce normal
        for (int i = index2; i < padre1.getParametrosReales().size(); i++) {
            if (mascaraReales.get(indiceMascara) == true) {
                hijo1.getParametrosReales().add(padre1.getParametrosReales().get(i));
                hijo2.getParametrosReales().add(padre2.getParametrosReales().get(i));
            } else {
                hijo1.getParametrosReales().add(padre2.getParametrosReales().get(i));
                hijo2.getParametrosReales().add(padre1.getParametrosReales().get(i));
            }
            indiceMascara++;
        }
    }

    public void mutacion(Individuo ind) {

        int probTotal = ind.getOperacionEscalar().size() + ind.getParametrosReales().size();
        int probBooleano = ind.getOperacionEscalar().size();
        int probReal = ind.getParametrosReales().size();
        // Se Genera un numero aleatorio entre los parametros totales
        int k = (int) Utilidades.numerosAleatoriosEntreAB(0, probTotal - 1);
        // El numero generado cae dentro de los parametros booleanos
        if ((0 <= k) && (k < probBooleano)) {
            if (ind.getOperacionEscalar().get(k) == false) {
                ind.getOperacionEscalar().remove(k);
                ind.getOperacionEscalar().add(k, true);
            }
            if (ind.getOperacionEscalar().get(k) == true) {
                ind.getOperacionEscalar().remove(k);
                ind.getOperacionEscalar().add(k, false);
            }
        } // El numero generado cae dentro de los reales
        else if (k > probBooleano) {
            double parametro = Utilidades.numerosAleatoriosEntreAB(ind.getLimiteInferior(), ind.getLimiteSuperior());
            ind.getParametrosReales().remove(k - probBooleano - 1);
            ind.getParametrosReales().add(k - probBooleano - 1, parametro);
        }

    }

    public void mutacionSesgada(Individuo ind) {
        // Es necesario elegir que parametro se va a mutar, si un booleano o un real
        boolean mutacion;
        double b = Utilidades.numerosAleatoriosEntre01();
        if (b >= 0.5) {
            mutacion = true;
        } else {
            mutacion = false;
        }
        if (mutacion == false) {
            // Mutación de un parametro booleano
            int tam = ind.getOperacionEscalar().size();
            int k = (int) Utilidades.numerosAleatoriosEntreAB(0, tam - 1);
            if (ind.getOperacionEscalar().get(k) == false) {
                ind.getOperacionEscalar().remove(k);
                ind.getOperacionEscalar().add(k, true);
            }
            if (ind.getOperacionEscalar().get(k) == true) {
                ind.getOperacionEscalar().remove(k);
                ind.getOperacionEscalar().add(k, false);
            }
        }
        if (mutacion == true) {
            // Mutación de un parametro real
            int tam = ind.getParametrosReales().size();
            int k = (int) Utilidades.numerosAleatoriosEntreAB(0, tam - 1);
            double parametro = Utilidades.numerosAleatoriosEntreAB(ind.getLimiteInferior(), ind.getLimiteSuperior());
            ind.getParametrosReales().remove(k);
            ind.getParametrosReales().add(k, parametro);
        }
    }
}
