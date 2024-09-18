package Services.Impl;

import Entity.Client;
import Exceptions.DatabaseException;
import Repositories.Impl.ClientRepoImpl;
import Repositories.Interfaces.ClientRepo;
import Services.Interfaces.ClientService;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ClientServiceImpl implements ClientService {
    private final ClientRepo clientRepo;

    public ClientServiceImpl(Connection connection){
        this.clientRepo = new ClientRepoImpl(connection);
    }
    @Override
    public void createClient(Client client) throws DatabaseException {
            Boolean isAdded = clientRepo.create(client);
            if (isAdded) {
                System.out.println("✅ Client added successfully!");
            } else {
                System.out.println("❗ Failed to add Client. An unexpected error occurred.");
            }

    }


    @Override
    public void updateClient(Client client) throws DatabaseException {
            Boolean isUpdated =  clientRepo.update(client);
            if (isUpdated){
                System.out.println("✅ Client updated successfully!");
            } else {
                System.out.println("❗ Failed to update Client. An unexpected error occurred.");
            }

    }

    @Override
    public void deleteClient(UUID id) throws DatabaseException {
            Boolean isDeleted = clientRepo.delete(id);
            if (isDeleted){
                System.out.println("✅ Client Deleted successfully!");
            } else {
                System.out.println("❗ Failed to delete Client. An unexpected error occurred.");
            }

    }

    @Override
    public Map<UUID, Client> searchClient(String query) throws DatabaseException {
            return clientRepo.search(query);
    }

    @Override
    public Optional<Client> getClientByID(UUID id) throws DatabaseException{
             return clientRepo.getByID(id);

    }
}