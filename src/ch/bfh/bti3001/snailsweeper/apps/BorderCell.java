package ch.bfh.bti3001.snailsweeper.apps;

public class BorderCell extends Cell{

    // class for all cells that are on the border of the grid  ==> default values VISIBLE and SOLID
    public BorderCell (){
        this.cellVisibility = CellVisibility.VISIBLE;
        this.cellState =  CellState.SOLID;
    }
}
