
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Student</title>

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
        <h3>Update Student</h3>

        <form action="StudentControllerServlet" method="post">

            <input type="hidden" name="command" value="UPDATE" />

            <input type="hidden" name="studentId" value="${THE_STUDENT.id}" />

            <table>
                <tbody>
                    <tr>
                        <td><label>First name:</label></td>
                        <td><input type="text" name="firstName"
                        value="${THE_STUDENT.firstName}"/></td>
                    </tr>

                    <tr>
                        <td><label>Last name:</label></td>
                        <td><input type="text" name="lastName"
                                   value="${THE_STUDENT.lastName}"/></td>
                    </tr>

                    <tr>
                        <td><label>Email:</label></td>
                        <td><input type="text" name="email"
                                   value="${THE_STUDENT.email}"/></td>
                    </tr>

                    <tr>
                        <td><label>Phone number:</label></td>
                        <td><input type="text" name="phoneNum"
                                   value="${THE_STUDENT.phoneNum}"/></td>
                    </tr>

                    <tr>
                        <td><label></label></td>
                        <td><input type="submit" value="Save" class="save"/></td>
                    </tr>

                </tbody>
            </table>

        </form>

        <p>
            <a href="StudentControllerServlet" class="backLink">Back</a>
        </p>

    <b>${UPDATE_STUDENT_ERROR}</b>

    </div>

</body>
</html>









