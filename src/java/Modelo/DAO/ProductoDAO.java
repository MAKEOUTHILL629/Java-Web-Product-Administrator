/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.DAO;

/**
 *
* @author Angelica Barros Espitia
 * @author Luis Felipe Hernandez chica
 */
import Conexiones.ConexionMsql;
import Modelo.DTO.ProductoDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductoDAO {

    private static final String SQL_INSERT = "INSERT INTO tb_producto " + "(nombre_producto, descripcion_producto, unidades, valor) VALUES (?, ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE from tb_producto WHERE id_producto = ?";
    private static final String SQL_UPDATE = "UPDATE tb_producto SET nombre_producto = ?,descripcion_producto = ?, unidades = ?, valor = ? WHERE id_producto = ?";
    private static final String SQL_READ = "SELECT * FROM tb_producto WHERE id_producto = ?";
    private static final String SQL_READALL = "SELECT * FROM tb_producto";

    private static final ConexionMsql con = ConexionMsql.getInstance();

    public boolean create(ProductoDTO c) {
        try {
            PreparedStatement ps;
            ps = con.getCnn().prepareStatement(SQL_INSERT);
            ps.setString(1, c.getNombre_pro());
            ps.setString(2, c.getDescripcion_pro());
            ps.setInt(3, c.getUnidades_pro());
            ps.setInt(4, c.getValor_pro());
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.cerrarConexion();
        }
        return false;
    }

    public List<ProductoDTO> readAll() {
        List<ProductoDTO> lst = null;
        PreparedStatement psnt;
        try {
            psnt = con.getCnn().prepareStatement(SQL_READALL);
            ResultSet rs = psnt.executeQuery();
            lst = new ArrayList<>();
            while (rs.next()) {
                ProductoDTO obj = new ProductoDTO(rs.getInt("id_producto"),
                        rs.getString("nombre_producto"),
                        rs.getString("descripcion_producto"),
                        rs.getInt("unidades"),
                        rs.getInt("valor"));
                lst.add(obj);
            }
        } catch (SQLException ex) {
           // System.out.println("Error en mostrar "+ ex.getMessage());
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.cerrarConexion();
        }
        return lst;
    }

    public boolean delete(ProductoDTO item) {
        PreparedStatement ps;
        try {
            ps = con.getCnn().prepareStatement(SQL_DELETE);
            ps.setInt(1, item.getId_pro());
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException ex) {
            throw new Error(ex.getMessage());
        } finally {
            con.cerrarConexion();
        }
        return false;
    }

    public boolean update(ProductoDTO item) {
        PreparedStatement ps;
        try {
            ps = con.getCnn().prepareStatement(SQL_UPDATE);
            ps.setString(1, item.getNombre_pro());
            ps.setString(2, item.getDescripcion_pro());
            ps.setInt(3, item.getUnidades_pro());
            ps.setInt(4, item.getValor_pro());
            ps.setInt(5, item.getId_pro());
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException ex) {
            throw new Error(ex.getMessage());
        } finally {
            con.cerrarConexion();
        }
        return false;
    }

    public ProductoDTO read(ProductoDTO filter) {
        ProductoDTO objRes = null;
        PreparedStatement psnt;
        try {
            psnt = con.getCnn().prepareStatement(SQL_READ);
            psnt.setInt(1, filter.getId_pro());
            ResultSet rs = psnt.executeQuery();
            while (rs.next()) {
                objRes = new ProductoDTO(
                        rs.getInt("id_producto"),
                        rs.getString("nombre_producto"),
                        rs.getString("descripcion_producto"),
                        rs.getInt("unidades"),
                        rs.getInt("valor"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.cerrarConexion();
        }
        return objRes;
    }
}
