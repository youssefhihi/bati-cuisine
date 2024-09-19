package View;

import ConnectDB.DBConnection;
import Services.Impl.ClientServiceImpl;
import Services.Impl.ProjectServiceImpl;
import Services.Interfaces.ClientService;
import Services.Interfaces.ProjectService;
import Utility.ViewUtility;

import java.sql.Connection;
import java.util.Scanner;

public class BatiCuisine {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Connection connection = DBConnection.connect();
    private static final ClientService clientService = new ClientServiceImpl(connection);
    private static final ProjectService projectService = new ProjectServiceImpl(connection);

    public void batiCuisineApp(){
            System.out.println("🛠️ === Bienvenue dans l'application de gestion des projets de rénovation de cuisines === 🛠️");
        int choice = 0;
        do {
            System.out.println("🍽️ === Menu Principal === 🍽️");
            System.out.println(" ➤ [1] 🏗️ Créer un nouveau projet");
            System.out.println(" ➤ [2] 📋 Afficher les projets existants");
            System.out.println(" ➤ [3] 💰 Calculer le coût d'un projet");
            System.out.println(" ➤ [4] ❌ Quitter");
            choice = ViewUtility.enterChoice(choice);
            switch (choice){
                case 1:
                    new ClientUI(scanner,clientService).clientUIMain();
                    break;
                case 2:
                    new ProjectUI(scanner, projectService).showProjectInProgress();
                    break;
                case 3:
                    System.out.println("💸 Calcul du coût du projet en cours...");
                    break;
                case 4:
                    System.out.println("👋 Merci d'avoir utilisé l'application. À bientôt !");
                    break;
                default:
                    System.out.println("❌ Choix invalide. Veuillez réessayer.");
            }
        } while (choice != 4);
    }



}
