<%-- 
    Document   : UsuarioVTA
    Created on : 23/02/2021, 01:57:02 AM
    Author     : Angelica Barros Espitia
    Author     : Luis Felipe Hernandez Chica
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><!-- Se importa la libreria jstl -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%HttpSession objSesion = request.getSession();
    if (objSesion.getAttribute("usuario") == null) {// Verifica que el usuario este logeado
        response.sendRedirect("index.jsp");
    }else{
        
    
%>
<c:if test="${user.getEstado().equals("REGISTRADO")}"><!-- verificacion de que el usuario se encuentre activo del sistema -->
    <% objSesion.setAttribute("infoBueno", "");
        response.sendRedirect("index.jsp"); %>
</c:if>

<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>USUARIO</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

        <!-- Importacion de una fuente de letra desde google -->
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,300;1,500;1,700&display=swap" rel="stylesheet">
        <!-- Se establece un fondo y la familia de fuentes que tiene la pagina web -->
        <style>
            body{
                background-image: url("Img/Backgound.svg");
font-family: 'Poppins', sans-serif;
                background-size: cover;
            }
        </style>
    </head>

    <body>
        <%
            //Si el objeto notificar existe, se le muestra la notificacion al usuario
            if (objSesion.getAttribute("Notificar") != null) {
        %><div class="alert alert-success" role="alert"><!--  Una alerta de que la cosas salieron bien-->
            Bien hecho!!  <%=objSesion.getAttribute("Notificar")%> <!-- Muestra muestra el mensaje de la notificacion -->
        </div>

        <%
            //Si el objeto error existe, se le muestra la notificacion al usuario
        } else if (objSesion.getAttribute("Error") != null) {
        %><div class="alert alert-danger" role="alert"><!-- Una alerta de tipo que la cosas salieron mal -->
            Ohh No!! <%=objSesion.getAttribute("Error")%> <!--  Notifica porque salio mal -->
        </div><%
            }
            objSesion.setAttribute("Error", null);// si ya mostro la notificacion la vuelve a dejar en null
            objSesion.setAttribute("Notificar", null);// si ya se mostro la notificacion la deja en null
        %>
        <div class="container-lg">

            <c:if test="${lista_usuarios != null}"><!-- En caso de que el controlador le haya enviado la lista de usuarios al administrador -->
<c:if test="${user.getEstado().equals("ADMIN")}"> <!-- Verifica que sea el administrador quien halla pedido la informacion -->


                <table class="table table-striped" style="margin-left: -40px"><!-- Crea los indices de la tabla -->
                    <thead class="thead-dark">
                        <tr>
                            <th>ID</th>
                            <th>NOMBRE</th>
                            <th>APELLIDO</th>
                            <th>NUMERO IDENTIFICACION</th>
                            <th>CORREO ELECTRONICO</th>
                            <th>SEXO</th>
                            <th>FECHA NACIMIENTO</th>
                            <th>ESTADO</th>
                            <th></th>
                            <th></th>
                            <th></th>

                        </tr>
                    </thead>
                    <tbody><!-- Muestra en cada espacio de la tabla el usuario -->
                        <c:forEach var="objP" items="${lista_usuarios}">
                            <tr >
                                <td>${objP.getId()}</td>
                                <td>${objP.getNombre()}</td>
                                <td>${objP.getApellido()}</td>
                                <td>${objP.getNumeroIdentificacion()}</td>
                                <td>${objP.getCorreo()}</td>
                                <td>${objP.getSexo()}</td>
                                <td>${objP.getFechaNacimiento()}</td>
                                <td>${objP.getEstado()}</td>
                                <td>
                                    <a class="btn btn-outline-dark" href="UsuarioCTO?accion=borrar&idEli=${objP.getId()}">BORRAR</a> <!-- Guarda el id del usuario a borrar, y crea el boton de borrar -->
                                </td>
                                <td><a class="btn btn-outline-dark" href="UsuarioCTO?accion=activar&idActivar=${objP.getId()}">ACTIVAR</a></td> <!-- Guarda el id del usuario para volverlo activar, y crea el boton de activar -->
                                <td><a class="btn btn-outline-dark" href="UsuarioCTO?accion=admin&idAdmin=${objP.getId()}">ADMIN</a></td> <!-- Guarda el id del usuario para volverlo admin, y crea el boton de admin -->
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>




                </c:if>
            </c:if>
            <!-- el if me evalua que el usuario haya dado click en configurar tu cuenta -->
            <c:if test="${configurar  !=  null}">
                <table class="table"><!-- Muestra la informacion de la cuenta en una tabla -->
                    <thead class="thead-light">
                        <tr>
                            <th scope="col">Configuración general de la cuenta</th>
                            <th scope="col"></th>
                            <th scope="col"></th>
                            <th scope="col"></th>
                            <th scope="col"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Se crea un formulario para cada atributo a modificar, teniendo en cuenta el id que se encuentra logeado en el sistema, posee el mismo funcionamiento que al momento de registrase -->
                        <tr>
                    <form method="POST" action="UsuarioCTO?accion=actualizar&atributos=nombre&id=${user.getId()}"> <!-- Se crea un formulario para modificar el nombre y se guarda el id del usuario -->
                        <th scope="row"><label>Nombre</label></th>
                        <!-- Respectivos inputs para modificar el nombre -->
                        <td><input type="text" name="txtNombreAc" class="from-control" placeholder="Nuevos nombres" required=""></td>
                        <td></td>
                        <td><input type="text" name="txtApellidoAc" class="from-control" placeholder="Nuevos apellidos" required=""></td>
                        <td><input type="submit" name="btnNombresAc" class="btn btn-outline-secondary from-control" value="Actualizar"></td>
                    </form>
                    </tr>
                    <tr>
                    <form method="POST" action="UsuarioCTO?accion=actualizar&atributos=numero&id=${user.getId()}">
                        <th scope="row"><label>Numero Identificación</label></th>
                        <td></td>
                        <td><input type="number" name="txtNumeroAc" class="from-control" placeholder="Nuevo numero de identificación" required=""></td>
                        <td></td>
                        <td><input type="submit" name="btnNumeroAc" class="btn btn-outline-secondary from-control" value="Actualizar"></td>
                    </form>
                    </tr>
                    <tr>
                    <form method="POST" action="UsuarioCTO?accion=actualizar&atributos=email&id=${user.getId()}">
                        <th scope="row"><label>Correo electrónico</label></th>
                        <td></td>
                        <td><input type="email" name="txtCorreoAc" class="from-control"  placeholder="Nuevo correo electrónico" required=""></td>
                        <td></td>
                        <td><input type="submit" name="btnCorreoAc" class="btn btn-outline-secondary from-control" value="Actualizar"></td>
                    </form>
                    </tr>
                    <tr>
                    <form method="POST" action="UsuarioCTO?accion=actualizar&atributos=contrasenia&id=${user.getId()}">
                        <th scope="row"><label>Contraseña</label></th>
                        <td><input type="password" name="txtClaveActual" class="from-control" placeholder="Contraseña actual" required=""></td>
                        <td><input type="password" name="txtClaveAc1" class="from-control" placeholder="Nueva contraseña" required=""></td>
                        <td><input type="password" name="txtClaveAc2" class="from-control" placeholder="Confirmar nueva contraseña" required=""></td>
                        <td><input type="submit"  name="btnClaveAc" class="btn btn-outline-secondary form-control" value="Actualizar"></td>
                    </form>
                    </tr>
                    <tr>
                    <form method="POST" action="UsuarioCTO?accion=actualizar&atributos=sexo&id=${user.getId()}">
                        <th scope="row"><label>Sexo</label></th>
                        <td><div class="form-check">
                                <input class="form-check-input " type="radio" name="generoAc" id="exampleRadios1" value="F" checked>
                                <label class="form-check-label" for="exampleRadios1">
                                    Mujer
                                </label>
                            </div></td>
                        <td></td>
                        <td><div class="form-check">
                                <input class="form-check-input" type="radio" name="generoAc" id="exampleRadios2" value="M">
                                <label class="form-check-label" for="exampleRadios2">
                                    Hombre
                                </label>
                            </div></td>
                        <td><input type="submit" name="btnGeneroAc" class="btn btn-outline-secondary from-control" value="Actualizar"></td>
                    </form>
                    </tr>
                    <tr>
                    <form method="POST" action="UsuarioCTO?accion=actualizar&atributos=fecha&id=${user.getId()}">
                        <th scope="row"><label>Fecha Nacimiento</label></th>
                        <td>
                            <select class="form-control " name="DiaAc" required="">
                                <option selected>Día</option>
                                <%
                                    for (int i = 1; i <= 31; i++) {
                                %> <option><%=i%></option><%
                                    }
                                %>
                            </select>
                        </td>
                        <td>
                            <select class="form-control  " name="MesAc" required="">
                                <option selected>Mes</option>
                                <%
                                    for (int i = 1; i <= 12; i++) {
                                %> <option><%=i%></option><%
                                    }
                                %>
                            </select>
                        </td>
                        <td><select class="form-control  " name="AnioAc" required="">
                                <option selected>Año</option>
                                <%
                                    for (int i = 2021; i >= 1905; i--) {
                                %> <option><%=i%></option><%
                                    }
                                %>
                            </select></td>
                        <td><input type="submit" name="btnfechaAc" class="btn btn-outline-secondary from-control" value="Actualizar"></td>
                    </form>
                    </tr>
                    </tbody><!-- aqui termina la tabla para modificar los atributos del usuario -->
                </table>
            </c:if>
            <% objSesion.setAttribute("configurar", null); %><!-- Se establece el objeto en null, hasta que denuevo se le de click en configurar -->
            <c:if test="${informacion  !=  null}"><!-- Se verifica que se haya dado click al boton de informacion para mostrar los datos del usuario -->
                <div  class="card text-center"><!-- se crea una tarjeta para verlo organizado -->
                    <div  class="row">

                        <div class="col-12 "><!-- verifica el sexo del usuario, para mostrar una imagen repectiva -->
                            <c:if test="${user.getSexo() eq 'M'.charAt(0)}"><!-- En caso de que sea hombre -->
                                <img src="Img/avatarMale.svg" height="200" width="200">  
                            </c:if>
                            <c:if test="${user.getSexo() eq 'F'.charAt(0)}"><!-- En caso de que sea mujer -->
                                <img src="Img/avatarFemale.svg" height="200" width="200">  
                            </c:if> 

                        </div>

                    </div>
                    <div  class="row">
                        <div  class="col-12"><!-- Se imprime la informacion del usuario logeado -->
                            <h3>Nombre: </h3><p>${user.getNombre()} ${user.getApellido()}</p>
                            <h3>Numero de identificacion:  </h3><p>${user.getNumeroIdentificacion()}</p>
                            <h3>Correo:  </h3><p>${user.getCorreo()}</p>
                            <h3>Sexo:  </h3><p>  
                                <c:if test="${user.getSexo() eq 'M'.charAt(0)}">
                                    Masculino
                                </c:if>
                                <c:if test="${user.getSexo() eq 'F'.charAt(0)}">
                                    Femenino
                                </c:if> 
                            </p>
                            <h3>Fecha nacimiento:  </h3><p>${user.getFechaNacimiento()}</p>
                        </div>
                    </div>
                </div>
            </c:if>
            <% objSesion.setAttribute("informacion", null);%><!-- Se vuelve a dejar en null informacion para no mostrarla de neuvo hasta que se click -->
        </div>
        <!-- Importaciones de boostrap -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
    </body>

</html>
<% }%>