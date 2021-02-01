package library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
	
    private final String url;
    private final String username;
    private final String password;
	
	public ProductDAO(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public Product getProduct(int id) throws SQLException {
		final String sql = "SELECT * FROM products WHERE productID = ?";
		
		Product product = null;
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
		    String name = rs.getString("name");
		    String description = rs.getString("description");
		    double unitPrice = rs.getInt("unitPrice");
		    int quantity = rs.getInt("quantity");
		    
		    product = new Product(id, name, description, unitPrice, quantity);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return product;
	}
	
	public List<Product> getProducts() throws SQLException {
		final String sql = "SELECT * FROM products ORDER BY productID ASC";
		
		List<Product> products = new ArrayList<>();
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next()) {
		    int id = rs.getInt("productID");
		    String name = rs.getString("name");
		    String description = rs.getString("description");
		    double unitPrice = rs.getDouble("unitPrice");
		    int quantity = rs.getInt("quantity");
		    
		    products.add(new Product(id, name, description, unitPrice, quantity));
		}
		
		rs.close();
		stmt.close();
		conn.close();
		
		return products;
	}
	
	public boolean insertProduct(String name, String description, double unitPrice, int quantity) throws SQLException {
    	final String sql = "INSERT INTO products (name, description, unitPrice, quantity) " +
    		"VALUES (?, ?, ?, ?)";
    	
    	Connection conn = getConnection();
    	PreparedStatement pstmt = conn.prepareStatement(sql);
    	
    	pstmt.setString(1, name);
    	pstmt.setString(2, description);
    	pstmt.setDouble(3, unitPrice);
    	pstmt.setInt(4, quantity);
    	int affected = pstmt.executeUpdate();
    	
    	pstmt.close();
    	conn.close();
    	
    	return affected == 1;
    }
	
	public boolean updateProduct(Product product) throws SQLException {
		final String sql = "UPDATE products SET name = ?, description = ?, unitPrice = ?, quantity = ? " +
			"WHERE productID = ?";
			
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
			
		pstmt.setString(1, product.getName());
		pstmt.setString(2, product.getDescription());
		pstmt.setDouble(3, product.getUnitPrice());
		pstmt.setInt(4, product.getQuantity());
		pstmt.setInt(5, product.getId());
		int affected = pstmt.executeUpdate();
			
		pstmt.close();
		conn.close();
			
		return affected == 1;
	}
	
	public boolean deleteProduct(Product product) throws SQLException {
		final String sql = "DELETE FROM products WHERE productID = ?";
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, product.getId());
		int affected = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return affected == 1;
	}
  
	private Connection getConnection() throws SQLException {
		final String driver = "com.mysql.cj.jdbc.Driver";
    
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return DriverManager.getConnection(url, username, password);
	}
}