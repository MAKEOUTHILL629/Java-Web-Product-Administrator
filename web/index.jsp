<%-- Document : index Created on : 14/12/2020, 08:53:42 PM 
    Author     : Angelica Barros Espitia
    Author     : Luis Felipe Hernandez Chica
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-15" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Taller Java Web</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
        <!-- Se importa la fuente de letra de google-->
        <link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,300;1,500;1,700&display=swap" rel="stylesheet">
    </head>

    <body>
        <%  //Muestra una notificacion en caso de haya un incoveniente o informacion repecto al logease
            HttpSession objSesion = request.getSession();
            if (objSesion.getAttribute("infoBueno") != null) {
        %><div class="alert alert-success" role="alert"><!-- Si es una notifiacion de inforBueno se muestra la notificacion buena -->
            Te haz registrado, espera a que un administrado active tu cuenta<!-- el usuario usuario se registro, pero el admin no lo ha activado -->
        </div>

        <%
        } else if (objSesion.getAttribute("infoMalo") != null) { // si es una notifiacion de error, o que algo ocurrio mal
        %><div class="alert alert-danger" role="alert"><!-- notificacion de tipo mala -->
            Ohh no!! <%=objSesion.getAttribute("infoMalo") %> <!-- se le informa al usuario el error que cometio o porque no le deja ingresar -->
        </div><%
            }
            objSesion.setAttribute("infoMalo", null);// como ya mostro la notificacion se deja en null
            objSesion.setAttribute("infoBueno", null);// como ya mostro la notificacion se deja en null
        %>
        <div class="container-sm text-center">
            <div class="row justify-content-lg-center">
                <div class="col-lg-5 border  ">
                    <div class="col-12">
                        <img src="Img/Avatar.png" height="123" width="120"><!-- Imagen inicial del index -->
                    </div>
                    <form class="col-12" action="MiSesionCTO?accion=Validar" method="POST"><!--Formulario para validar el inicio de sesion -->
                        <div class="form-group text-center">
                            <p>
                                <strong>
                                    Bienvenidos al sistema de compras
                                </strong>
                            </p>
                        </div>
                        <div class="form-group">

                            <input class="form-control" type="text" name="txtUsuario" placeholder="Correo electrónico" required>
                        </div>
                        <div class="form-group">

                            <input class="form-control" type="password" name="txtPss" placeholder="Contraseña" required>
                        </div>
                        <h5><input type="submit" name="btnIngresar" value="Iniciar sesión" class="btn btn-primary col-12 "></h5>
                    </form>
                    <div class="col-12">
                        <a href="#"><!-- Este boton no tiene funcion debido que no hay un metodo seguro para retablecer la contrasenia -->
                            ¿Olvidaste tu contraseña?
                        </a>

                    </div>
                    <hr class="col-10">
                    <div class="col-12 p-4  bg">
                        <!-- Boton para crear una cuenta nueva, muestra un modal -->
                        <button type="button" class="btn btn-success" data-toggle="modal" data-target="#registerModal">
                            <h5>Crear cuenta nueva</h5>
                        </button>

                        <!-- Modal para crear la cuenta -->
                        <div class="modal fade" id="registerModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <form action="UsuarioCTO?accion=Registrar" method="POST"><!-- Formulario para registrar al usuario -->
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="exampleModalLabel">Registrarte</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><!--  Boton para cerrar -->
                                                <span aria-hidden="true">&times;</span> 
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="container">

                                                <div class="row">
                                                    <!-- por cada atributo se crea un input para que usuario ingrese los datos -->
                                                    <div class="from-group col-6">
                                                        <label for=""></label>
                                                        <input type="text" class="form-control" name="txtNombre" placeholder="Nombre" required="">
                                                    </div>
                                                    <div class="from-group col-6">
                                                        <label for=""></label>
                                                        <input type="text" class="form-control" name="txtApellido" placeholder="Apellido" required="">
                                                    </div>
                                                </div>
                                                <div class="row">

                                                    <div class="from-group col-12">
                                                        <label for=""></label>
                                                        <input type="number" class="form-control" name="txtIdentificacion" placeholder="Numero de identificación" required="">
                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="from-group col-12">
                                                        <label for=""></label>
                                                        <input type="email" class="form-control" name="txtCorreo" placeholder="Correo electrónico" required="">
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="from-group col-12">
                                                        <label for=""></label>
                                                        <input type="password" class="form-control" name="txtPassword" placeholder="Contraseña nueva" required="">
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-12">
                                                        <label for="">Fecha de nacimiento</label>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="form-group col-4">
                                                        <select class="form-control " name="Dia" required="">
                                                            <option selected>Día</option>
                                                            <%                                                               
                                                              for (int i = 1; i <= 31; i++) {//Este for muestra los 31 dias que usuario puede elejir
                                                            %> <option><%=i%></option><%
                                                                }
                                                            %>
                                                        </select>
                                                    </div>

                                                    <div class="form-group col-4">
                                                        <select class="form-control  " name="Mes" required="">
                                                            <option selected>Mes</option>
                                                            <%
                                                                for (int i = 1; i <= 12; i++) {// Este for itinera 12 veces para mostrar los meses
                                                            %> <option><%=i%></option><%
                                                                }
                                                            %>
                                                        </select>
                                                    </div>

                                                    <div class="form-group col-4" >
                                                        <select class="form-control  " name="AnioNacimiento" required="">
                                                            <option selected>Año</option>
                                                            <%
                                                                for (int i = 2021; i >= 1905; i--) {//este for itinera para mostrar los distinos anios de nacimiento
                                                            %> <option><%=i%></option><%
                                                                }
                                                            %>
                                                        </select>
                                                    </div>


                                                </div>
                                                <div class="row">
                                                    <div class="col-12">
                                                        <label for=""> Sexo</label>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <!-- Me permite elejir el sexo palomeando el radioButton -->
                                                    <div class="form-group col-6 border">
                                                        <div class="form-check">
                                                            <input class="form-check-input " type="radio" name="genero" id="exampleRadios1" value="F" checked>
                                                            <label class="form-check-label" for="exampleRadios1">
                                                                Mujer
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <div class="form-group col-6 border">
                                                        <div class="form-check">
                                                            <input class="form-check-input" type="radio" name="genero" id="exampleRadios2" value="M">
                                                            <label class="form-check-label" for="exampleRadios2">
                                                                Hombre
                                                            </label>
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                        <div class="modal-footer justify-content-center">

                                            <h5><input type="submit" name="btnIngresar" value="Registrase" class="btn btn-success"></h5> <!-- El boton que indica que los campos estan completos -->
                                        </div>
                                    </form>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

                                                        <!-- Importaciones de boostrap -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>

        <footer>

        </footer>
    </body>

</html>