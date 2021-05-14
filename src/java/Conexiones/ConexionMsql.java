/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexiones;

    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.SQLException;
    import java.util.logging.Level;
    import java.util.logging.Logger;
/**
 *
* @author Angelica Barros Espitia
 * @author Luis Felipe Hernandez chica
 */
public class ConexionMsql {
    public static ConexionMsql instance;//Singleton
    private Connection cnn;
    
    private ConexionMsql(){
        try{
            Class.forName("com.mysql.jdbc.Driver");//Driver
            try{
                cnn=DriverManager.getConnection("jdbc:mysql://localhost:3306/bd_tallerjavaweb", "root","");
            }catch(SQLException ex){
                Logger.getLogger(ConexionMsql.class.getName()).log(Level.SEVERE,null,ex);
            }
        }catch(ClassNotFoundException ex){
            Logger.getLogger(ConexionMsql.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    public static synchronized ConexionMsql getInstance(){
        if(instance == null){
            instance = new ConexionMsql();
        }
        return instance;
    }
    public Connection getCnn(){
        return cnn;
    }
    public void cerrarConexion(){
        instance = null;
    }
    
}
