package Utility;

import Entity.Labor;
import Entity.Material;
import java.util.Map;

public class CostCalculation {


    public static Double calculateMaterialCost(Material material) {
       return material.getQuantity() * material.getUnitCost() * material.getQualityCoefficient() + material.getTransportCost();
    }


    public static Double calculateMaterialsCost(Map<Integer,Material> materials) {
        return materials.values().stream().mapToDouble(CostCalculation::calculateMaterialCost).sum();
    }


    public static Double calculateLaborCost(Labor labor) {
        return labor.getHourlyRate() * labor.getWorkingHours() * labor.getWorkerProductivity();
    }


    public static Double calculateLaborsCost(Map<Integer,Labor> labors) {
        return labors.values().stream().mapToDouble(CostCalculation::calculateLaborCost).sum();
    }


    public static Double calculateCostWithTVA(Map<Integer,Material> materials, Map<Integer,Labor> labors, Double vatRate){
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
