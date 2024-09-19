package Services.Impl;

import Entity.Project;
import Exceptions.DatabaseException;
import Repositories.Impl.ProjectRepoImpl;
import Repositories.Interfaces.ProjectRepo;
import Services.Interfaces.ProjectService;

import java.sql.Connection;
import java.util.Map;
import java.util.UUID;

public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepo projectRepo;

    public ProjectServiceImpl(Connection connection){
        this.projectRepo = new ProjectRepoImpl(connection);
    }

    @Override
    public Map<UUID, Project> getProjectInProgress() throws DatabaseException {
        return projectRepo.getInProgress();
    }

    @Override
    public void createProject(Project project)throws DatabaseException{
        Boolean isAdded = projectRepo.create(project);
        if (isAdded){
            System.out.println("✅ Project Added successfully!");
        } else {
            System.out.println("❗ Failed to added Client. An unexpected error occurred.");
        }

    }
}
