
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New Student</title>

    <link type="text/css" rel="stylesheet" href="css/style.css">
    <link type="text/css" rel="stylesheet" href="css/add-student-style.css">

</head>
<body>

    <div id="wrapper">
        <div id="header">
            <h2>University Management System</h2>
        </div>
    </div>

    <div id="container">
        <h3>Add New Student</h3>

        <form action="StudentSubjectController" method="post">

            <input type="hidden" name="command" value="ADD" />

            <table>
                <tbody>
                    <tr>
                        <td><label>First name:</label></td>
                        <td><input type="text" name="firstName" /></td>
                    </tr>

                    <tr>
                        <td><label>Last name:</label></td>
                        <td><input type="text" name="lastName" /></td>
                    </tr>

                    <tr>
                        <td><label>Email:</label></td>
                        <td><input type="email" name="email" /></td>
                    </tr>

                    <tr>
                        <td><label>Phone number:</label></td>
                        <td><input type="text" name="phoneNum" /></td>
                    </tr>

                    <tr>
                        <td><label></label></td>
                        <td><input type="submit" value="Save" class="save"/></td>
                    </tr>

                </tbody>
            </table>

        </form>

        <p>
            <a href="StudentSubjectController" class="backLink">Back</a>
        </p>

        <b>${ADD_STUDENT_ERROR}</b>

    </div>

</body>
</html>









