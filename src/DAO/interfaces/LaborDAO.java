package DAO.interfaces;

import Entity.Labor;
import Exceptions.DatabaseException;

import java.sql.SQLException;
import java.util.Map;

public interface LaborDAO {

    Boolean create(Map<Integer, Labor> labors)  throws SQLException;

}
