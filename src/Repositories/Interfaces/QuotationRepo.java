package Repositories.Interfaces;

import Entity.Project;
import Entity.Quotation;
import Exceptions.DatabaseException;

import java.util.Optional;

public interface QuotationRepo {


    Boolean create(Quotation quotation) throws DatabaseException;
    Optional<Quotation> getForProject(Project project) throws DatabaseException;

}
