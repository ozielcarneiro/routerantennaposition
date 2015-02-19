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
public class Main {

    private static final double[][] posRouters = {{7.5, 14.9, 22.3, 29.7, 37.1, 44.5, 51.9},
    {8.475, 8.475, 8.475, 8.475, 8.475, 8.475, 8.475}};
    private static final double[][] posAntennas = {{2.1, 5.5, 9.5, 12.9, 16.9, 20.3, 24.3, 27.7, 31.7,
        35.1, 39.1, 42.5, 46.5, 49.9, 53.9, 2.1, 5.5, 9.5,
        20.3, 24.3, 27.7, 31.7, 35.1, 39.1, 42.5, 54.7,
        7.5, 14.9, 22.3, 29.7, 37.1, 44.5, 51.9},
    {12.2, 12.2, 12.2, 12.2, 12.2, 12.2, 12.2, 12.2,
        12.2, 12.2, 12.2, 12.2, 12.2, 12.2, 12.2, 4.75,
        4.75, 4.75, 4.75, 4.75, 4.75, 4.75, 4.75, 4.75,
        4.75, 4.75, 8.475, 8.475, 8.475, 8.475, 8.475, 8.475, 8.475}};

    public static void main(String[] args) {
        ArrayList<Pos> poolRout = new ArrayList<>();
        ArrayList<Pos> poolAnt = new ArrayList<>();
        for (int i = 0; i < posRouters[0].length; i++) {
            poolRout.add(new Pos(posRouters[0][i], posRouters[1][i]));
        }
        for (int i = 0; i < posAntennas[0].length; i++) {
            poolAnt.add(new Pos(posAntennas[0][i], posAntennas[1][i]));
        }
        
        //Population Parameters
        int popSize = 10000;
        int cloneRate = 9000;
        double mutation = 0.1;

        //Population Creation
        Cell[] pop = new Cell[popSize];
        for (int i = 0; i < popSize; i++) {
            pop[i] = new Cell(poolRout, poolAnt);
        }
        //Initial Sort
        pop = sortPop(pop);
        
        Cell best = null;
        //iterative procedure
        for (int i = 0; i < 10000; i++) {
            best = pop[0];
            //Clone
            Cell[] clones = new Cell[cloneRate];
            for (int j = 0; j < cloneRate; j++) {
                double trand = Math.random()*popSize*(popSize+1)/2;
                int idx = ((int)Math.ceil(-0.5*(-(2*popSize+1)+Math.sqrt(Math.pow(2*popSize+1, 2)-8*trand))))-1;
                clones[j] = pop[idx].clone(mutation);
            }
            
            //Substitute Less Fit
            System.arraycopy(clones, 0, pop, popSize-cloneRate, cloneRate);
            
            //Sort
            pop = sortPop(pop);
            if(i%100==0){
                System.out.println(i+": "+pop[0].getFitness());
            }
        }
        
        System.out.println(pop[0]);
    }

    public static Cell[] sortPop(Cell[] pop) {
        if (pop.length > 1) {
            int mid = (int) pop.length / 2;
            Cell[] part1 = new Cell[mid];
            Cell[] part2 = new Cell[pop.length - mid];
            System.arraycopy(pop, 0, part1, 0, mid);
            System.arraycopy(pop, mid, part2, 0, pop.length - mid);
            part1 = sortPop(part1);
            part2 = sortPop(part2);
            pop = mergePop(part1, part2);
        } else {
            return pop;
        }
        return pop;
    }

    public static Cell[] mergePop(Cell[] part1, Cell[] part2) {
        Cell[] pop = new Cell[part1.length + part2.length];
        int ptr1 = 0;
        int ptr2 = 0;
        for (int i = 0; i < pop.length; i++) {
            if (ptr1 < part1.length && ptr2 < part2.length) {
                if (part1[ptr1].getFitness() <= part2[ptr2].getFitness()) {
                    pop[i] = part1[ptr1];
                    ptr1++;
                } else {
                    pop[i] = part2[ptr2];
                    ptr2++;
                }
            } else {
                if (ptr1 < part1.length) {
                    pop[i] = part1[ptr1];
                    ptr1++;
                } else {
                    pop[i] = part2[ptr2];
                    ptr2++;
                }
            }
        }
        return pop;
    }

}
