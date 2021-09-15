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
import java.util.Vector;

public class Individuo {
// Vector cuyo tamanio sera igual al numero de Ui's del individuo y que nos
// indicara si en el Ui se produce una multiplicacion (true) o una
// division (false)

    Vector<Boolean> operacionEscalar;
// Vector de alphas y betas (parametros) de valores reales
    Vector<Double> parametrosReales;
// Variable que almacena el valor de la funcion fitness del individuo
    double fitness;
// Rango de valores de los parametros reales
    double limiteInferior, limiteSuperior;
// Tamaño del individuo
    int tamanio;
// Numero maximo de operaciones no escalares
    int maxoperationsL;

    public Individuo() {
        this.fitness = 0.0;
        this.limiteInferior = 0.0;
        this.limiteSuperior = 0.1;
        operacionEscalar = new Vector<Boolean>();
        parametrosReales = new Vector<Double>();
    }

    public Individuo(int tamanio, int L, double limiteInf, double limiteSup) {
        this.tamanio = tamanio;
        this.maxoperationsL = L;
        this.limiteInferior = limiteInf;
        this.limiteSuperior = limiteSup;
// Vector booleano de tamaño L
        Vector<Boolean> aux = new Vector<Boolean>(L);
        operacionEscalar = aux;
// Para generar los valores de los parametros, primero se ha de saber cuantos
// parametros tendra el individuo. Se sabe el tamanio total del mismo, y se sabe
// cuantos Uk tiene (max_ops_L). Por tanto,
// Nº parametros = Tamanio_Total - max_ops_L (nº de Uk)
        int nParametrosReales = tamanio - maxoperationsL;
        Vector<Double> aux2 = new Vector<Double>(nParametrosReales);
        parametrosReales = aux2;
// Se rellena el vector de booleanos
// Se sabe que habra tantos parametros booleanos como Ui, y que hay tantos Ui
// como valor tenga max_ops_L (es decir, el tamanio eficaz). Por tanto
        for (int j = 0; j < maxoperationsL; j++) {
            double k = Utilidades.numerosAleatoriosEntre01();
            if (k >= 0.5) {
                operacionEscalar.add(j, true);
            } else {
                operacionEscalar.add(j, false);
            }
        }
// Y ahora se genera un vector de ese tamaño con valores entre a y b
        for (int j = 0; j < nParametrosReales; j++) {
            double parametroJ = Utilidades.numerosAleatoriosEntreAB(limiteInferior, limiteSuperior);
            parametrosReales.add(j, parametroJ);
        }
// Se inicializa el valor fitness a cero
        fitness = 0.0;
    }

    

    public Vector<Boolean> getOperacionEscalar() {
        return operacionEscalar;
    }

    public Vector<Double> getParametrosReales() {
        return parametrosReales;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getLimiteInferior() {
        return limiteInferior;
    }

    public double getLimiteSuperior() {
        return limiteSuperior;
    }
}
