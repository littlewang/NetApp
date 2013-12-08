package com.mycompany.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WwdEmbedded {

    private static Connection conn;
    private static Statement s;
    private static PreparedStatement psInsert;
    private static ResultSet myWishes;

    public static void main(String[] args) {
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        String dbName = "jdbcDemoDB";
        String connectionURL = "jdbc:derby:" + dbName + ";create=true";
        String createString = "CREATE TABLE WISH_LIST(WISH_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY , WISH_ITEM VARCHAR(32) NOT NULL) ";
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
   if (e instanceof SQLException) 
      SQLExceptionPrint((SQLException)e);
   else {
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
