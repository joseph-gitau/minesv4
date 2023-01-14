package ch.bfh.bti3001.snailsweeper.model;

import java.util.Random;

public class Model {

    // Constants for the different cell states
    public static final char LIVING_SNAIL = 'L';
    public static final char CRUSHED_SNAIL = 'C';
    public static final char EMPTY = 'E';
    public static final char SOLID = 'S';

    // Constants for the different cell visibility states
    public static final char HIDDEN = 'H';
    public static final char VISIBLE = 'V';
    public static final char FLAGGED = 'F';

    // 2D array to represent the grid of cells
    private char[][] grid;
    private char[][] visibilities;
    private int width;
    private int height;
    private int livingSnails;
    private int flaggedSnails;
    private int crushedSnails;

    public Model(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new char[height][width];
        this.visibilities = new char[height][width];
        this.livingSnails = 0;
        this.flaggedSnails = 0;
        this.crushedSnails = 0;
    }

    // Method to initialize the grid cells
    public void initializeGridCells() {
        Random rand = new Random();
        double probability = 0.1;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0 || i == height - 1 || j == 0 || j == width - 1) {
                    grid[i][j] = SOLID;
                    visibilities[i][j] = VISIBLE;
                } else {
                    double p = rand.nextDouble();
                    if (p < probability) {
                        grid[i][j] = LIVING_SNAIL;
                        livingSnails++;
                    } else {
                        grid[i][j] = EMPTY;
                    }
                    visibilities[i][j] = HIDDEN;
                }
            }
        }
    }

    // Method to set the number of snails around the grid
    public void setSnailsAroundGrid() {
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                if (grid[i][j] != LIVING_SNAIL) {
                    int count = 0;
                    for (int k = -1; k <= 1; k++) {
                        for (int l = -1; l <= 1; l++) {
                            if (grid[i + k][j + l] == LIVING_SNAIL) {
                                count++;
                            }
                        }
                    }
                    grid[i][j] = (char) (count + '0');
                }
            }
        }
    }

    // Method to uncover a cell at a given position
    public void uncover(int x, int y) {
        if (visibilities[x][y] == HIDDEN) {
            visibilities[x][y] = VISIBLE;
            if (grid[x][y] == LIVING_SNAIL) {
                crushedSnails++;
                grid[x][y] = CRUSHED_SNAIL;
            } else if (grid[x][y] == EMPTY) {
                uncoverAdjacentCells(x, y);
            }
        }
    }

    // Method to uncover adjacent cells
    public void uncoverAdjacentCells(int x, int y) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (x + i >= 0 && x + i < height && y + j >= 0 && y + j < width) {
                    uncover(x + i, y + j);
                }
            }
        }
    }

    // Method to toggle the flag of a cell at a given position
    public void toggleFlag(int x, int y) {
        if (visibilities[x][y] == HIDDEN) {
            visibilities[x][y] = FLAGGED;
            if (grid[x][y] == LIVING_SNAIL) {
                flaggedSnails++;
            }
        } else if (visibilities[x][y] == FLAGGED) {
            visibilities[x][y] = HIDDEN;
            if (grid[x][y] == LIVING_SNAIL) {
                flaggedSnails--;
            }
        }
    }

    // Method to check if the game is over
    public boolean gameOver() {
        return hasLost() || hasWon();
    }

    // Method to check if the player has lost the game
    public boolean hasLost() {
        return crushedSnails > 0;
    }

    // Method to check if the player has won the game
    public boolean hasWon() {
        return livingSnails == flaggedSnails;
    }

    // Overriding the equals method to compare two Grid objects
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        Model other = (Model) obj;
        if (this.width != other.width || this.height != other.height)
            return false;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (this.grid[i][j] != other.grid[i][j])
                    return false;
                if (this.visibilities[i][j] != other.visibilities[i][j])
                    return false;
            }
        }
        return true;
    }
}
