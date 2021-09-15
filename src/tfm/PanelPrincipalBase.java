/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tfm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

/**
 *
 * @author Carlos
 */
public final class PanelPrincipalBase extends Observable implements ActionListener {

    private JFrame interfaz;
    private JTextArea resumen;
    // Numero Variables
    private JLabel variablesLabel;
    private JTextField variables;
    // Numero operaciones
    private JLabel nMaxOperacionesLabel;
    private JTextField nMaxOperaciones;
    // Tamanio poblacion
    private JLabel tamPoblacionLabel;
    private JTextField tamPoblacion;
    // Rango Valores
    private JLabel rangoValoresLabel1;
    private JLabel rangoValoresLabel2;
    private JLabel rangoValoresLabel3;
    private JTextField rangoValorMin;
    private JTextField rangoValorMax;
    // Ruta Puntos Muestra
    private JLabel rutaLabel;
    private JTextField ruta;
    private JButton selectFile;
    // Seleccion
    private JLabel tipoSeleccion;
    private JLabel ktorneoLabel;
    private JTextField ktorneo;
    private JRadioButton ruletaRadio;
    private JRadioButton kTorneoRadio;
    // Cruce
    private JLabel tipoCruce;
    private JRadioButton cruce1Punto;
    private JRadioButton cruce1PuntoSelectivo;
    private JRadioButton cruceUniforme;
    private JRadioButton cruceUniformeSelectivo;
    // Mutacion
    private JLabel tipoMutacion;
    private JRadioButton mutacionNormalRadio;
    private JRadioButton mutacionSesgadaRadio;
    // Reemplazamiento
    private JLabel tipoReemplazo;
    private JRadioButton reemplazoHijos;
    private JRadioButton reemplazoMejores;
    // Probabilidad de Cruce
    private JLabel probabilidadCruceLabel;
    private JTextField probabilidadCruce;
    // Probabilidad de Mutacion
    private JLabel probabilidadMutacionLabel;
    private JTextField probabilidadMutacion;
    // Elitismo
    private JLabel elitismoLabel;
    private JCheckBox elitismo;
    // Test de parada
    private JLabel testParadaLabel;
    private JTextField testParada;
    // Controles
    private JButton startButton;
    private JButton endButton;
    private JButton iniButton;
    private JProgressBar barraProgreso;
    // Grupo Botones Cruce
    ButtonGroup botonesSeleccion;
    // Grupo Botones Cruce
    ButtonGroup botonesCruce;
    // Grupo Botones Cruce
    ButtonGroup botonesMutacion;
    // Grupo Botones Cruce
    ButtonGroup botonesReemplazamiento;
    // Resultado Algoritmo
    private JLabel resultadoAlgoritmo;
    /**
     * Variables propias para el Algoritmo Genético
     */
    // Numero de variables
    private int nVariables = 0;
    // Numero de operaciones no escalares (tamanio eficaz)
    private int L = 0;
    // Tamanio de la poblacion
    private int tamanioPoblacion = 0;
    // Rango minimo
    private double lower = 0;
    // Rango maximo
    private double upper = 0;
    // Ruta del fichero
    private String route = null;
    // Tipo de seleccion
    private int selection = 0;
    // Tipo de cruce
    private int cross = 0;
    // Tipo de mutacion
    private int mutation = 0;
    // Tipo de reemplazamiento
    private int replacement = 0;
    // Probabilidad de cruce
    private double crossProb = 0;
    // Probabilidad de mutacion
    private double mutationProb = 0;
    // Test de parada
    private int test = 0;
    // Variable kTorneo
    private int kTorneo = 0;
    // Elitismo
    private boolean elitism = false;
    // Campos vacíos
    private ArrayList<String> camposVacios = null;

    public PanelPrincipalBase() {
        super();
        inicializar();
    }

    public void inicializar() {
        // Creación de límites del panel principal
        interfaz = new JFrame(PanelPrincipalConstantes.TITULO_PRINCIPAL);
        interfaz.setSize(785, 825);
        interfaz.getContentPane().add(getResumen(), BorderLayout.NORTH);
        // Resumen
        interfaz.add(getResumen());
        // Variables
        interfaz.add(getVariablesLabel(), null);
        interfaz.add(getVariables(), null);
        // Operaciones
        interfaz.add(getMaxOperacionesLabel(), null);
        interfaz.add(getMaxOperaciones(), null);
        // Tamanio Poblacion
        interfaz.add(getTamPoblacionLabel(), null);
        interfaz.add(getTamPoblacion(), null);
        // Rango Valores
        interfaz.add(getRangoValoresLabel1(), null);
        interfaz.add(getRangoValorMin(), null);
        interfaz.add(getRangoValoresLabel2(), null);
        interfaz.add(getRangoValorMax(), null);
        interfaz.add(getRangoValoresLabel3(), null);
        // Ruta archivo de puntos muestra
        interfaz.add(getRutaLabel(), null);
        interfaz.add(getRuta(), null);
        interfaz.add(getSelectFile(), null);
        // Tipo de seleccion
        interfaz.add(getTipoSeleccion(), null);
        interfaz.add(getRuletaRadio(), null);
        interfaz.add(getKTorneoRadio(), null);
        interfaz.add(getKTorneoLabel(), null);
        interfaz.add(getKTorneo(), null);
        creaGrupoSeleccion();
        // Tipo de cruce
        interfaz.add(getTipoCruce(), null);
        interfaz.add(getCruce1Punto(), null);
        interfaz.add(getCruce1PuntoSelectivo(), null);
        interfaz.add(getCruceUniforme(), null);
        interfaz.add(getCruceUniformeSelectivo(), null);
        creaGrupoCruce();
        // Probabilidad de cruce
        interfaz.add(getProbabilidadCruceLabel(), null);
        interfaz.add(getProbabilidadCruce(), null);
        // Tipo de mutacion
        interfaz.add(getTipoMutacion(), null);
        interfaz.add(getMutacionNormalRadio(), null);
        interfaz.add(getMutacionSesgadaRadio(), null);
        creaGrupoMutacion();
        // Probabilidad de mutacion
        interfaz.add(getProbabilidadMutacionLabel(), null);
        interfaz.add(getProbabilidadMutacion(), null);
        // Tipo de Reemplazo
        interfaz.add(getTipoReemplazamiento(), null);
        interfaz.add(getReemplazamientoHijos(), null);
        interfaz.add(getReemplazamientoMejores(), null);
        creaGrupoReemplazamiento();
        // Elitismo
        interfaz.add(getElitismoLabel(), null);
        interfaz.add(getElitismo(), null);
        // Elitismo
        interfaz.add(getTestParadaLabel(), null);
        interfaz.add(getTestParada(), null);
        // Controles
        interfaz.add(getBotonStart(), null);
        interfaz.add(getBotonEnd(), null);
        interfaz.add(getIniButton(), null);
        interfaz.add(getBarraProgreso(), null);
        interfaz.add(getResultadoAlgoritmo(), null);
        // Se crean grupos de botones para la selección de cada tipo
        // de operacion
        botonesSeleccion = new ButtonGroup();
        botonesSeleccion.add(getRuletaRadio());
        botonesSeleccion.add(getKTorneoRadio());
        botonesCruce = new ButtonGroup();
        botonesCruce.add(getCruce1Punto());
        botonesCruce.add(getCruce1PuntoSelectivo());
        botonesCruce.add(getCruceUniforme());
        botonesCruce.add(getCruceUniformeSelectivo());
        botonesMutacion = new ButtonGroup();
        botonesMutacion.add(getMutacionNormalRadio());
        botonesMutacion.add(getMutacionSesgadaRadio());
        botonesReemplazamiento = new ButtonGroup();
        botonesReemplazamiento.add(getReemplazamientoHijos());
        botonesReemplazamiento.add(getReemplazamientoMejores());
    }

