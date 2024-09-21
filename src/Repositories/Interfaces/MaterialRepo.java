package Repositories.Interfaces;

import Entity.Labor;
import Entity.Material;
import Entity.Project;
import Exceptions.DatabaseException;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public interface MaterialRepo {

    Boolean create(Map<UUID, Material> materials)  throws SQLException;
    Map<UUID,Material> getForProject(Project project) throws DatabaseException;


}
