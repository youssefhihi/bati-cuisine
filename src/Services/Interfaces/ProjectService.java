package Services.Interfaces;

import Entity.Project;
import Exceptions.DatabaseException;

import java.util.Map;
import java.util.UUID;

public interface ProjectService {
    Map<UUID, Project> getProjectInProgress() throws DatabaseException;
    void createProject(Project project) throws DatabaseException ;
}
