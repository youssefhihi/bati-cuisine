package Repositories.Impl;

import DAO.Impl.LaborDaoImpl;
import DAO.interfaces.LaborDAO;
import Entity.Labor;
import Entity.Material;
import Entity.Project;
import Exceptions.DatabaseException;
import Repositories.Interfaces.LaborRepo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public class LaborRepoImpl implements LaborRepo {

    private final LaborDAO laborDao;

    public LaborRepoImpl(Connection connection){
        this.laborDao = new LaborDaoImpl(connection);
    }


    @Override
    public Boolean create(Map<UUID, Labor> labors) throws SQLException {
        return laborDao.create(labors);
    }
    @Override
    public Map<UUID, Labor> getForProject(Project project) throws DatabaseException {
        return laborDao.getForProject(project);
    }

}
