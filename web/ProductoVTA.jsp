<%-- 
    Document   : ProductoVTA
    Created on : 14/12/2020, 11:11:41 PM
    Author     : Angelica Barros Espitia
    Author     : Luis Felipe Hernandez Chica
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><!--  Importamos la liberia JSTL -->
<%@page contentType="text/html" pageEncoding="ISO-8859-15"%> 
<%HttpSession objSesion = request.getSession();
    if (objSesion.getAttribute("usuario") == null) {// Esta parte de codigo me redirige al index en caso de que el usuario no se haya logeado
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
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
        <!-- Importacion de una fuente de letras desde google fonts -->
        <link rel="preconnect" href="https://fonts.gstatic.com">
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
    <body>
        <c:if test="${user.getEstado().equals("ADMIN")}"><!-- Si el usuario es ADMIN se muestra la tabla donde se modifica los productos -->
            <div class="container">
                <div class="row">
                    <div class="col-sm-12">
                        <h1>Productos</h1>
                    </div>
                </div>
                <!-- Formulario para editar o agregar un producto -->
                <div class="row">
                    <div class="card col-sm-4">
                        <div class="card-body">
                            <form action="ProductoCTO?menu=Producto" method="POST">
                                <div class="form-group">
                                    <label>Nombre Producto</label>
                                    <input class="form-control" type="text" name="txt_nombre_pro" required value="${producto.getNombre_pro()}">
                                    <input type="hidden" value="${producto.getId_pro()}" name="txt_id_pro">
                                </div>
                                <div class="form-group">
                                    <label>Descripcion Producto</label>
                                    <input class="form-control" type="text" name="txt_descripcion_pro" value="${producto.getDescripcion_pro()}">
                                </div>
                                <div class="form-group">
                                    <label>Unidades Producto</label>
                                    <input class="form-control" type="text" name="txt_unidades_pro" required="" value="${producto.getUnidades_pro()}"> 
                                </div>
                                <div class="form-group">
                                    <label>Valor Producto</label>
                                    <input class="form-control" type="text" name="txt_valor_pro" required="" value="${producto.getValor_pro()}"> 
                                </div>
                                <c:if test="${producto.getValor_pro() ==null }"> 
                                    <input class="btn btn-success" type="submit" name="accion" value="Agregar">
                                </c:if>
                                <c:if test="${producto.getValor_pro() != null}">
                                    <input class="btn btn-warning" type="submit" name="accion" value="Actualizar">
                                </c:if>
                            </form>
                        </div>
                    </div>

                               <!-- Tabla que muestra producto por Producto y opciones de modificacion -->
                    <div class="card col-sm-6 col-md-6 col-lg-8">

                        <table class="table table-hover">
                            <thead>
                                <tr style="text-align: center;">
                                    <th>ID</th>
                                    <th>Nombre</th>
                                    <th>Descripcion</th>
                                    <th>Unidades</th>
                                    <th>Valor</th>
                                    <th>Acciones</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="objP" items="${lista_productos}">
                                    <tr>
                                        <td>${objP.getId_pro()}</td>
                                        <td>${objP.getNombre_pro()}</td>
                                        <td>${objP.getDescripcion_pro()}</td>
                                        <td>${objP.getUnidades_pro()}</td>
                                        <td>${objP.getValor_pro()}</td>
                                        <td>
                                            <a class="btn btn-warning" href="ProductoCTO?menu=Producto&accion=Editar&id=${objP.getId_pro()}">Editar</a>

                                        </td>
                                        <td> <a class="btn btn-danger" href="ProductoCTO?menu=Producto&accion=Eliminar&id=${objP.getId_pro()}">Eliminar</a></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>  
        </c:if>
        <div class="container">
            <div  class="row">
                <div class="d-flex flex-wrap "><!-- para la organizacion de las tarjetas -->
                    <c:forEach var="objP" items="${lista_productos}"><!-- Se encarga de mostrar producto por producto para comprarlo -->
                        <div class="card" style="width: 15rem; margin: 1.2rem;"><!-- card de boostrap -->
                            <img src="Img/Producto.svg" width="150px" height="150px" class="card-img-top" alt="Imagen por default del producto"> <!-- La imagen de la tarjeta -->
                            <div class="card-body">
                                <h5 class="card-title">${objP.getNombre_pro()}</h5><!-- Nombre del producto -->
                                <p class="card-text">${objP.getDescripcion_pro()}</p><!-- descripcion del producto -->
                                <p class="card-text">$${objP.getValor_pro()}</p><!-- el valor del producto -->
                                <c:if test="${objP.getUnidades_pro() <= 0 }"> <!-- En caso de que el producto no tenga unidades en existencia -->
                                    <p class="card-text"  data-toggle="tooltip" data-placement="top" title="No hay ${objP.getNombre_pro()} ahora mismo ">SIN STOCK!</p> <!-- Le muestra al cliente la falta de unidades -->
                                </c:if>
                                <c:if test="${objP.getUnidades_pro() > 0 }"><!-- Si hay unidades del producto, se muestra el boton de comprar -->
                                    <a href="ProductoCTO?menu=Producto&accion=Comprar&id=${objP.getId_pro()}"  data-toggle="modal" data-target="#comprar${objP.getId_pro()}" class="btn btn-primary">Comprar ahora</a><!-- Esta etiqueta muestra el modal y hace refencial al controlador de productoCTO -->
                                </c:if>

                            </div>
                        </div>
                        <div class="modal fade" id="comprar${objP.getId_pro()}" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true"><!-- Crea un modal por cada producto -->
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="staticBackdropLabel">${objP.getNombre_pro()}</h5><!-- Titulo del modal, nombre del producto -->
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>

                                    <from action="ProductoCTO?menu=Producto&id=${objP.getId_pro()}" method="POST"><!-- Formulario para relizar la compra -->
                                        <div class="modal-body">
                                            <div class="text-center">

                                                <h6>Cantidad</h6>
                                                <input type="number"  name="txt_cantidadComprar"  class="from-control"  required="" min="1" max="${objP.getUnidades_pro()}"><!-- Me indica la cantidad que se quiere comprar, con minimo de 1 y un maximo de la cantidad que tiene el producto  -->
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <input type="submit" name="accion" class="btn btn-primary" value="Comprar"><!-- Boton para comprar -->
                                            <button type="button" class="btn btn-danger" data-dismiss="modal">Cancelar</button><!-- Boton para cerrar el modal -->

                                        </div>

                                    </from>

                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>

        </div>


        <!-- Importaciones de boostrap -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
    </body>
</html>
