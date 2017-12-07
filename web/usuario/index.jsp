<%-- 
    Document   : index
    Created on : Sep 26, 2017, 2:39:00 PM
    Author     : gateway1
--%>
<%@page import="java.util.Calendar"%>
<%@page import="Modelo.Programa"%>
<%@page import="java.util.ArrayList"%>
<%@page import="persistencia.Avances"%>
<% HttpSession objSesion = request.getSession(true);
//i_d
    boolean estado;
    String usuario = (String) objSesion.getAttribute("usuario");
    String tipos = (String) objSesion.getAttribute("tipo");
    String ids = String.valueOf(objSesion.getAttribute("i_d"));

    //out.print(carrito.size());
    // out.println("" + tipos+"/"+ids);
    if (usuario != null && tipos != null) {
        if(tipos.equals("USUARIO")){
        }else
            response.sendRedirect("../index.jsp");
    } else {
        response.sendRedirect("../index.jsp");
    }
     Calendar fecha = Calendar.getInstance();
    int año = fecha.get(Calendar.YEAR);
    int mes = fecha.get(Calendar.MONTH) + 1;
    Avances bd = new Avances();
    // estado = bd.alerta();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
    <head>
        
        <meta http-equiv="refresh" content="800">
        <link rel="icon"  href="../images/aff.png" sizes="32x32"/>
        <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
        <link rel='stylesheet' type="text/css" href="../css/bootstrap.min.css">
        <link rel='stylesheet' type="text/css" href="../css/responsive.css">
        <link rel="stylesheet" type="text/css" href="../css/fondos.css">
        <link rel="stylesheet" type="text/css" href="../css/loginn.css">
        <link rel="stylesheet" type="text/css" href="../css/letras.css">
        <script type="text/javascript" src="../js/bootstrap.js"></script>
        <script type="text/javascript" src="../js/bootstrap.min.js"></script>
        <script type="text/javascript" src="../js/jquery-3.1.1.min.js"></script>
        <script type="text/javascript" src="http://librosweb.es/ejemplos/bootstrap_3/js/bootstrap.min.js"></script>
        <script>
           
            $(document).ready(function () {
                document.getElementById("lote").focus();
            });
        </script>
        <title>Home</title>
    </head>
    <body class="body1">
        <div class="container-fluid">
                <nav class="navbar navbar-default">
                
                <ul class="nav navbar-nav nav-pills">
                    <a class="navbar-brand" href=""><img src="../images/home.png" class="" width="25"></a>
                    <li class="ln"><a href="lote_detenido.jsp">Lotes detenidos</a></li>
                    <li class="ln"><a href="verpares.jsp">Ver Pares</a></li>                 
                    <li class="ln"><a href="../Cierresesion">Salir</a></li>
                </ul>
                    <div style="float:right" class="nav nav-pills">
                    <li > <label class="ln">Online: <%=usuario%></label></li>
                </div>
            </nav>
            <div class="container" align="center">
                <div class="espacio-md-up"></div>
                <div class="row espacios-lg fondos jumbis" align="center">
                    <label class="ln-ln fuera">Busquedas</label><br>
                    <div class="row" align="center">
                        <div class="col-sm-offset-5 col-sm-2">
                                <label class="ln">Lote</label><input class="form-control" type="text" name="lote" id="lote" onchange="jumpto()" maxlength="6" value=""><br>
                        </div>
                        <div class="col-sm-offset-5 col-sm-2">
                                <select id="mes" name="mes" onclick="saltok()" class="form-control" value="">
                                    <%
                                        for (int i = 1; i <= 12; i++) {
                                            if (i == mes) {
                                                out.print("<option selected>" + i + "</option>");
                                            } else {
                                                out.print("<option>" + i + "</option>");
                                            }

                                        }
                                    %>
                                </select>
                        </div>
                    </div>
                    <br><div class="row">
                        <div align="center"> <br><button class="btn btn-success ln" id="boton" onclick="nprograma()">Aceptar</button> 
                            <button class="btn btn-success ln" id="boton2" onclick="busqueda()">Historial de Lotes</button>
                        <button class="btn btn-success ln" id="boton3" onclick="lotes()">Generar reporte</button></div>
                         
                    </div>
                </div>
                   <div id="respuesta" class="row deep-sm"></div>
                   
            </div><br>
        </div> 
    <script>
       function jumpto(){
           document.getElementById("mes").focus();
       }
        function nprograma() {
            var lote ="";
            lote=$('#lote').val();
        var mes =$('#mes').val();
           if(lote===""){
               
           }else{
               if( !(/^[\d]+$/i.test(lote))){
                   //alert('Verifique Datos ingresados');
                   return 0;
               }else {
               
               var uso = "check";
            $.ajax({
                type: 'post',
                data: {f1: lote, uso: uso,mes:mes},
                url: '../Getregslote',
                success: function (result) {
                    $('#respuesta').html(result);
                   
                }
            });
                }    
           }
        }
        function lotes(){
            var prog =$('#lote').val();
            var mes =$('#mes').val();
            window.location="programadetallado.jsp?prog="+prog+"&mes="+mes;
        }
        function busqueda(){
             var programa = '0';
                var lote = $('#lote').val();
                var estilo = '0';
                var pares = '0';
                var corrida = '0';
                var combinacion = '0';
                var mes =$("#mes").val();

                var uso = "buscar";
                $.ajax({
                    type: 'post',
                    data: {f: programa, f1: lote, f2: estilo, f3: pares, f4: corrida, f5: combinacion, f6: mes, uso: uso},
                    url: '../Getregs',
                    success: function (result) {
                        $('#respuesta').html(result);
                        // document.location.reload();
                        //document.forma1.lote.focus();
                    }
                });
            }
            
        function saltok(){
            document.getElementById("boton").focus();
            
        }
    </script>
</body>
</html>
