package Utility.Validation;

import java.util.Scanner;

public class InputsValidation {
    private static Scanner scanner = new Scanner(System.in);

    public static Boolean isStringValid(String name){
        return name != null && !name.trim().isEmpty();
    }

    public static Boolean isPhoneValid(String phone){
        return phone != null && phone.matches("\\d{10}");
    }


        public static  Boolean isIntegerValid(Integer number){
            return number != null && number > 0;
    }

    public static double getValidDoubleInput(String prompt, String errorMessage) {
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


}
