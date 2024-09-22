import ConnectDB.DBConnection;
import DAO.Impl.MaterialDaoImpl;
import Entity.Labor;
import Entity.Material;
import Entity.Project;
import Services.Impl.LaborServiceImpl;
import Services.Impl.MaterialServiceImpl;
import Services.Interfaces.LaborService;
import Services.Interfaces.MaterialService;
import View.BatiCuisine;
import enums.ComponentType;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
       new BatiCuisine().batiCuisineApp();
//        Connection connection = DBConnection.connect();
//        MaterialService materialService = new MaterialServiceImpl(connection);
//        Map<UUID, Material>  materialMap = new HashMap<>();
//        Material material = new Material();
//        material.setComponentType(ComponentType.material);
//        material.setTransportCost(12.1);
//        material.setVatRate(12.0);
//        material.setQuantity(1.0);
//        material.setUnitName("test");
//        material.setQualityCoefficient(12.0);
//        material.setUnitCost(12.1);
//        Project project = new Project();
//        project.setId(UUID.fromString("06130004-d822-43ed-9af2-f1130d605f89"));
//        material.setProject(project);
//        materialMap.put(UUID.randomUUID(),material);
//        LaborService laborService = new LaborServiceImpl(connection);
//        Map<UUID, Labor>  laborMap = new HashMap<>();
//        Labor labor = new Labor();
//        labor.setComponentType(ComponentType.labor);
//        labor.setWorkerProductivity(12.1);
//        labor.setVatRate(12.0);
//        labor.setWorkingHours(1.0);
//        labor.setUnitName("test");
//        labor.setHourlyRate(12.0);
//        labor.setUnitCost(12.1);
//        labor.setProject(project);
//        laborMap.put(UUID.randomUUID(),labor);
//
//        try {
//        laborService.createLabors(laborMap);
//        materialService.createMaterials(materialMap);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

    }

}