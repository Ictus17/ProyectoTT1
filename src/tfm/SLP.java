/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tfm;

import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author Carlos
 */
class SLP {

    // Variable que establece el numero de operaciones distintas de {+,-}
    private int maxOperationsL;
    // Variable que establece el numero de variables que utilizara el programa
    private int nVariables;
    // Diccionario que representara al slp
    HashMap<Integer, Vector<Double>> slp = new HashMap<Integer, Vector<Double>>();
    // Vector que contendra los valores calculados de cada Ui
    Vector<Double> Uk;

    public SLP(int nVariables, int L) {
        this.nVariables = nVariables;
        this.maxOperationsL = L;
        for (int i = 1; i <= maxOperationsL; i++) {
            // Se calcula el numero de alphas que necesita el Ui
            int a = calculaAlphasBetas(i, nVariables);
            // El numero de betas sera igual que el numero de alphas
            // por lo que el numero total de ambos es
            a *= 2;
            Vector<Double> vector = new Vector<Double>();
            Utilidades.rellenaVector(vector, a);
            slp.put(i, vector);
        }
        // Se calcula el numero de parametros que tiene el OUTPUT
        // SUM (j = -n+1 , L) (X) = L + n - 1 + 1 veces (X) = L + n
        int o = maxOperationsL + nVariables;
        // Se inserta el OUTPUT en nuestra estructura
        Vector<Double> vector = new Vector<Double>();
        Utilidades.rellenaVector(vector, o);
        slp.put(maxOperationsL + 1, vector);
        // Se inicializa el vector de resultados a valor 0 para todos los Uk
        // Tendra el mismo tamanio como tamanio eficaz tenga el SLP, es decir, el numero
        // de opraciones distintas de {-, +} + 1, para albergar tambien el valor del
        // Output
        Vector<Double> aux = new Vector<Double>();
        Utilidades.rellenaVector(aux, maxOperationsL + 1);
        Uk = aux;
    }

    private int calculaAlphasBetas(int i, int nVar) {
        // Por propiedades de los sumatorios
        // SUM (j = -n+1, i-1 ) (X) = i - 1 + n - 1 + 1 veces (x) = i + n - 1
        return i + nVar - 1;
    }

    public int calculaParametros(int n, int L) {
        int aux = 2 * n;
        aux += L;
        aux *= L;
        aux += L + n;
        return aux;
    }

