<%-- 
    Document   : Log
    Created on : 19-ago-2016, 12:51:49
    Author     : mich
--%>
<%
    HttpSession objSesion = request.getSession(false);
    String usuario = (String) objSesion.getAttribute("usuario");
    String tipos = (String) objSesion.getAttribute("tipo");
    String ids = String.valueOf(objSesion.getAttribute("i_d"));

    // out.println("" + tipos+"/"+ids);
    int idprodu = 0;
    int idprodu1 = 0;
    String tipo = "";
    int stoc = 0;


%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ingreso al Sistema</title>
        <link rel="icon"  href="images/aff.png" sizes="32x32"/>
        <link rel="stylesheet" type="text/css" href="css/fondos.css">
        <link rel="stylesheet" type="text/css" href="css/loginn.css">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="css/opcional.css">
        <script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
        <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
        <script>
             $(document).ready(function () {
                document.form.nombrelog.focus();
            });
        </script>
        <script type="text/javascript"> 
          
            function valida_envia() {
                var valor= $("#nombrelog").val();
               if (!(/^([A-Za-z\d]*)$/i.test(valor))) {
                    alert("Usuario invalido");
                    document.form.nombrelog.focus();
                    document.getElementById("nombrelog").value="";
                    return 0;
               }
               var valor1 = document.form.contrasenalog.value;
                if (!(/^([A-Za-z\d\.]*)$/i.test(valor1))) {
                    alert("Contraseña invalida! ");
                    document.form.contrasenalog.focus();
                    document.getElementById("contrasenalog").value="";
                    return 0;
                }
            }
        </script>
    </head>
    <body class="" style="background-image: linear-gradient(rgb(255, 255, 255), rgb(153, 153, 255)); background-repeat: no-repeat">
        <div align="lest"><a href="http://athleticfootwear.com.mx/"><img src="images/AF.png" width="150px" height="70px" class="img-responsive"/></a></div>
        <br>

        <div align="center">
            <%                 if (usuario != null && tipos != null && tipos.equals("INTERMEDIO")) {
                    out.println("<div class=container-fluid align=left> ");

                    out.println("<div class=row>");
                    out.println("<form action=capturador/index.jsp> ");
                    out.println("<label>Conectado como:</label>");
                    out.println("<input type=submit value=" + usuario + " class=btn btn-default>");
                    out.println("</form>");
                    out.println("</div>");

                    out.println("</div>");

                } else {
                }
            %>
            <div class="container-fluid" align="center">
                <div class="container espacio-md-up espacio-md-down" >
                    <div class="row" >
                        <div class="col-sm-offset-4 col-sm-4" >
                            <div style="padding-top: 20%">
                                <div class="form-login" >
                                    <h3 class="h3" >Ingreso</h3>
                                    <form id="form" name="form" action="Validar" method="POST" class="form-login " onsubmit="valida_envia()">
                                        <input  type="text" id="nombrelog" class="form-control input-sm chat-input" name="nombrelog" placeholder="username" onkeypress="valida_envia()" required/>
                                        <br>
                                        <input type="password" id="contrasenalog" class="form-control input-sm chat-input" name="contrasenalog" placeholder="password" onkeypress="valida_envia()" required/>
                                        <br>
                                        <div class="wrapper">
                                            <span class="group-btn">     
                                                <input type="submit" value="Entrar" class="btn btn-default navbar-btn" />
                                            </span>
                                            <br>
                                            <label style="font-size:50px; font-family: monospace;">
                                                ${error}                      
                                            </label>
                                        </div> </form>
                                </div><br><br><br><br>
                            </div>
                        </div>
                    </div>    
                </div>   
            </div>
        </div>
        <footer>
            <div align="center">
                <div class="col-sm-3 espacio">
                    <a href="http://athleticfootwear.com.mx/"><img src="images/AFotter.png" width="150" height="30"></a><br><br>
                    <label>Derechos reservados, Athletic Footwear S.A de C.V</label><br>
                    <label class="shiro">01 (476) 743 1552 | 743 0448</label><br>
                    <label class="shiro">Descarga app android</label><br>
                    <!--<a href=""><img src="images/android.png" width="150" height="50" alt="" class="img-responsive"></a>-->
                    <hr style="">
                </div>
                <div class="col-sm-3 espacio">
                    <label class="shiro titulos">Marcas</label><br><br>
                    <label class="shiro">Omar Castell</label><br>
                    <label class="shiro">Duraland</label><br>
                    <label class="shiro">Traffic</label><br>
                    <label class="shiro">TopSuelas</label><br><br><br>
                    <hr>
                </div>
                <div class="col-sm-3 espacio" >
                    <label class="shiro titulos">Redes Sociales </label><br>
                    <br><a href="https:\\www.facebook.com" ><img src="images/face.png" class="btn" width="60" height="50"></a>
                    <a href="https:\\www.twitter.com"><img src="images/twi.png" class="btn" width="60" height="50"></a><br><br><br><br><br>
                    <hr>
                </div>
                <div class="col-sm-3 espacio">
                    <a class="" href="http://192.168.6.75:86/fichast"><label class="shiro titulos" >Fichas Tecnicas</label></a>
                    <br><img class="img-responsive jumbismini" src="images/QR.png" width="75px" height="75px"><br>
                    
                </div>
            </div>
        </footer>
    </body>
</html>
