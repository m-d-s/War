package validator;

import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * Validator is meant to contain utility methods for input validation
 */
public class Validator {
  /**
   * checks if the given integer is positive, if not it prompts the user
   * to enter a positive integer and loops until it gets one
   * @param toCheck
   *    the integer in question
   * @param message
   *    the prompting output message
   * @return
   *    a positive integer
   */
  public static int positiveDigit(int toCheck, String message) {
    Scanner input = new Scanner(System.in);
    boolean again;
    if (toCheck < 1) {
      do {
        System.out.print(message);
        try {
          toCheck = input.nextInt();
          input.nextLine();
          if (toCheck < 1) {
            System.out.println("Input must be a positive integer");
            again = true;
          } else {
            again = false;
          }
        } catch (InputMismatchException exception) {
          System.out.println("Invalid input, please try again");
          input.nextLine();
          again = true;
        }
      } while (again);
    }
    return toCheck;
  }
}
