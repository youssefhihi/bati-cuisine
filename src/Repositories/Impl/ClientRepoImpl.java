package Repositories.Impl;

import DAO.Impl.ClientDaoImpl;
import DAO.interfaces.ClientDAO;
import Entity.Client;
import Exceptions.DatabaseException;
import Repositories.Interfaces.ClientRepo;

import java.sql.Connection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ClientRepoImpl implements ClientRepo {
    private final ClientDAO clientDAO;

    public ClientRepoImpl(Connection connection){
        this.clientDAO = new ClientDaoImpl(connection);
    }


    @Override
    public Optional<Client> create(Client client) throws DatabaseException {
        return clientDAO.create(client);
    }

    @Override
    public Boolean update(Client client) throws DatabaseException {
        return clientDAO.update(client);
    }

    @Override
    public Boolean delete(UUID id) throws DatabaseException {
        return clientDAO.delete(id);
    }

    @Override
    public Map<UUID, Client> search(String query) throws DatabaseException {
        return clientDAO.search(query);
    }

    @Override
    public Optional<Client> getByID(UUID id) throws DatabaseException {
        return clientDAO.getByID(id);
    }

}