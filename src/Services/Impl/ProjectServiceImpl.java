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
    public Optional<Project> createProject(Project project)throws DatabaseException{
        return projectRepo.create(project);
    }
}
