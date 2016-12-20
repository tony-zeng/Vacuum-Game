package sprites;

public abstract class Dirt extends Sprite {
  private int value; // used to store dust and dustball values

  /**
   * Abstract <code>Dirt</code> class representing either dust or dustballs.
   * 
   * @param symbol Character of either dust or dustball
   * @param row row location of dirt
   * @param col col locaiton of dirt
   * @param value the amount of points this instance of dirt is worth
   */
  public Dirt(char symbol, int row, int col, int value) {
    super(symbol, row, col);
    // TODO Auto-generated constructor stub
  }

  /**
   * Finds the value of the given sprite.
   * 
   * @return integer of value
   */
  public int getValue() {
    return value;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + value;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Dirt other = (Dirt) obj;
    if (value != other.value) {
      return false;
    }
    return true;
  }

}
