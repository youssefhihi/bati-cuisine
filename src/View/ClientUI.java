package View;

import Entity.Client;
import Exceptions.DatabaseException;
import Services.Interfaces.ClientService;
import Utility.Validation.InputsValidation;
import Utility.ViewUtility;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class ClientUI {

    private final Scanner scanner;
    private final ClientService clientService;

    public ClientUI(Scanner scanner, ClientService clientService){
        this.scanner =scanner;
        this.clientService =clientService;

    }

    public void clientUIMain(){
        int choice = 0;
        do{
            System.out.println("🔍 Souhaitez-vous chercher un client existant ou en ajouter un nouveau ?");
            System.out.println(" ➤ [1] 🔎 Chercher un client existant");
            System.out.println(" ➤ [2] ➕ Ajouter un nouveau client");
            System.out.println(" ➤ [3] ❌ Quitter");
            choice = ViewUtility.enterChoice(choice);

            switch (choice){
                case 1:
                    handleSearchClient();
                    break;
                case 2:
                    handleAddClient();
                    break;
                default:
                    System.out.println("❌ Choix invalide. Veuillez réessayer.");
            }

        }while (choice != 3);
    }



    private void handleAddClient(){
        System.out.print("~~~> 👤 Nom du client : ");
        String name = scanner.nextLine();
        while (!InputsValidation.isStringValid(name)){
            System.out.println("❗Le nom ne peut pas être vide.");
            System.out.print("~~~> 👤 Veuillez entrer un nom valide : ");
            name =scanner.nextLine();
        }

        System.out.print("~~~> \uD83D\uDCCD Adresse du client : ");
        String address = scanner.nextLine();
        while (!InputsValidation.isStringValid(address)){
            System.out.println("❗L'Adresse ne peut pas être vide.");
            System.out.print("~~~> \uD83D\uDCCD Veuillez entrer une Adresse valide : ");
            address =scanner.nextLine();
        }

        System.out.print("~~~> \uD83D\uDCDE téléphone du client : ");
        String phone = scanner.nextLine();
        while (!InputsValidation.isPhoneValid(phone)){
            System.out.println("❗Le numéro de téléphone doit contenir exactement 10 nombre.");
            System.out.print("~~~> \uD83D\uDCDE Veuillez entrer une téléphone valide : ");
            phone =scanner.nextLine();
        }

        Boolean isProfessional = null;
        while (isProfessional == null) {
            System.out.print("~~~> \uD83E\uDDD1\u200D\uD83D\uDCBC Le client est-il professionnel ? (oui/non) : ");
            String professionalInput = scanner.nextLine().trim().toLowerCase();
            switch (professionalInput) {
                case "oui":
                    isProfessional = true;
                    break;
                case "non":
                    isProfessional = false;
                    break;
                default:
                    System.out.println("Réponse invalide, veuillez répondre par 'oui' ou 'non'.");
                    break;
            }
        }

        Client client = new Client(name,address,phone,isProfessional);
        try {
            clientService.createClient(client);
        }catch (DatabaseException e){
            System.err.println("❗ Error occurred while adding the client: " + e.getMessage());
        }
        Boolean continued = null;
        while (continued == null) {
            System.out.println("Souhaitez-vous continuer avec ce client ? (oui/non) :");
            String continuedInput = scanner.nextLine().trim().toLowerCase();
            switch (continuedInput) {
                case "oui":
                    // new ProjectUI(connection,scanner).handleCreateProject(client);
                    break;
                case "non":
                    return;
                default:
                    System.out.println("Réponse invalide, veuillez répondre par 'oui' ou 'non'.");
                    break;
            }
        }

    }

    private void handleSearchClient() {
        System.out.println("~~~ 🕵️‍♂️ Rechercher un Client ~~~");

        System.out.print("~~~> 👤 Veuillez Nom du client : ");
        String name = scanner.nextLine();
        while (!InputsValidation.isStringValid(name)) {
            System.out.println("❗Le nom ne peut pas être vide.");
            System.out.print("~~~> 👤 Veuillez entrer un nom valide : ");
            name = scanner.nextLine();
        }
        Map<UUID, Client> clients = new HashMap<>();
        try {
            clients = clientService.searchClient(name);
        } catch (DatabaseException e) {
            System.err.println(e.getMessage());
        }

        int index = 1;
        for (Map.Entry<UUID, Client> entry : clients.entrySet()) {
            Client client = entry.getValue();
            System.out.println("╔═══════════════════════════════════════════╗");
            System.out.println("  client : " + index++);
            System.out.println("  Nom : " + client.getName());
            System.out.println("  Adresse : " + client.getAddress());
            System.out.println("  Téléphone :" + client.getPhone());
            System.out.println("╚═══════════════════════════════════════════╝");
            System.out.println(" ");
        }
        while (true) {
            System.out.println("Souhaitez-vous continuer avec ce client ? (oui/non) :");
            String continuedInput = scanner.nextLine().trim().toLowerCase();
            switch (continuedInput) {
                case "oui":
                    //new ProjectUI(connection,scanner).handleCreateProject(client);
                    return;
                case "non":
                    return;
                default:
                    System.out.println("Réponse invalide, veuillez répondre par 'oui' ou 'non'.");
                    break;
            }
        }

    }

}