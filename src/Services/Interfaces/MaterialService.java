package Services.Interfaces;

import Entity.Material;

import java.sql.SQLException;
import java.util.Map;

public interface MaterialService {

    void createMaterials(Map<Integer, Material> materials)  throws SQLException;

}
