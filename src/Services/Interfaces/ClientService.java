package Services.Interfaces;

import Entity.Client;
import Exceptions.DatabaseException;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface ClientService {

    Client createClient(Client client) throws DatabaseException;
    void updateClient(Client client) throws DatabaseException;
    void deleteClient(UUID id) throws DatabaseException;
    Map<UUID, Client> searchClient(String query) throws DatabaseException;
    Optional<Client> getClientByID(UUID id) throws DatabaseException;

}