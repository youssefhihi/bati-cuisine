package Utility.Validation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class InputsValidation {
    private static Scanner scanner = new Scanner(System.in);

    public static String isStringValid(String prompt, String errorMessage){
        String input = "";
        while (input.trim().isEmpty()) {
            try {
                System.out.print(prompt);
                input = scanner.nextLine();
                if (input.trim().isEmpty()) {
                    System.out.println(errorMessage);
                }
            } catch (Exception e) {
                System.out.println("❗Erreur : Veuillez entrer un nombre valide.");
                scanner.nextLine();
            }
        }
        return input;
    }

    public static String isPhoneValid(String prompt, String errorMessage){
        String input = "";
        while (!input.matches("\\d{10}")) {
            try {
                System.out.print(prompt);
                input = scanner.nextLine();
                if (input.trim().isEmpty()) {
                    System.out.println(errorMessage);
                }
            } catch (Exception e) {
                System.out.println("❗Erreur : Veuillez entrer un nombre valide.");
                scanner.nextLine();
            }
        }
        return input;
    }


        public static  Integer isIntegerValid(String prompt, String errorMessage){
            Integer input = 0;
            while (input <= 0) {
                try {
                    System.out.print(prompt);
                    input = scanner.nextInt();
                    scanner.nextLine();
                    if (input <= 0) {
                        System.out.println(errorMessage);
                    }
                } catch (Exception e) {
                    System.out.println("❗Erreur : Veuillez entrer un nombre valide.");
                    scanner.nextLine();
                }
            }
            return input;
        }
    public static  Integer isIntegerValid(String prompt, String errorMessage, Integer endNum){
        Integer input = 0;
        while ( input <= 0 || input > endNum) {
            try {
                System.out.print(prompt);
                input = scanner.nextInt();
                scanner.nextLine();
                if (input <= 0 || input > endNum) {
                    System.out.println(errorMessage);
                }
            } catch (Exception e) {
                System.out.println("❗Erreur : Veuillez entrer un nombre valide.");
                scanner.nextLine();
            }
        }
        return input;
    }


    public static double isDoubleValid(String prompt, String errorMessage) {
        double input = 0;
        while (input <= 0) {
            try {
                System.out.print(prompt);
                input = scanner.nextDouble();
                scanner.nextLine();
                if (input <= 0) {
                    System.out.println(errorMessage);
                }
            } catch (Exception e) {
                System.out.println("❗Erreur : Veuillez entrer un nombre valide.");
                scanner.nextLine();
            }
        }
        return input;
    }

    public static Date isDateValid(String prompt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);

        Date today = new Date();

        Date inputDate = null;

        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                inputDate = dateFormat.parse(input);
                if (!inputDate.before(today)) {
                    return inputDate;
                } else {
                    System.out.println("La date ne peut pas être dans le passé.");
                }
            } catch (Exception e) {
                System.out.println(" Veuillez entrer la date dans le format jj/mm/aaaa.");
            }
        }
    }


}
