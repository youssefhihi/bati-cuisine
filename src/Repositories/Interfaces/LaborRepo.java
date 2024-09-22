package Repositories.Interfaces;

import Entity.Labor;
import Entity.Project;
import Exceptions.DatabaseException;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public interface LaborRepo {

    Boolean create(Map<UUID, Labor> labors)  throws SQLException;
    Map<UUID,Labor> getForProject(Project project) throws DatabaseException;


}
