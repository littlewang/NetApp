
package com.mycompany.db;

public class DbTest {
    
    public static void main(String[] args) {
        WwdEmbedded we=new WwdEmbedded();
        we.getcnn();
        we.list();
        we.close();    
    }
}
