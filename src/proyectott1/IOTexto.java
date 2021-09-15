/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectott1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Carlos
 */
public class IOTexto {
    
    public static String textoEntrada = ""; 
    
    public static void leerTexto(){
        
          FileNameExtensionFilter filtro =
                    new FileNameExtensionFilter("Texto","txt");
            // crear un selector de archivos
            JFileChooser selector = new JFileChooser();
            // agregar el filtro al selector
            selector.addChoosableFileFilter(filtro);
            // especificar que solo se puedan abrir archivos
            selector.setFileSelectionMode(JFileChooser.FILES_ONLY);
            
            //ejecutar el selector de imagenes
            
            int res = selector.showOpenDialog(null);
            
            if (res == 1 ){
                
                return ;
                
            }
          
            File archivo = selector.getSelectedFile();
            
            FileReader fr = null;
            BufferedReader br = null;
            String linea = "";
            
            
            
             try {
                // Apertura del fichero y creacion de BufferedReader para poder
                // hacer una lectura comoda (disponer del metodo readLine()).
               
                fr = new FileReader (archivo);
                br = new BufferedReader(fr);

                // Lectura del fichero
                
                while((linea=br.readLine())!=null)
                   textoEntrada+=(linea+'\n'); 
             }
             catch(Exception e){
                e.printStackTrace();
             }finally{
                // En el finally cerramos el fichero, para asegurarnos
                // que se cierra tanto si todo va bien como si salta 
                // una excepcion.
                try{                    
                   if( null != fr ){   
                      fr.close();     
                   }                  
                }catch (Exception e2){ 
                   e2.printStackTrace();
                }
             }

            
            
            
            return;
        
    }
    
    
    
    public static void guardarTexto(String texto){
        
        
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
                                    //esta es la ruta xd
            fichero = new FileWriter("prueba.txt");
            pw = new PrintWriter(fichero);
            pw.print(texto);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
        
        
        
    }
    
    
    
    
    
    
    
    public static void main(String[] args) {
        
        
        IOTexto.leerTexto(); 
        
        System.out.println(IOTexto.textoEntrada);
        
        
        
    }
    
    
}
