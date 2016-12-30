import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class UTest {
	
	public Product p;
	DAOImpl new_DAO = new DAOImpl();
	
	@Mock 
	Connection conn;
	
	@Mock
	PreparedStatement psmt;
	
	@InjectMocks
	DAOImpl NewDAO = new DAOImpl();



	@Before
	public void setUp() {
		 p = new Product(9);
	}

	
	// test Constructor
    @Test
	public void testproduct_Constructor() {
    	
    	 assertEquals(9, p.getId());
	}
    

	@Test
	public void testSetType() {
		
		p.setType("Shose"); 
		assertTrue(p.getType().equals("Shose"));

	}

	@Test
	public void testSetManufacturer() {

		p.setManufacturer("joe"); 
		assertTrue(p.getManufacturer().equals("joe"));
	}

	@Test
	public void testSetProductiondate() {

		p.setProductionDate("15-03-03"); 
		assertTrue(p.getProductionDate().equals("15-03-03"));
	}

	@Test
	public void testSetExpirydate() {

		p.setExpiryDate("23-03-03"); 
		assertTrue(p.getExpiryDate().equals("23-03-03"));
	}
	
	@Test
	public void TestHappyCase_1() throws SQLException, DAOException
	{
		when(conn.prepareStatement(anyString())).thenReturn(psmt);
		when(psmt.executeUpdate()).thenReturn(1);
		Product C = new Product(9);
		NewDAO.insertProduct(C);
	}
	
	@Test
	public void TestHappyCase_2() throws SQLException, DAOException{
		when(conn.prepareStatement(anyString())).thenReturn(psmt);
		when(psmt.executeUpdate()).thenReturn(1);
		Product C = new Product(10);
        C.setType("smartwatch");
        C.setManufacturer("samsung");
        C.setProductionDate("2011-12-20");
        C.setExpiryDate("2019-10-1");	
		NewDAO.insertProduct(C);
		verify(psmt, times(1)).executeUpdate();
			
		ArgumentCaptor<String> Stringcaptor = ArgumentCaptor.forClass(String.class);
		verify(psmt, times(4)).setString(anyInt(), Stringcaptor.capture());	
		Assert.assertTrue(Stringcaptor.getAllValues().get(0).equals("10"));
		Assert.assertTrue(Stringcaptor.getAllValues().get(1).equals("smartwatch"));
		Assert.assertTrue(Stringcaptor.getAllValues().get(2).equals("samsung"));
		Assert.assertTrue(Stringcaptor.getAllValues().get(3).equals("2011-12-20"));
		Assert.assertTrue(Stringcaptor.getAllValues().get(4).equals("2019-10-1"));
	}

	@Test (expected = DAOException.class)
	public void ExceptionCase() throws SQLException, DAOException{
		when(conn.prepareStatement(anyString())).thenReturn(psmt);
		when(psmt.executeUpdate()).thenThrow(new SQLException());
		Product p = new Product(20);
		NewDAO.insertProduct(p);	
	}	
	
	 @Test
     public void HappyCase_integrationtest() throws Exception ,DAOException{
		 DAOImpl DAO_test = new DAOImpl();
	    
		 Product testdelet_product = new Product(14);
        testdelet_product.setType("labtpop");
        testdelet_product.setManufacturer("dell");
        testdelet_product.setProductionDate("2010-05-12");
        testdelet_product.setExpiryDate("2018-08-11");
        DAO_test.insertProduct(testdelet_product);
        Assert.assertNotNull(testdelet_product);
        Assert.assertEquals(14,testdelet_product.getId());
        DAO_test.deleteProduct(14); 
        // to make sure the added one has been deleted
        Assert.assertTrue(DAO_test.getProduct(14) == null);
}
	
}