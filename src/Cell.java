
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ozielcarneiro
 */
public class Cell {
    
    private Pos[] routPos;
    private Pos[][] antPos;
    private ArrayList<Pos> poolRout;
    private ArrayList<Pos> poolAnt;
    private double fitness;
    private double[] fitRout;
    
    public Cell(Cell c){
        this.routPos = new Pos[4];
        System.arraycopy(c.routPos, 0, this.routPos, 0, 4);
        this.antPos = new Pos[4][8];
        for (int i = 0; i < antPos.length; i++) {
            System.arraycopy(c.antPos[i], 0, this.antPos[i], 0, 8);
        }
        this.poolRout = new ArrayList<>(c.poolRout);
        this.poolAnt = new ArrayList<>(c.poolAnt);
        computeFitness();
    }
    
    public Cell(ArrayList<Pos> poolRout, ArrayList<Pos> poolAnt){
        this.poolRout = new ArrayList<>(poolRout);
        this.poolAnt = new ArrayList<>(poolAnt);
        routPos = new Pos[4];
        antPos = new Pos[4][8];
        for (int i = 0; i < routPos.length; i++) {
            int idx = (int)(Math.random()*this.poolRout.size());
            routPos[i] = this.poolRout.get(idx);
            for (int j = 0; j < antPos[i].length; j++) {
                idx = (int)(Math.random()*this.poolAnt.size());
                antPos[i][j] = this.poolAnt.remove(idx);
            }
        }
        computeFitness();
    }
    
    public Cell clone(double mutation){
        Cell clone = new Cell(this);
        for (int i = 0; i < routPos.length; i++) {
            if(Math.random()>=1-mutation){
                int idx = (int)(Math.random()*clone.poolRout.size());
                clone.routPos[i] = clone.poolRout.get(idx);
            }
            for (int j = 0; j < antPos[i].length; j++) {
                if(Math.random()>=1-mutation){
                    int idx = (int) (Math.random()*clone.poolAnt.size());
                    Pos aux = clone.antPos[i][j];
                    clone.antPos[i][j] = clone.poolAnt.remove(idx);
                    clone.poolAnt.add(aux);
                }
                
            }
        }
        clone.computeFitness();
        return clone;
    }

    public final void computeFitness() {
        fitness = 0;
        fitRout = new double[4];
        for (int i = 0; i < routPos.length; i++) {
            for (int j = 0; j < antPos[i].length; j++) {
                fitness += Pos.dist(routPos[i], antPos[i][j]);
                fitRout[i] += Pos.dist(routPos[i], antPos[i][j]);
            }
            
        }
        //Penalty for not choosing a required value from poolAnt
        for (int i = 0; i < poolAnt.size(); i++) {
            if(poolAnt.get(i).getY()==4.75||poolAnt.get(i).getY()==12.2){
                fitness += 1000;
            }
        }
    }
    
    @Override
    public String toString(){
        String out = ""+getFitness();
        for (int i = 0; i < routPos.length; i++) {
            out += "\nRouter "+(i+1)+": "+routPos[i]+"; fit: "+fitRout[i];
            for (int j = 0; j < antPos[i].length; j++) {
                out += "\nAnt "+(j+1)+": "+antPos[i][j];
            }
        }
        return out;
    }

    /**
     * @return the routPos
     */
    public Pos[] getRoutPos() {
        return routPos;
    }

    /**
     * @param routPos the routPos to set
     */
    public void setRoutPos(Pos[] routPos) {
        this.routPos = routPos;
    }

    /**
     * @return the antPos
     */
    public Pos[][] getAntPos() {
        return antPos;
    }

    /**
     * @param antPos the antPos to set
     */
    public void setAntPos(Pos[][] antPos) {
        this.antPos = antPos;
    }

    /**
     * @return the poolRout
     */
    public ArrayList<Pos> getPoolRout() {
        return poolRout;
    }

    /**
     * @param poolRout the poolRout to set
     */
    public void setPoolRout(ArrayList<Pos> poolRout) {
        this.poolRout = poolRout;
    }

    /**
     * @return the poolAnt
     */
    public ArrayList<Pos> getPoolAnt() {
        return poolAnt;
    }

    /**
     * @param poolAnt the poolAnt to set
     */
    public void setPoolAnt(ArrayList<Pos> poolAnt) {
        this.poolAnt = poolAnt;
    }

    /**
     * @return the fitness
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * @param fitness the fitness to set
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
    
}
