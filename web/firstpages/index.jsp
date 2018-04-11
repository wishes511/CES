<%-- 
    Document   : index
    Author     : mich
--%>

<!DOCTYPE html>
<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0" />
        <title>E/S</title>
        <link rel="shortcut icon" type="image/x-icon" href="images/icono.png" />
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/dashboard.css">
        <link rel="stylesheet" type="text/css" href="css/fondos.css">
        <script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
        <script type="text/javascript" src="js/tether.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
    </head>
    <body >
        <script>
            $(document).ready(function () {
                document.getElementById("codigo").focus();
            });
            function apuntar() {
                document.getElementById("usu").focus();
            }
        </script>
        <div class="">
            <nav class="navbar navbar-toggleable-md navbar-inverse fixed-top bg-inverse">
                <button class="navbar-toggler navbar-toggler-right hidden-lg-up" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <a class="navbar-brand" ><img src="images/AF.png" width="170px" height="50"></a>
                <div class="collapse navbar-collapse" id="navbarsExampleDefault">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item active">
                        </li>
                        <!--<li class="nav-item">
                          <a class="nav-link" href="">Settings</a>
                        </li>-->
                    </ul>
                    <div class="row">
                        <!--<input class="form-control mr-sm-2" type="text" placeholder="Search">-->
                        <button class="btn btn-outline-secondary my-2 my-sm-0" data-toggle="modal" data-target="#myModal" >Iniciar</button>
                    </div>
                </div>
            </nav> 
        </div>
        <div class="">
            <div id="fondoES" class="container-fluid espacio-lg-down" align="center">
                <div class="btn" >
                    <div class="" align="center" >
                        <br><br><br><br><br><label class="">Codigo</label><input type="text" id="codigo" name="codigo" onchange="searchuser()" class="form-control ">
                    </div>
                </div>  
                <div id="respuesta" align="center">
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="espacio-lg-down"></div>
    <!--        <footer class="footerr">

                <div align="center" >
                    <section class="row text-center placeholders">
                        <div class="col-md-4 ">
                            <label>Derechos reservados, Athletic Footwear S.A de C.V</label><br>
                            <label class="shiro">01 (476) 743 1552 | 743 0448</label><br>
                            <!--<a href="Avances.apk"><img src="images/android.png" width="150" height="50" alt="" class="img-responsive"></a>
                            <hr>
                        </div>
                        <div class="col-md-4 ">
                            <label class="shiro ">Marcas</label><br><br>
                            <label class="shiro">Omar Castell</label><br>
                            <label class="shiro">Duraland</label><br>
                            <label class="shiro">Traffic</label><br>
                            <label class="shiro">TopSuelas</label><br><br><br>
                            <hr>
                        </div>
                        <div class="col-md-4 " >
                            <label class="shiro">Redes Sociales </label><br>
                            <br><a href="https://www.facebook.com/Atheticfootwear/" ><img src="images/face.png" class="btn" width="60" height="50"></a>
                        </div>
                    </section>
                </div>
            </footer> -->       </div> 
        <!-- Modal -->
        <div id="myModal" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Iniciar Sesion</h4>
                    </div>
                    <div class="modal-body">
                        <form method="post" action="Validar">
                            <label>Usuario</label><input type="text" name="usu" id="usu" class="form-control" placeholder="Usuario">
                            <label>Contrasena</label><input type="password" name="pass" id="pass" class="form-control" placeholder="Contrasena"><br>
                            <div align="center"><button class="btn btn-success">Iniciar</button></div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <div class=""></div>
        <script type="text/javascript" src="js/functions_js.js"></script>
    </body>
</html>