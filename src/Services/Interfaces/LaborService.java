package Services.Interfaces;

import Entity.Labor;

import java.sql.SQLException;
import java.util.Map;

public interface LaborService {

    void createLabors(Map<Integer, Labor> labors)  throws SQLException;

}
