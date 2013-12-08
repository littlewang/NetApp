package com.mycompany.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WwdEmbedded {

    private static Connection conn;
    private static Statement s;
    
    private static PreparedStatement psInsert;
    private static ResultSet myWishes;
    private static final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String dbName = "jdbcDemoDB";
    private static final String connectionURL = "jdbc:derby:" + dbName + ";create=true";
    private static final String createString = "CREATE TABLE com(COM_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY , COM_NAME VARCHAR(32) NOT NULL, USER_NAME VARCHAR(32) NOT NULL,PASSWORD VARCHAR(32) NOT NULL) ";

    
    
    
    public Connection getcnn() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(connectionURL);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WwdEmbedded.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(WwdEmbedded.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }   
    
    public void createTable(){
        try {
            s = conn.createStatement();
            s.execute(createString);
        } catch (SQLException ex) {
            Logger.getLogger(WwdEmbedded.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
    
    public void save(String COM,String name,String pass){       
        try {         
            psInsert = conn.prepareStatement("insert into com(COM_NAME,USER_NAME,PASSWORD) values (?,?,?)");
            psInsert.setString(1, COM);
            psInsert.setString(2, name);
            psInsert.setString(3, pass);          
            psInsert.executeUpdate();         
        } catch (SQLException ex) {
            Logger.getLogger(WwdEmbedded.class.getName()).log(Level.SEVERE, null, ex);
        }            
    }    
    public List list(){
        try {
             s = conn.createStatement();
             myWishes = s.executeQuery("select COM_NAME, USER_NAME from com order by COM_ID");
            while (myWishes.next()) {
                System.out.println("company:" + myWishes.getString(1)+ " user: " + myWishes.getString(2));
            }            
        } catch (SQLException ex) {
            Logger.getLogger(WwdEmbedded.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }  
    public void close() {
        try {   
            if(myWishes!=null){
            myWishes.close();
            }          
            if(s!=null){
            s.close();
            }
            if(psInsert!=null){
            psInsert.close();
            }           
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(WwdEmbedded.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(connectionURL);
            s = conn.createStatement();
            if (!WwdUtils.wwdChk4Table(conn)) {
                System.out.println(" . . . . creating table WISH_LIST");
                s.execute(createString);
            } else {
                s.execute("drop table WISH_LIST");
            }
            psInsert = conn.prepareStatement("insert into WISH_LIST(WISH_ITEM) values (?)");

            psInsert.setString(1, "answer");
            psInsert.executeUpdate();
            myWishes = s.executeQuery("select WISH_ID, WISH_ITEM from WISH_LIST order by WISH_ID");
            while (myWishes.next()) {
                System.out.println("On " + myWishes.getTimestamp(1)
                        + " I wished for " + myWishes.getString(2));
            }
            myWishes.close();

        } catch (java.lang.ClassNotFoundException e) {

        } catch (SQLException ex) {
            Logger.getLogger(WwdEmbedded.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static void errorPrint(Throwable e) {
        if (e instanceof SQLException) {
            SQLExceptionPrint((SQLException) e);
        } else {
            System.out.println("A non SQL error occured.");
            e.printStackTrace();
        }
    }  // END errorPrint 

    static void SQLExceptionPrint(SQLException sqle) {
        while (sqle != null) {
            System.out.println("\n---SQLException Caught---\n");
            System.out.println("SQLState:   " + (sqle).getSQLState());
            System.out.println("Severity: " + (sqle).getErrorCode());
            System.out.println("Message:  " + (sqle).getMessage());
            sqle.printStackTrace();
            sqle = sqle.getNextException();
        }
    }  //  

}
