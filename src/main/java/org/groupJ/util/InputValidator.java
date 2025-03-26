package org.groupJ.util;

import java.util.Scanner;

public class InputValidator {
    // Scanner instance for reading user input
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Prompts the user, trims the input, and checks for spaces or empty input.
     *
     * @param prompt The message displayed to the user.
     * @return A trimmed, validated string input without spaces or null if invalid.
     */
    private static String printTrimAndCheckSpaces(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();  // Read full line and remove spaces

        // Check if input contains spaces or is empty
        if (input.contains(" ") || input.isEmpty()) {
            System.out.println("\u274C Invalid input! Do not enter spaces or leave empty. Try again.");
            return null;
        }
        return input;
    }

    /**
     * Validates and returns a non-negative integer input from the user.
     *
     * @param prompt The message displayed to the user.
     * @return A valid non-negative integer.
     */
    public static int getValidIntInput(String prompt) {
        while (true) {
            String input = printTrimAndCheckSpaces(prompt);
            if (input == null) continue;

            try {
                int value = Integer.parseInt(input);
                if (value < 0) {
                    System.out.println("\u274C Invalid input! Please select from the menu and try again.");
                    continue;
                }

                return value;  // Return valid integer
            } catch (NumberFormatException e) {
                System.out.println("\u274C Invalid input! Please enter numbers only.");
            }
        }
    }

    /**
     * Validates and returns a double input from the user.
     *
     * @param prompt The message displayed to the user.
     * @return A valid double value.
     */
    public static double getValidDoubleInput(String prompt) {
        while (true) {
            String input = printTrimAndCheckSpaces(prompt);
            if (input == null) continue;

            try {
                return Double.parseDouble(input);  // Return valid double
            } catch (NumberFormatException e) {
                System.out.println("\u274C Invalid input! Please enter decimal value only.");
            }
        }
    }

    /**
     * Validates and returns a string input that may contain spaces.
     *
     * @param prompt The message displayed to the user.
     * @return A valid string input containing letters, numbers, and select punctuation.
     */
    public static String getValidStringWithSpaceInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (!input.matches("[a-zA-Z0-9!,. ]+")) {
                System.out.println("\u274C Invalid input! Only letters, numbers, and select punctuation are allowed. Try again.");
                continue;
            }

            return input;
        }
    }

    /**
     * Validates and returns a string input without spaces.
     *
     * @param prompt The message displayed to the user.
     * @return A valid string input containing only letters and numbers.
     */
    public static String getValidStringInput(String prompt) {
        while (true) {
            String input = printTrimAndCheckSpaces(prompt);
            if (input == null) continue;

            if (!input.matches("[a-zA-Z0-9]+")) {
                System.out.println("\u274C Invalid input! Only letters and numbers are allowed. Try again.");
                continue;
            }

            return input;
        }
    }

    /**
     * Validates and returns an integer input within a specified range.
     *
     * @param prompt The message displayed to the user.
     * @param end The maximum allowable value.
     * @return A valid integer within the specified range.
     */
    public static int getValidRangeIntInput(String prompt, int end) {
        while (true) {
            String input = printTrimAndCheckSpaces(prompt);
            if (input == null) continue;

            try {
                int value = Integer.parseInt(input);
                if (value > end || value < 0) {
                    System.out.println("\u274C Invalid input! Please select from the menu and try again.");
                    continue;
                }
                return value;  // Return valid integer
            } catch (NumberFormatException e) {
                System.out.println("\u274C Invalid input! Please enter numbers only.");
            }
        }
    }
}