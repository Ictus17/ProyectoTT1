/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectott1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author Carlos
 */

/**/
public class ECC_Algorithm {

    public static BigInteger fromDigits(ArrayList<BigInteger> l, BigInteger b) {
        //dada una lista de números n, representarlos como la concatenación de ese número 
        //luego, ese número que está en base b, convertirlo a base 10. 

        BigInteger s = new BigInteger("0");
        int n = 0;
        Collections.reverse(l);
        for (BigInteger d : l) {
            s = s.add(d.multiply(b.pow(n)));
            n++;
        }

        return s;
    }

    public static ArrayList<Integer> IntegerDigits(BigInteger n, BigInteger base) {

        ArrayList<Integer> l = new ArrayList<>();

        while (!n.equals(BigInteger.ZERO)) {
            l.add(n.mod(base).intValue());
            n = n.divide(base);
        }

        Collections.reverse(l);
        return l;
    }
    
    
    
    
    public static ArrayList<BigInteger> crearLista(String text) {
        ArrayList<Integer> a = new ArrayList<>();

        for (int i = 0; i < text.length(); i++) {
            a.add((int) text.charAt(i));
        }

        //System.out.println(Arrays.toString(a.toArray()));

        ArrayList<BigInteger> l = new ArrayList<>();

        for (int i : a) {
            l.add(new BigInteger("" + i));
        }

        return l;
    }

    public static void main(String[] args) {
        
        BigInteger base = new BigInteger("65536"); 
        
        BigInteger a = new BigInteger("-3");

        BigInteger b = new BigInteger("2455155546008943817740293915197451784769108058161191238065");

        BigInteger p = new BigInteger("6277101735386680763835789423207666416083908700390324961279");

        BigInteger nB = new BigInteger("28186466892849679686038856807396267537577176687436853369");

        BigInteger G[] = {new BigInteger("60204628237568865675821348058752611191669876636884684818"),
            new BigInteger("174050332293622031404857552280219410364023488927386650641")};

        BigInteger Pb[] = {new BigInteger("2803000786541617331377384897435095499124748881890727495642"),
            new BigInteger("4269718021105944287201929298168253040958383009157463900739")};
        
        
        
        
        /*Input text*/
        String text = "National Institute of Technology, Manipur, 795001 (English)";
        /*ASCII equivalent values*/
        ArrayList<Integer> ASCIIval = new ArrayList<Integer>(){{for(int i = 0; i<text.length(); i++)add((int)text.charAt(i)); }};
        
        System.out.print("[");
        for(Object i: ASCIIval){System.out.print(i+",");}
        System.out.println("]"+"s:"+ASCIIval.size());
        
        /* Group the ASCII values with size calculated as 
        Length [IntegerDigits [p, 65536]] - 1 
        which we get as 11*/
        
        int size = IntegerDigits(p,base).size()-1; 
        
        ArrayList<BigInteger> aux = new ArrayList<>(); 
        
        
        ArrayList<BigInteger> list = new ArrayList<>(); 
        
        for(int i = 0; i<ASCIIval.size(); i++){
            
            if(i%size ==0){
                /*Convert each group into big integers using FromDigits function with base 65536*/
                System.out.println(Arrays.toString(aux.toArray())+"s:"+aux.size());
                
                if(!aux.isEmpty()){
                    list.add(fromDigits(aux,base)); 
                    System.out.println(list.get(list.size()-1).toString());}
                aux.clear();
            }
            aux.add(new BigInteger(""+ASCIIval.get(i))); 
                
        }
        
        /*Pad with 32 at the end of the above list if 
        the number of term is odd, so that pairing can be done*/
        if(list.size()%2 == 1) list.add(new BigInteger("32")); 
        
           
        
    }

}
