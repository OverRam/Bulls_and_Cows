package bullscows.game;

import java.util.Random;
import java.util.Scanner;

public class StartClass {
    private final Scanner sc = new Scanner(System.in);
    private final String charsPool = "0123456789abcdefghijklmnopqrstuvwxyz";

    public void run() {
        String test = "";
        try {
            boolean isEnd = true;
            int rund = 1;
            System.out.println("Input the length of the secret code:");
            test = sc.nextLine();
            int lengthCode = Integer.parseInt(test);
            System.out.println("Input the number of possible symbols in the code:");
            test = sc.nextLine();
            int numberOfCharacterPool = Integer.parseInt(test);

            if (lengthCode > numberOfCharacterPool) {
                System.out.println("Error: it's not possible to generate a code with a length of " + lengthCode +
                        " with " + numberOfCharacterPool + " unique symbols.");
            } else if (lengthCode < 1) {
                System.out.println("Error: length of the secret code must be greater than 0");
            } else {
                if (numberOfCharacterPool > 36) {
                    System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                } else {
                    System.out.println("The secret is prepared: " + prepareChars(lengthCode, numberOfCharacterPool));
                    System.out.println("Okay, let's start a game!");
                    String secretCode = randomNumber(lengthCode);
                    while (isEnd) {
                        System.out.println("Turn: " + rund);
                        isEnd = checkAnswer(lengthCode, secretCode);
                        rund++;
                    }
                    System.out.println("Congratulations! You guessed the secret code.");
                }
                sc.close();
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: " + test + " isn't a valid number.");
        }
    }

    private String prepareChars(int lengthCode, int characterPool) {
        StringBuilder ans = new StringBuilder();
        ans.append("*".repeat(Math.max(0, lengthCode)));
        ans.append(" (0-");
        if (characterPool <= 10) {
            ans.append(charsPool.charAt(characterPool - 1));
        }
        if (characterPool > 10) {
            ans.append("9, a-").append(charsPool.charAt(characterPool - 1));
        }
        ans.append(").");
        return ans.toString();
    }

    private String randomNumber(int lengthCode) {
        Random random = new Random(36);
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < lengthCode; i++) {
            s.append(charsPool.charAt(random.nextInt(36)));
            random.setSeed(i);
        }
        return s.toString();
    }

    private boolean checkAnswer(int seed, String secretCode) {
        int bull = 0;
        int cow = 0;
        String user = sc.next();
        for (int i = 0; i < seed; i++) {
            if (user.charAt(i) == secretCode.charAt(i)) {
                bull++;
            } else {
                for (int j = 0; j < seed; j++) {
                    if (user.charAt(i) == secretCode.charAt(j) && j != i) {
                        cow++;
                    }
                }
            }
        }

        if (bull == 0 && cow == 0) {
            System.out.println("Grade: None.");
        } else if (bull > 0 && cow > 0) {
            System.out.printf("Grade: %d bull(s) and %d cow(s).\n", bull, cow);
        } else if (cow == 0) {
            System.out.printf("Grade: %d bull(s).\n", bull);
        } else {
            System.out.printf("Grade: %d cow(s).\n", cow);
        }
        return bull != seed;
    }
}