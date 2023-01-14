package ch.bfh.bti3001.snailsweeper.apps;


import ch.bfh.bti3001.snailsweeper.apps.Cell;
import ch.bfh.bti3001.snailsweeper.apps.CellState;
import ch.bfh.bti3001.snailsweeper.apps.CellVisibility;

public class GridSerializer {

    //taking a grid and turning it into a test-suitable string (to compare grids)
	public String serialize(Grid grid) {
        Cell[][] modelGrid = grid.getModelGrid();
        StringBuilder serializedGrid = new StringBuilder("_");
        for (Cell[] cells : modelGrid) {
            for (int a = 0; a < modelGrid[0].length; a++) {
                switch (cells[a].getCellState()) {
                    case SOLID -> serializedGrid.append("S");
                    case CRUSHED -> serializedGrid.append("C");
                    case LIVING -> serializedGrid.append("L");
                    case EMPTY -> serializedGrid.append("E");
                }
                switch (cells[a].getCellVisibility()) {
                    case VISIBLE -> serializedGrid.append("V");
                    case FLAGGED -> serializedGrid.append("F");
                    case HIDDEN -> serializedGrid.append("H");
                }
                serializedGrid.append(" ");
            }
            serializedGrid.append("| ");
        }
        serializedGrid = new StringBuilder(serializedGrid.toString().replace("_", ""));
        return serializedGrid.toString();
    }

    // taking a formatted String and turning it into a playable grid

    public Grid deserialize(String str) {

        // counting lines and deducting columns

        int linesCount = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '|') {
                linesCount++;
            }
        }
        int columnsCount = (str.length() - linesCount) / (linesCount * 3);

        //generating grid and formatting the String to get information to correctly initialize the cells

        Grid grid = new Grid(linesCount, columnsCount);
        Cell[][] modelGrid = grid.getModelGrid();
        str = str.replaceAll("\\|", "");
        str = str.replaceAll(" ", "");
        int index = 0;
        for (Cell[] cells : modelGrid) {
            for (int a = 0; a < modelGrid[0].length; a++) {
                switch (str.charAt(index)) {
                    case 'S' -> cells[a].setCellState(CellState.SOLID);
                    case 'E' -> cells[a].setCellState(CellState.EMPTY);
                    case 'C' -> cells[a].setCellState(CellState.CRUSHED);
                    case 'L' -> cells[a].setCellState(CellState.LIVING);
                }
                index++;
                switch (str.charAt(index)) {
                    case 'V' -> cells[a].setCellVisibility(CellVisibility.VISIBLE);
                    case 'H' -> cells[a].setCellVisibility(CellVisibility.HIDDEN);
                    case 'F' -> cells[a].setCellVisibility(CellVisibility.FLAGGED);

                }
                index++;
            }
        }
        grid.setSnailsAroundGrid();
        return grid;
    }

}


