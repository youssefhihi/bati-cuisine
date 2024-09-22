package Services.Impl;

import Entity.Project;
import Entity.Quotation;
import Exceptions.DatabaseException;
import Repositories.Impl.QuotationRepoImpl;
import Repositories.Interfaces.QuotationRepo;
import Services.Interfaces.QuotationService;

import java.sql.Connection;
import java.util.Optional;

public class QuotationServiceImpl implements QuotationService {

    private final QuotationRepo quotationRepo;
    public QuotationServiceImpl(Connection connection){
        this.quotationRepo = new QuotationRepoImpl(connection);
    }

    @Override
    public void createQuotation(Quotation quotation)throws DatabaseException {
        Boolean isAdded = quotationRepo.create(quotation);
        if(Boolean.TRUE.equals(isAdded)){
            System.out.println("✅ Quotation Added successfully!");
        } else {
            System.out.println("❗ Failed to add Quotation. An unexpected error occurred.");
        }
    }
    @Override
    public Optional<Quotation> getQuotationForProject(Project project) throws DatabaseException{
        return quotationRepo.getForProject(project);
    }
}
