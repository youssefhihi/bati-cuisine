package Entity;

import enums.ProjectStatus;
import java.util.UUID;

public class Project {
    private UUID id;
    private String projectName;
    private Double profitMargin;
    private Double totalCost;
    private ProjectStatus projectStatus;
    private Double area;
    private Double VATRate;
    private Client client;

    public Project(String projectName, Double profitMargin, Double totalCost, ProjectStatus projectStatus,Double area, Double VATRate, Client client) {
        this.projectName = projectName;
        this.profitMargin = profitMargin;
        this.totalCost = totalCost;
        this.projectStatus = projectStatus;
        this.area = area;
        this.VATRate = VATRate;
        this.client = client;
    }

    public Project() {}

    public Double getVATRate() {
        return VATRate;
    }

    public void setVATRate(Double VATRate) {
        this.VATRate = VATRate;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Double getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(Double profitMargin) {
        this.profitMargin = profitMargin;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    @Override
    public String toString() {
        return "Project{" +
                "VATRate=" + VATRate +
                ", area=" + area +
                ", totalCost=" + totalCost +
                ", profitMargin=" + profitMargin +
                ", projectName='" + projectName + '\'' +
                ", projectStatus=" + projectStatus +
                '}';
    }
}
