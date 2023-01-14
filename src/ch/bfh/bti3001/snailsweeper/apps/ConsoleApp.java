package ch.bfh.bti3001.snailsweeper.apps;


import ch.bfh.bti3001.snailsweeper.apps.Grid;

public class ConsoleApp {

	public static void main(String[] args) {

		System.out.println("WELCOME TO SNAILSWEEPER\nu=Uncover, f=Flag/Unflag");

		//initialisation of the game, grid size can be change as a parameter of the Grid object

		Grid consoleGame  = new Grid(15,26);
		consoleGame.initializeGridCells();
		consoleGame.setSnailsAroundGrid();

		//running rounds as long as the game is not finished

		while (!consoleGame.gameOver()){
			consoleGame.round();
		}

		//printing the game finishing grid after the last move, then showing Win or Lose

		System.out.println(consoleGame.printGrid());
		if (consoleGame.hasWon() && !consoleGame.hasLost()) System.out.println("WON!");
		if (!consoleGame.hasWon() && consoleGame.hasLost()){System.out.println("LOST!");}
	}
}
