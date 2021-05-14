<%-- 
    Document   : Principal
    Created on : 14/12/2020, 11:11:13 PM
    Author     : Angelica Barros Espitia
    Author     : Luis Felipe Hernandez Chica
--%>
<%@page contentType="text/html" pageEncoding="ISO-8859-15"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><!-- Se importa la libreria jstl -->
<%HttpSession objSesion = request.getSession();// Esta parte de codigo me redirige al index en caso de que el usuario no se haya logeado
    if (objSesion.getAttribute("usuario") == null) {
       response.sendRedirect("index.jsp");
    }
%>
<c:if test="${user.getEstado().equals("REGISTRADO")}"><!-- Esta parte valida que el usuario, se encuentre activo, por lo tanto si se encuentra en un estado registrado lo redirecciona al index -->
    <% objSesion.setAttribute("infoBueno", "");// Restablece la notificacion buena, en vacio
        response.sendRedirect("index.jsp");//Redirige al index %>
</c:if>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Pagina Principal</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous"> <!-- Hoja de estilos de Boostrap -->

        <link rel="preconnect" href="https://fonts.gstatic.com"><!-- Importacion de una fuente de letras desde google fonts -->
        <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,300;1,500;1,700&display=swap" rel="stylesheet">
        <style>

            body{

                background-image: url("Img/Backgound.svg");
                background-color: transparent;
                background-size: cover;
                font-family: 'Poppins', sans-serif;
            }
        </style><!-- Se establece el fondo de la pagina web, y la familia de fuentes de letra del proyectes -->
    </head>
    <body >
        <nav class="navbar navbar-expand-sm navbar-light bg-info "><!-- Menu de Bosostrap -->

            <a class="btn btn-outline-light" style="margin-left: 10px;border: none" href="Principal.jsp"><h4>Inicio</h4></a><!-- Boton de inicio -->
            <button class="navbar-toggler" type="button" data-toggle="collapse" 
                    data-target="#navbarNav" aria-controls="navbarNav" 
                    aria-expanded="false" aria-label="Toggle navigation"> 
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav justify-content-end">

                    <li class="nav-item">
                        <a class="btn btn-outline-light" style="margin-left: 10px; border: none" href="ProductoCTO?menu=Producto&accion=Listar" target="Frame"><h4>Producto</h4></a><!-- Un opcion del menu, muestra en el frame ProductoVTA.jsp -->
                    </li>
                    <c:if test="${user.getEstado().equals("ADMIN")}"><!-- En caso de que el usuario sea un administrador, se le muestra la opcion de usuarios registrados -->
                        <li class="nav-item">
                            <a class="btn btn-outline-light" style="margin-left: 10px; border: none" href="UsuarioCTO?rol=${user.getEstado()}&accion=usuario" target="Frame"><!-- El href hace referencia al controlador de usuarios, enviandole como parametros que es una administrador -->


                                <h4>Usuarios</h4>


                            </a>
                        </li>
                    </c:if>
                </ul>


            </div>


            <div class="dropdown dropleft"  > <!-- Boton de opciones de la cuenta -->
                <a href="#" class="btn btn-outline-light dropdown-toggle"
                   style="width: 100px; border: none" data-toggle="dropdown"><h6>Mi Sesión</h6></a>
                <div class="dropdown-menu ">
                    <div class="card text-center ">

                        <div class="col-12 "> <!-- En este div se compara que si el sexo es de mujer o hombre, se muestra una imagen respectiva al genero -->
                            <c:if test="${user.getSexo() eq 'M'.charAt(0)}"> <!-- En caso de que sea hombre -->
                                <img src="Img/avatarMale.svg" height="80" width="80">  
                            </c:if>
                            <c:if test="${user.getSexo() eq 'F'.charAt(0)}"><!-- En caso de que sea mujer -->
                                <img src="Img/avatarFemale.svg" height="80" width="80">  
                            </c:if> 
                        </div>

                        <a class="dropdown-item">${user.getNombre()} ${user.getApellido()} </a> <!-- Muestra el nombre del usuario -->
                        <a class="dropdown-item">${user.getCorreo()}</a><!-- Muestra el correo del usuario -->
                        <div class="dropdown-divider">



                        </div>
                        <a href="UsuarioCTO?accion=configurar" class="dropdown-item" target="Frame">Configuración de tu cuenta</a><!-- Boton que me redirige a configurar la informacion de la cuenta -->
                        <a href="UsuarioCTO?accion=datos" class="dropdown-item" target="Frame">información de tu cuenta</a> <!-- Boton que tiene como funcion ir  a mostrar los datos del usuario -->
                        <a href="MiSesionCTO?accion=Salir" class="dropdown-item">Cerrar sesión</a> 
                    </div>
                </div>
            </div>
        </nav>
        <div style="height: 100VH;"> <!-- Caja en donde se muestra la informacion que desea el usuario -->
            <iframe name="Frame" style="height: 100%; width: 100%; border: 2px"></iframe>
        </div>
        <footer>
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-6">
                        <h5>Integrates:</h5>
                        <p>Angelica Barros Espitia</p>
                        <p>Luis Felipe Hernandez Chica</p>
                    </div>
                    <div class="col-6">
                        <h5>Materia</h5>
                        <p>Profesor: Noé Arcos Muñoz  </p>
                        <p>Asigatura: Programación Avanzada </p>
                    </div>
                </div>
            </div>


        </footer>

        <!-- Importaciones de javascript de boostrap-->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
    </body>
</html>