    public void arrancar() {
        interfaz.setLocationRelativeTo(null);
        interfaz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        interfaz.setLayout(null);
        interfaz.setVisible(true);
    }

    /**
     * Creación de los distintos componentes que se verán en el panel principal
     */
    private JTextArea getResumen() {
        if (resumen == null) {
            resumen = new JTextArea(PanelPrincipalConstantes.RESUMEN_PRINCIPAL);
            resumen.setFont(new Font("Serif", Font.PLAIN, 13));
            resumen.setOpaque(false);
            resumen.setEditable(false);
            resumen.setBounds(30, 20, 750, 90);
            resumen.setLineWrap(true);
        }
        return resumen;
    }

    private JLabel getVariablesLabel() {
        if (variablesLabel == null) {
            variablesLabel = new JLabel();
            variablesLabel.setText(PanelPrincipalConstantes.NUMERO_VARIABLES);
            variablesLabel.setFont(new Font("Serif", Font.PLAIN, 13));
            variablesLabel.setBounds(30, 140, 90, 15);
            variablesLabel.setName("variablesLabel");
        }
        return variablesLabel;

    }

    private JTextField getVariables() {
        if (variables == null) {
            variables = new JTextField();
            variables.setBounds(110, 138, 40, 20);
            variables.setName("variables");
            variables.setEnabled(true);
            variables.setAutoscrolls(true);
            variables.addCaretListener((CaretEvent e) -> {
                try {
                    if (!getVariables().getText().equalsIgnoreCase("")) {
                        nVariables = Integer.valueOf(getVariables().getText());
                    }
                } catch (Exception ex) {
                }
            });
        }
        return variables;
    }

    private JLabel getMaxOperacionesLabel() {

        if (nMaxOperacionesLabel == null) {
            nMaxOperacionesLabel = new JLabel();
            nMaxOperacionesLabel.setText(PanelPrincipalConstantes.NUMERO_MAXIMO_OPERACIONES);
            nMaxOperacionesLabel.setFont(new Font("Serif", Font.PLAIN, 13));
            nMaxOperacionesLabel.setBounds(175, 140, 250, 15);
            nMaxOperacionesLabel.setName("nMaxOperacionesLabel");
        }
        return nMaxOperacionesLabel;
    }

    private JTextField getMaxOperaciones() {
        if (nMaxOperaciones == null) {
            nMaxOperaciones = new JTextField();
            nMaxOperaciones.setBounds(430, 138, 40, 20);
            nMaxOperaciones.setName("variables");
            nMaxOperaciones.setEnabled(true);
            nMaxOperaciones.setAutoscrolls(true);
            nMaxOperaciones.addCaretListener((CaretEvent e) -> {
                try {
                    if (!getMaxOperaciones().getText().equalsIgnoreCase("")) {
                        L = Integer.valueOf(getMaxOperaciones().getText().toString());
                    }
                } catch (Exception ex) {
                }
            });
        }
        return nMaxOperaciones;
    }

    private JLabel getTamPoblacionLabel() {
        if (tamPoblacionLabel == null) {
            tamPoblacionLabel = new JLabel();
            tamPoblacionLabel.setText(PanelPrincipalConstantes.TAMANIO_POBLACION);
            tamPoblacionLabel.setFont(new Font("Serif", Font.PLAIN, 13));
            tamPoblacionLabel.setBounds(525, 140, 125, 15);
            tamPoblacionLabel.setName("tPoblacionLabel");
        }
        return tamPoblacionLabel;

    }

    private JTextField getTamPoblacion() {
        if (tamPoblacion == null) {
            tamPoblacion = new JTextField();
            tamPoblacion.setBounds(640, 138, 40, 20);
            tamPoblacion.setName("tPoblacion");
            tamPoblacion.setEnabled(true);
            tamPoblacion.setAutoscrolls(true);
            tamPoblacion.addCaretListener((CaretEvent e) -> {
                try {
                    if (!getTamPoblacion().getText().toString().equalsIgnoreCase("")) {
                        tamanioPoblacion = Integer.valueOf(getTamPoblacion().getText().toString());
                    }
                } catch (Exception ex) {
                }
            });
        }
        return tamPoblacion;
    }

    private JLabel getRangoValoresLabel1() {

        if (rangoValoresLabel1 == null) {
            rangoValoresLabel1 = new JLabel();
            rangoValoresLabel1.setText(PanelPrincipalConstantes.RANGO_VALORES1);
            rangoValoresLabel1.setFont(new Font("Serif", Font.PLAIN, 13));
            rangoValoresLabel1.setBounds(30, 160, 700, 50);
            rangoValoresLabel1.setName("rangoValoresLabel1");
        }
        return rangoValoresLabel1;

    }

