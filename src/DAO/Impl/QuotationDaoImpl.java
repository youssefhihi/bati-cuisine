package DAO.Impl;

import DAO.interfaces.QuotationDAO;
import Entity.Project;
import Entity.Quotation;
import Exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class QuotationDaoImpl implements QuotationDAO {

    private final Connection connection;

    public QuotationDaoImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public Boolean create(Quotation quotation) throws DatabaseException {
        String sql = "INSERT INTO quotations (estimatedAmount,issueDate,validityDate,accepted,project_id) VALUES (?,?,?,?,?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setDouble(1,quotation.getEstimatedAmount());
            stmt.setDate(2, new java.sql.Date(quotation.getIssueDate().getTime()));
            stmt.setDate(3, new java.sql.Date(quotation.getValidityDate().getTime()));
            stmt.setBoolean(4,quotation.getAccepted());
            stmt.setObject(5,quotation.getProject().getId());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            throw new DatabaseException("❗Error occurred while creating quotation"+ e.getMessage(), e);
        }
    }

    @Override
    public Optional<Quotation> getForProject(Project project) throws  DatabaseException{
        String sql = "SELECT id,estimatedAmount,issueDate,validityDate,accepted FROM quotations WHERE project_id = ? ";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setObject(1,project.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return Optional.of(mapResultSet(rs,project));
            }
            return Optional.empty();
        }catch (Exception e){
            throw new DatabaseException("❗Error occurred while getting quotation"+ e.getMessage(), e);
        }
    }


    private  Quotation mapResultSet(ResultSet rs,Project project) throws SQLException {
        Quotation quotation = new Quotation(
                rs.getDouble("estimatedAmount"),
                rs.getDate("issueDate"),
                rs.getDate("validityDate"),
                rs.getBoolean("accepted"),
                project
                );
        quotation.setId((UUID) rs.getObject("id"));
        return quotation;
    }
}
