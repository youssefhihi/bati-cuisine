package View;

import Entity.Project;
import Entity.Quotation;
import Exceptions.DatabaseException;
import Services.Interfaces.LaborService;
import Services.Interfaces.MaterialService;
import Services.Interfaces.ProjectService;
import Services.Interfaces.QuotationService;
import Utility.Validation.InputsValidation;
import Utility.ViewUtility;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

public class QuotationUI {
    private final Scanner scanner;
    private final QuotationService quotationService;


    public QuotationUI(Scanner scanner,QuotationService quotationService){
        this.scanner = scanner;
        this.quotationService = quotationService;

    }


    public void createQuotation(Project project){
        System.out.println("--- Enregistrement du Devis ---");
        Date issueDate = InputsValidation.isDateValid("Entrez la date d'émission du devis (format : jj/mm/aaaa) :");
        Date validityDate = InputsValidation.isDateValid("Entrez la date de validité du devis (format : jj/mm/aaaa) :", issueDate);
        Quotation quotation = new Quotation(project.getTotalCost(),issueDate,validityDate,false,project);
        String choice = ViewUtility.yesORno("Souhaitez-vous enregistrer le devis ? (oui/non) : ");

        if (choice.equals("oui")){
            try {
                 quotationService.createQuotation(quotation);
            } catch (DatabaseException e) {
                System.err.println(e.getMessage());
            }
        }


    }

    public void getQuotationForProject(Project project){
        Optional<Quotation> quotation = Optional.empty();
        try {
           quotation = quotationService.getQuotationForProject(project);
        } catch (DatabaseException e) {
            System.out.println(e.getMessage());
        }

            if (quotation.isPresent()) {
                Quotation q = quotation.get();

                System.out.println("📋 --- Détails du Projet ---");
                System.out.println("👤 Nom du client : " + project.getClient().getName());
                System.out.println("🔖 Nom du projet : " + project.getProjectName());
                System.out.println("🚧 État du projet : " + ViewUtility.getProjectStatus(project.getProjectStatus()));
                System.out.println("📐 Surface du projet : " + project.getArea() + " m²");

                System.out.println("\n💶 --- Détails du Devis ---");
                System.out.println("🗓️ Date d'émission : " + q.getIssueDate());
                System.out.println("📅 Date de validité : " + q.getValidityDate());
                System.out.println("💵 Montant estimé : " + String.format("%.2f €", q.getEstimatedAmount()));
                System.out.println("✔️ Devis accepté : " + (q.getAccepted() ? "Oui" : "Non"));

                LocalDate validityDate = q.getValidityDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                if(LocalDate.now().isBefore(validityDate) && q.getAccepted().equals(false)){
                    String choice =  ViewUtility.yesORno("Pouvez-vous accepter ce devis ? oui/non");
                    if (choice.equals("oui")){
//                        quotationService.acceptQuotation(q);
                    }
                }
            } else {
                System.out.println("❗Aucun devis trouvé pour ce projet.");
            }
    }
}
