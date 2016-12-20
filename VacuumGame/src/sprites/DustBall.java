package sprites;

import game.Constants;

public class DustBall extends Dirt {
  /**
   * Class representing <code>DustBall</code>.
   * 
   * @param row row location of dustball
   * @param col column location of dustball
   * @param value score given of cleaning dustball
   */
  public DustBall(int row, int col, int value) {
    super(Constants.DUST_BALL, row, col, value);
    // TODO Auto-generated constructor stub
  }

  /**
   * Calls updateCoordinates to move the dustball.
   * 
   * @param row location of row that dustball moves to
   * @param col location of col that dustball moves to
   */
  public void moveTo(int row, int col) {
    this.updateCoordinates(row, col);
  }

}
