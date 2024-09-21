package DAO.interfaces;

import Entity.Project;
import Exceptions.DatabaseException;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface ProjectDAO {

    Map<UUID, Project> getInProgress() throws DatabaseException;
    Optional<Project> create(Project project) throws DatabaseException;

}
