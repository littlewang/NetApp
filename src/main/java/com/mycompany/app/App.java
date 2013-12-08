package com.mycompany.app;
import com.mycompany.db.WwdEmbedded;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        WwdEmbedded em=new WwdEmbedded();
        em.getcnn();
       // em.createTable();
       // em.save("tom", "cat", "cat");
        em.list();
        em.close();       
//        System.out.println( "Hello World!" );     
    }
}
