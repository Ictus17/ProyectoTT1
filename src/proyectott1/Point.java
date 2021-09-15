/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectott1;

import java.math.BigInteger;

/**
 *
 * @author Carlos
 */
public class Point {
    
    private BigInteger x; 
    private BigInteger y; 
    
    
    public  Point(){
    
    }
    public  Point(BigInteger x, BigInteger y){
        this.x = x; 
        this.y = y; 
        
    }
    
    public  Point(BigInteger x, BigInteger y, BigInteger z){
    
    }
    
    public Point add(Point Q,BigInteger p){
        BigInteger λ; 
        BigInteger x,y; 
        
        λ = (Q.y.subtract(this.y)).multiply((Q.x.subtract(this.x)).modInverse(p)); 
        
        x= ((λ.multiply(λ)).subtract(this.x).subtract(Q.x)).modInverse(p); 
        y = (λ.multiply((this.x.subtract(x))).subtract(this.y)).modInverse(p); 
        
        return new Point(x,y); 
    }
    
    public Point substract(Point Q,BigInteger p){
        
        BigInteger λ; 
        BigInteger x,y; 
        
        λ = (Q.y.subtract(this.y)).multiply((Q.x.subtract(this.x)).modInverse(p)); 
        
        x= ((λ.multiply(λ)).subtract(this.x).subtract(Q.x)).modInverse(p); 
        y = (λ.multiply((this.x.subtract(x))).subtract(this.y)).modInverse(p); 
        
        y = y.multiply(new BigInteger("-1")); 
        
        return new Point(x,y);  
    }
    
    
    
}
