<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>
<script>
    $(function () {
        $('input[name=dateTime]').datepicker();
    });
</script>

<form method="POST" action='meals' name="frmAddMeal">
    <table>
        <tr>
            <td><input type="hidden" name="mealId" value="${meal == null ? '' : meal.id}"></td>
        </tr>
        <tr>
            <td>DateTime:</td>
            <td><input type="datetime-local" name="dateTime"
                <javatime:parseLocalDateTime value="${meal == null ? '' : meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm"
                                             var="parsedDate"/>
                <javatime:format value="${parsedDate}" pattern="yyyy-MM-dd HH:mm"/></td>
        </tr>
        <tr>
            <td>Description:</td>
            <td><input type="text" name="description" value="${meal == null ? '' : meal.description}"></td>
        </tr>
        <tr>
            <td>Calories:</td>
            <td><input type="text" name="calories" value="${meal == null ? '' : meal.calories}"></td>
        </tr>
    </table>
    <input type="submit" value="Save"/>
    <input type="submit" value="Cancel" name="Cancel"/>
</form>
</body>
</html>
