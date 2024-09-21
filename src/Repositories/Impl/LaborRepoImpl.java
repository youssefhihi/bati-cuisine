package Repositories.Impl;

import DAO.Impl.LaborDaoImpl;
import DAO.interfaces.LaborDAO;
import Entity.Labor;
import Repositories.Interfaces.LaborRepo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class LaborRepoImpl implements LaborRepo {

    private final LaborDAO laborDao;

    public LaborRepoImpl(Connection connection){
        this.laborDao = new LaborDaoImpl(connection);
    }


    @Override
    public Boolean create(Map<Integer, Labor> labors) throws SQLException {
        return laborDao.create(labors);
    }
}
