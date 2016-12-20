package ui;

import game.Constants;
import game.VacuumGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class TextUi implements Ui {
  private VacuumGame game;
  private BufferedReader input;

  /**
   * Initalizes <code>TextUi</code> for the given <code>VacuumGame</code>.
   * 
   * @param game type of game
   */
  public TextUi(VacuumGame game) {
    this.game = game;

  }

  @Override
  public void launchGame() {
    while (!game.gameOver()) {
      System.out.println(game.getGrid());
      input = new BufferedReader(new InputStreamReader(System.in));
      try {
        String command = input.readLine();
        if (!command.isEmpty()) {
          game.move(command.charAt(0));
        }
      } catch (IOException errorE) {
        // TODO Auto-generated catch block
        errorE.printStackTrace();
      }

    }
  }


  @Override
  public void displayWinner() {
    if (game.gameOver()) {
      if (game.getWinner() == Constants.P1) {
        System.out.println("Congratulations player " + Constants.P2 + "! You won with a score of "
            + game.getVacuum1().getScore());
      } else if (game.gameOver()) {
        System.out.println("Congratulations player " + Constants.P2 + "! You won with a score of "
            + game.getVacuum2().getScore());
      } else {
        System.out.print("It's a tie!");
      }
    }
  }

}
