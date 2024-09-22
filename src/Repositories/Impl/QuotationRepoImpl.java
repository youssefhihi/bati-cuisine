package Repositories.Impl;

import DAO.Impl.QuotationDaoImpl;
import DAO.interfaces.QuotationDAO;
import Entity.Project;
import Entity.Quotation;
import Exceptions.DatabaseException;
import Repositories.Interfaces.QuotationRepo;

import java.sql.Connection;
import java.util.Optional;

public class QuotationRepoImpl implements QuotationRepo {

    private final QuotationDAO quotationDAO ;

    public QuotationRepoImpl(Connection connection){
        this.quotationDAO = new QuotationDaoImpl(connection);
    }

    @Override
    public Boolean create(Quotation quotation) throws DatabaseException{
        return quotationDAO.create(quotation);
    }

    @Override
    public Optional<Quotation> getForProject(Project project) throws  DatabaseException{
        return quotationDAO.getForProject(project);
    }
}
