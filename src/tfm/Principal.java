/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tfm;

import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Carlos
 */
public class Principal implements Observer {

    PanelPrincipalBase vista = null;

    /**
     * Inicializa el panel principal y añade esta clase como observadora
     */
    public void inicializar() {
        vista = new PanelPrincipalBase();
        vista.addObserver(this);
    }

    /**
     * Hace visible el panel principal
     */
    public void arrancar() {
        vista.arrancar();
    }

    /**
     * Método de observación que recibirá una notificación desde el panel
     * principal indicando que todo está inicializado y listo para la ejecución
     * del algoritmo genético.
     */
    public void update(Observable e, Object estado) {
        if (((String) estado).equals(PanelPrincipalConstantes.ALGORITMO_LISTO)) {
// Se crea una tarea que ejecutará el algoritmo genético pasados unos segundos
            ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
            Runnable task = new Runnable() {
                public void run() {

                    // Instancia de toda la lógica del algoritmo genético
                    Algoritmo regresionSimbolica = new Algoritmo();
                    regresionSimbolica.inicializar(vista);

                    // Se ejecuta el algoritmo genético
                    double resultado = regresionSimbolica.ejecutar();

                    vista.muestraResultado(resultado);

                    Toolkit.getDefaultToolkit().beep();
                }
            };
// Se fuerza que la tarea tenga un retardo de comienzo
            worker.schedule(task, 3, TimeUnit.SECONDS);
        }
    }

    /**
     * Programa principal. Punto de entrada.
     *
     * @param args
     */
    public static void main(String[] args) {
        Principal principal = new Principal();
        principal.inicializar();
        principal.arrancar();
    }

    
}
