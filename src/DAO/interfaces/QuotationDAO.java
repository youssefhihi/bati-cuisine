package DAO.interfaces;

import Entity.Project;
import Entity.Quotation;
import Exceptions.DatabaseException;

import java.util.Optional;

public interface QuotationDAO {

    Boolean create(Quotation quotation) throws DatabaseException;
    Optional<Quotation> getForProject(Project project) throws DatabaseException;
    Boolean acceptQuotation(Quotation quotation) throws DatabaseException;

}

