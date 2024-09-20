package DAO.Impl;

import DAO.interfaces.ClientDAO;
import Entity.Client;
import Exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ClientDaoImpl implements ClientDAO {

    private final Connection connection;

    public ClientDaoImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public Optional<Client> create(Client client) throws DatabaseException {
        String sql = "INSERT INTO clients (name, address, phone, isProfessional) VALUES (?,?,?,?) Returning *";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getAddress());
            stmt.setString(3,client.getPhone());
            stmt.setBoolean(4,client.getIsProfessional());
           ResultSet rs =  stmt.executeQuery();
           if (rs.next()){
               return Optional.of(mapResultSetForClient(rs));
           }
        }catch (Exception e){
            throw new DatabaseException("❗Error occurred while creating a client", e);
        }
        return Optional.empty();
    }
    @Override
    public Boolean update(Client client) throws DatabaseException {
        String sql = "UPDATE clients SET name = ? , address = ?, phone = ?, isProfessional = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1,client.getName());
            stmt.setString(2,client.getAddress());
            stmt.setString(3,client.getPhone());
            stmt.setBoolean(4, client.getIsProfessional());
            stmt.setObject(5, client.getId());
            stmt.executeUpdate();
            return true;
        }catch (Exception e){
            throw new DatabaseException("❗Error occurred while updating a client", e);
        }
    }
    @Override
    public Boolean delete(UUID id) throws  DatabaseException{
        String sql = "DELETE FROM clients WHERE id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setObject(1, id);
            stmt.executeUpdate();
            return  true;
        } catch (Exception e) {
            throw new DatabaseException("❗Error occurred while Deleting a client", e);
        }
    }
    @Override
    public Map<UUID, Client> search(String query) throws DatabaseException {
        Map<UUID, Client> clientMap = new HashMap<>();
        String sql = "SELECT id, name, address, phone, isProfessional FROM clients WHERE name ILIKE ? OR address ILIKE ? OR phone ILIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 1; i <= 3; i++) {
                stmt.setString(i, "%" + query + "%");
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Client client = mapResultSetForClient(rs);
                clientMap.put(client.getId(), client);
            }
        } catch (Exception e) {
            throw new DatabaseException("❗Error occurred while searching for clients", e);
        }
        return clientMap;
    }

    @Override
    public Optional<Client> getByID(UUID id) throws  DatabaseException{
        String sql = "SELECT id,name,address,phone,isProfessional from clients WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setObject(1,id);
            ResultSet rs = stmt.executeQuery();
            Client client = mapResultSetForClient(rs);
            return Optional.of(client);
        } catch (Exception e) {
            throw new DatabaseException("❗Error occurred while getting  a client by ID", e);
        }
    }



    private Client mapResultSetForClient(ResultSet rs) throws SQLException {
        Client client = new Client(
                rs.getString("name"),
                rs.getString("address"),
                rs.getString("phone"),
                rs.getBoolean("isProfessional")
        );
        client.setId((UUID) rs.getObject("id"));
        return client;
    }
}
