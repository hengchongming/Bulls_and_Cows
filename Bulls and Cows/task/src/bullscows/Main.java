package bullscows;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        startGame();
    }

    private static void startGame() {

        Scanner scanner = new Scanner(System.in);
        int codeSize = 0;
        int symbolSize = 0;

        try{
            System.out.println("Input the length of the secret code:");
            codeSize = scanner.nextInt();
            System.out.println("Input the number of possible symbols in the code:");
            symbolSize = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Error: " + e);
            System.exit(1);
        }

        if (symbolSize > 36 ||  symbolSize < 10 || (codeSize > symbolSize)) {
            System.out.println("Error: can't generate a secret number because there aren't " +
                    "enough unique digits.");
            System.exit(1);
        }

        String secretCode = generateCode(codeSize, symbolSize);

        // Available symbols
        String symbols = "";
        if (symbolSize > 11) {
            symbols = "a" + "-" + (char) ('a' + (symbolSize - 11));
        }

//        System.out.println(secretCode);
        System.out.print("The secret is prepared: ");
        for (int i = 0; i < codeSize; i++) {
            System.out.print("*");
        }
        System.out.print(" ");

        if (symbolSize == 10) {
            System.out.println("(0-9).");
        } else if (symbolSize == 11) {
            System.out.println("(0-9, a).");
        } else {
            System.out.println("(0-9, " + symbols + ").");
        }

        System.out.println("Okay, let's start a game!");

        int turn = 1;
        while (true) {
            System.out.println("Turn " + turn + ":");
            String inputCode = scanner.nextLine();
            int bulls = 0;
            int cows = 0;

            for (int i = 0; i < inputCode.length(); i++) {
                if (inputCode.charAt(i) == secretCode.charAt(i)) {
                    bulls++;
                } else if (secretCode.indexOf(inputCode.charAt(i)) >= 0) {
                    cows++;
                }
            }

            System.out.print("Grade: ");

            if (bulls == 1) {
                System.out.print(bulls + " bull");
            } else if (bulls > 1) {
                System.out.print(bulls + " bulls");
            }

            if (bulls > 0 && cows > 0) {
                System.out.print(" and ");
            }

            if (cows == 1) {
                System.out.print(cows + " cow");
            } else if (cows > 1) {
                System.out.print(cows + " cows");
            }

            if (bulls == 0 && cows == 0) {
                System.out.print("None");
            }

            System.out.println();

            if (bulls == secretCode.length()) {
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            }

            turn++;
        }
    }

    private static String generateCode(int codelength, int symbols) {
        Random random = new Random();
        StringBuilder secretCode = new StringBuilder();
        while (secretCode.length() < codelength) {
            int digit = random.nextInt(symbols);

            char c;
            if (digit < 10) {
                c = (char) ('0' + digit);
            }  else {
                c = (char) ('a' + digit - 10);
            }

            // skip if digit already in secret code
            if (secretCode.indexOf(String.valueOf(c)) >= 0) {
                continue;
            }

            secretCode.append(c);
        }

        return secretCode.toString();
    }
}
