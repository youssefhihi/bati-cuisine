package DAO.Impl;

import DAO.interfaces.LaborDAO;
import Entity.Labor;
import Entity.Project;
import Exceptions.DatabaseException;
import enums.ComponentType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LaborDaoImpl implements LaborDAO {

    private final Connection connection;

    public LaborDaoImpl(Connection connection){
        this.connection = connection;
    }


    @Override
    public Boolean create(Map<UUID, Labor> labors) throws SQLException {
        String sql = """
            INSERT INTO labors
                (unitName, componentType,vatRate, project_id, hourlyRate, workingHours, workerProductivity)
                VALUES (?,?::componentType,?,?,?,?,?)
            """;
        try {
            connection.setAutoCommit(false);
            for (Map.Entry<UUID,Labor> entry : labors.entrySet()) {
                Labor labor = entry.getValue();
                try (PreparedStatement stmt = connection.prepareStatement(sql)){
                    stmt.setString(1,labor.getUnitName());
                    stmt.setString(2, labor.getComponentType().name());
                    stmt.setDouble(3,labor.getVatRate());
                    stmt.setObject(4,labor.getProject().getId());
                    stmt.setDouble(5,labor.getHourlyRate());
                    stmt.setDouble(6,labor.getWorkingHours());
                    stmt.setDouble(7,labor.getWorkerProductivity());
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


    @Override
    public Map<UUID,Labor> getForProject(Project project) throws DatabaseException{
        Map<UUID,Labor> laborMap = new HashMap<>();
        String sql = "SELECT id,unitName, componentType,vatRate, project_id, hourlyRate, workingHours, workerProductivity FROM labors WHERE project_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setObject(1,project.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Labor m = mapResultSet(rs,project);
                laborMap.put(m.getId(),m);
            }
        }catch (Exception e){
            throw new  DatabaseException("❗Error occurred while to get labors  "+ e.getMessage(),e);
        }
        return laborMap;
    }


    private Labor mapResultSet(ResultSet  rs,Project project) throws SQLException{
        Labor labor = new Labor(
                rs.getString("unitName"),
                enums.ComponentType.valueOf(rs.getString("componentType")),
                rs.getDouble("vatRate") ,
                project,
                rs.getDouble("hourlyRate"),
                rs.getDouble("workingHours"),
                rs.getDouble("workerProductivity")
        );
        labor.setId((UUID)rs.getObject("id"));
        return labor;
    }
}
