import game.War;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
  public static void main(String[] args) {
    CardGame war = new CardGame(new War());
    boolean playAgain = false;
    BufferedReader br =
        new BufferedReader(new InputStreamReader(System.in));
    war.play();
    do {
      if(playAgain) war.rematch();
      System.out.println("Would you like to play again? (y/n)");
      try{
        playAgain = br.readLine().toLowerCase().equals("y");
      }catch(IOException io){
        io.printStackTrace();
      }
    }while(playAgain);
   System.out.println("Goodbye");
  }
}