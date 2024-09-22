package Services.Impl;

import Entity.Material;
import Entity.Project;
import Exceptions.DatabaseException;
import Repositories.Impl.MaterialRepoImpl;
import Repositories.Interfaces.MaterialRepo;
import Services.Interfaces.MaterialService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepo materialRepo;

    public MaterialServiceImpl(Connection connection){
        this.materialRepo = new MaterialRepoImpl(connection);
    }


    @Override
    public void createMaterials(Map<UUID, Material> materials) throws SQLException {
        Boolean isAdded =  materialRepo.create(materials);
        if (Boolean.TRUE.equals(isAdded)){
            System.out.println("✅ materials Added successfully!");
        } else {
            System.out.println("❗ Failed to add materials. An unexpected error occurred.");
        }
    }

    @Override
    public Map<UUID, Material> getMaterialsForProject(Project project) throws DatabaseException {
        return materialRepo.getForProject(project);
    }

}
