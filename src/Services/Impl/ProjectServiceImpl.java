package Services.Impl;

import Entity.Project;
import Exceptions.DatabaseException;
import Repositories.Impl.ProjectRepoImpl;
import Repositories.Interfaces.ProjectRepo;
import Services.Interfaces.ProjectService;

import java.sql.Connection;
import java.util.Map;
import java.util.Optional;
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
    public Optional<Project> createProject(Project project) throws DatabaseException{
        return projectRepo.create(project);
    }

    @Override
    public  Map<UUID,Project> searchProject(String input) throws DatabaseException{
        return projectRepo.search(input);
    }
    @Override
    public  void updateStatusOfProject(UUID id) throws DatabaseException {
        Boolean isUpdated = projectRepo.UpdateStatus(id);
        if (Boolean.TRUE.equals(isUpdated)) {
            System.out.println("✅ Project canceled successfully!");
        } else {
            System.out.println("❗ Failed to canceled Project. An unexpected error occurred.");
        }
    }

}
