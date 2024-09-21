package Entity;

import enums.ComponentType;
import java.util.UUID;

public class Component {
    private UUID id;
    private String unitName;
    private ComponentType componentType;
    private Double vatRate;
    private Double unitCost;
    private Project project;

    public Component(String unitName, ComponentType componentType, Double vatRate, Double unitCost, Project project) {
        this.unitName = unitName;
        this.componentType = componentType;
        this.vatRate = vatRate;
        this.project = project;
        this.unitCost =unitCost;
    }
    public Component(){}

    public Double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }


    public ComponentType getComponentType() {
        return componentType;
    }

    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }

    public Double getVatRate() {
        return vatRate;
    }

    public void setVatRate(Double vatRate) {
        this.vatRate = vatRate;
    }
}
