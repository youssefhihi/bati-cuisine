package Services.Impl;

import Entity.Labor;
import Entity.Material;
import Entity.Project;
import Exceptions.DatabaseException;
import Repositories.Impl.LaborRepoImpl;
import Repositories.Impl.MaterialRepoImpl;
import Repositories.Interfaces.LaborRepo;
import Repositories.Interfaces.MaterialRepo;
import Services.Interfaces.LaborService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public class LaborServiceImpl implements LaborService {

    private final LaborRepo laborRepo;

    public LaborServiceImpl(Connection connection){
        this.laborRepo = new LaborRepoImpl(connection);
    }


    @Override
    public void createLabors(Map<UUID, Labor> labors) throws SQLException {
        Boolean isAdded = laborRepo.create(labors);
        if (Boolean.TRUE.equals(isAdded)){
            System.out.println("✅ labors Added successfully!");
        } else {
            System.out.println("❗ Failed to add labors. An unexpected error occurred.");
        }
    }

    @Override
    public Map<UUID, Labor> getLaborsForProject(Project project) throws DatabaseException {
        return laborRepo.getForProject(project);
    }
}
