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
    <div>
      <c:if test="${product != null}">
        <h2>Edit Product</h2>
        <form action="update" method="post">
          
          <label>ID<input type="text" name="id" value="<c:out value="${product.id}" />" readonly/></label>
          <label>Name<input type="text" name="name" value="<c:out value="${product.name}" />" /></label>
          <label>Rating<input type="text" name="description" value="<c:out value="${product.description}" />" /></label>
          <label>Price Per Unit<input type="text" name="unitPrice" value="<c:out value="${product.unitPrice}" />" /></label>
          <label>Quantity<input type="text" name="quantity" value="<c:out value="${product.quantity}" />" /></label>
          <div class="form-actions">
            <input type="submit" value="SAVE" name="submit" />
            <input type="submit" value="DELETE" name="submit" />
          </div>
        </form>
      </c:if>
      <c:if test="${product == null}">
        <h2>Add Product</h2>
        <form action="insert" method="post">
          <input type="hidden" name="id" />
          
          <label>ID<input type="text" name="id" value="<c:out value="TBD" />" readonly/></label>
          <label>Name<input type="text" name="name" /></label>
          <label>Description<input type="text" name="description" /></label>
          <label>Price Per Unit<input type="text" name="unitPrice" /></label>
          <label>Quantity<input type="text" name="quantity" /></label>
          <input type="submit" value="ADD" name="submit" />
        </form>
      </c:if>
    </div>
  </body>
</html>