    private JLabel getRangoValoresLabel2() {
        if (rangoValoresLabel2 == null) {
            rangoValoresLabel2 = new JLabel();
            rangoValoresLabel2.setText(PanelPrincipalConstantes.RANGO_VALORES2);
            rangoValoresLabel2.setFont(new Font("Serif", Font.PLAIN, 13));
            rangoValoresLabel2.setBounds(650, 160, 20, 50);
            rangoValoresLabel2.setName("rangoValoresLabel2");
        }
        return rangoValoresLabel2;
    }

    private JLabel getRangoValoresLabel3() {
        if (rangoValoresLabel3 == null) {
            rangoValoresLabel3 = new JLabel();
            rangoValoresLabel3.setText(PanelPrincipalConstantes.RANGO_VALORES3);
            rangoValoresLabel3.setFont(new Font("Serif", Font.PLAIN, 13));
            rangoValoresLabel3.setBounds(695, 160, 20, 50);
            rangoValoresLabel3.setName("rangoValoresLabel3");
        }
        return rangoValoresLabel3;
    }

    private JTextField getRangoValorMin() {
        if (rangoValorMin == null) {
            rangoValorMin = new JTextField();
            rangoValorMin.setBounds(617, 176, 40, 20);
            rangoValorMin.setName("rangoValorMin");
            rangoValorMin.setEnabled(true);
            rangoValorMin.setAutoscrolls(true);
            rangoValorMin.addCaretListener((CaretEvent e) -> {
                try {
                    if (!getRangoValorMin().getText().equalsIgnoreCase("")) {
                        lower = Double.valueOf(getRangoValorMin().getText());
                    }
                } catch (Exception ex) {
                }
            });
        }
        return rangoValorMin;
    }

    private JTextField getRangoValorMax() {
        if (rangoValorMax == null) {
            rangoValorMax = new JTextField();
            rangoValorMax.setBounds(660, 176, 40, 20);
            rangoValorMax.setName("rangoValorMax");
            rangoValorMax.setEnabled(true);
            rangoValorMax.setAutoscrolls(true);
            rangoValorMax.addCaretListener((CaretEvent e) -> {
                try {
                    if (!getRangoValorMax().getText().equalsIgnoreCase("")) {
                        upper = Double.valueOf(getRangoValorMax().getText());
                    }
                } catch (NumberFormatException ex) {
                }
            });
        }
        return rangoValorMax;
    }

    private JLabel getRutaLabel() {
        if (rutaLabel == null) {
            rutaLabel = new JLabel();
            rutaLabel.setText(PanelPrincipalConstantes.RUTA_PUNTOS_MUESTRA);
            rutaLabel.setFont(new Font("Serif", Font.PLAIN, 13));
            rutaLabel.setBounds(30, 220, 400, 15);
            rutaLabel.setName("rangoValoresLabel3");
        }
        return rutaLabel;
    }

    private JTextField getRuta() {
        if (ruta == null) {
            ruta = new JTextField();
            ruta.setBounds(300, 218, 300, 20);
            ruta.setName("ruta");
            ruta.setEnabled(true);
            ruta.setAutoscrolls(true);
            ruta.addCaretListener((CaretEvent e) -> {
                try {
                    if (!getRuta().getText().equalsIgnoreCase("")) {
                        route = getRuta().getText();
                    }
                } catch (Exception ex) {
                }
            });
        }
        return ruta;
    }

    private JButton getSelectFile() {
        if (selectFile == null) {
            selectFile = new JButton();
            selectFile.setBounds(610, 218, 107, 19);
            selectFile.setPreferredSize(new Dimension(50, 50));
            selectFile.setText("Elige Archivo");
            selectFile.setName("selectFile");
            selectFile.addActionListener(this);
        }
        return selectFile;
    }

    private JLabel getTipoSeleccion() {

        if (tipoSeleccion == null) {
            tipoSeleccion = new JLabel();
            tipoSeleccion.setText(PanelPrincipalConstantes.TIPO_SELECCION);
            tipoSeleccion.setFont(new Font("Serif", Font.PLAIN, 13));
            tipoSeleccion.setBounds(30, 260, 700, 15);
            tipoSeleccion.setName("tipoSeleccion");
        }
        return tipoSeleccion;

    }

    private JLabel getKTorneoLabel() {
        if (ktorneoLabel == null) {
            ktorneoLabel = new JLabel();
            ktorneoLabel.setEnabled(true);
            ktorneoLabel.setVisible(false);
            ktorneoLabel.setText(PanelPrincipalConstantes.K_TORNEO_SELECCION);
            ktorneoLabel.setFont(new Font("Serif", Font.ITALIC, 12));
            ktorneoLabel.setForeground(Color.BLUE);
            ktorneoLabel.setBounds(175, 315, 300, 15);
            ktorneoLabel.setName("ktorneo");
        }
        return ktorneoLabel;
    }

    private JTextField getKTorneo() {
        if (ktorneo == null) {
            ktorneo = new JTextField();
            ktorneo.setBounds(400, 313, 40, 20);
            ktorneo.setName("ktorneo");
            ktorneo.setEnabled(true);
            ktorneo.setVisible(false);
            ktorneo.setAutoscrolls(true);
            ktorneo.addCaretListener((CaretEvent e) -> {
                try {
                    if (!getKTorneo().getText().equalsIgnoreCase("")) {
                        kTorneo = Integer.valueOf(getKTorneo().getText());
                    }
                } catch (Exception ex) {
                }
            });
        }
        return ktorneo;
    }

    private JRadioButton getRuletaRadio() {
        if (ruletaRadio == null) {
            ruletaRadio = new JRadioButton(PanelPrincipalConstantes.RULETA);
            ruletaRadio.setFont(new Font("Serif", Font.ITALIC, 13));
            ruletaRadio.setActionCommand("ruleta");
            ruletaRadio.setBounds(50, 290, 150, 17);
            ruletaRadio.setSelected(false);
            ruletaRadio.addActionListener(this);
        }
        return ruletaRadio;
    }

