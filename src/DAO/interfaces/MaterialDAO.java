package DAO.interfaces;

import Entity.Material;
import Entity.Project;
import Exceptions.DatabaseException;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public interface MaterialDAO {
    Boolean create(Map<UUID, Material>materials)  throws SQLException;
    Map<UUID,Material> getForProject(Project project) throws DatabaseException;
}
