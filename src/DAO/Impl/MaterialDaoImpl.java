package DAO.Impl;

import DAO.interfaces.MaterialDAO;
import Entity.Material;
import Entity.Project;
import Exceptions.DatabaseException;
import Repositories.Interfaces.MaterialRepo;
import enums.ComponentType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MaterialDaoImpl implements MaterialDAO {

    private final Connection connection;

    public MaterialDaoImpl(Connection connection){
        this.connection = connection;
    }


    @Override
    public Boolean create(Map<UUID,Material> materials) throws SQLException {
        String sql = """
            INSERT INTO materials
                (unitName, componentType,vatRate, project_id, transportCost, quantity, qualityCoefficient, unitCost)
                VALUES (?,?::componentType,?,?,?,?,?,?)
            """;
        try {
            connection.setAutoCommit(false);
            for (Map.Entry<UUID,Material> entry : materials.entrySet()) {
                Material material = entry.getValue();
                try (PreparedStatement stmt = connection.prepareStatement(sql)){
                    stmt.setString(1,material.getUnitName());
                    stmt.setString(2, material.getComponentType().name());
                    stmt.setDouble(3,material.getVatRate());
                    stmt.setObject(4,material.getProject().getId());
                    stmt.setDouble(5,material.getTransportCost());
                    stmt.setDouble(6,material.getQuantity());
                    stmt.setDouble(7,material.getQualityCoefficient());
                    stmt.setDouble(8,material.getUnitCost());
                     stmt.executeUpdate();
                }
            }
            connection.commit();
            return true;
        } catch (Exception e) {
            connection.rollback();
            throw new SQLException("❗Error occurred while to create materials  "+ e.getMessage(), e);
        }finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public Map<UUID,Material> getForProject(Project project) throws DatabaseException{
        Map<UUID,Material> materialMap = new HashMap<>();
        String sql = "SELECT id,unitName, componentType,vatRate, project_id, transportCost, quantity, qualityCoefficient, unitCost FROM materials WHERE project_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setObject(1,project.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Material m = mapResultSet(rs,project);
                materialMap.put(m.getId(),m);
            }
        }catch (Exception e){
            throw new  DatabaseException("❗Error occurred while to get materials  "+ e.getMessage(),e);
        }
        return materialMap;
    }


    private Material mapResultSet(ResultSet  rs,Project project) throws SQLException{
        Material material = new Material(
                rs.getString("unitName"),
                rs.getDouble("unitCost") ,
                rs.getDouble("quantity"),
                ComponentType.valueOf(rs.getString("componentType")),
                 project,
                rs.getDouble("vatRate") ,
                rs.getDouble("transportCost"),
                rs.getDouble("qualityCoefficient")
        );
        material.setId((UUID)rs.getObject("id"));
        return material;
    }

}