    private JRadioButton getKTorneoRadio() {
        if (kTorneoRadio == null) {
            kTorneoRadio = new JRadioButton(PanelPrincipalConstantes.K_TORNEO);
            kTorneoRadio.setFont(new Font("Serif", Font.ITALIC, 13));
            kTorneoRadio.setActionCommand("kTorneo");
            kTorneoRadio.setBounds(50, 315, 100, 17);
            kTorneoRadio.setSelected(false);
            kTorneoRadio.addActionListener(this);
        }
        return kTorneoRadio;
    }

    private JLabel getTipoCruce() {
        if (tipoCruce == null) {
            tipoCruce = new JLabel();
            tipoCruce.setText(PanelPrincipalConstantes.TIPO_CRUCE);
            tipoCruce.setFont(new Font("Serif", Font.PLAIN, 13));
            tipoCruce.setBounds(30, 355, 415, 17);
            tipoCruce.setName("tipoCruce");
        }
        return tipoCruce;
    }

    private JRadioButton getCruce1Punto() {
        if (cruce1Punto == null) {
            cruce1Punto = new JRadioButton(PanelPrincipalConstantes.CRUCE_1_PUNTO);
            cruce1Punto.setFont(new Font("Serif", Font.ITALIC, 13));
            cruce1Punto.setActionCommand("cruce1Punto");
            cruce1Punto.setBounds(50, 385, 150, 17);
            cruce1Punto.setSelected(false);
            cruce1Punto.addActionListener(this);

        }
        return cruce1Punto;
    }

    private JRadioButton getCruce1PuntoSelectivo() {
        if (cruce1PuntoSelectivo == null) {
            cruce1PuntoSelectivo = new JRadioButton(PanelPrincipalConstantes.CRUCE_1_PUNTO_SELECTIVO);
            cruce1PuntoSelectivo.setFont(new Font("Serif", Font.ITALIC, 13));
            cruce1PuntoSelectivo.setActionCommand("cruce1PuntoSelectivo");
            cruce1PuntoSelectivo.setBounds(50, 410, 200, 17);
            cruce1PuntoSelectivo.setSelected(false);
            cruce1PuntoSelectivo.addActionListener(this);
        }
        return cruce1PuntoSelectivo;
    }

    private JRadioButton getCruceUniforme() {

        if (cruceUniforme == null) {
            cruceUniforme = new JRadioButton(PanelPrincipalConstantes.CRUCE_UNIFORME);
            cruceUniforme.setFont(new Font("Serif", Font.ITALIC, 13));
            cruceUniforme.setActionCommand("cruceUniforme");
            cruceUniforme.setBounds(300, 385, 150, 17);
            cruceUniforme.setSelected(false);
            cruceUniforme.addActionListener(this);
        }

        return cruceUniforme;
    }

    private JRadioButton getCruceUniformeSelectivo() {
        if (cruceUniformeSelectivo == null) {
            cruceUniformeSelectivo = new JRadioButton(PanelPrincipalConstantes.CRUCE_UNIFORME_SELECTIVO);
            cruceUniformeSelectivo.setFont(new Font("Serif", Font.ITALIC, 13));
            cruceUniformeSelectivo.setActionCommand("cruceUniformeSelectivo");
            cruceUniformeSelectivo.setBounds(300, 410, 175, 17);
            cruceUniformeSelectivo.setSelected(false);
            cruceUniformeSelectivo.addActionListener(this);
        }
        return cruceUniformeSelectivo;
    }

    private JLabel getTipoMutacion() {
        if (tipoMutacion == null) {
            tipoMutacion = new JLabel();
            tipoMutacion.setText(PanelPrincipalConstantes.TIPO_MUTACION);
            tipoMutacion.setFont(new Font("Serif", Font.PLAIN, 13));
            tipoMutacion.setBounds(30, 450, 700, 15);
            tipoMutacion.setName("tipoMutacion");
        }
        return tipoMutacion;
    }

    private JRadioButton getMutacionNormalRadio() {
        if (mutacionNormalRadio == null) {
            mutacionNormalRadio = new JRadioButton(PanelPrincipalConstantes.MUTACION_NORMAL);
            mutacionNormalRadio.setFont(new Font("Serif", Font.ITALIC, 13));
            mutacionNormalRadio.setActionCommand("mutacionNormal");
            mutacionNormalRadio.setBounds(50, 480, 250, 17);
            mutacionNormalRadio.setSelected(false);
            mutacionNormalRadio.addActionListener(this);
        }
        return mutacionNormalRadio;
    }

    private JRadioButton getMutacionSesgadaRadio() {
        if (mutacionSesgadaRadio == null) {
            mutacionSesgadaRadio = new JRadioButton(PanelPrincipalConstantes.MUTACION_SESGADA);
            mutacionSesgadaRadio.setFont(new Font("Serif", Font.ITALIC, 13));
            mutacionSesgadaRadio.setActionCommand("mutacionSesgadaRadio");
            mutacionSesgadaRadio.setBounds(50, 505, 275, 17);
            mutacionSesgadaRadio.setSelected(false);
            mutacionSesgadaRadio.addActionListener(this);
        }
        return mutacionSesgadaRadio;
    }

    private JLabel getTipoReemplazamiento() {
        if (tipoReemplazo == null) {
            tipoReemplazo = new JLabel();
            tipoReemplazo.setText(PanelPrincipalConstantes.TIPO_REEMPLAZAMIENTO);
            tipoReemplazo.setFont(new Font("Serif", Font.PLAIN, 13));
            tipoReemplazo.setBounds(30, 545, 700, 15);
            tipoReemplazo.setName("tipoReemplazo");
        }
        return tipoReemplazo;
    }

    private JRadioButton getReemplazamientoHijos() {
        if (reemplazoHijos == null) {
            reemplazoHijos = new JRadioButton(PanelPrincipalConstantes.REEMPLAZAMIENTO_HIJOS);
            reemplazoHijos.setFont(new Font("Serif", Font.ITALIC, 13));
            reemplazoHijos.setActionCommand("reemplazoHijos");
            reemplazoHijos.setBounds(50, 575, 250, 17);
            reemplazoHijos.setSelected(false);
            reemplazoHijos.addActionListener(this);
        }
        return reemplazoHijos;
    }

