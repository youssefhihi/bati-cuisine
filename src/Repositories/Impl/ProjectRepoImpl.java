package Repositories.Impl;

import DAO.Impl.ProjectDaoImpl;
import DAO.interfaces.ProjectDAO;
import Entity.Project;
import Exceptions.DatabaseException;
import Repositories.Interfaces.ProjectRepo;

import java.sql.Connection;
import java.util.Map;
import java.util.UUID;

public class ProjectRepoImpl implements ProjectRepo {
    private final ProjectDAO projectDAO;

    public ProjectRepoImpl(Connection connection){
        this.projectDAO = new ProjectDaoImpl(connection);
    }

    @Override
    public Map<UUID, Project>getInProgress() throws DatabaseException {
        return projectDAO.getInProgress();
    }

    @Override
    public Boolean create(Project project)throws DatabaseException {
        return projectDAO.create(project);
    }
}
