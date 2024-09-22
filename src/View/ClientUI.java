package View;

import Entity.Client;
import Exceptions.DatabaseException;
import Services.Interfaces.ClientService;
import Utility.Validation.InputsValidation;
import Utility.ViewUtility;

import java.sql.Connection;
import java.util.*;

public class ClientUI {

    private final Scanner scanner;
    private final ClientService clientService;

    public ClientUI(Scanner scanner, ClientService clientService){
        this.scanner =scanner;
        this.clientService =clientService;

    }

    public Client getClientForProject(){
        int choice = 0;
        Client client = new Client();
            System.out.println("🔍 Souhaitez-vous chercher un client existant ou en ajouter un nouveau ?");

            System.out.println(" ➤ [1] 🔎 Chercher un client existant");
            System.out.println(" ➤ [2] ➕ Ajouter un nouveau client");

            choice = ViewUtility.enterChoice(choice);

            switch (choice){
                case 1 -> client = handleSearchClient();
                case 2 -> client = handleAddClient();
                default -> System.out.println("❌ Choix invalide. Veuillez réessayer.");
            }

        return client;

    }



    private Client handleAddClient(){
        String name =  InputsValidation.isStringValid(
                "~~~> 👤 Nom du client : ",
                "❗Le nom ne peut pas être vide."
            );



        String address = InputsValidation.isStringValid(
                "~~~> \uD83D\uDCCD Adresse du client : ",
                "❗L'Adresse ne peut pas être vide."
            );

        String phone = InputsValidation.isPhoneValid(
                "~~~> \uD83D\uDCDE téléphone du client : ",
                "❗Le numéro de téléphone doit contenir exactement 10 nombre.");


        Boolean isProfessional = null;
        String choiceProfessional = ViewUtility.yesORno("~~~> \uD83E\uDDD1\u200D\uD83D\uDCBC Le client est-il professionnel ? (oui/non) : ");
        if(choiceProfessional.equals("oui")){
            isProfessional = true;
        } else if (choiceProfessional.equals("non")) {
            isProfessional = false;
        }

        Client client = new Client(name,address,phone,isProfessional);
        Client clientInserted = new Client();
        try {
         clientInserted = clientService.createClient(client);
        }catch (DatabaseException e){
            System.err.println("❗ Error occurred while adding the client: " + e.getMessage());
        }

        return clientInserted;

    }

    private Client handleSearchClient() {
        System.out.println("~~~ 🕵️‍♂️ Rechercher un Client ~~~");

        String name = InputsValidation.isStringValid(
                "~~~> 👤 Veuillez Nom du client : ",
                "❗Le nom ne peut pas être vide."
            );

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

        Integer selectedIndex = InputsValidation.isIntegerValid(
                "Veuillez sélectionner un client par son numéro : ",
                "❗ Index invalide. Veuillez réessayer.",
                clients.size()
            );

        return clients.entrySet().stream()
                    .skip(selectedIndex - 1)
                    .findFirst()
                    .map(Map.Entry::getValue)
                    .orElse(null);


    }



    private void associateProjectToClient(Client client){

    }

}