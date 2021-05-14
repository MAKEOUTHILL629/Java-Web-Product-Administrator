/*
Esta clase tiene como finalidad manipular la vista de Usuario VTA
 */
package Controlador;

import Modelo.DTO.ProductoDTO;
import Modelo.DTO.UsuarioDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
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
public class UsuarioCTO extends HttpServlet {

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
        
            String accion = request.getParameter("accion");//Guarda la accion que va a hacer el usuario en la aplicacion web
            Facade obj = new Facade();
            switch (accion) {
                case "Registrar": {
                    HttpSession sesion = request.getSession();

                    String nombre = request.getParameter("txtNombre");
                    String apellido = request.getParameter("txtApellido");
                    String identificacion = request.getParameter("txtIdentificacion");
                    String correo = request.getParameter("txtCorreo");
                    String password = request.getParameter("txtPassword");

                    String fechaNacimiento = request.getParameter("Dia") + "/" + request.getParameter("Mes") + "/" + request.getParameter("AnioNacimiento");//crea el string completo de la fecha
                    String genero = request.getParameter("genero");
                    password = this.AsegurarClave(password);//cifra la clave con sha 256

                    if (obj.registrarUsuario(new UsuarioDTO(nombre, apellido, identificacion, correo, password, fechaNacimiento, "REGISTRADO", genero.charAt(0)))) {//Al momento de registralo se va queda con un estado de REGISTRADO

                        sesion.setAttribute("infoBueno", "CORRECTO");// se crea el objeto info bueno, que ele informa al usuario que se registro
                        request.getRequestDispatcher("index.jsp").forward(request, response);//Se le envia al index, porque no es un usuario activo

                    } else {

                        sesion.setAttribute("infoMalo", "te encuentras registrado");// Si no se pudo registrar, es porque esta registrado
                        request.getRequestDispatcher("index.jsp").forward(request, response);//se redirige al index

                    }
                    break;
                }
                case "usuario": {//Lista los usuarios registrados en el programa
                    String rol = request.getParameter("rol");//verifica que el rol sea adminitrador
                    if (rol.equals("ADMIN")) {
                        List<UsuarioDTO> lista = obj.listarUsuarios();
                        request.setAttribute("lista_usuarios", lista);//se crea el objeto lista de usuarios
                        request.getRequestDispatcher("UsuarioVTA.jsp").forward(request, response);
                    } else if (rol.equals("ACTIVO")) {//no haga nada, porque no es admin
                        request.getRequestDispatcher("UsuarioVTA.jsp").forward(request, response);
                    }
                    break;
                }
                case "configurar": {//si se da click en configurar 
                    HttpSession sesion = request.getSession();//se pide la sesion
                    sesion.setAttribute("configurar", "configurar");//se crea el objeto configurar
                    request.getRequestDispatcher("UsuarioVTA.jsp").forward(request, response);//se redige a la vista de usuario
                    break;
                }
                case "actualizar": {//si el usuario quiere actualizar algun atributo
                    HttpSession sesion = request.getSession();//se pide la sesion
                    String atributo = request.getParameter("atributos");//se verifica que atribuo quiere modificar
                    UsuarioDTO userActualizar = obj.userSearch(new UsuarioDTO(Integer.parseInt(request.getParameter("id"))));//se crea el usuario a modificar, obteniendo de la base de datos con el id
                    switch (atributo) {
                        case "nombre": {//si se desea modificar el nombre del usuario
                            String nombre = request.getParameter("txtNombreAc");
                            String apellido = request.getParameter("txtApellidoAc");
                            userActualizar.setNombre(nombre);
                            userActualizar.setApellido(apellido);
                            if (obj.actualizarUsuario(userActualizar)) {
                                sesion.setAttribute("Notificar", "el nombre se ha cambiado, reinice la sesion para observar los cambios");// se crea el objeto notificar y se le informa que todo salio bien
                                request.getRequestDispatcher("UsuarioVTA.jsp").forward(request, response);
                            } else {
                                sesion.setAttribute("Error", "el nombre no se cambio, ocurrio un error");//se crea el objeto error informadole al usuario que algo ocurrio mal
                                request.getRequestDispatcher("UsuarioVTA.jsp").forward(request, response);
                            }

                            break;

                        }

                        case "numero": {
                            String numeroIdentificacion = request.getParameter("txtNumeroAc");
                            userActualizar.setNumeroIdentificacion(numeroIdentificacion);
                            if (obj.actualizarUsuario(userActualizar)) {
                                sesion.setAttribute("Notificar", "el numero de identificacion se ha cambiado, reinice la sesion para observar los cambios");
                                request.getRequestDispatcher("UsuarioVTA.jsp").forward(request, response);
                            } else {
                                sesion.setAttribute("Error", "el numero de identificacion no se cambio, ocurrio un error");
                                request.getRequestDispatcher("UsuarioVTA.jsp").forward(request, response);
                            }
                            break;
                        }
                        case "email": {
                            String correo = request.getParameter("txtCorreoAc");
                            userActualizar.setCorreo(correo);
                            if (obj.actualizarUsuario(userActualizar)) {
                                sesion.setAttribute("Notificar", "el correo se ha cambiado, reinice la sesion para observar los cambios");
                                request.getRequestDispatcher("UsuarioVTA.jsp").forward(request, response);
                            } else {
                                sesion.setAttribute("Error", "el correo no se cambio, ocurrio un error");
                                request.getRequestDispatcher("UsuarioVTA.jsp").forward(request, response);
                            }

                            break;
                        }
                        case "contrasenia": {
                            String actual = request.getParameter("txtClaveActual");
                            actual = this.AsegurarClave(actual);//se asegura la clave que se coloco como actual
                            if (actual.equals(userActualizar.getClave())) {// se verifica que la cotrasenia actual sea igual a la registrada
                                String nueva = request.getParameter("txtClaveAc1");
                                String nueva2 = request.getParameter("txtClaveAc2");
                                if (nueva.equals(nueva2)) {//se comprara si las dos claves nuevas son iguales
                                    nueva = this.AsegurarClave(nueva);
                                    userActualizar.setClave(nueva);
                                    if (obj.actualizarUsuario(userActualizar)) {
                                        sesion.setAttribute("Notificar", "la clave se ha cambiado");// se le informa que todo salio bien
                                        request.getRequestDispatcher("UsuarioVTA.jsp").forward(request, response);
                                    } else {
                                        sesion.setAttribute("Error", "La clave no se cambio, ocurrio un error");// se le informa que hubo algun error
                                        request.getRequestDispatcher("UsuarioVTA.jsp").forward(request, response);
                                    }

                                } else {
                                    sesion.setAttribute("Error", "la clave nueva no coincide");// la clave no coincide en caso de que la clave nueva y la nueva 2 no sean iguales
                                    request.getRequestDispatcher("UsuarioVTA.jsp").forward(request, response);
                                }
                            } else {
                                sesion.setAttribute("Error", "la clave actual es incorrecta");//la clave que coloco como actual sea incorrecta
                                request.getRequestDispatcher("UsuarioVTA.jsp").forward(request, response);
                            }
                            break;
                        }
                        case "sexo": {
                            char sexo = request.getParameter("generoAc").charAt(0);
                            userActualizar.setSexo(sexo);
                            if (obj.actualizarUsuario(userActualizar)) {
                                sesion.setAttribute("Notificar", "el sexo se ha cambiado, reinice la sesion para observar los cambios");
                                request.getRequestDispatcher("UsuarioVTA.jsp").forward(request, response);
                            } else {
                                sesion.setAttribute("Error", "el sexo no se cambio, ocurrio un error");
                                request.getRequestDispatcher("UsuarioVTA.jsp").forward(request, response);
                            }
                            break;
                        }
                        case "fecha": {
                            String fecha = request.getParameter("DiaAc") + "/" + request.getParameter("MesAc") + "/" + request.getParameter("AnioAc");
                            userActualizar.setFechaNacimiento(fecha);
                            if (obj.actualizarUsuario(userActualizar)) {
                                sesion.setAttribute("Notificar", "la fecha de nacimiento se ha cambiado, reinice la sesion para observar los cambios");
                                request.getRequestDispatcher("UsuarioVTA.jsp").forward(request, response);
                            } else {
                                sesion.setAttribute("Error", "la fecha de nacimiento no se cambio, ocurrio un error");
                                request.getRequestDispatcher("UsuarioVTA.jsp").forward(request, response);
                            }
                            break;
                        }

                    }
                    sesion.setAttribute("user", userActualizar);//se actualiza el objeto user con el usuario actulizado
                    break;
                }
                case "datos": {// dio click en ver informacion del usuario
                    HttpSession sesion = request.getSession();
                    sesion.setAttribute("informacion", "informacion");//se crea el objeto informacion 
                    request.getRequestDispatcher("UsuarioVTA.jsp").forward(request, response);//se redirige para observar la informacion
                    break;

                }
                case "borrar": {// cuando se quiere borrar el usuario
                    HttpSession sesion = request.getSession();
                    
                    UsuarioDTO userBorrar = obj.userSearch(new UsuarioDTO(Integer.parseInt(request.getParameter("idEli"))));// buscar al usuario a borrar con el id
                    if (obj.eliminarUsuario(userBorrar)) {//si el usuario se elimina de forma correcta 
                        sesion.setAttribute("Notificar", "Se ha borrado al usuario " + userBorrar.getNombre() + " " + userBorrar.getApellido());// se le informa al administrador el usuario que elimino
                        request.getRequestDispatcher("UsuarioVTA.jsp").forward(request, response);
                    } else {
                        sesion.setAttribute("Error", "No se ha podido borrar el usuario");// se le informa al admin que no se pudo borrar al usuario, por algun error en el sistema
                        request.getRequestDispatcher("UsuarioVTA.jsp").forward(request, response);
                    }
                    break;
                }
                case "activar": {
                    HttpSession sesion = request.getSession();

                    UsuarioDTO userActivar = obj.userSearch(new UsuarioDTO(Integer.parseInt(request.getParameter("idActivar"))));//busca al usuario, en la base de datos
                    userActivar.setEstado("ACTIVO");
                    if (obj.actualizarUsuario(userActivar)) {
                        sesion.setAttribute("Notificar", "Se ha activado al usuario " + userActivar.getNombre() + " " + userActivar.getApellido());// se le informa que se activo el usuario
                        request.getRequestDispatcher("UsuarioVTA.jsp").forward(request, response);
                    } else {
                        sesion.setAttribute("Error", "No se ha podido activar el usuario");// se le informa que ocurrio un error
                        request.getRequestDispatcher("UsuarioVTA.jsp").forward(request, response);
                    }
                    break;
                }
                case "admin": {//msimo paso que e activar usuario
                    HttpSession sesion = request.getSession();

                    UsuarioDTO userAdmin = obj.userSearch(new UsuarioDTO(Integer.parseInt(request.getParameter("idAdmin"))));
                    userAdmin.setEstado("ADMIN");
                    if (obj.actualizarUsuario(userAdmin)) {
                        sesion.setAttribute("Notificar", "El usuario " + userAdmin.getNombre() + " " + userAdmin.getApellido() + " ahora es administrador");
                        request.getRequestDispatcher("UsuarioVTA.jsp").forward(request, response);
                    } else {
                        sesion.setAttribute("Error", "El usuario no se pudo volver administrador");
                        request.getRequestDispatcher("UsuarioVTA.jsp").forward(request, response);
                    }
                    break;
                }
            }
        
    }

    private String AsegurarClave(String clave) {//funcion que me asegura o me cifra la clave

        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            sha256.update(clave.getBytes("UTF-8"));
            clave = String.format("%064x", new BigInteger(1, sha256.digest()));
            System.out.println("clave: " + clave);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("sha1 " + ex.getMessage());
        } catch (UnsupportedEncodingException ex) {
            System.out.println("sha2 " + ex.getMessage());
        }

        return clave;
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
