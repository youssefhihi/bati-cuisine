package Utility;

import enums.ProjectStatus;

import java.util.Scanner;

public class ViewUtility {

    private static final Scanner scanner = new Scanner(System.in);

    public static Integer enterChoice(int choice) {
        try {
            System.out.print("👉 Select your option: ");
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            scanner.nextLine();
            choice = 0;
        }
        return choice;
    }

    public static void showLoading(String message) {
        System.out.print(message);
        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(600);
                System.out.print(".");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println();
    }


    public static String getProjectStatus(ProjectStatus status) {
        switch (status) {
            case inProgress:
                return "🚧 En cours";
            case completed:
                return "✅ Terminé";
            case canceled:
                return "❌ Annulé";
            default:
                return "❓ Inconnu";
        }
    }
}
