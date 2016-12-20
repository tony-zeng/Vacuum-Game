package game;

import sprites.CleanHallway;
import sprites.Dumpster;
import sprites.Dust;
import sprites.DustBall;
import sprites.Sprite;
import sprites.Vacuum;
import sprites.Wall;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * A class that represents the basic functionality of the vacuum game. This class is responsible for
 * performing the following operations: 1. At creation, it initializes the instance variables used
 * to store the current state of the game. 2. When a move is specified, it checks if it is a legal
 * move and makes the move if it is legal. 3. It reports information about the current state of the
 * game when asked.
 */
public class VacuumGame {

  private Random random; // a random number generator to move the DustBalls
  private Grid<Sprite> grid; // the grid
  private Vacuum vacuum1; // the first player
  private Vacuum vacuum2; // the second player
  private List<Dust> dusts; // the dusts
  private List<DustBall> dustBalls; // the dust balls

  /**
   * Creates a new <code>VacuumGame</code> that corresponds to the given input text file. Assumes
   * that the input file has one or more lines of equal lengths, and that each character in it
   * (other than newline) is a character that represents one of the <code>Sprite</code>s in this
   * game. Uses gridType to implement the grid.
   * 
   * @param layoutFileName path to the input grid file
   * @param gridType the type of grid implementation to use
   */
  public VacuumGame(String layoutFileName, Constants.GridType gridType) throws IOException {
    dusts = new ArrayList<>();
    dustBalls = new ArrayList<>();
    random = new Random();

    // open the file, read the contents, and determine dimensions of the
    // grid
    int[] dimensions = getDimensions(layoutFileName);
    int numRows = dimensions[0];
    int numColumns = dimensions[1];
    if (gridType.equals(Constants.GridType.LIST_GRID)) {
      grid = new ListGrid<>(numRows, numColumns);
    } else {
      grid = new MapGrid<>(numRows, numColumns);
    }

    // open the file again, read the contents, and store them in grid
    Scanner sc = new Scanner(new File(layoutFileName));
    for (int row = 0; row < numRows; row++) {
      String nextLine = sc.nextLine();
      // for loop to add every character in nextLine to the current row
      /********
       * Initialize the grid
       ********/
      for (int column = 0; column < numColumns; column++) {
        // relate the character of the string to the object
        grid.setCell(row, column, converter(row, column, nextLine.charAt(column)));
      }
      ;
    }
    sc.close();
  }

  /*********
   * Lots of methods.
   ************/
  /**
   * Takes in a character and converts it into a sprite object according to the symbol and character
   * taken.
   * 
   * @param row location for sprite that has been converted
   * @param col location for sprite that has been convereted
   * @param letter character value that corresponds the a Sprite
   * @return the Sprite the corresponds to the character value being inputted
   */
  private Sprite converter(int row, int col, char letter) {
    // base case is clean hallway
    Sprite sprite = new CleanHallway(row, col);
    // checks to see if letter is wall
    if (letter == Constants.WALL) {
      sprite = new Wall(row, col);
    } else if (letter == Constants.DUMPSTER) {
      // checks to see if letter is dumpster
      sprite = new Dumpster(row, col);
    } else if (letter == Constants.DUST) {
      // checks to see if letter is dust
      sprite = new Dust(row, col, 1);
      dusts.add((Dust) sprite); // add dust into the dust list for game
    } else if (letter == Constants.DUST_BALL) {
      // checks to see if letter is dustball
      sprite = new DustBall(row, col, 2);
      dustBalls.add((DustBall) sprite); // add all dustballs into dustball list for gmae
    } else if (letter == Constants.P1) {
      vacuum1 = new Vacuum(Constants.P1, row, col, Constants.CAPACITY);
      vacuum1.setUnder(sprite);
      return vacuum1;
    } else if (letter == Constants.P2) {
      vacuum2 = new Vacuum(Constants.P2, row, col, Constants.CAPACITY);
      vacuum2.setUnder(sprite);
      return vacuum2;
    }
    return sprite;
  }

  /**
   * Counts the number of rows in grid.
   * 
   * @return an integer value of the number of rows
   */
  public int getNumRows() {
    return grid.getNumRows();
    // get numRows from Grid method
  }

  /**
   * Counts the number of columns in grid.
   * 
   * @return an integer value of the number of columns
   */
  public int getNumColumns() {
    return grid.getNumColumns();
    // get numCols from grid method
  }

