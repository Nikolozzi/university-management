<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>

    <link type="text/css" rel="stylesheet" href="css/style.css">
    <link type="text/css" rel="stylesheet" href="css/add-student-style.css">

</head>
<body>

    <div id="wrapper">
        <div id="header">
            <h2>University Management System</h2>
        </div>
    </div>

    <br id="container">
        <h3>Add Student To Subject</h3>

        <form action="StudentSubjectController" method="post">

            <input type="hidden" name="command" value="REGISTER" />

            <input type="hidden" name="studentId" value="${THE_STUDENT.id}" />

            <table>
                <tbody>
                    <tr>
                        <td><label>First name:</label></td>
                        <td><input type="text" name="firstName"
                        value="${THE_STUDENT.firstName}" disabled/></td>
                    </tr>

                    <tr>
                        <td><label>Last name:</label></td>
                        <td><input type="text" name="lastName"
                                   value="${THE_STUDENT.lastName}" disabled/></td>
                    </tr>

                    <tr>
                        <td><label>Email:</label></td>
                        <td><input type="text" name="email"
                                   value="${THE_STUDENT.email}" disabled/></td>
                    </tr>

                    <tr>
                        <td><label>Phone number:</label></td>
                        <td><input type="text" name="phoneNum"
                                   value="${THE_STUDENT.phoneNum}" disabled/></td>
                    </tr>

                    <tr>
                        <td><label></label></td>
                        <td><input type="submit" value="Register" class="save"/></td>
                    </tr>

                </tbody>
            </table>

            <select name="subjectSelect" class="selectSubjects">

                <c:forEach var="tempSubj" items="${THE_SUBJECTS}">
                    <option>${tempSubj.subjName}</option>
                </c:forEach>

            </select>

        </form>

        <p>
            <a href="StudentSubjectController" class="backLink">Back</a>
        </p>

    <p class="error">
        <b>${REGISTER_STUDENT_ERROR}</b>
    </p>

    </div>

</body>
</html>









