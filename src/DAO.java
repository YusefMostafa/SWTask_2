import java.sql.SQLException;
import java.util.List;

public interface DAO {
    public void insertProduct (Product product) throws SQLException, DAOException;
    public Product getProduct(int ID) throws SQLException, DAOException;
    public void updateProduct (Product product) throws SQLException, DAOException;
    public void deleteProduct (int id) throws SQLException, DAOException;
}
