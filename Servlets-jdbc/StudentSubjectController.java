package com.gmail.khitirinikoloz.servlets;

import com.gmail.khitirinikoloz.jdbc.StudentDbUtil;
import com.gmail.khitirinikoloz.jdbc.SubjectDbUtil;

import com.gmail.khitirinikoloz.objects.Student;
import com.gmail.khitirinikoloz.objects.Subject;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "StudentSubjectController", urlPatterns = {"/StudentSubjectController"})
public class StudentSubjectController extends HttpServlet {

    private StudentDbUtil studentDbUtil;
    private SubjectDbUtil subjectDbUtil;
    @Resource(name = "jdbc/university-management")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException { //this is called by tomcat when the servlet is first initilized
        super.init();

        try{
            studentDbUtil = new StudentDbUtil(dataSource);
            subjectDbUtil = new SubjectDbUtil(dataSource);
        }
        catch(Exception e){
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String addSubjError = null;
        String addStudError = null;
        String updateStudError = null;
        String registerStudError = null;
        try {
            String theCommand = request.getParameter("command");
            switch (theCommand) {
                case "ADD":
                    addStudError = addStudent(request, response);
                    break;
                case "ADD_SUBJECT":
                    addSubjError = addSubject(request,response);
                    break;
                case "UPDATE":
                    updateStudError = updateStudent(request,response);
                    break;
                case "REGISTER":
                    registerStudError = registerStudent(request,response);
                    break;
                default:
                    listStudentsSubjects(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }

        RequestDispatcher dispatcher = null;


        if(addSubjError != null){
            dispatcher = request.getRequestDispatcher("/add-subject-form.jsp");
            request.setAttribute("ADD_SUBJECT_ERROR",addSubjError);
            dispatcher.forward(request,response);
        }

        if(addStudError != null){
            dispatcher = request.getRequestDispatcher("/add-student-form.jsp");
            request.setAttribute("ADD_STUDENT_ERROR",addStudError);
            dispatcher.forward(request,response);
        }

        if(updateStudError != null){
            dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
            request.setAttribute("UPDATE_STUDENT_ERROR", updateStudError);
            dispatcher.forward(request,response);
        }

        if(registerStudError != null){
            dispatcher = request.getRequestDispatcher("/register-student-form.jsp");
            request.setAttribute("REGISTER_STUDENT_ERROR", registerStudError);
            dispatcher.forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            String theCommand = request.getParameter("command");
            if(theCommand == null){
                theCommand = "LIST";
            }

            switch(theCommand){
                case "LOAD_UPDATE":
                    loadStudent(request,response,"/update-student-form.jsp");
                    break;
                case "LOAD_REGISTER":
                    loadStudent(request,response,"/register-student-form.jsp");
                    break;
                case "LOAD_SUBJECT":
                    loadSubject(request,response);
                    break;
                case "DELETE":
                    deleteStudent(request,response);
                    break;
                case "DELETE_SUBJECT":
                    deleteSubject(request,response);
                    break;
                case "GET_STUDENTS_PER_SUBJECT":
                    getStudentsPerSubject(request,response);
                    break;
                 default:
                     listStudentsSubjects(request, response);

            }
        }
        catch(Exception e){
            throw new ServletException(e);
        }
    }

    private void getStudentsPerSubject(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String subjName = request.getParameter("subjectSelect");
        List<Student> students_subjs = studentDbUtil.getStudentsPerSubject(subjName);

        request.setAttribute("STUDENT_PER_SUBJECT",students_subjs);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/register-student-on-subject-form.jsp");
        dispatcher.forward(request,response);
    }

    private void loadSubject(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Subject> subjects = subjectDbUtil.getSubjects();
        request.setAttribute("THE_SUBJECTS", subjects);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/register-student-on-subject-form.jsp");
        dispatcher.forward(request,response);
    }


    private String registerStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String studentId = request.getParameter("studentId");
        if(studentId.isEmpty())
            return "Empty Field!";

        int studId = Integer.parseInt(studentId);
        String subjName = request.getParameter("subjectSelect");

        //check if the student is already registered
        boolean checked = studentDbUtil.checkStudentRegistered(studId,subjName);
        if(checked)
            return "Already Registered!";

        studentDbUtil.registerStudent(studId,subjName);

        response.sendRedirect(request.getContextPath() + "/StudentSubjectController?command=LIST");

        return null;
    }



    private void deleteSubject(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String theSubjectId = request.getParameter("subjectId");

        subjectDbUtil.deleteSubject(theSubjectId);

        listStudentsSubjects(request,response);
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String theStudentId = request.getParameter("studentId");

        studentDbUtil.deleteStudent(theStudentId);

        listStudentsSubjects(request,response);

    }


    private String updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String studentId = request.getParameter("studentId");
        if(studentId.isEmpty())
            return "Empty Field!";

        int id = Integer.parseInt(studentId);
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phoneNum");

        String checkUpdatedStudent = checkStudentUpdated(id, firstName,lastName,email,phone);
        if(checkUpdatedStudent != null)
            return checkUpdatedStudent;

        Student theStudent = new Student(id,firstName,lastName,email,phone);

        studentDbUtil.updateStudent(theStudent);

        response.sendRedirect(request.getContextPath() + "/StudentSubjectController?command=LIST");

        return null;
    }

    private String checkStudentUpdated(int id, String firstName, String lastName, String email, String phone)
    throws Exception {

        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty())
            return "Empty Field!";

        for (int i = 0; i < firstName.length(); i++) {
            if(!Character.isLetter(firstName.charAt(i)))
                return "Invalid First Name!";
        }

        //check lastName
        for (int i = 0; i < lastName.length(); i++) {
            if(!Character.isLetter(lastName.charAt(i)))
                return "Invalid Last Name!";
        }

        //check phone number
        for (int i = 0; i < phone.length(); i++) {
            if(!Character.isDigit(phone.charAt(i)))
                return "Invalid Phone Number!";
        }

        if(studentDbUtil.checkEmailUpdated(id,email)){
            return "This Email already exists!";
        }

        return null;
    }

