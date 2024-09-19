package DAO.interfaces;

import Entity.Project;
import Exceptions.DatabaseException;

import java.util.Map;
import java.util.UUID;

public interface ProjectDAO {

    Map<UUID, Project> getInProgress() throws DatabaseException;
    Boolean create(Project project) throws DatabaseException;

}
