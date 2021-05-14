/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.DAO;

import Conexiones.ConexionMsql;
import Modelo.DTO.SesionDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
* @author Angelica Barros Espitia
 * @author Luis Felipe Hernandez chica
 */
public class SesionDAO {
    private static final String SQL_READ="select id, correo, clave FROM tb_usuario WHERE correo=? AND clave=?";
    private ConexionMsql con=null;
    
    public SesionDTO read(SesionDTO filter){
        SesionDTO objRes =null;
        PreparedStatement psnt;
        con=ConexionMsql.getInstance();
        try {
            psnt=con.getCnn().prepareStatement(SQL_READ);
            psnt.setString(1, filter.getCorreo());
            psnt.setString(2, filter.getClave());
            ResultSet rs = psnt.executeQuery();
            if(rs.next()){
                objRes=new SesionDTO(rs.getInt("id"),rs.getString("correo"),rs.getString("clave"));
            }
        } catch (SQLException ex) {
            System.out.println("Error en sesion: "+ ex.getMessage());
            
        }finally{
            con.cerrarConexion();
        }
        return objRes;
    }
    
}
