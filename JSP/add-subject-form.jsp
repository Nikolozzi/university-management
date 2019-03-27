
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New Subject</title>

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
        <h3>Add New Subject</h3>

        <form action="StudentSubjectController" method="post">

            <input type="hidden" name="command" value="ADD_SUBJECT" />

            <table>
                <tbody>
                    <tr>
                        <td><label>Subject Name:</label></td>
                        <td><input type="text" name="subjName" /></td>
                    </tr>

                    <tr>
                        <td><label>Credit Amount:</label></td>
                        <td><input type="text" name="creditNum" /></td>
                    </tr>

                    <tr>
                        <td><label></label></td>
                        <td><input type="submit" value="Save" class="save"/></td>
                    </tr>

                </tbody>
            </table>

        </form>

        <a href="StudentSubjectController" class="backLink">Back</a>

    </div>

    <b>${ADD_SUBJECT_ERROR}</b>


</body>
</html>









