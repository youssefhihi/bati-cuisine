package View;

import Entity.Client;
import Entity.Labor;
import Entity.Material;
import Entity.Project;
import Exceptions.DatabaseException;
import Services.Interfaces.LaborService;
import Services.Interfaces.MaterialService;
import Services.Interfaces.ProjectService;
import Utility.CostCalculation;
import Utility.Validation.InputsValidation;
import Utility.ViewUtility;
import enums.ProjectStatus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class ProjectUI {

    private final Scanner scanner;
    private final ProjectService projectService;
    private final LaborService laborService;
    private final MaterialService materialService;
    private final  ClientUI clientUI;

    public ProjectUI(Scanner scanner, ProjectService projectService, LaborService laborService, MaterialService materialService,ClientUI clientUI){
        this.scanner = scanner;
        this.projectService = projectService;
        this.clientUI = clientUI;
        this.laborService =laborService;
        this.materialService =materialService;

    }

    public void handleCreateProject(){
        Client client = clientUI.getClientForProject();
        String continueClient = ViewUtility.yesORno("Souhaitez-vous continuer avec ce client ? (oui/non)");
        if (continueClient.equals("non")){
            return;
        }
        String projectName = InputsValidation.isStringValid(
                "~~~> \uD83C\uDFD7\uFE0F  Entrez le nom du projet : ",
                "❗Le nom du projet ne peut pas être vide."
            );

        double kitchenArea = InputsValidation.isDoubleValid(
                "~~~> 📐 Entrez la surface de la cuisine (en m²) : ",
                "❗La surface saisie n'est pas valide. Veuillez entrer un nombre positif."
                );

        Project project = new Project();
        project.setProjectName(projectName);
        project.setArea(kitchenArea);
        project.setClient(client);
        project.setProjectStatus(ProjectStatus.inProgress);
        Map<Integer,Material> materials = handleCreateMaterial();
        Map<Integer,Labor> labors = handleCreateLabor();

        String choiceVAT = ViewUtility.yesORno("Souhaitez-vous appliquer une TVA au projet ? (oui/non)");
       if(choiceVAT.equals("oui")) {
           double vatRate = InputsValidation.isDoubleValid(
                   "Entrez le pourcentage de TVA (%) : ",
                   "❗Le le pourcentage de TVA doit être supérieur à zéro."
           );
               project.setVATRate(vatRate);
       }

       String choicePM = ViewUtility.yesORno("Souhaitez-vous appliquer une marge bénéficiaire au projet ?(oui/non)");
        if (choicePM.equals("oui")) {
            double profitMargin = InputsValidation.isDoubleValid(
                    "Entrez le pourcentage de marge bénéficiaire (%)",
                    "❗Le le pourcentage de marge bénéficiaire doit être supérieur à zéro."
               );
            project.setProfitMargin(profitMargin);
        }

        project.setTotalCost(
                CostCalculation.calculateProjectCost(
                CostCalculation.calculateMaterialsCost(materials),
                CostCalculation.calculateLaborsCost(labors),
                project.getProfitMargin()
        ));

       try {
           Optional<Project> insertedProject = projectService.createProject(project);
           materials.values().forEach(m -> m.setProject(insertedProject.get()));
           labors.values().forEach(l -> l.setProject(insertedProject.get()));
           laborService.createLabors(labors);
           materialService.createMaterials(materials);

       } catch (DatabaseException | SQLException e) {
           System.err.println(e.getMessage());
       }
    }
    private Map<Integer, Material> handleCreateMaterial() {
        Map<Integer,Material> materials = new HashMap<>();
        System.out.println(" 🧱--- Ajout des matériaux --- 🧱");
        String choice = "oui";
        Integer index = 1;
        while (choice.equals("oui")) {
            // Nom du matétriau
            String materialName = InputsValidation.isStringValid(
                    "~~~> 🧱 Entrez le nom du matériau : ",
                    "❗Le nom du matériau ne peut pas être vide."
            );

            // Quantité
            double quantity = InputsValidation.isDoubleValid(
                    "~~~> 📦 Entrez la quantité de ce matériau (en litres) : ",
                    "❗La quantité doit être supérieure à zéro."
            );
            // Coût unitaire
            double unitCost = InputsValidation.isDoubleValid(
                    "~~~> 💶 Entrez le coût unitaire de ce matériau (€/litre) : ",
                    "❗Le coût unitaire doit être supérieur à zéro."
            );

            //Coût du transport du matériau
            double transportCost = InputsValidation.isDoubleValid(
                    "~~~> 🚛 Entrez le coût de transport de ce matériau (€) : ",
                    "❗Le coût de transport doit être supérieur à zéro."
            );

            // Coefficient reflétant la qualité du matériau
            double qualityCoefficient = InputsValidation.isDoubleValid(
                    "~~~> 🏗️ Entrez le coefficient de qualité du matériau (1.0 = standard, > 1.0 = haute qualité) : ",
                    "❗Le coefficient de qualité doit être supérieur à zéro."
            );
            double vatRate = InputsValidation.isDoubleValid(
                    "Entrez le pourcentage de TVA du " + materialName+" (%): ",
                    "le pourcentage de TVA doit être supérieur à zéro."
            );

            Material material = new Material();
            material.setUnitName(materialName);
            material.setQuantity(quantity);
            material.setUnitCost(unitCost);
            material.setTransportCost(transportCost);
            material.setQualityCoefficient(qualityCoefficient);
            material.setVatRate(vatRate);
            materials.put(index++,material);

            // Ask user if they want to add another material
          choice = ViewUtility.yesORno("Voulez-vous ajouter un autre matériau ? (oui/non) : ");
        }
        return materials;
    }

    private Map<Integer,Labor> handleCreateLabor() {
        Map<Integer,Labor> labors = new HashMap<>();
        System.out.println("🛠️--- Ajout de la main-d'œuvre ---🛠️");
        String choice = "oui";
        Integer index = 1;
        while (choice.equals("oui")) {
            //labor type input
            String name = InputsValidation.isStringValid("~~~> 🛠️ Entrez le type de main-d'oeuvre (e.g., Ouvrier de base, Spécialiste) : ",
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
            double vatRate = InputsValidation.isDoubleValid(
                    "Entrez le pourcentage de TVA du " + name +" (%): ",
                    "le pourcentage de TVA doit être supérieur à zéro."
            );

            Labor labor = new Labor();
            labor.setUnitName(name);
            labor.setHourlyRate(hourlyRate);
            labor.setWorkingHours(hoursWorked);
            labor.setWorkerProductivity(productivityFactor);
            labor.setVatRate(vatRate);
            labors.put(index++,labor);

            choice = ViewUtility.yesORno("Voulez-vous ajouter un autre main-d'oeuvre ? (oui/non) : ");

        }
        return labors;


    }





//
//
//    public void handleCalculCosts(Project project, Map<Integer, Material> materials , Map<Integer, Labor> labors){
//        System.out.println("--- Calcul du coût total ---");
//       String choiceVAT = ViewUtility.yesORno("Souhaitez-vous appliquer une TVA au projet ? (oui/non)");
//       if(choiceVAT.equals("oui")){
//           double vatRate = InputsValidation.isDoubleValid(
//                   "Entrez le pourcentage de TVA (%) : ",
//                   "❗Le le pourcentage de TVA doit être supérieur à zéro."
//           );
//           String choicePM = ViewUtility.yesORno("Souhaitez-vous appliquer une marge bénéficiaire au projet ?(oui/non)");
//           if (choicePM.equals("oui")){
//               double profitMargin = InputsValidation.isDoubleValid(
//                       "Entrez le pourcentage de marge bénéficiaire (%)",
//                       "❗Le le pourcentage de marge bénéficiaire doit être supérieur à zéro."
//               );
//
//               ViewUtility.showLoading("Calcul du coût en cours");
//               System.out.println("📊 --- Résultat du Calcul ---");
//               System.out.println("🏗️ Nom du projet : " + project.getProjectName());
//               System.out.println("👤 Client : " + project.getClient().getName());
//               System.out.println("📍 Adresse du chantier : " + project.getClient().getAddress());
//               System.out.println("📏 Surface : "+ project.getArea() +" m²");
//
//               System.out.println("\n🛠️ --- Détail des Coûts ---");
//
//               // Matériaux
//               System.out.println("1️⃣ Matériaux :");
//               System.out.println("   🧱 Carrelage : 710.00 € (quantité : 20 m², coût unitaire : 30 €/m², qualité : 1.1, transport : 50 €)");
//               System.out.println("   🎨 Peinture : 170.00 € (quantité : 10 litres, coût unitaire : 15 €/litre, transport : 20 €)");
//               System.out.println("   **Coût total des matériaux avant TVA : 880.00 €**");
//               System.out.println("   **Coût total des matériaux avec TVA (20%) : 1 056.00 €**");
//
//               // Main-d'œuvre
//               System.out.println("\n2️⃣ Main-d'œuvre :");
//               System.out.println("   👷‍♂️ Ouvrier de base : 800.00 € (taux horaire : 20 €/h, heures travaillées : 40 h, productivité : 1.0)");
//               System.out.println("   👷‍♀️ Ouvrier spécialisé : 770.00 € (taux horaire : 35 €/h, heures travaillées : 20 h, productivité : 1.1)");
//               System.out.println("   **Coût total de la main-d'œuvre avant TVA : 1 570.00 €**");
//               System.out.println("   **Coût total de la main-d'œuvre avec TVA (20%) : 1 884.00 €**");
//
//               // Total avant marge et marge bénéficiaire
//               System.out.println("\n📈 Coût total avant marge : 2 940.00 €");
//               System.out.println("💼 Marge bénéficiaire (15%) : 441.00 €");
//
//               // Coût total final
//               System.out.println("\n💰 **Coût total final du projet : 3 381.00 €**");
//
//           }
//       }
//    }
//





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

        }


}