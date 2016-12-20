package sprites;

public abstract class Sprite {
  private char symbol; // character used to represent the symbols
  private int row; // integer used to store row
  private int column; // interger used to store column

  /**
   * A class used to represent a general <code>Sprite</code> class. 
   * Responsible for getting symbols, getting row
   * and getting column of the sprite. Can change its coordinates.
   * 
   * @param symbol character that varies depending on specific sprite
   * @param row row location of the sprite
   * @param col col location of the sprite
   */
  public Sprite(char symbol, int row, int col) {
    this.symbol = symbol;
    this.row = row;
    this.column = col;

  }

  /**
   * Gets the symbol of the sprite.
   * 
   * @return character of given sprite
   */
  public char getSymbol() {
    return this.symbol;
  }

  /**
   * Gets the row of the sprite.
   * 
   * @return integer of row location
   */
  public int getRow() {
    return this.row;
  }

  /**
   * gets the column of the sprite.
   * 
   * @return integer of col location
   */
  public int getColumn() {
    return this.column;
  }

  /**
   * changes the location of the sprite to given row and col.
   * 
   * @param row new row location to be changed
   * @param col new col location to be changed
   */
  protected void updateCoordinates(int row, int col) {
    this.row = row;
    this.column = col;
    // changes coordinates of current sprite
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + column;
    result = prime * result + row;
    result = prime * result + symbol;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }  
    if (getClass() != obj.getClass()) {
      return false;
    }  
    Sprite other = (Sprite) obj;
    if (column != other.column) {
      return false;
    }  
    if (row != other.row) {
      return false;
    }  
    if (symbol != other.symbol) {
      return false;
    }  
    return true;
  }

  @Override
  public String toString() {
    return "" + getSymbol();
  }
}
