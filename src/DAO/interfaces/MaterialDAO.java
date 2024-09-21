package DAO.interfaces;

import Entity.Material;
import Exceptions.DatabaseException;

import java.sql.SQLException;
import java.util.Map;

public interface MaterialDAO {
    Boolean create(Map<Integer, Material>materials)  throws SQLException;
}
