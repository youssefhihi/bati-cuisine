package Utility;

import Entity.Labor;
import Entity.Material;
import java.util.List;

public class CostCalculation {


    public static Double calculateMaterialCost(Material material) {
       return material.getQuantity() * material.getUnitCost() * material.getQualityCoefficient() + material.getTransportCost();
    }

    public static Double calculateMaterialsCost(List<Material> materials) {
        double materialsCost = 0.0;
        for(Material material: materials){
            materialsCost +=  calculateMaterialCost(material);
        }
        return materialsCost;
    }

    public static Double calculateLaborCost(Labor labor) {
        return labor.getHourlyRate() * labor.getWorkingHours() * labor.getWorkerProductivity();
    }

    public static Double calculateLaborsCost(List<Labor> labors) {
        double laborsCost = 0.0;
        for(Labor labor: labors){
            laborsCost +=  calculateLaborCost(labor);
        }
        return laborsCost;
    }

    public static Double calculateCostWithTVA(List<Material> materials, List<Labor> labors, Double vatRate){
        return (calculateMaterialsCost(materials) + calculateLaborsCost(labors)) * (1 + vatRate / 100);
    }

    public static Double calculateCostBeforeMarge(Double materialsTotalCost, Double laborsTotalCost){
        return materialsTotalCost + laborsTotalCost;
    }

    public static Double calculateProfitMarge(Double materialsTotalCost, Double laborsTotalCost, Double profitMarginPercentage){
        return (materialsTotalCost + laborsTotalCost) * profitMarginPercentage / 100;
    }

    public static Double calculateProjectCost(Double materialsTotalCost, Double laborsTotalCost,Double profitMargin){
        return calculateCostBeforeMarge(materialsTotalCost, laborsTotalCost) + calculateProfitMarge(materialsTotalCost, laborsTotalCost,profitMargin);
    }



}
