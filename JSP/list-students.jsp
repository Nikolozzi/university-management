
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>University Management</title>

    <link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<body>

    <div id="wrapper">
        <div id="header">
            <h2>University Management System</h2>
        </div>

    </div>

    <div id="container">

        <div id="content">

            <!-- Add button -->
            <input type="button" value="Add Student"
                    onclick="window.location.href='add-student-form.jsp'; return false;"
                    class="add-student-button"
            />

            <div class="studentlbl">
                <p><b>Students: ${COUNT_STUDENT}</b></p>
            </div>

            <table>

                <tr>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Action</th>
                </tr>

                <c:forEach var="tempStudent" items="${STUDENT_LIST}">

                    <c:url var="tempLinkUpdate" value="StudentControllerServlet">
                        <c:param name="command" value="LOAD_UPDATE" />
                        <c:param name="studentId" value="${tempStudent.id}" />
                    </c:url>

                    <c:url var="tempLinkRegister" value="StudentControllerServlet">
                        <c:param name="command" value="LOAD_REGISTER" />
                        <c:param name="studentId" value="${tempStudent.id}" />
                    </c:url>

                    <c:url var="deleteLink" value="StudentControllerServlet">
                        <c:param name="command" value="DELETE" />
                        <c:param name="studentId" value="${tempStudent.id}" />
                    </c:url>

                    <tr>
                        <td>${tempStudent.firstName}</td>
                        <td>${tempStudent.lastName}</td>
                        <td>${tempStudent.email}</td>
                        <td>${tempStudent.phoneNum}</td>
                        <td>
                            <a href="${tempLinkUpdate}">Update</a>
                            |
                            <a href="${deleteLink}"
                            onclick="if(!(confirm('Are you sure you want to delete?'))) return false;">
                                Delete</a>
                            |
                            <a href="${tempLinkRegister}">Add To Class</a>
                        </td>
                    </tr>
                </c:forEach>

            </table>


            <!-- subjects' table -->

            <div class="subjectLbl">
                <p><b>Subjects: ${COUNT_SUBJECT}</b></p>

            </div>

            <table class="subjectTable">

                <tr>
                    <th>Class Name</th>
                    <th>Credits</th>
                    <th>Action</th>
                </tr>


                <c:forEach var="tempSubject" items="${SUBJECT_LIST}">

                    <c:url var="deleteSubject" value="StudentControllerServlet">
                        <c:param name="command" value="DELETE_SUBJECT" />
                        <c:param name="subjectId" value="${tempSubject.id}" />
                    </c:url>


                    <tr>
                        <td>${tempSubject.subjName}</td>
                        <td>${tempSubject.creditNum}</td>
                        <td>
                            <a href="${deleteSubject}"
                               onclick="if(!(confirm('Are you sure you want to delete?'))) return false;">Delete</a>
                        </td>
                    </tr>

                </c:forEach>

            </table>

            <input type="button" value="Add Subject"
                   onclick="window.location.href='add-subject-form.jsp'; return false;"
                   class="add-subject-button"
            />


            <c:url var="tempStudSubj" value="StudentControllerServlet">
                <c:param name="command" value="LOAD_SUBJECT" />
            </c:url>

            <input type="button" value="List Students Per Subject"
                   onclick="window.location.href='${tempStudSubj}'; return false;"
                   class="list-students-subject-button"
            />

        </div>
    </div>

</body>
</html>