    private JRadioButton getReemplazamientoMejores() {
        if (reemplazoMejores == null) {
            reemplazoMejores = new JRadioButton(PanelPrincipalConstantes.REEMPLAZAMIENTO_MEJORES);
            reemplazoMejores.setFont(new Font("Serif", Font.ITALIC, 13));
            reemplazoMejores.setActionCommand("reemplazoMejores");
            reemplazoMejores.setBounds(50, 600, 275, 17);
            reemplazoMejores.setSelected(false);
            reemplazoMejores.addActionListener(this);
        }
        return reemplazoMejores;
    }

    private JLabel getProbabilidadCruceLabel() {
        if (probabilidadCruceLabel == null) {
            probabilidadCruceLabel = new JLabel();
            probabilidadCruceLabel.setVisible(false);
            probabilidadCruceLabel.setText(PanelPrincipalConstantes.PROBABILIDAD_CRUCE);
            probabilidadCruceLabel.setFont(new Font("Serif", Font.ITALIC, 12));
            probabilidadCruceLabel.setForeground(Color.BLUE);
            probabilidadCruceLabel.setBounds(500, 395, 200, 15);
            probabilidadCruceLabel.setName("probabilidadCruceLabel");
        }
        return probabilidadCruceLabel;
    }

    private JTextField getProbabilidadCruce() {
        if (probabilidadCruce == null) {
            probabilidadCruce = new JTextField();
            probabilidadCruce.setBounds(695, 392, 40, 20);
            probabilidadCruce.setName("probabilidadCruce");
            probabilidadCruce.setEnabled(true);
            probabilidadCruce.setVisible(false);
            probabilidadCruce.setAutoscrolls(true);
            probabilidadCruce.addCaretListener((CaretEvent e) -> {
                try {
                    if (!getProbabilidadCruce().getText().equalsIgnoreCase("")) {
                        crossProb = Double.valueOf(getProbabilidadCruce().getText());
                    }
                } catch (Exception ex) {
                }
            });
        }
        return probabilidadCruce;
    }

    private JLabel getProbabilidadMutacionLabel() {
        if (probabilidadMutacionLabel == null) {
            probabilidadMutacionLabel = new JLabel();
            probabilidadMutacionLabel.setVisible(false);
            probabilidadMutacionLabel.setText(PanelPrincipalConstantes.PROBABILIDAD_MUTACION);
            probabilidadMutacionLabel.setFont(new Font("Serif", Font.ITALIC, 12));
            probabilidadMutacionLabel.setForeground(Color.BLUE);
            probabilidadMutacionLabel.setBounds(375, 490, 200, 15);
            probabilidadMutacionLabel.setName("probabilidadMutacionLabel");
        }
        return probabilidadMutacionLabel;
    }

    private JTextField getProbabilidadMutacion() {
        if (probabilidadMutacion == null) {
            probabilidadMutacion = new JTextField();
            probabilidadMutacion.setBounds(575, 487, 40, 20);
            probabilidadMutacion.setName("probabilidadMutacion");
            probabilidadMutacion.setEnabled(true);
            probabilidadMutacion.setVisible(false);
            probabilidadMutacion.setAutoscrolls(true);
            probabilidadMutacion.addCaretListener((CaretEvent e) -> {
                try {
                    if (!getProbabilidadMutacion().getText().equalsIgnoreCase("")) {
                        mutationProb = Double.valueOf(getProbabilidadMutacion().getText());
                    }
                } catch (Exception ex) {
                }
            });
        }
        return probabilidadMutacion;
    }

    private JLabel getElitismoLabel() {
        if (elitismoLabel == null) {
            elitismoLabel = new JLabel();
            elitismoLabel.setText(PanelPrincipalConstantes.ALGORITMO_ELITISTA);
            elitismoLabel.setFont(new Font("Serif", Font.PLAIN, 13));
            elitismoLabel.setBounds(30, 640, 700, 15);
            elitismoLabel.setName("elitismoLabel");
        }
        return elitismoLabel;
    }

