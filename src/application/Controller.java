package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import library.Product;
import library.ProductDAO;

@SuppressWarnings("serial")
public class Controller extends HttpServlet {
	
	private ProductDAO dao;
	private String searchTerm = "";
	private String searchFor = "";
	
	public void init() {
		final String url = getServletContext().getInitParameter("JDBC-URL");
		final String username = getServletContext().getInitParameter("JDBC-USERNAME");
		final String password = getServletContext().getInitParameter("JDBC-PASSWORD");
		
		dao = new ProductDAO(url, username, password);
	}
  
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		searchTerm = request.getParameter("searchTerm");
		searchFor = request.getParameter("searchFor");
		doGet(request, response);
	}
  
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String action = request.getServletPath();
		
		try {
			switch (action) {
				case "/search": searchProducts(request, response); break;
				case "/add": // intentionally fall through
				case "/edit": showEditForm(request, response); break;
				case "/insert": insertProducts(request, response); break;
				case "/update": updateProducts(request, response); break;
				default: viewProducts(request, response); break;
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}
  
	private void viewProducts(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		List<Product> products = dao.getProducts();
		request.setAttribute("products", products);
    
		RequestDispatcher dispatcher = request.getRequestDispatcher("inventory.jsp");
		dispatcher.forward(request, response);
	}
	
	private void insertProducts(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		double unitPrice = Double.parseDouble(request.getParameter("unitPrice"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
			
		dao.insertProduct(name, description, unitPrice, quantity);
		response.sendRedirect(request.getContextPath() + "/");
	}
	
	private void updateProducts(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		final String action = request.getParameter("action") != null
			? request.getParameter("action")
		    : request.getParameter("submit").toLowerCase();
		final int id = Integer.parseInt(request.getParameter("id"));
			
		Product product = dao.getProduct(id);
		switch (action) {
			case "rent": product.rentMe(); break;
		    case "return": product.returnMe(); break;
		    case "save":
		      String name = request.getParameter("name");
		      String description = request.getParameter("description");
		      double unitPrice = Double.parseDouble(request.getParameter("unitPrice"));
		      int quantity = Integer.parseInt(request.getParameter("quantity"));
				
		      product.setName(name);
		      product.setDescription(description);
		      product.setUnitPrice(unitPrice);
		      product.setQuantity(quantity);
		      break;
		    case "delete": deleteProduct(id, request, response); return;
		}

		dao.updateProduct(product);
		response.sendRedirect(request.getContextPath() + "/");
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		try {
			final int id = Integer.parseInt(request.getParameter("id"));
		    
		    Product product = dao.getProduct(id);
		    request.setAttribute("product", product);
		} catch (NumberFormatException e) {
			// this is expected for empty forms (i.e., without a valid id)
		} finally {
		    RequestDispatcher dispatcher = request.getRequestDispatcher("productform.jsp");
		    dispatcher.forward(request, response);
		}
	}
		    
	private void deleteProduct(final int id, HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {	
		dao.deleteProduct(dao.getProduct(id));	
		response.sendRedirect(request.getContextPath() + "/");
	}
	
	private void searchProducts(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		List<Product> products = dao.getProducts();
		if (searchTerm.equals("searchNONE")) {
			request.setAttribute("products", products);
		    
			RequestDispatcher dispatcher = request.getRequestDispatcher("inventory.jsp");
			dispatcher.forward(request, response);
		} else if (searchTerm.equalsIgnoreCase("searchID")) {
			for (int i = 0; i < products.size(); i++) {
				if (!Integer.toString(products.get(i).getId()).equalsIgnoreCase(searchFor)) {
					products.remove(i);
					i -= 1;
				}
			}
			request.setAttribute("products", products);
		    
			RequestDispatcher dispatcher = request.getRequestDispatcher("inventory.jsp");
			dispatcher.forward(request, response);
		} else if (searchTerm.equalsIgnoreCase("searchNAME")) {
			for (int i = 0; i < products.size(); i++) {
				if (!products.get(i).getName().equalsIgnoreCase(searchFor)) {
					products.remove(i);
					i -= 1;
				}
			}
			request.setAttribute("products", products);
		    
			RequestDispatcher dispatcher = request.getRequestDispatcher("inventory.jsp");
			dispatcher.forward(request, response);
		} else if (searchTerm.equalsIgnoreCase("searchDESCRIPTION")) {
			for (int i = 0; i < products.size(); i++) {
				if (!products.get(i).getDescription().equalsIgnoreCase(searchFor)) {
					products.remove(i);
					i -= 1;
				}
			}
			request.setAttribute("products", products);
		    
			RequestDispatcher dispatcher = request.getRequestDispatcher("inventory.jsp");
			dispatcher.forward(request, response);
		} else if (searchTerm.equalsIgnoreCase("searchPrice")) {
			for (int i = 0; i < products.size(); i++) {
				if (!Double.toString(products.get(i).getUnitPrice()).equalsIgnoreCase(searchFor)) {
					products.remove(i);
					i -= 1;
				}
			}
			request.setAttribute("products", products);
		    
			RequestDispatcher dispatcher = request.getRequestDispatcher("inventory.jsp");
			dispatcher.forward(request, response);
		} else if (searchTerm.equalsIgnoreCase("searchQUANTITY")) {
			for (int i = 0; i < products.size(); i++) {
				if (!Integer.toString(products.get(i).getQuantity()).equalsIgnoreCase(searchFor)) {
					products.remove(i);
					i -= 1;
				}
			}
			request.setAttribute("products", products);
		    
			RequestDispatcher dispatcher = request.getRequestDispatcher("inventory.jsp");
			dispatcher.forward(request, response);
		} else {
			request.setAttribute("products", products);
		    
			RequestDispatcher dispatcher = request.getRequestDispatcher("inventory.jsp");
			dispatcher.forward(request, response);
		}
	}
}
