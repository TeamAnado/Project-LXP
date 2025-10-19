package com.lxp.global.support;

import java.util.Scanner;

public class InputUtil {

    private final Scanner scanner;

    public InputUtil(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Gets integer input with validation
     *
     * @param prompt       Message to display to user
     * @param errorMessage Message to display on invalid input
     * @return Valid integer input
     */
    public int getInt(String prompt, String errorMessage) {
        return getInt(prompt, errorMessage, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Gets integer input with range validation
     *
     * @param prompt       Message to display to user
     * @param errorMessage Message to display on invalid input
     * @param min          Minimum allowed value (inclusive)
     * @param max          Maximum allowed value (inclusive)
     * @return Valid integer input within range
     */
    public int getInt(String prompt, String errorMessage, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println(errorMessage);
                    continue;
                }
                int value = Integer.parseInt(input);
                if (value < min || value > max) {
                    System.out.println(errorMessage + " (" + min + "-" + max + " 범위)");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println(errorMessage);
            }
        }
    }

    /**
     * Gets long input with validation
     *
     * @param prompt       Message to display to user
     * @param errorMessage Message to display on invalid input
     * @return Valid long input
     */
    public long getLong(String prompt, String errorMessage) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println(errorMessage);
                    continue;
                }
                long value = Long.parseLong(input);
                if (value <= 0) {
                    System.out.println("양수를 입력해주세요.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println(errorMessage);
            }
        }
    }

    /**
     * Gets string input with validation
     *
     * @param prompt       Message to display to user
     * @param errorMessage Message to display on invalid input
     * @return Valid non-empty string input
     */
    public String getString(String prompt, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println(errorMessage);
        }
    }

    /**
     * Gets yes/no confirmation from user
     *
     * @param prompt Message to display to user
     * @return true for yes/y, false for no/n
     */
    public boolean getConfirmation(String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            switch (input) {
                case "y", "yes" -> {
                    return true;
                }
                case "n", "no" -> {
                    return false;
                }
                default -> System.out.println("y(yes) 또는 n(no)를 입력해주세요.");
            }
        }
    }

}
