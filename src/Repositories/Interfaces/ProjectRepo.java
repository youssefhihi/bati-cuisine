package Repositories.Interfaces;

import Entity.Project;
import Exceptions.DatabaseException;

import java.util.Map;
import java.util.UUID;

public interface ProjectRepo {

    Map<UUID, Project> getInProgress() throws DatabaseException;
    Boolean create(Project project) throws DatabaseException;

}
