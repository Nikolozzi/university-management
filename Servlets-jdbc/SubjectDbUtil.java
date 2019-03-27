package com.gmail.khitirinikoloz.jdbc;

import com.gmail.khitirinikoloz.objects.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDbUtil {

    private DataSource dataSource;

    public SubjectDbUtil(DataSource dataSource){
        this.dataSource = dataSource;
    }
    public SubjectDbUtil(){ } //default constructor

    public List<Subject> getSubjects() throws Exception {

        List<Subject> subjects = new ArrayList<>();

        Connection con = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try{

            con = dataSource.getConnection();
            String sql = "SELECT * FROM subject ORDER BY subject_name";

            statement = con.prepareStatement(sql);
            result = statement.executeQuery();

            while(result.next()){
                int id = result.getInt("subject_id");
                String subjName = result.getString("subject_name");
                int creditNum = result.getInt("credit_amount");

                Subject subject = new Subject(id,subjName,creditNum);
                subjects.add(subject);
            }

            return subjects;
        }
        finally {
            close(con,statement,result);
        }

    }

    private void close(Connection con, Statement statement, ResultSet result) {
        try {
            if (con != null)
                con.close();

            if(statement != null)
                statement.close();

            if(result != null)
                result.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    public void addSubject(Subject theSubject) throws Exception {
        Connection con = null;
        PreparedStatement statement = null;

        try{
            con = dataSource.getConnection();
            String sql = "INSERT INTO subject " + "(subject_name,credit_amount) " + "VALUES(?,?)";

            statement = con.prepareStatement(sql);

            statement.setString(1, theSubject.getSubjName());
            statement.setInt(2, theSubject.getCreditNum());

            statement.execute();
        }
        finally {
            close(con,statement,null);
        }
    }

    public void deleteSubject(String theSubjectId) throws Exception {
        Connection con = null;
        PreparedStatement statement = null;

        try{
            int subjectId = Integer.parseInt(theSubjectId);

            con = dataSource.getConnection();
            String sql = "DELETE FROM subject WHERE subject_id=?";

            statement = con.prepareStatement(sql);
            statement.setInt(1,subjectId);

            statement.execute();
        }
        finally {
            close(con,statement,null);
        }
    }

    public boolean checkSubject(String subject) throws Exception {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try{

            con = dataSource.getConnection();
            String sql = "SELECT subject_id from subject WHERE subject_name=?";
            statement = con.prepareStatement(sql);
            statement.setString(1,subject);
            result = statement.executeQuery();

            return result.next();
        }
        finally {
            close(con,statement,result);
        }
    }

    public int countSubjects() throws Exception {

        Connection con = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try{

            con = dataSource.getConnection();
            String sql = "SELECT COUNT(*) AS subjCount FROM subject";
            statement = con.prepareStatement(sql);
            result = statement.executeQuery();

            int count = -1;
            if(result.next())
                count = Integer.parseInt(result.getString("subjCount"));

            return count;
        }
        finally {
            close(con,statement,result);
        }
    }
}