  /**
   * Calls the grid method getCell and return the sprite currently residing on that cell.
   * 
   * @param row the row which Sprite resides on
   * @param col the column which Sprite resides on
   * @return The sprite that is on (row,col) on the grid
   */
  public Sprite getSprite(int row, int col) {
    return grid.getCell(row, col);
    // returns the object sprite at the current row/col
  }

  /**
   * Gets the grid class.
   * 
   * @return the grid class
   */
  public Grid<Sprite> getGrid() {
    return grid;
    // returns the grid
  }

  /**
   * Gets the sprite class of player 1's vacuum.
   * 
   * @return the sprite of player 1's vacuum
   */
  public Vacuum getVacuum1() {
    return vacuum1;
  }

  /**
   * Gets the sprite class of player 2's vacuum.
   * 
   * @return the sprite of player 2's vacuum
   */
  public Vacuum getVacuum2() {
    return vacuum2;
  }

  /**
   * Automatically cleans any dust balls vacuums go under. Checks capacity and auto dump when vacuum
   * are under dumpsters, only if the action(s) can be performed.
   * 
   * @param vacuum specifies which vacuum is cleaning
   * @return true or false depending on whether dusts/dustballs were cleaned
   */
  private boolean autocleaner(Vacuum vacuum) {
    if (vacuum.getUnder().getSymbol() == Constants.DUST) {
      if (vacuum.clean(Constants.DUST_SCORE)) {
        dusts.remove(0);
        return true;
      }
    } else if (vacuum.getUnder().getSymbol() == Constants.DUST_BALL) {
      // if it is a dustball
      if (vacuum.clean(Constants.DUST_BALL_SCORE)) {
        for (int ball = 0; ball < dustBalls.size(); ball++) {
          // removing specific dustball
          if (dustBalls.get(ball).getRow() == vacuum.getRow()
              && dustBalls.get(ball).getColumn() == vacuum.getColumn()) {
            dustBalls.remove(ball);
          }
        }
        return true;
      }
    } else if (vacuum.getUnder().getSymbol() == Constants.DUMPSTER) {
      // if it is a dumpster
      vacuum.empty();
      return false;
    }
    return false;
  }

  /**
   * Updates all horizontal movement by changing row value on grid and sprite.
   * 
   * @param vacuum shows which vacuum is being moved
   * @param direction value showing whether the vacuum moves left or right
   */
  private void moveHUpdate(Vacuum vacuum, Integer direction) {
    grid.setCell(vacuum.getRow(), vacuum.getColumn(), vacuum.getUnder());
    // updategrid for sprite under vacuum
    vacuum.moveTo(vacuum.getRow(), vacuum.getColumn() + direction);
    // move vacuum
    vacuum.setUnder(getSprite(vacuum.getRow(), vacuum.getColumn()));
    // set what is under the new vacuum
    if (autocleaner(vacuum)) {
      // clean dust/dustballs if possible, if cleaned then set under as clean
      // hallway
      Sprite hallway = new CleanHallway(vacuum.getRow(), vacuum.getColumn());
      vacuum.setUnder(hallway);
    }
    grid.setCell(vacuum.getRow(), vacuum.getColumn(), vacuum);
    // update grid for new vacuum1 position
  }

  /**
   * Updates all vertical movement by changing column value on grid and sprite.
   * 
   * @param vacuum shows which vacuum is being moved
   * @param direction value showing whether the vacuum moves left or right
   */
  private void moveVUpdate(Vacuum vacuum, Integer direction) {
    grid.setCell(vacuum.getRow(), vacuum.getColumn(), vacuum.getUnder());
    // update grid for sprite under vacuum
    vacuum.moveTo(vacuum.getRow() + direction, vacuum.getColumn());
    // move vacuum
    vacuum.setUnder(getSprite(vacuum.getRow(), vacuum.getColumn()));
    // set what is under the new vacuum
    if (autocleaner(vacuum)) {
      Sprite hallway = new CleanHallway(vacuum.getRow(), vacuum.getColumn());
      vacuum.setUnder(hallway);
    }
    grid.setCell(vacuum.getRow(), vacuum.getColumn(), vacuum);
    // update grid for new vacuum1 position
  }

