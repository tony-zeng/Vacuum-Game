package sprites;

import game.Constants;

public class Vacuum extends Sprite {
  private int score; // holds vacuums score count
  private int capacity; // how much dust/dustballs a vacuum can hold
  private int fullness; // how full a vacuum is
  private Sprite under; // sprite under the vacuum

  /**
   * A representation of the <code>Vacuum</code> class. Shows the score accumlated for each vacuum,
   * keeps track of how full the vacuum is and gets/sets the sprite under the vacuum. 
   * Can move and clean depending on the <code>Sprite</code>e under the vacuum.
   * 
   * @param symbol symbol of vacuum, each vacuums is unique
   * @param row row location of the vacuum
   * @param col col location of the vacuum
   * @param capacity the max capacity of the vacuum
   */
  public Vacuum(char symbol, int row, int col, int capacity) {
    super(symbol, row, col);
    this.capacity = capacity;
    this.fullness = 0;
    this.score = Constants.INIT_SCORE;
    // TODO Auto-generated constructor stub
  }

  /**
   * Moves the vacuum to the designated location.
   * 
   * @param row new row location
   * @param col new col location
   */
  public void moveTo(int row, int col) {
    super.updateCoordinates(row, col);
  }

  /**
   * Checks whether the vacuum is able to clean what is under it.
   * 
   * @param score the score gained from potentially cleaning
   * @return true or false depending on whether cleaning is valid
   */
  public boolean clean(int score) {
    if (fullness < capacity) {
      this.score += score;
      this.fullness += Constants.FULLNESS_INC;
      return true;
    } else {
      return false;
    }
  }

  /**
   * Empties all the dust inside the vacuum, resetting its fullness.
   */
  public void empty() {
    this.fullness = Constants.EMPTY;
  }

  /**
   * Gets what is under the vacuum.
   * 
   * @return Sprite of what is under
   */
  public Sprite getUnder() {
    return this.under;
  }

  /**
   * Sets what should be under the vacuum.
   * 
   * @param under the object that should be under the vacuum
   */
  public void setUnder(Sprite under) {
    this.under = under;
  }

  /**
   * Gets the current score of the unique vacuum.
   * 
   * @return integer value representing the score
   */
  public int getScore() {
    return this.score;
  }


}