    private JCheckBox getElitismo() {
        if (elitismo == null) {
            elitismo = new JCheckBox();
            elitismo.setBounds(645, 638, 700, 15);
            elitismo.setVisible(true);
            elitismo.setName("elitismo");
            elitismo.addItemListener((ItemEvent e) -> {
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    elitism = false;
                }
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    elitism = true;
                }
            });
        }
        return elitismo;
    }

    private JLabel getTestParadaLabel() {
        if (testParadaLabel == null) {
            testParadaLabel = new JLabel();
            testParadaLabel.setText(PanelPrincipalConstantes.TEST_PARADA);
            testParadaLabel.setFont(new Font("Serif", Font.PLAIN, 13));
            testParadaLabel.setBounds(30, 680, 400, 15);
            testParadaLabel.setName("testParadaLabel");
        }
        return testParadaLabel;
    }

    private JTextField getTestParada() {
        if (testParada == null) {
            testParada = new JTextField();
            testParada.setBounds(415, 678, 40, 20);
            testParada.setName("testParada");
            testParada.setAutoscrolls(true);
            testParada.addCaretListener((CaretEvent e) -> {
                try {
                    if (!getTestParada().getText().equalsIgnoreCase("")) {
                        test = Integer.valueOf(getTestParada().getText());
                    }
                } catch (Exception ex) {
                }
            });
        }
        return testParada;
    }

    private void creaGrupoSeleccion() {
        ButtonGroup group = new ButtonGroup();
        group.add(getRuletaRadio());
        group.add(getKTorneoRadio());
    }

    private void creaGrupoCruce() {
        ButtonGroup group = new ButtonGroup();
        group.add(getCruce1Punto());
        group.add(getCruce1PuntoSelectivo());
        group.add(getCruceUniforme());
        group.add(getCruceUniformeSelectivo());
    }

    private void creaGrupoMutacion() {
        ButtonGroup group = new ButtonGroup();
        group.add(getMutacionNormalRadio());
        group.add(getMutacionSesgadaRadio());
    }

    private void creaGrupoReemplazamiento() {
        ButtonGroup group = new ButtonGroup();
        group.add(getReemplazamientoHijos());
        group.add(getReemplazamientoMejores());
    }

    private JButton getBotonStart() {
        if (startButton == null) {
            startButton = new JButton();
            startButton.setBounds(225, 730, 100, 20);
            startButton.setPreferredSize(new Dimension(50, 50));
            startButton.setText("Comenzar");
            startButton.setName("ComenzarB");
            startButton.addActionListener(this);
        }
        return startButton;
    }

    private JButton getBotonEnd() {
        if (endButton == null) {
            endButton = new JButton();
            endButton.setBounds(425, 730, 100, 20);
            endButton.setText("Salir");
            endButton.setName("TerminarB");
            endButton.addActionListener(this);
        }
        return endButton;
    }

    private JButton getIniButton() {
        if (iniButton == null) {
            iniButton = new JButton();
            iniButton.setBounds(670, 765, 55, 20);
            iniButton.setText("Inicio");
            iniButton.setName("Inicio");
            iniButton.addActionListener(this);
            iniButton.setVisible(false);
        }
        return iniButton;
    }

    private JProgressBar getBarraProgreso() {
        if (barraProgreso == null) {
            barraProgreso = new JProgressBar(0, 100);
            barraProgreso.setVisible(false);
            barraProgreso.setStringPainted(true);
            barraProgreso.setIndeterminate(true);
            barraProgreso.setString("Calculando...");
            barraProgreso.setBounds(325, 730, 100, 20);
        }
        return barraProgreso;
    }

    private JLabel getResultadoAlgoritmo() {
        if (resultadoAlgoritmo == null) {
            resultadoAlgoritmo = new JLabel();
            resultadoAlgoritmo.setBounds(75, 730, 700, 20);
            resultadoAlgoritmo.setName("resultadoAlgoritmo");
            resultadoAlgoritmo.setEnabled(true);
            resultadoAlgoritmo.setAutoscrolls(true);
            resultadoAlgoritmo.setFont(new Font("Serif", Font.BOLD, 14));
            resultadoAlgoritmo.setForeground(Color.DARK_GRAY);
            resultadoAlgoritmo.setVisible(false);
        }
        return resultadoAlgoritmo;
    }

    /**
     * Función que realizará una tarea y recogerá valores dependiendo del
     * coponente que se haya visto manipulado en el panel principal
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == getCruce1Punto() || source == getCruce1PuntoSelectivo()
                || source == getCruceUniforme() || source == getCruceUniformeSelectivo()) {
            getProbabilidadCruceLabel().setVisible(true);
            getProbabilidadCruceLabel().setEnabled(true);
            getProbabilidadCruce().setVisible(true);
            if (source == getCruce1Punto()) {
                this.cross = PanelPrincipalConstantes.CROSS_1_PUNTO;
            }

            if (source == getCruce1PuntoSelectivo()) {
                this.cross = PanelPrincipalConstantes.CROSS_1_PUNTO_SELECTIVO;
            }
            if (source == getCruceUniforme()) {
                this.cross = PanelPrincipalConstantes.CROSS_UNIFORME;
            }
            if (source == getCruceUniformeSelectivo()) {
                this.cross = PanelPrincipalConstantes.CROSS_UNIFORME_SELECTIVO;
            }
        }
        if (source == getSelectFile()) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                getRuta().setText(selectedFile.getPath());
            }
        }
        if (source == getRuletaRadio()) {
            getKTorneoLabel().setVisible(false);
            getKTorneo().setVisible(false);
            this.selection = PanelPrincipalConstantes.SELECTION_RULETA;
        }
        if (source == getKTorneoRadio()) {
            getKTorneoLabel().setVisible(true);
            getKTorneoLabel().setEnabled(true);
            getKTorneo().setEnabled(true);
            getKTorneo().setVisible(true);
            this.selection = PanelPrincipalConstantes.SELECTION_K_TORNEO;
        }
        if (source == getMutacionNormalRadio() || source == getMutacionSesgadaRadio()) {
            getProbabilidadMutacionLabel().setVisible(true);
            getProbabilidadMutacionLabel().setEnabled(true);
            getProbabilidadMutacion().setVisible(true);
            if (source == getMutacionNormalRadio()) 
                this.mutation = PanelPrincipalConstantes.MUTATION_NORMAL;
            
            if (source == getMutacionSesgadaRadio()) 
                this.mutation = PanelPrincipalConstantes.MUTATION_SESGADA;
            
        }
        if (source == getReemplazamientoHijos()) 
            this.replacement = PanelPrincipalConstantes.REPLACE_HIJOS;
        
        if (source == getReemplazamientoMejores()) 
            this.replacement = PanelPrincipalConstantes.REPLACE_MEJORES;
        
        if (source == getBotonStart()) {
            if (camposValidos()) {
                deshabilitarComponentes();
                setChanged();
                notifyObservers(PanelPrincipalConstantes.ALGORITMO_LISTO);
            } else {
                String salidaError = "Las siguientes opciones/campos no están seleccionados o tienen \nvalores erróneos:\n\n";
                for (int i = 0; i < camposVacios.size(); i++) {
                    salidaError = salidaError + " - " + camposVacios.get(i) + "\n";
                }
                salidaError = salidaError + "\nPor favor, revíselos.";
                JOptionPane.showMessageDialog(null, salidaError);
                camposVacios.clear();
                camposVacios = null;

            }
        }  
            if (source == getBotonEnd()) {
                int dialogResult = JOptionPane.showConfirmDialog(
                        null,
                        "¿Estás seguro de que quieres salir del programa?",
                        "Regresión Simbólica",
                        JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
            if (source == getIniButton()) {
                int dialogResult = JOptionPane.showConfirmDialog(
                        null,
                        "¿Desea volver al inicio?",
                        "Regresión Simbólica",
                        JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    restauraComponentes();
                }
            }
        }
    

    /**
     * Funcion que deshabilita los componentes durante el proceso de cálculo del
     * algoritmo
     */
    private void deshabilitarComponentes() {
        getCruce1Punto().setEnabled(false);
        getCruce1PuntoSelectivo().setEnabled(false);
        getCruceUniforme().setEnabled(false);
        getCruceUniformeSelectivo().setEnabled(false);
        getElitismo().setEnabled(false);
        getKTorneo().setEnabled(false);
        getKTorneoLabel().setEnabled(false);
        getKTorneoRadio().setEnabled(false);
        getMaxOperaciones().setEnabled(false);
        getMutacionNormalRadio().setEnabled(false);
        getMutacionSesgadaRadio().setEnabled(false);
        getProbabilidadCruce().setEnabled(false);
        getProbabilidadCruceLabel().setEnabled(false);
        getProbabilidadMutacion().setEnabled(false);
        getProbabilidadMutacionLabel().setEnabled(false);
        getRangoValorMax().setEnabled(false);
        getRangoValorMin().setEnabled(false);
        getReemplazamientoHijos().setEnabled(false);
        getReemplazamientoMejores().setEnabled(false);
        getRuletaRadio().setEnabled(false);
        getRuta().setEnabled(false);
        getSelectFile().setEnabled(false);
        getTamPoblacion().setEnabled(false);
        getTestParada().setEnabled(false);
        getVariables().setEnabled(false);
        getBotonEnd().setVisible(false);
        getBotonStart().setVisible(false);
        getIniButton().setVisible(false);
        getBarraProgreso().setVisible(true);
        interfaz.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }

    /**
     * Función que habilitará de nuevo los componentes tras realizar el proceso
     * de cálculo de los algoritmos
     */
    private void habilitarComponentes() {
        getResultadoAlgoritmo().setVisible(false);
        getVariables().setEnabled(true);
        getVariables().setText("");
        getMaxOperaciones().setEnabled(true);
        getMaxOperaciones().setText("");
        getTamPoblacion().setEnabled(true);
        getTamPoblacion().setText("");
        getRangoValorMax().setEnabled(true);
        getRangoValorMax().setText("");
        getRangoValorMin().setEnabled(true);
        getRangoValorMin().setText("");
        getRuta().setEnabled(true);
        getRuta().setText("");
        getSelectFile().setEnabled(true);
        botonesSeleccion.clearSelection();
        botonesCruce.clearSelection();
        botonesMutacion.clearSelection();
        botonesReemplazamiento.clearSelection();
        getRuletaRadio().setEnabled(true);
        getKTorneo().setText("");
        getKTorneo().setVisible(false);
        getKTorneoLabel().setVisible(false);
        getKTorneoRadio().setEnabled(true);
        getCruce1Punto().setEnabled(true);
        getCruce1PuntoSelectivo().setEnabled(true);
        getCruceUniforme().setEnabled(true);
        getCruceUniformeSelectivo().setEnabled(true);
        getProbabilidadCruce().setText("");
        getProbabilidadCruce().setVisible(false);
        getProbabilidadCruceLabel().setVisible(false);
        getMutacionNormalRadio().setEnabled(true);
        getMutacionSesgadaRadio().setEnabled(true);
        getProbabilidadMutacion().setText("");
        getProbabilidadMutacion().setVisible(false);
        getProbabilidadMutacionLabel().setVisible(false);
        getReemplazamientoHijos().setEnabled(true);
        getReemplazamientoMejores().setEnabled(true);
        getElitismo().setEnabled(true);
        getElitismo().setSelected(false);
        getProbabilidadCruce().setEnabled(true);
        getProbabilidadCruceLabel().setEnabled(true);
        getProbabilidadMutacion().setEnabled(true);
        getProbabilidadMutacionLabel().setEnabled(true);
        getTestParada().setEnabled(true);
        getTestParada().setText("");
        getBotonStart().setVisible(true);
        getBotonEnd().setVisible(true);
        getIniButton().setVisible(false);
        getBarraProgreso().setVisible(false);
    }

    public int getnVariables() {
        return nVariables;
    }

    public int getL() {
        return L;
    }

    public int getTamanioPoblacion() {
        return tamanioPoblacion;
    }

    public double getLower() {
        return lower;
    }

    public double getUpper() {
        return upper;
    }

    public String getRoute() {
        return route;
    }

    public int getSelection() {
        return selection;
    }

    public int getCross() {
        return cross;
    }

    public int getMutation() {
        return mutation;
    }

    public int getReplacement() {
        return replacement;
    }

    public double getCrossProb() {
        return crossProb;
    }

    public double getMutationProb() {
        return mutationProb;
    }

    public int getTest() {
        return test;
    }

    public int getkTorneo() {
        return kTorneo;
    }

    public boolean getElitism() {
        return elitism;
    }

    public JFrame getInterfaz() {
        return interfaz;
    }

    /**
     * Función que valida los valores que se han introducido en el panel
     * principal
     */
    private boolean camposValidos() {
        boolean ok = true;
        camposVacios = new ArrayList<>();
        if (nVariables <= 0) {
            camposVacios.add("Nº. variables");
        }
        if (L <= 0) {
            camposVacios.add("Nº. máximo de operaciones distintas de {+,-}");
        }
        if (tamanioPoblacion <= 0) {
            camposVacios.add("Tamaño población");
        }
        if ("".equals(getRangoValorMax().getText())) {
            camposVacios.add("Límite superior del rango");
        }
        if ("".equals(getRangoValorMin().getText())) {
            camposVacios.add("Límite inferior del rango");
        }
        if (lower >= upper) {
            camposVacios.add("Límite inferior >= superior del rango");
        }
        if (route == null || "".equals(route)) {
            camposVacios.add("Fichero de puntos muestra originales no escogido");
        }
        if (selection == 0) {
            camposVacios.add("Tipo de selección");
        }
        if (selection == 2 && kTorneo <= 0) {
            camposVacios.add("Valor de K en K-Torneo");
        }
        if (selection == 2 && kTorneo > tamanioPoblacion) {
            camposVacios.add("Valor de K en K-Torneo mayor que el tamaño de la población");
        }
        if (cross == 0) {
            camposVacios.add("Tipo de cruce");
        }
        if (cross > 0 && cross < 5 && (crossProb <= 0 || crossProb > 1)) {
            camposVacios.add("Probabilidad de cruce");
        }
        if (mutation == 0) {
            camposVacios.add("Tipo de mutación");
        }
        if (mutation > 0 && mutation < 3 && (mutationProb <= 0 || mutationProb > 1)) {
            camposVacios.add("Probabilidad de mutación");
        }
        if (replacement == 0) {
            camposVacios.add("Tipo de reemplazamiento");
        }
        if (test <= 0) {
            camposVacios.add("Test de parada");
        }
        if (camposVacios.size() > 0) {
            ok = false;
        }
        return ok;
    }

    public void restauraComponentes() {
        // Se restauran los componentes (cursor por defecto, vista original, etc.)
        habilitarComponentes();
    }

    /**
     * Función que muestra el resultado del algoritmo genético
     *
     * @param resultado
     */
    public void muestraResultado(double resultado) {

        getBarraProgreso().setVisible(false);
        getResultadoAlgoritmo().setText(PanelPrincipalConstantes.TEST_RESULTADO_ALGORITMO + resultado);
        getResultadoAlgoritmo().setVisible(true);
        getIniButton().setVisible(true);
        interfaz.setCursor(Cursor.getDefaultCursor());
        // Se muestra la gráfica
        muestraGrafica();

    }

    private void muestraGrafica() {
        Process p;
        try {
            String path = new java.io.File(".").getCanonicalPath() + PanelPrincipalConstantes.COMANDO_GNUPLOT;
               // C:\Users\Carlos\Documents\NetBeansProjects\ProyectoTT1/gnuplot/gnuplot
            
            System.out.println("");
            String[] comando = {path};
            p = Runtime.getRuntime().exec(comando);
            // Ruta del archivo de entrada
            String rutaPuntosSalida = Utilidades.conseguirRutaRelativa(getRoute()) + "p_salida.dat";
            OutputStream out = p.getOutputStream();
            if (getnVariables() == 1) {
                // Envio de comandos al programa gnuplot para generar la grafica 2D
                out.write("set title 'Regresion Simbolica' \n".getBytes());
                out.write("set xtics -100, 0.5, 100 \n ".getBytes());
                out.write("set ytics -100, 0.5, 100 \n ".getBytes());
                out.write("set grid \n ".getBytes());
                out.write("set grid xtics\n ".getBytes());
                out.write("set grid ytics\n ".getBytes());
                out.write("set xlabel 'Variable x'\n ".getBytes());
                out.write("set ylabel 'Variable y'\n ".getBytes());
                out.write("set style data lines \n ".getBytes());
                out.write("set style line 1 lt 3 lc rgb 'black' lw 6 \n".getBytes());
                out.write("set style line 2 lt 1 lc rgb 'orange' lw 4 \n".getBytes());
                StringBuffer grafica = new StringBuffer("plot [-5:5] '");
                grafica.append(getRoute());
                grafica.append("' ls 1 title 'Puntos Muestra Entrada', '");
                grafica.append(rutaPuntosSalida);
                grafica.append("' ls 2 title 'Resultados_Salida' \n");
                out.write(grafica.toString().getBytes());
            }
            if (getnVariables() == 2) {
                // Envio de comandos al programa gnuplot para generar la grafica 3D
                out.write("set title 'Regresion Simbolica' \n".getBytes());
                out.write("set xtics -100, 0.5, 100 \n ".getBytes());
                out.write("set ytics -100, 0.5, 100 \n ".getBytes());
                out.write("set grid \n ".getBytes());
                out.write("set grid xtics\n ".getBytes());
                out.write("set grid ytics\n ".getBytes());
                out.write("set grid ztics\n ".getBytes());
                out.write("set dgrid3d 30, 30 \n".getBytes());
                out.write("set hidden3d \n".getBytes());
                out.write("set xlabel 'Variable x'\n ".getBytes());
                out.write("set ylabel 'Variable y'\n ".getBytes());
                out.write("set style data lines \n ".getBytes());
                out.write("set parametric \n ".getBytes());
                out.write("set style line 1 lt 3 lc rgb 'black' lw 1 \n".getBytes());
                out.write("set style line 2 lt 1 lc rgb 'blue' lw 1 \n".getBytes());
                out.write("set term aqua 0 \n".getBytes());
                out.write("set view 60, 15 \n".getBytes());
                out.write("set key outside \n".getBytes());
                out.write("set key left bottom Left title 'Leyenda' box \n".getBytes());
                StringBuffer grafica = new StringBuffer("splot [-5:5] '");
                grafica.append(getRoute());
                grafica.append("' ls 1 title \'Puntos Muestra Entrada', '");
                grafica.append(rutaPuntosSalida);
                grafica.append("' ls 2 title 'Resultados_Salida' \n");
                out.write(grafica.toString().getBytes());
                out.write("set term aqua 1 \n".getBytes());
                out.write("set view 60, 80 \n".getBytes());
                out.write(grafica.toString().getBytes());
                out.write("set term aqua 2 \n".getBytes());
                out.write("set view 60, 125 \n".getBytes());
                out.write(grafica.toString().getBytes());
                out.write("set term aqua 3 \n".getBytes());
                out.write("set view 90,0 \n".getBytes());
                out.write("unset ylabel \n".getBytes());
                out.write("unset ytics \n".getBytes());
                out.write("unset dgrid3d \n".getBytes());
                out.write("set xlabel 'Variable x' offset 0, -3 \n".getBytes());
                out.write("set zlabel 'Variable z' offset 0, 0 \n".getBytes());
                out.write("set xtics -100, 0.5, 100 offset 0, -1 \n".getBytes());
                out.write("set offset -1, 0 \n".getBytes());
                out.write("set grid xtics \n".getBytes());
                out.write("unset grid \n".getBytes());
                StringBuffer grafica2 = new StringBuffer("splot [-5:5] '");
                grafica2.append(getRoute());
                grafica2.append("' lt 3 lc rgb 'black' lw 6 title 'Puntos Muestra Entrada', '");
                grafica2.append(rutaPuntosSalida);
                grafica2.append("' lt 1 lc rgb 'blue' lw 5 title 'Resultados_Salida' \n");
                out.write(grafica.toString().getBytes());
            }
            out.flush();
            out.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
