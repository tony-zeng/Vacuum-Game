package game;

import sprites.Sprite;

public abstract class Grid<T> {

  /**
   * Locates a specific cell on the <code>Grid</code>.
   * 
   * @param row row number of the grid
   * @param column column number of the grid
   */
  public abstract T getCell(int row, int column);

  /**
   * Sets a specific cell. If it is null then adds a new object.
   * 
   * @param row row number of the grid
   * @param column column number of the grid
   * @param item object being added.
   */
  public abstract void setCell(int row, int column, T item);

  /**
   * Shows how many rows are on the grid.
   * 
   * @return integer of number of rows.
   */
  public abstract int getNumRows();

  /**
   * Shows how many columns are on the grid.
   * 
   * @return integer of number of columns
   */
  public abstract int getNumColumns();

  @Override
  public abstract int hashCode();

  /**
   * Prints out a visual representation of the grid.
   */
  @Override
  public String toString() {
    String gridPicture = "";
    for (int row = 0; row < getNumRows(); row++) { // looping through all rows
      for (int column = 0; column < getNumColumns(); column++) {
        gridPicture += ((Sprite) getCell(row, column)).getSymbol();
      }
      gridPicture += "\n";
    }

    return gridPicture;
  }

  /**
   * Compares this object to another and returns true or false given if they are exactly the same.
   */
  public boolean equals(Object obj) {
    boolean same = false;
    if (obj.hashCode() == this.hashCode()) { // if object is mapgrid or listgrid
      for (int row = 0; row < getNumRows(); row++) {
        for (int column = 0; column < getNumColumns(); column++) {
          if (this.getCell(row, column).equals(((Grid<T>) obj).getCell(row, column))) {
            same = true; // comparing each cell
          } else {
            same = false;
            return same;
          }
        }
      }
    }
    return same;
  }
}
