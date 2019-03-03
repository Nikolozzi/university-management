<%--
  Created by IntelliJ IDEA.
  User: Geo Computers
  Date: 03.03.2019
  Time: 2:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Registered Students</title>

    <link type="text/css" rel="stylesheet" href="css/style.css">
    <link type="text/css" rel="stylesheet" href="css/add-student-style.css">

</head>
<body>

<div id="wrapper">
    <div id="header">
        <h2>University Management System</h2>
    </div>

</div>

<h3>See Students on each Subject</h3>

<form action="StudentControllerServlet" method="get">

    <input type="hidden" name="command" value="GET_STUDENTS_PER_SUBJECT" />

    <select name="subjectSelect" class="selectSubjectsStudents">

        <c:forEach var="tempSubj" items="${THE_SUBJECTS}">
            <option>${tempSubj.subjName}</option>
        </c:forEach>

    </select>

    <input type="submit" value="Show" class="ShowStudents" />

</form>

<div id="container">

    <div id="content">

        <table class="studentPerSubjectTable">

            <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Phone</th>
            </tr>

            <c:forEach var="tempStudent" items="${STUDENT_PER_SUBJECT}">

                <tr>
                    <td>${tempStudent.firstName}</td>
                    <td>${tempStudent.lastName}</td>
                    <td>${tempStudent.email}</td>
                    <td>${tempStudent.phoneNum}</td>
                </tr>
            </c:forEach>

        </table>
    </div>
</div>

<p>
    <a href="StudentControllerServlet" class="backLink2">Back</a>
</p>

</body>
</html>
