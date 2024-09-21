package Repositories.Impl;

import DAO.Impl.MaterialDaoImpl;
import DAO.interfaces.MaterialDAO;
import Entity.Material;
import Repositories.Interfaces.MaterialRepo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class MaterialRepoImpl implements MaterialRepo {

    private final MaterialDAO materialDAO;

    public MaterialRepoImpl(Connection connection){
       this.materialDAO = new MaterialDaoImpl(connection);
    }


    @Override
    public Boolean create(Map<Integer, Material> materials) throws SQLException {
        return materialDAO.create(materials);
    }
}
