package bullscows;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        action();
    }
    private static void action() {
        Scanner scanner = new Scanner(System.in);
        int [] bullsCows = {};
        int cows = 0;
        int bulls = 0;
        int i = 1;

        try {
            System.out.println("Input the length of the secret code:");
            int numberL = scanner.nextInt();
            System.out.println("Input the number of possible symbols in the code:");
            int numberV = scanner.nextInt();
            if (numberV > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                System.exit(0);
            } else if (numberL > numberV) {
                System.out.println("Error: it's not possible to generate a code with a length of " +
                        numberL + " with " + numberV + " unique symbols.");
                System.exit(0);
            } else if (numberL == 0){
                System.out.println("Error: Cant create a code that is 0 long.");
                System.exit(0);
            }
            infoUser(numberL, numberV);
            System.out.println("Okay, let's start a game!");
            String secretCode = createTheCode(numberL, numberV);

            do {
                System.out.println("Turn " + i + ":");
                String userInput = scanner.next();
                bullsCows = guessTheCode(secretCode, userInput);
                cows = bullsCows[0];
                bulls = bullsCows[1];
                if (bulls + cows == 0) {
                    System.out.println("Grade: None.");
                } else if (cows == 0) {
                    System.out.println("Grade: " + bulls + " bull(s).");
                } else if(bulls == 0) {
                    System.out.println("Grade: " + cows + " cow(s).");
                } else {
                    System.out.println("Grade: " + bulls + " bull(s) and " + cows +
                            " cow(s).");
                }
                i++;
            } while (bulls < secretCode.length());
            System.out.println("Congratulations! You guessed the secret code.");
        }  catch(InputMismatchException e) {
            System.out.println("Error: This is not a valid Input!");
        }



    }

    private static String createTheCode(int numberL, int numberV) {
        Random random = new Random();
        String secretChars = ("0123456789abcdefghijklmnopqrstuvwxyz");
        String secretCode = "";
        while (secretCode.length() != numberL) {

            for (int j = 0; j < numberL; j++) {
                secretCode += secretChars.charAt(random.nextInt(numberV));
            }
            for(int j = 0, i = 0; i < numberL; j++) {
                if (secretCode.charAt(j) == secretCode.charAt(i) && i != j) {
                    secretCode = "";
                    break;
                }
                if (j + 1 == numberL) {
                    i++;
                    j = 0;
                }
            }

        }

        return secretCode;
    }

    private static int[] guessTheCode(String secretCode1, String userInput) {
        String[] userInput1 = userInput.split("");
        String[] cleanedCode = secretCode1.split("");
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < cleanedCode.length; i++) {
            if (cleanedCode[i].equals(userInput1[i])) {
                bulls += 1;
                cleanedCode[i].replace(cleanedCode[i], "");
            } else {
                for (int j = 0; j < userInput1.length; j++) {
                    if (cleanedCode[i].equals(userInput1[j])) {
                        cows += 1;
                    }
                }
            }
        }
        int[] buCo = {cows, bulls};
        return buCo;
    }

    private static void infoUser(int numberL, int numberV) {
        String stars = "";
        String numbers = "";
        String letters = "";
        String secretChars = ("0123456789abcdefghijklmnopqrstuvwxyz");
        for (int i = 0; i < numberL; i++) {
            stars += "*";
        }
        if (numberV >= 10) {
            numbers = "0-9";
            if (numberV == 11) {
                letters = ", a";
            } else {
                letters = ", a-" + secretChars.charAt(numberV-1);
            }
        } else {
            numbers = "0-" + secretChars.charAt(numberV-1);
        }
        System.out.println("The secret is prepared: " + stars + " (" + numbers + letters + ").");
    }
}
