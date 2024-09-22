package Utility;

import Entity.Labor;
import Entity.Material;
import java.util.Map;
import java.util.UUID;

public class CostCalculation {


    public static Double calculateMaterialCost(Material material) {
       return (material.getQuantity() * material.getUnitCost() * material.getQualityCoefficient() + material.getTransportCost()) * (1 + material.getVatRate() / 100);
    }


    public static Double calculateMaterialsCost(Map<UUID,Material> materials) {
        return materials.values().stream().mapToDouble(CostCalculation::calculateMaterialCost).sum();
    }


    public static Double calculateLaborCost(Labor labor) {
        return (labor.getHourlyRate() * labor.getWorkingHours() * labor.getWorkerProductivity()) * (1 + labor.getVatRate() / 100);
    }


    public static Double calculateLaborsCost(Map<UUID,Labor> labors) {
        return labors.values().stream().mapToDouble(CostCalculation::calculateLaborCost).sum();
    }


    public static Double calculateMaterialsWithTVA(Map<UUID,Material> materials, Double vatRate){
        return calculateMaterialsCost(materials) * (1 + vatRate / 100);
    }

    public static Double calculateLaborsWithTVA(Map<UUID,Labor> labors, Double vatRate){
        return calculateLaborsCost(labors) * (1 + vatRate / 100);
    }
    public static Double calculateCostWithTVA(Map<UUID,Material> materials, Map<UUID,Labor> labors, Double vatRate){
        return (calculateMaterialsCost(materials) + calculateLaborsCost(labors)) * (1 + vatRate / 100);
    }


    public static Double calculateCostBeforeMarge(Map<UUID,Material> materials, Map<UUID,Labor> labors){
        return calculateMaterialsCost(materials) + calculateLaborsCost(labors);
    }


    public static Double calculateProfitMarge(Map<UUID,Material> materials, Map<UUID,Labor> labors, Double profitMarginPercentage){
        return calculateCostBeforeMarge(materials, labors) * profitMarginPercentage / 100;
    }


    public static Double calculateProjectCost(Map<UUID,Material> materials, Map<UUID,Labor> labors,Double profitMargin){
        return calculateCostBeforeMarge(materials, labors) + calculateProfitMarge(materials, labors,profitMargin);
    }



}
