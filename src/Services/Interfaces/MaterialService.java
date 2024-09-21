package Services.Interfaces;

import Entity.Material;
import Entity.Project;
import Exceptions.DatabaseException;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public interface MaterialService {

    void createMaterials(Map<UUID, Material> materials)  throws SQLException;
    Map<UUID,Material> getMaterialsForProject(Project project) throws DatabaseException;


}
