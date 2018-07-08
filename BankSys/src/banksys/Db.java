/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banksys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author all
 */
public class Db {
    
    public Connection getConnection() {
                
        Connection con;
        con = null;
        try{
            String drivername = "oracle.jdbc.driver.OracleDriver";
            Class.forName(drivername);
            String url = "jdbc:oracle:thin:@localhost:1521:oracle";   //jdbc:oracle:thin:@localhost:1521:oracle
            String username = "BankSystem";
            String password = "banksystem";
            con = DriverManager.getConnection(url,username,password);
        //   System.out.println("Connection successfully build");
            
            
        }catch(ClassNotFoundException | SQLException e){System.out.println("could not establish connection due to : "+e.getMessage());}
        
        return con;
    }
}

