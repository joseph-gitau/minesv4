package ch.bfh.bti3001.snailsweeper.apps;


import ch.bfh.bti3001.snailsweeper.apps.BorderCell;
import ch.bfh.bti3001.snailsweeper.apps.Cell;
import ch.bfh.bti3001.snailsweeper.apps.CellState;
import ch.bfh.bti3001.snailsweeper.apps.CellVisibility;

import java.util.Scanner;
import static java.lang.Integer.parseInt;

public class Grid {

	private final Cell[][] modelGrid;

	//initializing the Grid with modelGrid as 2D array of Cells, they are all initialized as Cells

	public Grid(int iSize, int jSize){
		this.modelGrid = new Cell[iSize][jSize];
		for (int y = 0; y < modelGrid.length; y++) {
			for (int i = 0; i < modelGrid[0].length; i++) {
				modelGrid[y][i] = new Cell();
			}
		}

	}

	// setting the border cells to border cells and setting the pre-generated solid cells to visible.

	public void initializeGridCells(){
		for (int y =0; y < modelGrid[0].length; y ++){

			modelGrid[0][y] = new BorderCell();
			modelGrid[modelGrid.length-1][y] = new BorderCell();
		}
		for (int y = 0; y<modelGrid.length; y ++){
			modelGrid[y][0] = new BorderCell();
			modelGrid[y][modelGrid[0].length-1] = new BorderCell();
		}

		for (int i=1; i<modelGrid.length-1; i++){
			for (int j =1; j < modelGrid[0].length-1; j++){
				if (modelGrid[i][j].getCellState().equals(CellState.SOLID)){
					modelGrid[i][j].setCellVisibility(CellVisibility.VISIBLE);
				}
			}
		}
	}

	// running through grid and setting a number of surrounding snails for each cell

	public void setSnailsAroundGrid(){
		for (int i=1; i< modelGrid.length-1; i ++){
			for (int j=1; j< modelGrid[0].length-1; j++){
				modelGrid[i][j].setSnailsAround(initSnailsAround(i,j));
			}
		}
	}

	public int initSnailsAround(int i, int j){
		int snailsAround =0;
		if (modelGrid[i-1][j-1].getCellState().equals(CellState.LIVING)){snailsAround++;}
		if (modelGrid[i-1][j].getCellState().equals(CellState.LIVING)){snailsAround++;}
		if (modelGrid[i-1][j+1].getCellState().equals(CellState.LIVING)){snailsAround++;}
		if (modelGrid[i+1][j-1].getCellState().equals(CellState.LIVING)){snailsAround++;}
		if (modelGrid[i+1][j].getCellState().equals(CellState.LIVING)){snailsAround++;}
		if (modelGrid[i+1][j+1].getCellState().equals(CellState.LIVING)){snailsAround++;}
		if (modelGrid[i][j-1].getCellState().equals(CellState.LIVING)){snailsAround++;}
		if (modelGrid[i][j+1].getCellState().equals(CellState.LIVING)){snailsAround++;}
		return snailsAround;
	}

	// keeping track of the important information for the grid and putting it in a String

	public String stateCount(){
		int living =0;
		int crushed =0;
		int flagged =0;
		int hidden =0;
		for (int i=1; i< modelGrid.length; i ++){
			for (int j=1; j< modelGrid[0].length; j++){
				if (modelGrid[i][j].getCellVisibility().equals(CellVisibility.HIDDEN)){
					hidden++;
				}
				if (modelGrid[i][j].getCellVisibility().equals(CellVisibility.FLAGGED)){
					flagged++;
				}
				switch (modelGrid[i][j].getCellState()){
					case LIVING -> living++;
					case CRUSHED -> crushed++;
				}


			}
		}
		return "L="+living+" / C="+crushed+"/ F="+flagged+"/ H="+hidden;
	}

	// method to uncover the wanted Cell and every other Cell according to game logic.
	// the uncover method runs the other way round with inputs and grid coordinates.(test related)

	public void uncover(int i, int j) {
		int x = i;
		i = j;
		j=x;
		if (!this.modelGrid[i][j].getCellVisibility().equals(CellVisibility.HIDDEN)) {
			return;
		}

		if (this.modelGrid[i][j].getCellState().equals(CellState.LIVING)) {
			this.modelGrid[i][j].setCellVisibility(CellVisibility.VISIBLE);
			this.modelGrid[i][j].setCellState(CellState.CRUSHED);

		}
		if (this.modelGrid[i][j].getCellState().equals(CellState.EMPTY)){
			this.modelGrid[i][j].setCellVisibility(CellVisibility.VISIBLE);
			if (modelGrid[i][j].getSnailsAround() == 0) {
				if (i > 0 && j > 0 && i < modelGrid.length-1 && j < modelGrid[0].length-1) {
					int a = i;
					i = j;
					j=a;
					uncover(i-1, j);
					uncover(i+1, j);
					uncover(i, j-1);
					uncover(i, j+1);
				}
			}
		}
	}