    private void loadStudent(HttpServletRequest request, HttpServletResponse response, String link) throws Exception{

        //read student id from form data
        String theStudentId = request.getParameter("studentId");

        //get student from database
        Student theStudent = studentDbUtil.getStudent(theStudentId);

        if(link.equals("/register-student-form.jsp")){
            List<Subject> subjects = subjectDbUtil.getSubjects();
            request.setAttribute("THE_SUBJECTS", subjects);
        }

        //place student in the request attribute
        request.setAttribute("THE_STUDENT",theStudent);

        //send to update-student-form.jsp or register-student-form.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher(link);

        dispatcher.forward(request,response);
    }

    private String addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //read student info from form data
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phoneNum");

        String checkResult = checkStudentAdded(firstName,lastName,email,phone);
        if(checkResult != null)
            return checkResult;

        //create a new student object
        Student theStudent = new Student(firstName,lastName,email,phone);

        //add the student to the database
        studentDbUtil.addStudent(theStudent);

        //send as REDIRECT to avoid multiple inserts on reloads
        response.sendRedirect(request.getContextPath()+"/StudentSubjectController?command=LIST");

        return null;
    }

    private String checkStudentAdded(String firstName, String lastName, String email, String phone) throws Exception {

        //check firstName

        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty())
            return "Empty Field!";

        for (int i = 0; i < firstName.length(); i++) {
            if(!Character.isLetter(firstName.charAt(i)))
                return "Invalid First Name!";
        }

        //check lastName
        for (int i = 0; i < lastName.length(); i++) {
            if(!Character.isLetter(lastName.charAt(i)))
                return "Invalid Last Name!";
        }

        //check phone number
        for (int i = 0; i < phone.length(); i++) {
            if(!Character.isDigit(phone.charAt(i)))
                return "Invalid Phone Number!";
        }


        //check if the user email already exists

        if(studentDbUtil.checkEmail(email)) {
            return "Email already exists!";
        }

        return null;
    }


    private String addSubject(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String subjName = request.getParameter("subjName");
        String creditNum = request.getParameter("creditNum");

        String checked = checkSubjectAdded(subjName,creditNum);
        if(checked != null)
            return checked;

        int creditAmount = Integer.parseInt(creditNum);

        Subject subject = new Subject(subjName,creditAmount);

        subjectDbUtil.addSubject(subject);

        response.sendRedirect(request.getContextPath()+"/StudentSubjectController?command=LIST");

        return null;
    }

    public String checkSubjectAdded(String subject, String credit) throws Exception {
        for (int i = 0; i <credit.length() ; i++) {
            if(!Character.isDigit(credit.charAt(i)))
                return "Illegal Credit!";
        }

        if(credit.isEmpty() || subject.isEmpty())
            return "Empty Field!";


        if(subjectDbUtil.checkSubject(subject))
            return "Subject already exists!";

        return null;
    }

    private void listStudentsSubjects(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //get students from db util

        List<Student> students = studentDbUtil.getStudents();
        List<Subject> subjects = subjectDbUtil.getSubjects();


        //count total number of subjects and students
        int totalStudents = studentDbUtil.countStudents();
        int totalSubjects = subjectDbUtil.countSubjects();
        //add students to the request

        request.setAttribute("STUDENT_LIST", students);
        request.setAttribute("SUBJECT_LIST", subjects);
        request.setAttribute("COUNT_STUDENT",totalStudents);
        request.setAttribute("COUNT_SUBJECT", totalSubjects);
        //send to JSP(view)

        RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");


        dispatcher.forward(request,response);
    }
}
