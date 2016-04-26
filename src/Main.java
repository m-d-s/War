import game.GameType;
import game.War;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * War
 * Michael Simpson
 */
public class Main {
  public static void main(String[] args) {
    GameType war;
    boolean playAgain = true;
    BufferedReader br =
        new BufferedReader(new InputStreamReader(System.in));
    war = new War();
    while(playAgain) {
      war.play(2,4,13);
      System.out.println("Would you like to play again? (y/n)");
      try{
        playAgain = br.readLine().toLowerCase().equals("y");
      }catch(IOException io){
        io.printStackTrace();
      }
    }
  }
}