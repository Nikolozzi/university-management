package com.gmail.khitirinikoloz.jdbc;

import com.gmail.khitirinikoloz.objects.Student;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDbUtil {

    private DataSource dataSource;

    public StudentDbUtil(DataSource theDataSource){
        this.dataSource = theDataSource;
    }

    public List<Student> getStudents() throws Exception {
        List<Student> students = new ArrayList<>();

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet result = null;

        try {
            //get a connection
            con = dataSource.getConnection();

            //create sql statement
            String sql = "SELECT * FROM student ORDER BY last_name";
            stm = con.prepareStatement(sql);

            //execute the query
            result = stm.executeQuery();

            //result set
            while(result.next()){
                //retrieve data from result set row
                 int id = result.getInt("id");
                 String firstName = result.getString("first_name");
                 String lastName = result.getString("last_name");
                 String email = result.getString("email");
                 String phoneNum = result.getString("phone_number");

                //create new student object
                 Student tempStudent = new Student(id,firstName,lastName,email,phoneNum);

                //add it to the list
                students.add(tempStudent);
            }

            return students;
        }
        finally {
            //close JDBC objects

            close(con,stm,result);

        }
    }

    private void close(Connection con, Statement stm, ResultSet result) {
        try{
            if(result != null){
                result.close();
            }

            if(stm != null){
                stm.close();
            }

            if(con != null){
                con.close(); //puts con object back in connection pool
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addStudent(Student theStudent) throws Exception {
        Connection con = null;
        PreparedStatement statement = null;

        try {
            //get db connection
            con = dataSource.getConnection();

            //create sql for insert
            String sql = "INSERT INTO student " +
                    "(first_name, last_name, email, phone_number) " + "VALUES(?,?,?,?)";

            statement = con.prepareStatement(sql);

            //set the param values
            statement.setString(1,theStudent.getFirstName());
            statement.setString(2,theStudent.getLastName());
            statement.setString(3,theStudent.getEmail());
            statement.setString(4,theStudent.getPhoneNum());

            //execute sql
            statement.execute();
        }
        finally {
            close(con,statement,null);

        }
    }

    public Student getStudent(String theStudentId) throws Exception {
        Student theStudent = null;
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        int studentId;

        try{

            studentId = Integer.parseInt(theStudentId);

            con = dataSource.getConnection();
            String sql = "SELECT * FROM student WHERE id=?";
            statement = con.prepareStatement(sql);
            //set params for prepared statement
            statement.setInt(1,studentId);

            result = statement.executeQuery();

            if(result.next()){
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String email = result.getString("email");
                String phoneNum = result.getString("phone_number");

                theStudent = new Student(studentId,firstName,lastName,email,phoneNum);

            }
            else{
                throw new Exception("Could not find student id: " + studentId);
            }

            return theStudent;
        }
        finally{
            close(con,statement,result);
        }
    }

    public void updateStudent(Student theStudent) throws Exception{
        Connection con = null;
        PreparedStatement statement = null;

        try {

            con = dataSource.getConnection();

            String sql = "UPDATE student " +
                    "SET first_name=?, last_name=?, email=?, phone_number=? "
                    + "WHERE id=?";

            statement = con.prepareStatement(sql);

            statement.setString(1, theStudent.getFirstName());
            statement.setString(2, theStudent.getLastName());
            statement.setString(3, theStudent.getEmail());
            statement.setString(4, theStudent.getPhoneNum());
            statement.setInt(5, theStudent.getId());

            statement.execute();
        }
        finally {
            close(con,statement,null);
        }
    }

    public void deleteStudent(String theStudentId) throws Exception {
        Connection con = null;
        PreparedStatement statement = null;

        try{

            int studentId = Integer.parseInt(theStudentId);

            con = dataSource.getConnection();
            String sql = "DELETE FROM student WHERE id=?";

            statement = con.prepareStatement(sql);
            statement.setInt(1,studentId);

            statement.execute();

        }
        finally {
            close(con,statement,null);
        }

    }

    public boolean checkEmail(String email) throws Exception {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try{
            con = dataSource.getConnection();
            String sql = "SELECT id FROM student WHERE email=?";
            statement = con.prepareStatement(sql);
            statement.setString(1,email);
            result = statement.executeQuery();

            return result.next();
        }
        finally {
            close(con,statement,result);
        }
    }

    public boolean checkEmailUpdated(int id, String email) throws Exception {

        Connection con = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try{

            con = dataSource.getConnection();
            String sql = "SELECT id FROM student WHERE email=? AND id<>?";
            statement = con.prepareStatement(sql);
            statement.setString(1,email);
            statement.setInt(2,id);
            result = statement.executeQuery();

            return result.next();
        }
        finally {
            close(con,statement,result);
        }
    }

    public void registerStudent(int studId, String subjName) throws Exception {

        SubjectDbUtil subjectDbUtil = new SubjectDbUtil();
        //get subject Id by subjName
        int subjId = getSubjID(subjName);

        Connection con = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try{
            con = dataSource.getConnection();
            String sql = "INSERT INTO student_subject " +
                    "(student_id,subject_id) " + "VALUES(?,?)";

            statement = con.prepareStatement(sql);
            statement.setInt(1,studId);
            statement.setInt(2,subjId);

            statement.execute();
        }
        finally {
            close(con,statement,result);
        }
    }

    private int getSubjID(String subjName) throws Exception {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try{
            con = dataSource.getConnection();
            String sql = "SELECT subject_id FROM subject WHERE subject_name=?";

            statement = con.prepareStatement(sql);
            statement.setString(1,subjName);

            result = statement.executeQuery();

            //System.out.println(subjId);
            int subjId = -1;
            if(result.next()){
                subjId = result.getInt("subject_id");
            }

            return subjId;
        }
        finally {
            close(con,statement,result);
        }
    }

    public boolean checkStudentRegistered(int studId, String subjName) throws Exception {
        int subjId = getSubjID(subjName);

        Connection con = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try{
            con = dataSource.getConnection();
            String sql = "SELECT * FROM student_subject WHERE student_id=? AND subject_id=?";
            statement = con.prepareStatement(sql);
            statement.setInt(1,studId);
            statement.setInt(2,subjId);

            result = statement.executeQuery();
            return result.next();
        }
        finally {
            close(con,statement,result);
        }
    }

    public int countStudents() throws Exception {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try{
            con = dataSource.getConnection();
            String sql = "SELECT COUNT(*) AS totalStud FROM student";
            statement = con.prepareStatement(sql);
            result = statement.executeQuery();

            int count = -1;
            if(result.next())
                count = Integer.parseInt(result.getString("totalStud"));

            return count;
        }
        finally {
            close(con,statement,result);
        }
    }

    public List<Student> getStudentsPerSubject(String subjName) throws Exception {
        int subjId = getSubjID(subjName);
        List<Student> theStudents = new ArrayList<>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try{
            con = dataSource.getConnection();
            String sql = "SELECT first_name, last_name, email, phone_number " +
                    "FROM student INNER JOIN student_subject ON student.id=student_subject.student_id " +
                    "WHERE subject_id=?";

            statement = con.prepareStatement(sql);
            statement.setInt(1,subjId);
            result = statement.executeQuery();

            while(result.next()){
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String email = result.getString("email");
                String phoneNumber = result.getString("phone_number");

                Student student = new Student(firstName,lastName,email,phoneNumber);
                theStudents.add(student);
            }

            return theStudents;
        }
        finally {
            close(con,statement,result);
        }
    }
}