  /**
   * Checks if either vacuum is moving into a wall or another vacuum.
   * 
   * @param row integer location of where the object is supposed to move.
   * @param col integer location of where the object is supposed to move.
   * @return true or false value depending on whether the location is movable.
   */
  private boolean restrictions(int row, int col) {
    if (getSprite(row, col).getSymbol() == Constants.WALL
        || getSprite(row, col).getSymbol() == Constants.P2
        || getSprite(row, col).getSymbol() == Constants.P1) {
      // if direction moving to is a wall
      return true;
    } else {
      return false;
    }
  }

  /**
   * Moves all dustballs in the grid in a random direction every time move is called.
   */
  private void randomMove() {
    for (int ball = 0; ball < dustBalls.size(); ball++) { // move every dustball
      int randDirection = (random.nextInt(5) - 2);
      // call restrictions
      int dustBallRow = dustBalls.get(ball).getRow();
      int dustBallCol = dustBalls.get(ball).getColumn();
      if (randDirection == -2 || randDirection == 2) {
        randDirection = randDirection / 2;
        if ((getSprite(dustBallRow + randDirection, dustBallCol).getSymbol() == Constants.CLEAN
            || getSprite(dustBallRow + randDirection, dustBallCol).getSymbol() == Constants.DUST)
            && vacuum1.getUnder() != dustBalls.get(ball)
            && vacuum2.getUnder() != dustBalls.get(ball)) {
          // generate new dust
          Dust dust = new Dust(dustBallRow, dustBallCol, Constants.DUST_SCORE);
          // considering already existing dust case
          if (getSprite(dustBallRow + randDirection, dustBallCol).getSymbol() == Constants.CLEAN) {
            dusts.add(dust);
          }
          grid.setCell(dustBallRow, dustBallCol, dust); // update grid for new dust cell
          // if it moves under a vacuum
          if (getSprite(dustBallRow + randDirection, dustBallCol).getSymbol() == Constants.P1) {
            dustBalls.get(ball).moveTo(dustBallRow + randDirection, dustBallCol);
            vacuum1.setUnder(dustBalls.get(ball));
          } else if (getSprite(dustBallRow + randDirection, dustBallCol)
              .getSymbol() == Constants.P2) {
            dustBalls.get(ball).moveTo(dustBallRow + randDirection, dustBallCol);
            vacuum2.setUnder(dustBalls.get(ball));
          } else {
            grid.setCell(dustBallRow + randDirection, dustBallCol, dustBalls.get(ball));
            // update grid
            dustBalls.get(ball).moveTo(dustBallRow + randDirection, dustBallCol);
          }
        }
      } else if (randDirection == -1 || randDirection == 1) {
        if ((getSprite(dustBallRow, dustBallCol + randDirection).getSymbol() == Constants.CLEAN
            || getSprite(dustBallRow, dustBallCol + randDirection).getSymbol() == Constants.DUST)
            && vacuum1.getUnder() != dustBalls.get(ball)
            && vacuum2.getUnder() != dustBalls.get(ball)) { // same restrictions as vacuum + no
          // same restrictions as vacuum + no hitting dumpsters or dustballs
          // generate new dust
          Dust dust = new Dust(dustBallRow, dustBallCol, Constants.DUST_SCORE);
          // new dust left by dustball
          // considering already existing dust case
          if (getSprite(dustBallRow, dustBallCol + randDirection).getSymbol() == Constants.CLEAN) {
            dusts.add(dust);
          }
          grid.setCell(dustBallRow, dustBallCol, dust); // update grid for new dust cell

          // if dustball moves under a vacuum
          if (getSprite(dustBallRow, dustBallCol + randDirection).getSymbol() == Constants.P1) {
            dustBalls.get(ball).moveTo(dustBallRow + randDirection, dustBallCol);
            vacuum1.setUnder(dustBalls.get(ball));
          } else if (getSprite(dustBallRow, dustBallCol + randDirection)
              .getSymbol() == Constants.P2) {
            dustBalls.get(ball).moveTo(dustBallRow, dustBallCol + randDirection);
            vacuum2.setUnder(dustBalls.get(ball));
          } else {
            grid.setCell(dustBallRow, dustBallCol + randDirection, dustBalls.get(ball));
            // update grid
            dustBalls.get(ball).moveTo(dustBallRow, dustBallCol + randDirection);
          }
        }
      }
    }
  }

