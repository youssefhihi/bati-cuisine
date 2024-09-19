package View;

import Entity.Client;
import Entity.Labor;
import Entity.Material;
import Entity.Project;
import Exceptions.DatabaseException;
import Services.Interfaces.ProjectService;
import Utility.Validation.InputsValidation;
import Utility.ViewUtility;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class ProjectUI {

    private final Scanner scanner;
    private final ProjectService projectService;

    public ProjectUI(Scanner scanner, ProjectService projectService){
        this.scanner = scanner;
        this.projectService = projectService;

    }

    public void handleCreateProject(Client client){
        String projectName = InputsValidation.isStringValid(
                "~~~> \uD83C\uDFD7\uFE0F  Entrez le nom du projet : ",
                "❗Le nom du projet ne peut pas être vide."
            );

        double kitchenArea = InputsValidation.isDoubleValid("~~~> 📐 Entrez la surface de la cuisine (en m²) : ",
                "❗La surface saisie n'est pas valide. Veuillez entrer un nombre positif."
                );


        Project project = new Project();
        project.setProjectName(projectName);
        project.setArea(kitchenArea);
        project.setClient(client);
        handleCreateMaterial(project);

    }

    private void handleCreateLabor(Project project) {
        String choice = "oui";
        while (choice.equals("oui")) {
            //labor type input
            String laborType = InputsValidation.isStringValid("~~~> 🛠️ Entrez le type de main-d'oeuvre (e.g., Ouvrier de base, Spécialiste) : ",
                    "❗Le type de main-d'oeuvre ne peut pas être vide."
                );
            // hourly rate input
            double hourlyRate = InputsValidation.isDoubleValid(
                    "~~~> 💶 Entrez le taux horaire de cette main-d'œuvre (€/h) : ",
                    "❗Le taux horaire doit être supérieur à zéro."
            );

            // hours worked input
            double hoursWorked = InputsValidation.isDoubleValid(
                    "~~~> ⏱️ Entrez le nombre d'heures travaillées : ",
                    "❗Le nombre d'heures doit être supérieur à zéro."
            );

            //productivity factor input
            double productivityFactor = InputsValidation.isDoubleValid(
                    "~~~> ⚙️ Entrez le facteur de productivité (1.0 = standard, > 1.0 = haute productivité) : ",
                    "❗Le facteur de productivité doit être supérieur à zéro."
            );

            Labor labor = new Labor();
            labor.setLaborType(laborType);
            labor.setProject(project);
            labor.setHourlyRate(hourlyRate);
            labor.setWorkingHours(hoursWorked);
            labor.setWorkerProductivity(productivityFactor);

            //controller here

            System.out.print("Voulez-vous ajouter un autre matériau ? (oui/non) : ");
            choice = scanner.nextLine().trim().toLowerCase();
            while (!choice.equals("oui") && !choice.equals("non")) {
                System.out.print("Réponse invalide, veuillez répondre par 'oui' ou 'non': ");
                choice = scanner.nextLine().trim().toLowerCase();
            }
        }
        System.out.println("fifnish");
    }



    private void handleCreateMaterial(Project project) {
        String choice = "oui";
        while (choice.equals("oui")) {
            // Material name input
            String materialName = InputsValidation.isStringValid(
                    "~~~> 🧱 Entrez le nom du matériau : ",
                    "❗Le nom du matériau ne peut pas être vide."
            );

            // Refactored numeric inputs
            double quantity = InputsValidation.isDoubleValid(
                    "~~~> 📦 Entrez la quantité de ce matériau (en litres) : ",
                    "❗La quantité doit être supérieure à zéro."
            );
            double unitCost = InputsValidation.isDoubleValid(
                    "~~~> 💶 Entrez le coût unitaire de ce matériau (€/litre) : ",
                    "❗Le coût unitaire doit être supérieur à zéro."
            );
            double transportCost = InputsValidation.isDoubleValid(
                    "~~~> 🚛 Entrez le coût de transport de ce matériau (€) : ",
                    "❗Le coût de transport doit être supérieur à zéro."
            );
            double qualityCoefficient = InputsValidation.isDoubleValid(
                    "~~~> 🏗️ Entrez le coefficient de qualité du matériau (1.0 = standard, > 1.0 = haute qualité) : ",
                    "❗Le coefficient de qualité doit être supérieur à zéro."
            );

            Material material = new Material();
            material.setUnitName(materialName);
            material.setQuantity(quantity);
            material.setUnitCost(unitCost);
            material.setTransportCost(transportCost);
            material.setQualityCoefficient(qualityCoefficient);
            material.setProject(project);

            // Ask user if they want to add another material
            System.out.print("Voulez-vous ajouter un autre matériau ? (oui/non) : ");
            choice = scanner.nextLine().trim().toLowerCase();
            while (!choice.equals("oui") && !choice.equals("non")) {
                System.out.print("Réponse invalide, veuillez répondre par 'oui' ou 'non': ");
                choice = scanner.nextLine().trim().toLowerCase();
            }
        }
        handleCreateLabor(project);
    }


    public void showProjectInProgress(){
        Map<UUID,Project> projects =new HashMap<>();
        try {
            projects = projectService.getProjectInProgress();
        }catch (DatabaseException e){
            System.err.println(e.getMessage());
        }
        for (Map.Entry<UUID, Project> entry : projects.entrySet()) {
            Project project = entry.getValue();
            System.out.println("╔══\uD83D\uDEE0\uFE0F\uD83D\uDD27\uD83D\uDD28\uD83D\uDD29\uD83D\uDD27\uD83D\uDD28\uD83D\uDD29\uD83D\uDD27\uD83D\uDD28\uD83D\uDD29\uD83D\uDD27\uD83D\uDD28\uD83D\uDD27\uD83D\uDD28\uD83D\uDD29\uD83D\uDD27\uD83D\uDD28══╗");
            System.out.println("    \uD83C\uDF1F  Nom du Projet: " + project.getProjectName());
            System.out.println("    \uD83D\uDCCF  Surface : " + project.getArea());
            System.out.println("    \uD83D\uDCBC  Marge bénéficiaire :" + project.getProfitMargin());
            System.out.println("    \uD83D\uDCB0  Coût Total: " + project.getTotalCost());
            System.out.println("    \uD83D\uDCCA  Statut du projet: " + ViewUtility.getProjectStatus(project.getProjectStatus()))    ;
            System.out.println("╚═══\uD83D\uDEE0\uFE0F\uD83D\uDD27\uD83D\uDD28\uD83D\uDD29\uD83D\uDD27\uD83D\uDD28\uD83D\uDD29\uD83D\uDD27\uD83D\uDD28\uD83D\uDD29\uD83D\uDD27\uD83D\uDD28\uD83D\uDD27\uD83D\uDD28\uD83D\uDD29\uD83D\uDD27\uD83D\uDD28══╝");
            System.out.println(" ");
        }

        }


}