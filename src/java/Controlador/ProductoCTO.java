/*
Esta clase tiene como fin manipular la vista de ProductoVTA
 */
package Controlador;

import Modelo.DTO.ProductoDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Angelica Barros Espitia
 * @author Luis Felipe Hernandez chica
 */
public class ProductoCTO extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String menu = request.getParameter("menu");
        String accion = request.getParameter("accion");

        if (menu.equalsIgnoreCase("Producto")) {
            Facade obj = new Facade();
            switch (accion) {
                case "Listar":
                    List<ProductoDTO> lis = obj.listarProductos();
                    request.setAttribute("lista_productos", lis);
                    break;
                case "Agregar":
                    String nombre = request.getParameter("txt_nombre_pro");
                    String des = request.getParameter("txt_descripcion_pro");
                    int und = Integer.parseInt(request.getParameter("txt_unidades_pro"));
                    int val = Integer.parseInt(request.getParameter("txt_valor_pro"));
                    ProductoDTO nuevo = new ProductoDTO(nombre, des, und, val);
                    obj.crearProducto(nuevo);
                    request.getRequestDispatcher("ProductoCTO?menu=Producto&accion=Listar").forward(request, response);
                    break;
                case "Editar":
                    ProductoDTO edit = new ProductoDTO();
                    edit.setId_pro(Integer.parseInt(request.getParameter("id")));
                    edit = obj.verProducto(edit);
                    request.setAttribute("producto", edit);
                    request.getRequestDispatcher("ProductoCTO?menu=Producto&accion=Listar").forward(request, response);
                    break;
                case "Actualizar":
                    int id1 = Integer.parseInt(request.getParameter("txt_id_pro"));
                    String nombre1 = request.getParameter("txt_nombre_pro");
                    String des1 = request.getParameter("txt_descripcion_pro");
                    int und1 = Integer.parseInt(request.getParameter("txt_unidades_pro"));
                    int val1 = Integer.parseInt(request.getParameter("txt_valor_pro"));
                    ProductoDTO actual = new ProductoDTO(id1, nombre1, des1, und1, val1);
                    obj.actualizarProducto(actual);
                    request.getRequestDispatcher("ProductoCTO?menu=Producto&accion=Listar").forward(request, response);
                    break;
                case "Eliminar":
                    ProductoDTO elim = new ProductoDTO();
                    elim.setId_pro(Integer.parseInt(request.getParameter("id")));
                    obj.eliminarProductor(elim);
                    request.getRequestDispatcher("ProductoCTO?menu=Producto&accion=Listar").forward(request, response);
                    break;
                case "Comprarwqwq":// Este caso es al momento de comprar para establece el id en modal
                    ProductoDTO comprar = new ProductoDTO();
                    comprar.setId_pro(Integer.parseInt(request.getParameter("id")));
                    comprar = obj.verProducto(comprar);
                    request.setAttribute("productoComprar", comprar);
                    request.getRequestDispatcher("ProductoCTO?menu=Producto&accion=Listar").forward(request, response);
                    break;
                case "Comprar"://Este es al darle click y llenar la cantidad de elementos a comprar
                    
                    ProductoDTO comprarAhora = new ProductoDTO();//crea un producto
                    int cantidad = Integer.parseInt(request.getParameter("txt_cantidadComprar"));//guarda la cantidad a comprar
                    comprarAhora.setId_pro(Integer.parseInt(request.getParameter("id")));//se establece el id del producto a comprar
                    comprarAhora = obj.verProducto(comprarAhora);//se busca el objeto en la base de datos
                    if (cantidad <= comprarAhora.getUnidades_pro()) {//se verifica que el producto tenga unidades
                        //si el producto tiene unidades, se actualiza la cantidad restandole a la cantidad actual
                        comprarAhora.setUnidades_pro(comprarAhora.getUnidades_pro() - cantidad);
                        //se actualiza la cantidad en la base de datos
                        obj.actualizarProducto(comprarAhora);
                       
                        request.getRequestDispatcher("ProductoCTO?menu=Producto&accion=Listar").forward(request, response);

                    } else {
                       
                        request.getRequestDispatcher("ProductoCTO?menu=Producto&accion=Listar").forward(request, response);

                    }

                    break;
            }

            request.getRequestDispatcher("ProductoVTA.jsp").forward(request, response);

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        

            processRequest(request, response);
        

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
