package Services.Interfaces;

import Entity.Labor;
import Entity.Project;
import Exceptions.DatabaseException;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public interface LaborService {

    void createLabors(Map<UUID, Labor> labors)  throws SQLException;
    Map<UUID,Labor> getLaborsForProject(Project project) throws DatabaseException;


}
