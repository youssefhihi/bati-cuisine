package Entity;

import enums.ComponentType;

public class Material extends Component {
    private Double transportCost;
    private Double quantity;
    private Double qualityCoefficient;
    private Double unitCost;


    public Material(String unitName ,Double unitCost, Double quantity, ComponentType componentType, Project project, Double vatRate, Double transportCost, Double qualityCoefficient) {
        super(unitName, componentType, vatRate, project);
        this.transportCost = transportCost;
        this.qualityCoefficient = qualityCoefficient;
        this.quantity = quantity;
        this.unitCost =unitCost;

    }
    public Material(){}
    public Double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }
    public Double getQuantity() {
        return quantity;
    }


    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
    public Double getQualityCoefficient() {
        return qualityCoefficient;
    }

    public void setQualityCoefficient(Double qualityCoefficient) {
        this.qualityCoefficient = qualityCoefficient;
    }

    public Double getTransportCost() {
        return transportCost;
    }

    public void setTransportCost(Double transportCost) {
        this.transportCost = transportCost;
    }

    @Override
    public String toString() {
        return "Material{" +
                "transportCost=" + transportCost +
                ", quantity=" + quantity +
                ", qualityCoefficient=" + qualityCoefficient +
                ", unitCost=" + unitCost +
                '}';
    }
}
