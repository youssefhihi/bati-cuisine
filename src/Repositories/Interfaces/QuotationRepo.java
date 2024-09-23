package Repositories.Interfaces;

import Entity.Project;
import Entity.Quotation;
import Exceptions.DatabaseException;

import java.util.Optional;
import java.util.UUID;

public interface QuotationRepo {


    Boolean create(Quotation quotation) throws DatabaseException;
    Optional<Quotation> getForProject(Project project) throws DatabaseException;
    Boolean acceptQuotation(Quotation quotation) throws DatabaseException;
}
