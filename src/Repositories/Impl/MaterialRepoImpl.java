package Repositories.Impl;

import DAO.Impl.MaterialDaoImpl;
import DAO.interfaces.MaterialDAO;
import Entity.Labor;
import Entity.Material;
import Entity.Project;
import Exceptions.DatabaseException;
import Repositories.Interfaces.MaterialRepo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public class MaterialRepoImpl implements MaterialRepo {

    private final MaterialDAO materialDAO;

    public MaterialRepoImpl(Connection connection){
       this.materialDAO = new MaterialDaoImpl(connection);
    }


    @Override
    public Boolean create(Map<UUID, Material> materials) throws SQLException {
        return materialDAO.create(materials);
    }

    @Override
    public Map<UUID, Material> getForProject(Project project) throws DatabaseException{
        return materialDAO.getForProject(project);
    }

}
