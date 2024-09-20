package Repositories.Interfaces;

import Entity.Labor;

import java.sql.SQLException;
import java.util.Map;

public interface LaborRepo {

    Boolean create(Map<Integer, Labor> labors)  throws SQLException;

}
