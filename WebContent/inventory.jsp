<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>GameStop Inventory</title>
    
    <style type="text/css">
      <%@ include file="css/styles.css" %>
    </style>
  </head>
  <body>
    <div>
      <h1>Current Inventory</h1>
      
      <div class="header">
        <a href="${pageContext.request.contextPath}/" class="header-button">VIEW ALL</a>
        <a href="${pageContext.request.contextPath}/add" class="header-button">ADD PRODUCTS</a> 
      </div>
    </div>
    <form action="search" method="post">
    <div class="search">
    	<select name="searchTerm">
    		<option value="searchNONE" selected>-- Select --</option>
    		<option value="searchID">ID</option>
    		<option value="searchNAME">Name</option>
    		<option value="searchDESCRIPTION">Rating</option>
    		<option value="searchPRICE">Price</option>
    		<option value="searchQUANTITY">Quantity</option>
    	</select>
    	<input type="text" name="searchFor"/>
    	<input type="submit" value="Search" name="submit" class="search-button"/>
    </div>
    </form>
    <div>
      <table border="1">        
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Rating</th>
          <th>Price Per Unit</th>
          <th>Quantity</th>
          <th>Actions</th>
        </tr>
        <c:forEach var="product" items="${products}">
          <tr>
            <td><c:out value="${product.id}" /></td>
            <td><c:out value="${product.name}" /></td>
            <td><c:out value="${product.description}" /></td>
            <td><c:out value="${product.unitPrice}" /></td>
            <td><c:out value="${product.quantity}" /></td>
            <td>
	          <div>
	            <a href="${pageContext.request.contextPath}/update?action=rent&id=${product.id}" class="button">DEC</a>
	            <a href="${pageContext.request.contextPath}/update?action=return&id=${product.id}" class="button">INC</a>
	            <a href="${pageContext.request.contextPath}/edit?id=${product.id}" class="button">EDIT</a>
	          </div>
            </td>
          </tr>
        </c:forEach>
      </table>
    </div>
  </body>
</html>