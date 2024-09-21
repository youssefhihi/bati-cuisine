package DAO.Impl;

import DAO.interfaces.MaterialDAO;
import Entity.Material;
import Entity.Project;
import Exceptions.DatabaseException;

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
    public Boolean create(Map<Integer,Material> materials) throws SQLException {
        String sql = """
            INSERT INTO materials
                (unitName, componentType,vatRate, project_id, transportCost, quantity, qualityCoefficient, unitCost)
                VALUES (?,?,?,?,?,?,?,?)
            """;
        try {
            connection.setAutoCommit(false);
            for (Map.Entry<Integer,Material> entry : materials.entrySet()) {
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
}
