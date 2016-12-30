import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOImpl implements DAO {

	private Connection conn;
	
	public DAOImpl(){
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database?autoReconnect=true&useSSL=false", "root", "336600");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
    public void insertProduct (Product product) throws SQLException, DAOException{
        try{
            PreparedStatement psmt = conn.prepareStatement("INSERT INTO product VALUES (id, type, manufacturer, productionDate, expiryDate) VALUES (?,?,?,?,?);");
            psmt.setInt(1, product.getId());
            psmt.setString(2, product.getType());
            psmt.setString(3, product.getManufacturer());
            psmt.setString(4, product.getProductionDate());
            psmt.setString(5, product.getExpiryDate());
            psmt.executeUpdate();
        }
        catch (SQLException e) {
        	throw new DAOException(e);
        }
    }
    public Product getProduct(int ID) throws SQLException, DAOException
    {
    	Product product = null;
    	try
    	{
    		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database?autoReconnect=true&useSSL=false", "root", "336600");
    		PreparedStatement psmt = conn.prepareStatement("SELECT * FROM product WHERE id = ?");
    		psmt.setInt(1, ID);
            ResultSet res =  psmt.executeQuery();
            while (res.next()) {
            	product = new Product(res.getInt("id"));
            	product.setType(res.getString("type"));
            	product.setManufacturer(res.getString("manufacturer"));
            	product.setProductionDate(res.getString("productionDate"));
            	product.setExpiryDate(res.getString("expiryDate"));
			}
    	}
    	catch (SQLException e) {
    		throw new DAOException(e);
		}
    	
    	return product;
    }
    public void updateProduct (Product product) throws SQLException, DAOException{
        try{
            PreparedStatement psmt = conn.prepareStatement("UPDATE `store`.`product` SET `Type` = ?, `Manufacturer` = ?, `Production_Date` = ?, `Expiry_Date` = ? WHERE `product`.`Product_ID` = ?;");
            psmt.setString(1, product.getType());
            psmt.setString(2, product.getManufacturer());
            psmt.setString(3, product.getProductionDate());
            psmt.setString(4, product.getExpiryDate());
            psmt.setInt(5, product.getId());
            psmt.executeUpdate();
        }
        catch (SQLException e) {
        	throw new DAOException(e);
        }
    }
    public void deleteProduct (int id) throws SQLException, DAOException{
        try{
            PreparedStatement psmt = conn.prepareStatement("DELETE FROM product WHERE id = ?;");
            psmt.setInt(1, id);
            psmt.executeUpdate();
        }
        catch (SQLException e) {
        	throw new DAOException(e);
        }
    }
}
