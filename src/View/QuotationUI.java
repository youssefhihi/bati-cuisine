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

import java.util.Date;
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
        Date validityDate = InputsValidation.isDateValid("Entrez la date de validité du devis (format : jj/mm/aaaa) :");
        Quotation quotation = new Quotation(project.getTotalCost(),issueDate,validityDate,false,project);
        String choice = ViewUtility.yesORno("Souhaitez-vous enregistrer le devis ? (oui/non) : ");

        if (choice.equals("yes")){
            try {
                 quotationService.createQuotation(quotation);
            } catch (DatabaseException e) {
                System.err.println(e.getMessage());
            }
        }


    }
}
