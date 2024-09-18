package Entity;

import enums.ComponentType;

public class Material extends Component {
    private Double transportCost;
    private Double quantity;
    private Double qualityCoefficient;

    public Material(Double unitCost, Double quantity, ComponentType componentType, Project project, Double vatRate, Double transportCost, Double qualityCoefficient) {
        super(unitCost, componentType, vatRate, project);
        this.transportCost = transportCost;
        this.qualityCoefficient = qualityCoefficient;
        this.quantity = quantity;
    }
    public Material(){}

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
}
