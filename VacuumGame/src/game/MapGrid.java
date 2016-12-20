package game;

import java.util.HashMap;
import java.util.Map;


public class MapGrid<T> extends Grid<T> {
  // nested Map data structure, first key represents row number, inner key represents
  // column number which then gives access to object data.
  private Map<Integer, Map<Integer, T>> grid; // Hashmap grid
  private int numRows; // variable used to store number of rows
  private int numColumns; // used to store number of columns
  private T item; // used to hold objects of <T>

  /**
   * Initializes new 2D <code>MapGrid</code> used to store sprite objects and 
   * represent grid object. Every outer key represents the row number, every inner 
   * key represents the column number and data accessed by inner key is the sprite 
   * object. Responsible for getting and setting individual cells as well
   * as getting the dimensions (row,column) of the grid itself. 
   * Should be equal to <code>MapGrid</code> object.
   * 
   * @param row showing how many rows are in the grid
   * @param column showing how many columns are in the grid
   */
  public MapGrid(int row, int column) {
    super();
    this.numRows = row;
    this.numColumns = column;
    grid = new HashMap<Integer, Map<Integer, T>>(); // initalize the grid

  }

  @Override
  public T getCell(int row, int column) {
    return grid.get(row).get(column);
  }

  @Override
  public void setCell(int row, int column, T item) {
    this.item = item;
    // if the memory location does not exist
    if (!grid.containsKey(row)) {
      HashMap<Integer, T> innerMap = new HashMap<Integer, T>();
      innerMap.put(column, this.item);
      grid.put(row, innerMap);
    } else { // update memory location
      grid.get(row).put(column, item);
    }
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
    // no idea wtf this does still
    return 0;
  }

}
