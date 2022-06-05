/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Benny Hammer Pérez Vásquez
 */
public class Conexion_DB {
    Connection con;
    public Connection getConnection(){
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con=DriverManager.getConnection("jdbc:derby://localhost:1527/TFM;user=TFM;password=TFM");
            
        }catch(Exception e){
            
        }
        return con;
    }
   
}
