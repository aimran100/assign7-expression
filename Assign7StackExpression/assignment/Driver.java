package assignment;

import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
    	Scanner input = new Scanner(System.in);

        Stack<Integer> evaluator = new Stack<>();

        System.out.print(
            "Enter an infix expression (e.g., (3 + 5) * 2): "
        );

        String infix = input.nextLine();

        try {

            String postfix = evaluator.infixToPostfix(infix);

            int result = evaluator.evaluatePostfix(postfix);

            System.out.println("\n--- Results ---");

            System.out.println("Infix Expression:   " + infix);

            System.out.println("Postfix Expression: " + postfix);

            System.out.println("Evaluated Result:   " + result);

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());

        }

        input.close();
 
    }
}

