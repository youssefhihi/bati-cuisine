package Services.Interfaces;

import Entity.Project;
import Exceptions.DatabaseException;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface ProjectService {
    Map<UUID, Project> getProjectInProgress() throws DatabaseException;
    Optional<Project> createProject(Project project) throws DatabaseException ;
}
