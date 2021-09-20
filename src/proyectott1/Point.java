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
    public static BigInteger a; 
    
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
        
        x= ((λ.multiply(λ)).subtract(this.x).subtract(Q.x)).mod(p); 
        y = (λ.multiply((this.x.subtract(x))).subtract(this.y)).mod(p); 
        
        return new Point(x,y); 
    }
    
    public Point doubling(Point Q,BigInteger p){
        BigInteger λ; 
        BigInteger x3,y3,x1=Q.x,y1=Q.y; 
        
        λ = (new BigInteger("3").multiply(x1).multiply(x1).add(a)).multiply(new BigInteger("2").multiply(y1).modInverse(p)); 
        
        x3= ((λ.multiply(λ)).subtract(new BigInteger("2").multiply(this.x))).mod(p); 
        y3 = (λ.multiply((x1.subtract(x3))).subtract(y1)).mod(p); 
        
        return new Point(x3,y3); 
    }
    
    
    public Point multiply(Point Q, BigInteger k, BigInteger p){
        
        long k_ = k.longValue(); 
        
        Point A = new Point(); 
        
        if(k_ == 1) return Q; 
        
        for(int i = 0; i<k_; i++) A = Q.doubling(Q, p); 
            
        
        
        return A; 
        
        
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
