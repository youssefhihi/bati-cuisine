package Entity;

import enums.ComponentType;

public class Labor extends Component {
    private Double hourlyRate;
    private Double workingHours;
    private Double workerProductivity;
    private String laborType;

    public Labor( ComponentType componentType, Double vatRate, Project project, Double hourlyRate, Double workingHours, Double workerProductivity, String laborType) {
        super(componentType, vatRate, project);
        this.hourlyRate = hourlyRate;
        this.workingHours = workingHours;
        this.workerProductivity = workerProductivity;
        this.laborType = laborType;
    }
    public Labor(){}

    public String getLaborType() {
        return laborType;
    }

    public void setLaborType(String laborType) {
        this.laborType = laborType;
    }

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
