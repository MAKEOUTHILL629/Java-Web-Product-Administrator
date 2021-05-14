/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.DAO.ProductoDAO;
import Modelo.DAO.SesionDAO;
import Modelo.DAO.UsuarioDAO;
import Modelo.DTO.ProductoDTO;
import Modelo.DTO.SesionDTO;
import Modelo.DTO.UsuarioDTO;
import java.util.List;

/**
 *
 * @author Angelica Barros Espitia
 * @author Luis Felipe Hernandez Chica
 */
public class Facade {

    public boolean crearProducto(ProductoDTO objP) {
        boolean rta = false;
        if (objP != null) {
            ProductoDAO dao = new ProductoDAO();
            rta = dao.create(objP);
        }
        return rta;
    }

    public List<ProductoDTO> listarProductos() {
        List<ProductoDTO> lst = null;
        ProductoDAO dao = new ProductoDAO();
        lst = dao.readAll();
        return lst;
    }
    
    public List<UsuarioDTO> listarUsuarios(){
        List<UsuarioDTO> lista = null;
        UsuarioDAO dao = new UsuarioDAO();
        lista = dao.readAll();
        return lista;
    }

    public ProductoDTO verProducto(ProductoDTO item) {
        ProductoDTO obj;
        ProductoDAO dao = new ProductoDAO();
        obj = dao.read(item);
        return obj;
    }

    public boolean actualizarProducto(ProductoDTO objP) {
        boolean rta = false;
        if (objP != null) {
            ProductoDAO dao = new ProductoDAO();
            rta = dao.update(objP);
        }
        return rta;
    }
    
    public boolean actualizarUsuario(UsuarioDTO user){
        if(user != null){
            UsuarioDAO dao = new UsuarioDAO();
            return dao.actualizarUsuario(user);
        }
        return false;
        
    }

    public boolean eliminarProductor(ProductoDTO elim) {
        ProductoDAO dao = new ProductoDAO();
        return dao.delete(elim);
    }
    
    public boolean eliminarUsuario(UsuarioDTO user){
        UsuarioDAO dao = new UsuarioDAO();
        return dao.borrarUsuario(user);
    }
    
    public SesionDTO ValidarSesion(SesionDTO filter){
        SesionDTO obj;
        SesionDAO dao = new SesionDAO();
        obj=dao.read(filter);
        return obj;
    }
    
    public boolean registrarUsuario(UsuarioDTO user){
        
        if(user!=null){
            UsuarioDAO dao = new UsuarioDAO();
            return dao.registrar(user);
        }
        return false;
    }
    
    public UsuarioDTO buscarUser(SesionDTO s){//Esta funcion acepta un SesionDTO para covertilo en un UsuarioDTO con los atributos completos
        if(s != null){
            UsuarioDAO dao = new UsuarioDAO();
            UsuarioDTO aux = new UsuarioDTO();
            aux.setId(s.getId());
            UsuarioDTO obj = dao.read(aux);
         
            return obj;
        }
        
        return null;
    }
    
    public UsuarioDTO userSearch(UsuarioDTO user){
        if(user !=   null){
            UsuarioDAO dao = new UsuarioDAO();
            UsuarioDTO aux = new UsuarioDTO();
            aux  = dao.read(user);
            return aux;
        }
        
        return null;
    }
}