    public double calculaOutput(Vector<Double> components, Vector<Boolean> op, Vector<Double> parameters) {

        // Variable que almacenara el valor del Ouput actual
        double out = 0.0;
        // Se sabe que el Straight Line Program tiene tanto tamanio como tamanio tenga
        // el vector Uk de la estructura, ya que almacena el numero de Ui y el output.
        // Por tanto
        int numeroUis = Uk.size() - 1;
        // Indice que recorrera el vector de parametros del individuo
        int index = -1;
        for (int i = 1; i <= numeroUis; i++) {
            // Bucle que simulara el recorrido de cada Ui. Se inicia con valor uno ya que
            // en el diccionario del straight line program los valores de los Uis toman
            // valores i = 1....n
            // Se calcula el numero de parametros que tiene el Ui actual
            int tamanio_Ui = tamanioUi(i);
            // Declaracion de operandos. Al final El resultado siempre sera una operacion
            // binaria de dos operandos -->
            // (a1, a2,.., ak+1)(x, y, U1, ... Uk)opB(b1, b2, ..., bk+1)(x, y, U1, ... Uk)
            double op1, op2;
            //System.out.println("");
            if (i == 1) {
                // El primer Ui se trata distinto ya que no necesita el calculo de otros
                // Ui, solo de las variables
                op1 = op2 = 0.0;
                op1 = calculaOperandoVariables(index, components, parameters);
                op2 = calculaOperandoVariables(index, components, parameters);
                // Se resta un valor al indice ya que la funcion anterior devuelve el
                // indice apuntando a un elemento posterior tras el calculo de los
                // dos operandos
                index--;
                // Comprobacion de operacion. En este caso tenemos que mirar siempre el U1
                // que se almacena en la posicion 0 del vector Uk. Por tanto
                if (op.get(i - 1) == true) {
                    Uk.remove(i - 1);
                    Uk.add(i - 1, op1 * op2);
                } else {
                    Uk.remove(i - 1);
                    Uk.add(i - 1, op1 / op2);
                }

            }
            if (i > 1) {
                op1 = op2 = 0.0;
                index++;
                op1 = calculaOperandoVariables(index, components, parameters);
                // Ahora hay que sumar el valor de los Ui anteriores multiplicados por el
                // parametro escalar al que que apunte index. Hay que calcular un numero de
                // Uis equivalente al numero de parametros que tenga el Ui actual dividido
                // entre 2 (que corresponde al numero de parametros del primer operando) y
                // a ese valor restarle el numero de variables. Es decir, supongase que
                // estamos en el U2 con 3 variables, el tamanio de U2 seria
                // (3 variables_alphas + 1 U1 + 3 variables_betas + 1 U1). Habria que
                // calcular solo un Ui, el U1, por tanto quedaria
                // Nº Ui a calcular = (8/2)-3 = 1
                for (int j = 0; j < (tamanio_Ui / 2) - (int) components.size(); j++) {
                    index++;
                    op1 += parameters.get(index) * Uk.get(j);
                }
                // Ahora se calcula el segundo parametro
                op2 = calculaOperandoVariables(index, components, parameters);
                // Y se suma el valor de los Ui anteriores analogamente al caso anterior
                
                
               
                for (int j = 0; j < (tamanio_Ui / 2) - (int) components.size(); j++) {
                    index++;
                    op2 += parameters.get(index) * Uk.get(j);
                }
                // Comprobacion de operacion. En este caso tenemos que mirar siempre el Ui
                // que se almacena en la posicion i-1 del vector Uk. Por tanto
                if (op.get(i - 1) == true) {
                    Uk.remove(i - 1);
                    Uk.add(i - 1, op1 * op2);
                } else {
                    Uk.remove(i - 1);
                    Uk.add(i - 1, op1 / op2);
                }
                // Se resta un valor al indice ya que la funcion anterior devuelve el
                // indice apuntando a un elemento posterior tras el calculo de los
                // dos operandos y luego se ha vuelto a incrementar
                index--;
            }

        }
        // Aqui se tendran todos los valores de los Ui y habra que calcular
        // el valor del ouput. Ademas, el index se mantiene actualizado apuntando
        // al siguiente parametro del individuo. En la estructura del striaight line
        // program, el output se almacena en la ultima posicion del diccionario, asi
        // pues para calcular su tamanio
        int tamanio_output = slp.get(slp.size()).size();
        // Se actualiza el output con el valor aplicado a las variables
        for (int i = 0; i < components.size(); i++) {
            index++;
            if(index<0) index =0; 
            out += parameters.get(index) * components.get(i);
        }
        // Se termina de actualizar el output con los valores
        for (int i = 0; i < tamanio_output - (int) components.size(); i++) {
            index++;
            out += parameters.get(index) * Uk.get(i);
        }

        return out;
    }

    public int tamanioUi(int i) {
        return slp.get(i).size();
    }

    private double calculaOperandoVariables(int ind, Vector<Double> c, Vector<Double> p) {
        // Variable auxiliar para almacenar el resultado
        double aux = 0.0;
        // Indice para recorrer el vector de componentes
        int j = 0;
        // En el caso de procesar U1, el primer Ui
        if (ind == -1) {
            ind = 0;
        }
        while (j < (int) c.size()) {
            aux += p.get(ind) * c.get(j);
            j++;
            ind++;
        }
        return aux;
    }

    public HashMap<Integer, Vector<Double>> getSlp() {
        return slp;
    }

    public Vector<Double> getUk() {
        return Uk;
    }

}
