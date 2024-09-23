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
import java.util.Optional;
import java.util.UUID;

public class ProjectDaoImpl implements ProjectDAO {
    private final Connection connection;

    public ProjectDaoImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public Map<UUID,Project> search(String input) throws DatabaseException{
        Map<UUID, Project> projectMap = new HashMap<>();
        String sql = """
            SELECT p.id, p.projectName, p.profitMargin, p.totalCost, p.area, p.projectStatus,p.VATRate, 
                   c.id AS client_id, c.name, c.address, c.phone ,c.isProfessional
            FROM projects p
            JOIN clients c ON p.client_id = c.id
            WHERE p.projectName ILIKE  ? OR c.name ILIKE  ? OR c.address ILIKE  ? OR  c.phone ILIKE  ?;
            """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 1; i < 5; i++) {
                stmt.setString(i, "%" +input+"%");
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Project project = mapResultSet(rs);
                    projectMap.put(project.getId(), project);
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("❗Error occurred while searching projects"+ e.getMessage(), e);
        }

        return projectMap;
    }

    @Override
    public Boolean UpdateStatus(UUID id) throws DatabaseException{
        String sql = "UPDATE projects SET projectStatus = ? WHERE id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1 , ProjectStatus.canceled.name());
            stmt.setObject(2,id);
            stmt.executeUpdate();
            return  true;
        }catch (Exception e){
            throw new DatabaseException("❗Error occurred while updating status of project"+ e.getMessage(), e);
        }
    }


    @Override
    public Map<UUID, Project> getInProgress() throws DatabaseException {
        Map<UUID, Project> projectMap = new HashMap<>();
        String sql = """
            SELECT p.id, p.projectName, p.profitMargin, p.totalCost, p.area, p.projectStatus,p.VATRate, 
                   c.id AS client_id, c.name, c.address, c.phone ,c.isProfessional
            FROM projects p
            JOIN clients c ON p.client_id = c.id
            WHERE p.projectStatus = ?::projectStatus
            """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ProjectStatus.inProgress.name());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Project project = mapResultSet(rs);
                    projectMap.put(project.getId(), project);
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("❗Error occurred while getting in-progress projects"+ e.getMessage(), e);
        }

        return projectMap;
    }


    public Optional<Project> create(Project project) throws DatabaseException {
        String sql = """
                    WITH inserted_project AS (
                         INSERT INTO projects (projectName, profitMargin, totalCost, projectStatus, area, VATRate, client_id)
                         VALUES (?,?, ?, ?::projectStatus, ?, ?, ?)
                         RETURNING *
                    )SELECT inserted_project.*,
                        c.id AS client_id, c.name, c.address ,c.phone, c.isProfessional
                    FROM inserted_project
                    JOIN clients c ON inserted_project.client_id = c.id;
                """;
        System.err.println(project);
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1,project.getProjectName());
            if (project.getProfitMargin() != null){
                stmt.setDouble(2,project.getProfitMargin());
            }else{
                stmt.setNull(2, java.sql.Types.DOUBLE);
            }
            stmt.setDouble(3,project.getTotalCost());
            stmt.setString(4,project.getProjectStatus().name());
            stmt.setDouble(5,project.getArea());

            if (project.getVATRate() != null) {
                stmt.setDouble(6, project.getVATRate());
            } else {
                stmt.setNull(6, java.sql.Types.DOUBLE);
            }            stmt.setObject(7,project.getClient().getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){

                    return Optional.of(mapResultSet(rs));
            }
        return Optional.empty();
        } catch (SQLException e) {
            throw new DatabaseException("❗Error occurred while creating  project"+ e.getMessage(), e);
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
                rs.getDouble("VATRate"),
                client);
        project.setId((UUID) rs.getObject("id"));

        return project;
    }


}
