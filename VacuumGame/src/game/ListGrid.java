package game;

import java.util.ArrayList;
import java.util.List;

public class ListGrid<T> extends Grid<T> {

  private int numRows; // used to store number of rows
  private int numColumns; // used to store number of columns
  private T item; // used to store item of <T>
  private List<List<T>> grid; // List grid

  /**
   * Initializes new 2D <code>ListGrid</code> used to store sprites and represent the grid object. 
   * Every element in the outer list represents the row number and every element in the 
   * inner list represents the column number. Responsible for getting and setting specific 
   * cells as well as getting dimensions (row,column) of the grid itself. 
   * Should be equal to <code>MapGrid</code>.
   * 
   * @param row shows how many rows are in the grid
   * @param column show how many columns are in the grid
   */
  public ListGrid(int row, int column) {
    this.numRows = row;
    this.numColumns = column;
    // generate a nested list with objects <T>, every inner list represents
    // the row
    // every element represents column
    grid = new ArrayList<List<T>>();
    for (int rowList = 0; rowList < numRows; rowList++) {
      ArrayList<T> innerlist = new ArrayList<T>();
      for (int columnList = 0; columnList < numColumns; columnList++) {
        innerlist.add(columnList, null);
      }
      grid.add(rowList, innerlist);
    }
  }

  @Override
  public void setCell(int row, int column, T item) {
    this.item = item;
    grid.get(row).remove(column);
    grid.get(row).add(column, this.item);
  }

  @Override
  public T getCell(int row, int column) {
    // access and return element from grid[row][column]
    return grid.get(row).get(column);
  }

  @Override
  public int getNumRows() {
    return this.numRows;
  }

  @Override
  public int getNumColumns() {
    return this.numColumns;
  }

  @Override
  public int hashCode() {
    // TODO Auto-generated method stub
    return 0;
  }

}
