package ch.bfh.bti3001.snailsweeper.apps;

import java.util.ArrayList;
import java.util.Random;

public class Cell {
    protected CellVisibility cellVisibility;
    protected CellState cellState;

    protected int snailsAround = 0;

    // for cells that are not on the border the default visibility is Hidden. The value changes over game time
    public Cell(){
        this.cellVisibility = CellVisibility.HIDDEN ;
        this.cellState = initialCellState();
    }

    //provides a boolean based on a decimal probability.
    public boolean getRadom(double decimalChance){
        boolean chance = false;
        decimalChance = decimalChance*100;
        ArrayList<Integer> pickBag= new ArrayList<>();
        for (int i =1; i< decimalChance;i++){
            pickBag.add(1);
        }
        Random rand = new Random();
        if (pickBag.contains(rand.nextInt(10))){
            chance= true;
        }
        return chance;
    }
    // Initializes the states (snail or solid or not) on random input.
    private CellState initialCellState(){
        if (getRadom(0.1)){ return CellState.LIVING;}
        if (getRadom(0.1)){ return CellState.SOLID;}
        else {return CellState.EMPTY;}
    }

    // overrides the default "Cell" equals.
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Cell c)) {
            return false;
        }
        if (this.cellVisibility.equals(c.cellVisibility))
            return this.cellState.equals(c.cellState);
        return  false;
    }


    // getter and setter
    public CellVisibility getCellVisibility() {
        return cellVisibility;
    }

    public void setCellVisibility(CellVisibility cellVisibility) {
        this.cellVisibility = cellVisibility;
    }

    public CellState getCellState() {
        return cellState;
    }

    public void setCellState(CellState cellState) {
        this.cellState = cellState;
    }

    public int getSnailsAround() {
        return snailsAround;
    }

    public void setSnailsAround(int snailsAround) {
        this.snailsAround = snailsAround;
    }

}
