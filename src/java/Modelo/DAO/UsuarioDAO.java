/*
Esta clase tiene como funcion manipular la tabla en la base de datos llamada tb_usuarios
 */
package Modelo.DAO;

import Conexiones.ConexionMsql;
import Modelo.DTO.UsuarioDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
* @author Angelica Barros Espitia
 * @author Luis Felipe Hernandez chica
 */
public class UsuarioDAO {

    private static final String SQL_INSERT = "INSERT INTO tb_usuario ( nombre1, apellido1,  n_identificacion, correo, clave, sexo, fecha_nac, estado) VALUES ( ?,  ?,  ?, ?, ?, ?, ?, ?)";// sql para registrar usuarios
    private static final String SQL_DELETE ="DELETE FROM tb_usuario WHERE id = ?";
    private static final String SQL_UPDATE ="UPDATE tb_usuario SET nombre1 = ?, apellido1 = ?, n_identificacion=?, correo = ?, clave = ?, sexo=?, fecha_nac = ?, estado = ? WHERE id = ?";
    private static final String SQL_READ ="SELECT id, nombre1, apellido1, n_identificacion, correo, clave, sexo, fecha_nac, estado FROM tb_usuario WHERE id=?";
    private static final String SQL_READALL ="SELECT id, nombre1, apellido1, n_identificacion, correo, sexo, fecha_nac, estado FROM tb_usuario";
    private static final ConexionMsql con = ConexionMsql.getInstance();
    
    public boolean registrar(UsuarioDTO user){//esta funcion me permite agregar un usuario a la base de datos
        try {
            PreparedStatement ps;
            ps = con.getCnn().prepareStatement(SQL_INSERT);
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getApellido());
            ps.setString(3, user.getNumeroIdentificacion());
            ps.setString(4, user.getCorreo());
            ps.setString(5, user.getClave());
            ps.setString(6, String.valueOf(user.getSexo()));
            ps.setString(7, user.getFechaNacimiento());
            ps.setString(8, user.getEstado());
            if(ps.executeUpdate() > 0){
                return true;
            }
            
        } catch (SQLException ex) {
            
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally{
            con.cerrarConexion();
        }
        return false;
    }
    
    public List<UsuarioDTO> readAll(){//esta funcion me retorna todos los usuarios registrados y sus datos menos la clave
        List<UsuarioDTO> lista = null;
        PreparedStatement ps;
        try{
            ps = con.getCnn().prepareStatement(SQL_READALL);
            ResultSet rs = ps.executeQuery();
            lista = new ArrayList<>();
            while(rs.next()){
                UsuarioDTO objeto = new UsuarioDTO(rs.getInt("id"),
                        rs.getString("nombre1"),
                        rs.getString("apellido1"),
                        rs.getString("n_identificacion"),
                        rs.getString("correo"),
                        rs.getString("fecha_nac"),
                        rs.getString("estado"),
                        rs.getString("sexo").charAt(0));
                lista.add(objeto);
                        
            }
        }catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            con.cerrarConexion();
        }
        
        return lista;
    }

    public boolean borrarUsuario(UsuarioDTO user){// esta funcion me permite borrar al usuario de la base de datos
        PreparedStatement ps;
        
        try{
           ps = con.getCnn().prepareStatement(SQL_DELETE);
           ps.setInt(1, user.getId());
           if(ps.executeUpdate() > 0){
               return true;
           }
        }catch (SQLException ex) {
            throw new Error(ex.getMessage());
        } finally {
            con.cerrarConexion();
        }
        return false;
    }
    
    public boolean actualizarUsuario(UsuarioDTO user){//esta funncion me permite actualizar al usuario
        PreparedStatement ps;
        try{
            ps = con.getCnn().prepareStatement(SQL_UPDATE);
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getApellido());
            ps.setString(3, user.getNumeroIdentificacion());
            ps.setString(4,user.getCorreo());
            ps.setString(5, user.getClave());
            ps.setString(6, String.valueOf(user.getSexo()));
            ps.setString(7, user.getFechaNacimiento());
            ps.setString(8, user.getEstado());
            ps.setInt(9, user.getId());
            if(ps.executeUpdate() > 0){
                return true;
            }
        }catch (SQLException ex) {
            throw new Error(ex.getMessage());
        } finally {
            con.cerrarConexion();
        }
        return false;
    }
    
    public UsuarioDTO read(UsuarioDTO user){//esta funcion me retorna al usuario que estoy buscado con el id
        UsuarioDTO objetoResultante = null;
        PreparedStatement ps;
        try{
            ps = con.getCnn().prepareStatement(SQL_READ);
            ps.setInt(1, user.getId());
           
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                objetoResultante = new UsuarioDTO(rs.getInt("id"),
                        rs.getString("nombre1"),
                        rs.getString("apellido1"),
                        rs.getString("n_identificacion"),
                        rs.getString("correo"),
                        rs.getString("clave"),
                        rs.getString("fecha_nac"),
                        rs.getString("estado"),
                        rs.getString("sexo").charAt(0));
            }
        }catch (SQLException ex) {
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.cerrarConexion();
        }
        return objetoResultante;
    }
    
    
}
