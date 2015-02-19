/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ozielcarneiro
 */
public class Pos {
    
    private double x;
    private double y;
    
    public Pos(){
        
    }
    
    public Pos(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public String toString(){
        return (""+x+", "+y);
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = y;
    }
    
    public static double dist(Pos a, Pos b){
        return (Math.abs((a.getX()-b.getX()))+Math.abs((a.getY()-b.getY())));
    }
    
}
