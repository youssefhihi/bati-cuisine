package Entity;

import enums.ComponentType;

public class Labor extends Component {
    private Double hourlyRate;
    private Double workingHours;
    private Double workerProductivity;


    public Labor(String unitName, ComponentType componentType, Double vatRate, Double unitCost, Project project, Double hourlyRate, Double workingHours, Double workerProductivity) {
        super(unitName, componentType, vatRate, unitCost, project);
        this.hourlyRate = hourlyRate;
        this.workingHours = workingHours;
        this.workerProductivity = workerProductivity;
    }
    public Labor(){}


    public Double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Double getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Double workingHours) {
        this.workingHours = workingHours;
    }

    public Double getWorkerProductivity() {
        return workerProductivity;
    }

    public void setWorkerProductivity(Double workerProductivity) {
        this.workerProductivity = workerProductivity;
    }
}
