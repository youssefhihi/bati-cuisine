package View;

import ConnectDB.DBConnection;
import Entity.Project;
import Exceptions.DatabaseException;
import Services.Impl.ClientServiceImpl;
import Services.Impl.LaborServiceImpl;
import Services.Impl.MaterialServiceImpl;
import Services.Impl.ProjectServiceImpl;
import Services.Interfaces.ClientService;
import Services.Interfaces.LaborService;
import Services.Interfaces.MaterialService;
import Services.Interfaces.ProjectService;
import Utility.Validation.InputsValidation;
import Utility.ViewUtility;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class BatiCuisine {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Connection connection = DBConnection.connect();
    //services
    private static final ClientService clientService = new ClientServiceImpl(connection);
    private static final ProjectService projectService = new ProjectServiceImpl(connection);
    private static final MaterialService materialService = new MaterialServiceImpl(connection);
    private static final LaborService laborService = new LaborServiceImpl(connection);
    //UI
    private static final ClientUI clientUI = new ClientUI(scanner,clientService);


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
                    new ProjectUI(scanner, projectService,laborService,materialService,clientUI).handleCreateProject();
                    break;
                case 2:
                    new ProjectUI(scanner, projectService,laborService,materialService,clientUI).showProjectInProgress();
                    break;
                case 3:
                    calculateCost();
                    break;
                case 4:
                    System.out.println("👋 Merci d'avoir utilisé l'application. À bientôt !");
                    break;
                default:
                    System.out.println("❌ Choix invalide. Veuillez réessayer.");
            }
        } while (choice != 4);
    }


    private void calculateCost(){
                    ViewUtility.showLoading("💸 Calcul du coût du projet en cours");
        Map<UUID, Project> projectMap = new HashMap<>();
        String input = InputsValidation.isStringValid(
                "~~~> \uD83C\uDFD7\uFE0F  Entrez le key pour chercher une  projet : "
                ,"❗Le nom du projet ne peut pas être vide.");
        try{
           projectMap = projectService.searchProject(input);
        } catch (DatabaseException e) {
            System.err.println(e.getMessage());
        }
        Integer index = 1;
        for (Map.Entry<UUID,Project> entry : projectMap.entrySet()){
            Project project = entry.getValue();
            System.out.println("╔══\uD83D\uDEE0\uFE0F\uD83D\uDD27\uD83D\uDD28\uD83D\uDD29\uD83D\uDD27\uD83D\uDD28\uD83D\uDD29\uD83D\uDD27\uD83D\uDD28\uD83D\uDD29\uD83D\uDD27\uD83D\uDD28\uD83D\uDD27\uD83D\uDD28\uD83D\uDD29\uD83D\uDD27\uD83D\uDD28══╗");
            System.out.println("                  Projet : "+ index++);
            System.out.println("    \uD83C\uDF1F  Nom du Projet: " + project.getProjectName());
            System.out.println("     👤 Client : " + project.getClient().getName());
            System.out.println("    \uD83D\uDCCD Adresse : " + project.getClient().getAddress());
            System.out.println("    \uD83D\uDCDE Téléphone : " + project.getClient().getPhone());
            System.out.println("    \uD83D\uDCCF  Surface : " + project.getArea());
            System.out.println("    \uD83D\uDCBC  Marge bénéficiaire :" + project.getProfitMargin());
            System.out.println("    \uD83D\uDCB0  Coût Total: " + project.getTotalCost());
            System.out.println("    \uD83D\uDCCA  Statut du projet: " + ViewUtility.getProjectStatus(project.getProjectStatus()))    ;
            System.out.println("╚═══\uD83D\uDEE0\uFE0F\uD83D\uDD27\uD83D\uDD28\uD83D\uDD29\uD83D\uDD27\uD83D\uDD28\uD83D\uDD29\uD83D\uDD27\uD83D\uDD28\uD83D\uDD29\uD83D\uDD27\uD83D\uDD28\uD83D\uDD27\uD83D\uDD28\uD83D\uDD29\uD83D\uDD27\uD83D\uDD28══╝");
            System.out.println(" ");
        }

        Integer selectedIndex = InputsValidation.isIntegerValid(
                "Veuillez sélectionner un client par son numéro : ",
                "❗ Index invalide. Veuillez réessayer.",
                projectMap.size()
        );

        new ProjectUI(scanner, projectService,laborService,materialService,clientUI).handleCalculCosts(
                projectMap.entrySet().stream()
                        .skip(selectedIndex - 1)
                        .findFirst()
                        .map(Map.Entry::getValue).get()
        );

    }



}