	// toggleFlag allows to change to a flagged State or to return to hidden state
	// this method also runs on inverted parameters. (test related)

	public void toggleFlag(int i, int j) {
		int x = i;
		i=j;
		j=x;
		if (modelGrid[i][j].getCellVisibility().equals(CellVisibility.FLAGGED)){
			modelGrid[i][j].setCellVisibility(CellVisibility.HIDDEN);
			return;
		}
		if (modelGrid[i][j].getCellVisibility().equals(CellVisibility.HIDDEN)){
			modelGrid[i][j].setCellVisibility(CellVisibility.FLAGGED);
		}
	}

	// Override method to compare two grids

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Grid g)) {
			return false;
		}
		if (this.modelGrid.length != g.modelGrid.length) {
			return false;
		}
		for (int i = 0; i < this.modelGrid.length; i++) {
			for (int j = 0; j < this.modelGrid[0].length; j++) {
				if (!this.modelGrid[i][j].equals(g.modelGrid[i][j])) {
					return false;
				}
			}
		}
		return true;
	}

	//method to get the actual grid in form of a String

	public String printGrid(){
		StringBuilder gridString = new StringBuilder(" ");
		final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
		for (int a=0; a < modelGrid[0].length; a++){
			gridString.append(ALPHABET.charAt(a));
		}
		gridString = new StringBuilder(gridString.toString().trim());
		gridString.append("\n");
		for (int b =0; b< modelGrid.length; b++){
			for (int a =0; a<modelGrid[0].length; a ++){
				switch (modelGrid[b][a].getCellVisibility()){
					case HIDDEN ->  gridString.append("X");
					case FLAGGED -> gridString.append("F");
					case VISIBLE  -> {
						switch (modelGrid[b][a].getCellState()) {

							case LIVING -> gridString.append("L");
							case CRUSHED -> gridString.append("C");
							case EMPTY -> {
								if (modelGrid[b][a].getSnailsAround() > 0) {
									gridString.append(modelGrid[b][a].getSnailsAround());
								} else {
									gridString.append(" ");
								}
							}
							case SOLID ->  gridString.append("#");
						}
					}
					default ->
							throw new IllegalStateException("Unexpected value: " + modelGrid[b][a].getCellVisibility());
				}
			}
			gridString.append(b).append("\n");
		}
		gridString.append(stateCount());
		return gridString.toString();
	}

	// method to get in an outputs for a round, combining methods to uncover or flag/unflag cells

	public void round(){
		final String ALPHABETLOOKUP = "abcdefghijklmnopqrstuvwxyz";
		String entry = "  ";
		int i =0;

		Scanner sc = new Scanner(System.in);
		System.out.println(printGrid());

		while (entry.length()!=3|| !(entry.charAt(0) =='u') && !(entry.charAt(0)=='f')){
			System.out.println(">");
			entry = sc.next();
		}
		for (int a =0; a < ALPHABETLOOKUP.length(); a++){
			if (entry.charAt(1)==ALPHABETLOOKUP.charAt(a)){
				i=a;
				break;
			}
		}
		int j =parseInt(String.valueOf(entry.charAt(2)));
		switch (entry.charAt(0)){
			case 'u' -> uncover(i,j);
			case 'f' -> toggleFlag(i,j);
			default -> throw new IllegalStateException("Unexpected value: " + entry.charAt(0));
		}

	}

	// checking States and Visibilities to see if conditions for game end are accomplished

	public boolean gameOver() {
		for (Cell[] cells : modelGrid)
			for (int a = 0; a < modelGrid[0].length; a++)
				if (cells[a].getCellState() == CellState.CRUSHED) return true;
		for (Cell[] cells : modelGrid) {
			for (int a = 0; a < modelGrid[0].length; a++) {
				switch (cells[a].getCellState()) {

					case LIVING -> {
						if (cells[a].getCellVisibility().equals(CellVisibility.HIDDEN)) {
							return false;
						}
					}
					case EMPTY -> {
						if (cells[a].getCellVisibility().equals(CellVisibility.FLAGGED)) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	// checking losing conditions
	public boolean hasLost() {
		for (Cell[] cells : modelGrid) {
			for (int a = 0; a < modelGrid[0].length; a++)
				if (cells[a].getCellState() == CellState.CRUSHED) {
					return true;
				}
		}
		return false;
	}

	//checking winning conditions

	public boolean hasWon(){
		for (Cell[] cells : modelGrid) {
			for (int a = 0; a < modelGrid[0].length; a++) {
				switch (cells[a].getCellState()) {
					case EMPTY, LIVING -> {
						if (cells[a].getCellVisibility().equals(CellVisibility.HIDDEN)) {
							return false;
						}
					}
					case CRUSHED -> {
						return false;
					}
				}
			}
		}
		return true;
	}

	// getter

	public Cell[][] getModelGrid() {
		return modelGrid;
	}
}
