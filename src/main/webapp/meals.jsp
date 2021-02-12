<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<p><a href="meals?action=insert">Add Meal</a></p>
<table border="1" cellpadding="5" cellspacing="1">
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan=2>Action</th>
    </tr>
    </thead>
    <tbody>
    <jsp:useBean id="meals" scope="request" type="java.util.List"/>
    <c:forEach items="${meals}" var="item">
        <tr style="color: ${item.excess == false ? "green" : "red"}">
            <td>
                <javatime:parseLocalDateTime value="${item.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate"/>
                <javatime:format value="${parsedDate}" pattern="yyyy-MM-dd HH:mm"/></td>
            <td>${item.description}</td>
            <td>${item.calories}</td>
            <td><a href="meals?action=edit&mealId=${item.id}">Update</a></td>
            <td><a href="meals?action=delete&mealId=${item.id}">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>