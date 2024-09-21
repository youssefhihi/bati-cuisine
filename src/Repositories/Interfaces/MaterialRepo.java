package Repositories.Interfaces;

import Entity.Labor;
import Entity.Material;

import java.sql.SQLException;
import java.util.Map;

public interface MaterialRepo {

    Boolean create(Map<Integer, Material> materials)  throws SQLException;

}
