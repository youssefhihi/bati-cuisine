package Services.Impl;

import Entity.Material;
import Repositories.Impl.MaterialRepoImpl;
import Repositories.Interfaces.MaterialRepo;
import Services.Interfaces.MaterialService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepo materialRepo;

    public MaterialServiceImpl(Connection connection){
        this.materialRepo = new MaterialRepoImpl(connection);
    }


    @Override
    public void createMaterials(Map<Integer, Material> materials) throws SQLException {
        Boolean isAdded =  materialRepo.create(materials);
        if (Boolean.TRUE.equals(isAdded)){
            System.out.println("✅ labors Added successfully!");
        } else {
            System.out.println("❗ Failed to add labors. An unexpected error occurred.");
        }
    }
}