  /**
   * Update coordinates of either vacuum1 or vacuum2. Vacuums cannot move to walls and other vacuums
   * but can move to everything else. Vacuums clean dust and dustballs until they are full. Vacuums
   * empty their capacity at dumpsters
   * 
   * @param nextMove character representing which way vacuum will move.
   */
  public void move(char nextMove) {
    System.out.println(dusts);
    // if move is p1 up
    if (nextMove == Constants.P1_UP) {
      if (!restrictions(vacuum1.getRow() + Constants.UP, vacuum1.getColumn())) {
        moveVUpdate(vacuum1, Constants.UP);
        // move dustballs
        randomMove();
      }
    } else if (nextMove == Constants.P2_UP) {
      // if move is p2 up
      if (!restrictions(vacuum2.getRow() + Constants.UP, vacuum2.getColumn())) {
        moveVUpdate(vacuum2, Constants.UP);
        // move dustballs
        randomMove();
      }
    } else if (nextMove == Constants.P2_LEFT) {
      // if move is p2 left
      if (!restrictions(vacuum2.getRow(), vacuum2.getColumn() + Constants.LEFT)) {
        moveHUpdate(vacuum2, Constants.LEFT);
        // move dustballs
        randomMove();
      }
    } else if (nextMove == Constants.P1_LEFT) {
      // if move is p1 left
      if (!restrictions(vacuum1.getRow(), vacuum1.getColumn() + Constants.LEFT)) {
        moveHUpdate(vacuum1, Constants.UP);
        // move dustballs
        randomMove();
      }
    } else if (nextMove == Constants.P1_RIGHT) {
      // if move is p1 right
      if (!restrictions(vacuum1.getRow(), vacuum1.getColumn() + Constants.RIGHT)) {
        moveHUpdate(vacuum1, Constants.RIGHT);
        // move dustballs
        randomMove();
      }
    } else if (nextMove == Constants.P2_RIGHT) {
      // if move is p2 right
      if (!restrictions(vacuum2.getRow(), vacuum2.getColumn() + Constants.RIGHT)) {
        moveHUpdate(vacuum2, Constants.RIGHT);
        // move dustballs
        randomMove();
      }
    } else if (nextMove == Constants.P2_DOWN) {
      // if move is p2 down
      if (!restrictions(vacuum2.getRow() + Constants.DOWN, vacuum2.getColumn())) {
        moveVUpdate(vacuum2, Constants.DOWN);
        // move dustballs
        randomMove();
      }
    } else if (nextMove == Constants.P1_DOWN) {
      // if move is p1 down
      if (!restrictions(vacuum1.getRow() + Constants.DOWN, vacuum1.getColumn())) {
        moveVUpdate(vacuum1, Constants.DOWN);
        // move dustballs
        randomMove();
      }
    }
  }

  /**
   * Checks to see if all dust and dustballs in the grid have been cleared.
   * 
   * @return boolean showing whether or not the condition above is true.
   */
  public boolean gameOver() {
    if (dusts.isEmpty() && dustBalls.isEmpty()) {
      System.out.println("reached gamevoer");
      return true;
    }
    return false;
    // checks to see if either player reached the max score
  }

  /**
   * Compares the scores of player 1 and player 2, returning the player with the higher score. If
   * the score is equal, a tie is called.
   * 
   * @return char representing either player 1, player 2 or a tie.
   */
  public char getWinner() {
    System.out.println("reached getwinner");
    if (getVacuum1().getScore() > getVacuum2().getScore()) {
      return Constants.P1;
    } else if (getVacuum1().getScore() < getVacuum2().getScore()) {
      return Constants.P2;
    } else {
      return Constants.TIE;
    }
    // returns the character of the player with the higher score after
    // gameover() is true
  }

  /**
   * Returns the dimensions of the grid in the file named layoutFileName.
   * 
   * @param layoutFileName path of the input grid file
   * @return an array [numRows, numCols], where numRows is the number of rows and numCols is the
   *         number of columns in the grid that corresponds to the given input grid file
   * @throws IOException if cannot open file layoutFileName
   */
  private int[] getDimensions(String layoutFileName) throws IOException {

    Scanner sc = new Scanner(new File(layoutFileName));

    // find the number of columns
    String nextLine = sc.nextLine();
    int numCols = nextLine.length();

    // find the number of rows
    int numRows = 1;
    while (sc.hasNext()) {
      numRows++;
      nextLine = sc.nextLine();
    }

    sc.close();
    return new int[] {numRows, numCols};
  }

}
