<%-- 
    Document   : Log
    Author     : mich
--%>
<%
    HttpSession objSesion = request.getSession(false);
    String usuario = (String) objSesion.getAttribute("usuario");
    String tipos = (String) objSesion.getAttribute("tipo");
    String ids = String.valueOf(objSesion.getAttribute("i_d"));
    String tipo = "";
    try{        
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ingreso al Sistema</title>
        <link rel="icon"  href="images/icono.png" sizes="32x32"/>
        <link rel="stylesheet" type="text/css" href="css/fondos.css">
        <link rel="stylesheet" type="text/css" href="css/loginn.css">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min2-0.css">
        <link rel="stylesheet" type="text/css" href="css/opcional.css">
        <script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
        <script>
             $(document).ready(function () {
                document.form.usu.focus();
            });
            function erasefield(){
                document.getElementById("usu").value="";
            }
        </script>
        <script type="text/javascript"> 
          
            function valida_envia() {
                var valor= $("#usu").val();
               if (!(/^([A-Za-z\d]*)$/i.test(valor))) {
                    alert("Usuario invalido");
                    document.form.usu.focus();
                    document.getElementById("usu").value="";
                    return 0;
               }
               var valor1 = document.form.pass.value;
                if (!(/^([A-Za-z\d\.]*)$/i.test(valor1))) {
                    alert("Contraseña invalida! ");
                    document.form.pass.focus();
                    document.getElementById("pass").value="";
                    return 0;
                }
            }
        </script>
    </head>
    <body class="" style="background-image: linear-gradient(rgb(255, 255, 255), rgb(153, 153, 255)); background-repeat: no-repeat">
        <div align="lest"><a href="http://athleticfootwear.com.mx/"><img src="images/AF.png" width="150px" height="70px" class="img-responsive"/></a></div>
        <br>
        <div align="center">
            <div class="container-fluid" align="center">
                <div class="container espacio-md-up espacio-md-down" >
                    <div class="row" >
                        <div class="col-sm-offset-4 col-sm-4" >
                            <div style="padding-top: 20%">
                                <div class="form-login" >
                                    <h3 class="h3" >Ingreso</h3>
                                    <form id="form" name="form" action="Validar" method="POST" class="form-login " onsubmit="valida_envia()">
                                        <input  type="text" id="usu" class="form-control input-sm chat-input" name="usu" placeholder="Usuario" onclick="erasefield()" required/>
                                        <br>
                                        <input type="password" id="pass" class="form-control input-sm chat-input" name="pass" placeholder="password" onkeypress="valida_envia()" required/>
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
                                </div><br><br><br><br><br><br><br><br>
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
                   <!-- <label class="shiro">Descarga app android</label><br>
                    <a href="Avances.apk"><img src="images/android.png" width="150" height="50" alt="" class="img-responsive"></a>-->
                    <hr style="">
                </div>
                <div class="col-sm-3 espacio" align="center">
                    <label class="shiro titulos">Marcas</label><br><br>
                    <a href="https://www.omarcastell.com.mx/"><div class="row espacio-md"><div class="col-sm-5"><label class="shiro">Omar Castell</label><br></div>
                    <div class="col-sm-6"><img src="images/omarf.png" width="105px" style="max-width: 105px;max-height: 40px" height="40px" alt=""></div>
                    </div></a>
                    <div class="row espacio-md"><div class="col-sm-5"><a href="https://dutygearmexico.com/"><label class="shiro">Duty Gear</label><br></a></div>
                    <div class="col-sm-6"><img src="images/dutyf.png" alt="" width="105px" height="40px" style="max-width: 105px;max-height: 40px"></div>
                    </div>
                    <div class="row espacio-md"><div class="col-sm-5"><a href="http://www.trafficshoes.com.mx/#"><label class="shiro">Traffic</label><br></a></div>
                    <div class="col-sm-6"><img src="images/trafficf.png" alt="" width="105px" height="40px" style="max-width: 105px;max-height: 40px"></div>
                    </div>
                    <div class="row espacio-md"><div class="col-sm-5"><a href="http://topsuelas.com/"><label class="shiro">TopSuelas</label><br></a><br></div>
                    <div class="col-sm-6"><img src="images/topf.png" alt="" width="105px" height="40px" style="max-width: 105px;max-height: 40px"></div>
                    </div>
                    <hr>
                </div>
                <div class="col-sm-3 espacio" >
                    <label class="shiro titulos">Redes Sociales </label><br>
                    <br><a href="https://www.facebook.com/Atheticfootwear/" ><img src="images/face.png" class="btn" width="60" height="50"></a>
                    <!--<a href="https:\\www.twitter.com"><img src="images/twi.png" class="btn" width="60" height="50"></a>--><br><br><br><br><br>
                    <hr>
                </div>
                <div class="col-sm-3 espacio">
                    <a class="" href="http://192.168.6.75:86/fichast"><label class="shiro titulos" >Fichas Tecnicas</label></a>
                    <br>
                    <a class="" href="http://192.168.6.8:85/Avances"><label class="shiro titulos" >Avances</label></a>
                    <br><img class="img-responsive jumbismini" src="images/QR.png" width="75px" height="75px"><br>
                </div>
            </div>
        </footer>
    </body>
</html>
<%
}catch(Exception e){}
%>