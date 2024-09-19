package DAO.Impl;

import DAO.interfaces.ProjectDAO;
import Entity.Client;
import Entity.Project;
import Exceptions.DatabaseException;
import enums.ProjectStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProjectDaoImpl implements ProjectDAO {
    private final Connection connection;

    public ProjectDaoImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public Map<UUID, Project> getInProgress() throws DatabaseException {
        Map<UUID, Project> projectMap = new HashMap<>();
        String sql = """
            SELECT p.id, p.projectName, p.profitMargin, p.totalCost, p.area, p.projectStatus, 
                   c.id AS client_id, c.name, c.address, c.phone ,c.isProfessional
            FROM projects p
            JOIN clients c ON p.client_id = c.id
            WHERE p.projectStatus = ?::projectStatus
            """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ProjectStatus.inProgress.name());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Project project = mapResultSet(rs);
                projectMap.put(project.getId(), project);
            }
        } catch (SQLException e) {
            throw new DatabaseException("❗Error occurred while getting in-progress projects"+ e.getMessage(), e);
        }

        return projectMap;
    }


    public  Boolean create(Project project) throws DatabaseException {
        String sql = """
                    INSERT INTO projects
                    (projectName, profitMargin, totalCost, projectStatus, area, client_id)
                    VALUES (?,?,?,?,?,?)
                """;
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1,project.getProjectName());
            stmt.setDouble(2,project.getProfitMargin());
            stmt.setDouble(3,project.getTotalCost());
            stmt.setString(4,project.getProjectStatus().name());
            stmt.setDouble(5,project.getArea());
            stmt.setObject(6,project.getClient().getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DatabaseException("❗Error occurred while getting in progress projects", e);
        }
    }

    private Project mapResultSet(ResultSet rs) throws SQLException {
        Client client = new Client(
                rs.getString("name"),
                rs.getString("address"),
                rs.getString("phone"),
                rs.getBoolean("isProfessional")
        );
        client.setId((UUID)rs.getObject( "client_id"));
        Project project = new Project(rs.getString("projectName"),
                rs.getDouble("profitMargin"),
                rs.getDouble("totalCost"),
                ProjectStatus.valueOf(rs.getString("projectStatus")),
                rs.getDouble("area"),
                client);
        project.setId((UUID) rs.getObject("id"));

        return project;
    }


}
