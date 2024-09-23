package Services.Interfaces;

import Entity.Project;
import Entity.Quotation;
import Exceptions.DatabaseException;

import java.util.Optional;

public interface QuotationService {

    void createQuotation(Quotation quotation) throws DatabaseException;
    Optional<Quotation> getQuotationForProject(Project project) throws DatabaseException;
    void acceptQuotation(Quotation quotation) throws DatabaseException;


}
