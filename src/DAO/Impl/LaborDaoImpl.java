package DAO.Impl;

import DAO.interfaces.LaborDAO;
import Entity.Labor;
import Entity.Material;
import Exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class LaborDaoImpl implements LaborDAO {

    private final Connection connection;

    public LaborDaoImpl(Connection connection){
        this.connection = connection;
    }


    @Override
    public Boolean create(Map<Integer, Labor> labors) throws SQLException {
        String sql = """
            INSERT INTO labors
                (unitName, componentType,vatRate, project_id, hourlyRate, workingHours, workerProductivity,unitCost)
                VALUES (?,?,?,?,?,?,?,?)
            """;
        try {
            connection.setAutoCommit(false);
            for (Map.Entry<Integer,Labor> entry : labors.entrySet()) {
                Labor labor = entry.getValue();
                try (PreparedStatement stmt = connection.prepareStatement(sql)){
                    stmt.setString(1,labor.getUnitName());
                    stmt.setString(2, labor.getComponentType().name());
                    stmt.setDouble(3,labor.getVatRate());
                    stmt.setObject(4,labor.getProject().getId());
                    stmt.setDouble(5,labor.getHourlyRate());
                    stmt.setDouble(6,labor.getHourlyRate());
                    stmt.setDouble(7,labor.getWorkerProductivity());
                    stmt.setDouble(8,labor.getUnitCost());
                    stmt.executeUpdate();
                }
            }
            connection.commit();
            return true;
        } catch (Exception e) {
                connection.rollback();
            throw new SQLException("❗Error occurred while to create labors  "+ e.getMessage(), e);
        }finally {
                connection.setAutoCommit(true);

        }
    }
}
