package Services.Impl;

import Entity.Labor;
import Repositories.Impl.LaborRepoImpl;
import Repositories.Impl.MaterialRepoImpl;
import Repositories.Interfaces.LaborRepo;
import Repositories.Interfaces.MaterialRepo;
import Services.Interfaces.LaborService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class LaborServiceImpl implements LaborService {

    private final LaborRepo laborRepo;

    public LaborServiceImpl(Connection connection){
        this.laborRepo = new LaborRepoImpl(connection);
    }


    @Override
    public void createLabors(Map<Integer, Labor> labors) throws SQLException {
        Boolean isAdded = laborRepo.create(labors);
        if (Boolean.TRUE.equals(isAdded)){
            System.out.println("✅ materials Added successfully!");
        } else {
            System.out.println("❗ Failed to add materials. An unexpected error occurred.");
        }
    }
}
