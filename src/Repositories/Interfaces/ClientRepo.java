package Repositories.Interfaces;

import Entity.Client;
import Exceptions.DatabaseException;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepo {

    Optional<Client> create(Client client) throws DatabaseException;
    Boolean update(Client client) throws DatabaseException;
    Boolean delete(UUID id) throws DatabaseException;
    Map<UUID, Client> search(String query) throws DatabaseException;
    Optional<Client> getByID(UUID id) throws DatabaseException;

}