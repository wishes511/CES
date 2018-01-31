<%-- 
    Document   : index
    Created on : Sep 26, 2017, 2:39:00 PM
    Author     : Michel
--%>
<%@page import="Modelo.Programa"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Calendar"%>
<%@page import="persistencia.Avances"%>
<%
    HttpSession objSesion = request.getSession(true);
    String usuario = (String) objSesion.getAttribute("usuario");
    String tipos = (String) objSesion.getAttribute("tipo");
    String ids = String.valueOf(objSesion.getAttribute("i_d"));
try{

    if (usuario == null) {
       // System.out.println("zom");
        response.sendRedirect("../index.jsp");
    } else {
       // System.out.println("inter");
        if (tipos.equals("INTERMEDIO")) {
        } else {
            response.sendRedirect("../index.jsp");
        }
    }
    Programa pro = new Programa();
    ArrayList<String> lista;
    lista = (ArrayList<String>) objSesion.getAttribute("cap");
    Calendar fecha = Calendar.getInstance();
    int año = fecha.get(Calendar.YEAR);
    int mes = fecha.get(Calendar.MONTH) + 1;
    int dia = fecha.get(Calendar.DAY_OF_MONTH);
    String fechac = dia + "-" + mes + "-" + año;
    if (lista.isEmpty()) {// validar si la lista de el programa esta vacio
        pro.setMes(mes);
    } else {//utilizarel objeto de programa para usar los datos de la consulta
        pro.setPrograma(Integer.parseInt(lista.get(0)));
        pro.setEstilo(Integer.parseInt(lista.get(1)));
        pro.setPares(Integer.parseInt(lista.get(2)));
        pro.setCombinacion(lista.get(3));
        pro.setCorrida(lista.get(4));
        pro.setMes(Integer.parseInt(lista.get(5)));
        pro.setFechae(lista.get(6));
        pro.setStatus(lista.get(7));
        pro.setLote(Integer.parseInt(lista.get(8)));
        pro.setId(Integer.parseInt(lista.get(9)));
    }
    Avances bd = new Avances();
    ArrayList<String> array = array = bd.getcoms();// obtener todas las combinaciones agregadas

%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
    <head>
        <meta http-equiv="refresh" content="500">
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
        <script type="text/javascript" src="../js/bootstrap.min.js"></script>
        <script script language="JavaScript" type="text/javascript" >
            function validar() {
                var valor = $("#programa").val();
                if (!(/^[\d]+$/i.test(valor))) {
                    alert("datos erroneos verifique nuevamente");
                    document.getElementById("programa").value = "";
                    return 0;
                }
            }
            function validarr1() {
                var valor1 = $("#lote").val();
                if (!(/^[\d]+$/i.test(valor1))) {
                    alert("datos erroneos verifique nuevamente");
                    document.forma1.lote.focus();
                    document.getElementById("lote").value = "";
                    return 0;
                }
            }
            function validar2() {
                var valor2 = $("#estilo").val();
                if (!(/^[\d]+$/i.test(valor2))) {
                    alert("datos erroneos verifique nuevamente");
                    document.forma1.estilo.focus();
                    document.getElementById("estilo").value = "";
                    return 0;
                }
            }

            function validar3() {
                var valor3 = $("#pares").val();
                if (!(/^[\d]+$/i.test(valor3))) {
                    alert("datos erroneos verifique nuevamente");
                    document.forma1.pares.focus();
                    document.getElementById("pares").value = "";
                    return 0;
                }
            }
            function validar4() {
                var valor3 = $("#combinacion").val();
                if (!(/^[\a-z\d\ ]+$/i.test(valor3))) {
                    alert("datos erroneos verifique nuevamente");
                    document.forma1.combinacion.focus();
                    document.getElementById("combinacion").value = "";
                    return 0;
                }
            }
            function validar5() {
                var valor4 = $("#corrida").val();
                if (!(/^[\d]|\d{2}$/i.test(valor4))) {
                    alert("datos erroneos verifique nuevamente");
                    document.forma1.corrida.focus();
                    document.getElementById("corrida").value = "";
                    return 0;
                }
            }
            function validarfecha() {
                var valor = $("#fechae").val();
                if (!(/^\d{4}\-\d{2}|\d{1}\-\d{2}$/i.test(valor))) {
                    alert("fecha invalida invalido");
                    document.forma.fechae.focus();
                    document.getElementById("fechae").value = "<%=fechac%>";
                    return 0;
                }
            }
            $(document).ready(function () {
                document.forma1.programa.focus();
            });
        </script>
        <title>Capturista</title>
    </head>
    <body class="body1">
        <div class="container-fluid">
            <nav class="navbar navbar-default">
                <ul class="nav navbar-nav nav-pills">
                    <li class="active"><a class="navbar-brand" href="index.jsp"><img src="../images/home.png" class="" width="25"></a></li>

                    <li class="ln"><a href="buscalote.jsp">Reporte Programa</a></li>
                    <li class="ln"><a href="avancegeneral.jsp">Avance General</a></li>
                    <li class="ln"><a href="../Cierresesion">Salir</a></li>
                </ul>
                <div style="float:right" class="nav nav-pills">
                    <li > <label class="ln">Online: <%=usuario%></label></li>
                </div>
            </nav>
            <div class="container" align="center" >
                <div class="espacio-md-down"></div>
                <div class="row espacios-lg fondos jumbis ">
                    <div style="position: absolute;float: left;font: black" class="stealth">
                        <input type="text" value="<%=pro.getId()%>" name="idprod" id="idprod" class="ln" >
                    </div>

                    <!--<div style="position: absolute;float: left">
                         
                         <label class="ln">buscar</label><input type="radio" value="buscar" name="group1" id="g1" onchange="presalto1()">
                     </div>--> 
                    <label class="ln-ln fuera">Captura de Avances de Produccion </label><br>
                    <br>
                    <form name="forma1" method="post" action="">
                        <div class="row ">
                            <div class="col-sm-2">
                                <label class="ln">Programa</label><input class="form-control" type="text" name="programa" id="programa" onchange="saltoa()" maxlength="2" value="<%=pro.getPrograma()%>">
                            </div>
                            <div class="col-sm-2" id="getsmes">             
                                <label class="ln">Mes</label><br>
                                <select id="mes" name="mes" onclick="presalto1()" class="form-control" value="<%=pro.getMes()%>">
                                    <%
                                        for (int i = 0; i <= 12; i++) {
                                            if (i == pro.getMes()) {
                                                out.print("<option selected>" + i + "</option>");
                                            } else {
                                                out.print("<option>" + i + "</option>");
                                            }

                                        }
                                    %>
                                </select>
                            </div>  
                        </div>
                        <div class="row"><br>
                            <div class="col-sm-2">
                                <label class="ln">Lote</label><input class="form-control" type="text" name="lote" id="lote" onchange="salto()" maxlength="6" required value="<%=pro.getLote()%>">
                            </div>
                            <div class="col-sm-2">
                                <label class="ln">Estilo</label><input class="form-control" type="text" name="estilo" id="estilo" onchange="salto1()" maxlength="6" required value="<%=pro.getEstilo()%>">
                            </div>
                            <div class="col-sm-2">
                                <label class="ln"># de Pares</label><input class="form-control" type="text" name="pares" id="pares" onchange="salto2()" maxlength="5" required value="<%=pro.getPares()%>">
                            </div>
                            <div class="col-sm-2">
                                <div class="row">
                                    <label class="ln">Corrida</label>
                                </div>
                                <div class="row">
                                    <div class="col-xs-8">
                                        <input type="text" class="form-control" id="corrida" name="corrida" onchange="salto7()" maxlength="2" required value="<%=pro.getCorrida()%>"><br>
                                    </div>
                                    <div class="col-xs-4">
                                        <select class="form-control" id="corri" name="corri" onclick="salto3()">
                                            <option value="21">21/92(22/24)</option>
                                            <option value="31">31/94(25/30)</option>
                                            <option value="32">32/96(30/33)</option>
                                            <option value="24">24/98(23/27)</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="row">
                                    <label class="ln">Combinacion</label>
                                </div>
                                <div class="row">
                                    <div class="col-xs-9">
                                        <input class="form-control" type="text" name="combinacion" id="combinacion" onchange="salto4()" maxlength="50" required value="<%=pro.getCombinacion()%>"><br>
                                    </div>
                                    <div class="col-xs-3">
                                        <select class="form-control" id="comb" name="comb" onclick="combi()">
                                            <%
                                                for (int i = 0; i < array.size(); i++) {//llenado de 
                                                    out.print("<option >" + array.get(i) + "</option>");
                                                }
                                            %>
                                        </select>
                                    </div>
                                </div>
                            </div>

                        </div> <div class="row"></div>
                    </form>
                    <div align="center"> <br><button class="btn btn-danger" id="boton" onclick="prog()">Agregar Lote</button>
                        <button class="btn btn-warning" id="boton1" onclick="modiprog()">Modificar</button>
                        <button class="btn btn-success" id="boton2" onclick="searchlote()">Buscar</button>
                        <button class="btn btn-success" id="boton3" onclick="searchlote1()">Historial por lote</button>
                        <button class="btn btn-success" id="boton4" onclick="cleanfields()">Limpiar campos</button>
                    </div>
                </div>
            </div><br>
            <div class="endchapter jumbis" id="msjresponse" align="center">
                <label>Captura Completada</label>
            </div>
            <div class="endchapter jumbis" id="msjresponse1" align="center">
                <label>Modificacion Completada</label>
            </div>
            <div id="respuesta" class="row deep-sm">

            </div>  
        </div>
        <script script language="JavaScript" type="text/javascript" >
            function combi() {
                var combi = $('#comb').val();
                document.getElementById("combinacion").value = combi;
                document.getElementById("boton").focus();
            }
            function presalto() {
                document.forma1.programa.focus();
            }
            function presalto1() {
                document.forma1.lote.focus();
            }
            function saltoa() {
                document.forma1.mes.focus();
               // validar();
            }
            function salto() {
                document.forma1.estilo.focus();
                validarr1();
            }
            function salto1() {
                document.forma1.pares.focus();
                validar2();
            }
            function salto2() {
                document.forma1.corrida.focus();
                validar3();
            }
            function salto3() {
                document.getElementById("corrida").value = $('#corri').val();
                document.forma1.corrida.focus();
            }
            function salto4() {
                document.forma1.mes.focus();
                validar4();
            }
            function salto5() {
                document.forma1.fechae.focus();
            }
            function salto6() {
                document.forma1.codigo.focus();
                validarfecha();
            }
            function salto7() {
                document.forma1.comb.focus();
                validar5();
            }
            function prog() {
                if (confirm("¿Realmente desea guardar nueva Lote?")) {
                    var programa = $('#programa').val();
                    var lote = $('#lote').val();
                    var estilo = $('#estilo').val();
                    var pares = $('#pares').val();
                    var corrida = $('#corrida').val();
                    var combinacion = $('#combinacion').val();
                    var mes = $('#mes').val();
                    var fechae = $('#fechae').val();
                    var uso = "nuevo";

                    $.ajax({
                        type: 'post',
                        data: {f: programa, f1: lote, f2: estilo, f3: pares, f4: corrida, f5: combinacion, f6: mes, f7: fechae, uso: uso},
                        url: '../Getregs',
                        success: function (result) {
                            document.getElementById("programa").value = "";
                            document.getElementById("lote").value = "";
                            document.getElementById("estilo").value = "";
                            document.getElementById("pares").value = "";
                            document.getElementById("corrida").value = "";
                            document.getElementById("combinacion").value = "";
                           uso="combupdate";
                                        $.ajax({
                                        type: 'post',
                                        data: {f: programa, f1: lote, f2: estilo, f3: pares, f4: corrida, f5: combinacion, f6: mes, f7: fechae, uso: uso},
                                        url: '../Getregs',
                                        success: function (result1) {
                                            $('#comb').html(result1);
                                        }
                                        });
                            $('#respuesta').html(result);
                            document.forma1.programa.focus();
                        }
                    });
                }
            }
            function searchlote1() {
                var programa = $('#programa').val();
                var lote = $('#lote').val();
                var estilo = $('#estilo').val();
                var pares = $('#pares').val();
                var corrida = $('#corrida').val();
                var combinacion = $('#combinacion').val();
                var mes = $('#mes').val();

                var uso = "buscar";
                $.ajax({
                    type: 'post',
                    data: {f: programa, f1: lote, f2: estilo, f3: pares, f4: corrida, f5: combinacion, f6: mes, uso: uso},
                    url: '../Getregs',
                    success: function (result) {
                        $('#respuesta').html(result);
                        
                        document.getElementById("getsmes").innerHTML=mess;
                        // document.location.reload();
                        //document.forma1.lote.focus();
                    }
                });
            }
            function searchlote() {
                var programa = $('#programa').val();
                var lote = $('#lote').val();
                var estilo = $('#estilo').val();
                var pares = $('#pares').val();
                var corrida = $('#corrida').val();
                var combinacion = $('#combinacion').val();
                var mes = $('#mes').val();

                var uso = "buscar";
                $.ajax({
                    type: 'post',
                    data: {f: programa, f1: lote, f2: estilo, f3: pares, f4: corrida, f5: combinacion, f6: mes, uso: uso},
                    url: '../Getregs',
                    success: function (result) {
                        document.location.reload();
                    }
                });
            }
            function modiprog() {
                if (confirm("¿Realmente desea Modificar el Programa?")) {
                    var programa = $('#programa').val();
                    var lote = $('#lote').val();
                    var estilo = $('#estilo').val();
                    var pares = $('#pares').val();
                    var corrida = $('#corrida').val();
                    var combinacion = $('#combinacion').val();
                    var mes = $('#mes').val();
                    var fechae = $('#fechae').val();
                    var uso = "modificar";
                    var idprod = $('#idprod').val();
                    $.ajax({
                        type: 'post',
                        data: {f: programa, f1: lote, f2: estilo, f3: pares, f4: corrida, f5: combinacion, f6: mes, f7: fechae, uso: uso, idprod: idprod},
                        url: '../Getregs',
                        success: function (result) {
                            document.getElementById("programa").value = "";
                            document.getElementById("lote").value = "";
                            document.getElementById("estilo").value = "";
                            document.getElementById("pares").value = "";
                            document.getElementById("corrida").value = "";
                            document.getElementById("combinacion").value = "";
                            $('#msjresponse1').fadeIn(1000);
                            $('#msjresponse1').fadeOut(300);

                            $('#respuesta').html(result);
                            document.forma1.programa.focus();
                        }
                    });
                }
            }
            function cleanfields() {
                document.getElementById("programa").value = "";
                document.getElementById("lote").value = "";
                document.getElementById("estilo").value = "";
                document.getElementById("pares").value = "";
                document.getElementById("corrida").value = "";
                document.getElementById("combinacion").value = "";
                document.getElementById("programa").focus();
                document.getElementById("mes").value=<%=mes%>;
            }
            function searchprog() {
                var valida = validar();
                if (valida == 0) {
                    document.getElementById("programa").focus();
                } else {
                    var programa = $('#programa').val();
                    var lote = $('#lote').val();
                    var estilo = $('#estilo').val();
                    var pares = $('#pares').val();
                    var corrida = $('#corrida').val();
                    var combinacion = $('#combinacion').val();
                    var mes = $('#mes').val();
                    var uso = "buscarp";
                    $.ajax({
                        type: 'post',
                        data: {f: programa, f1: lote, f2: estilo, f3: pares, f4: corrida, f5: combinacion, f6: mes, uso: uso},
                        url: '../Getregs',
                        success: function (result) {
                            if (result == "ok") {//compara la respuesta que obtuvo con la estatica
                                searchprogm(programa);
                            } else {
                                var d = new Date();
                                var mess='<label class="ln">Mes</label><br><select id="mes" name="mes" onclick="presalto1()" class="form-control">';
                                for(var i =1;i<= 12;i++){//llenar de nuevo el select del mes
                                    if(i==(d.getMonth()+1)){
                                        mess +=' <option selected>'+i+'</option>'; 
                                    }else{
                                        mess +=' <option>'+i+'</option>';
                                    }
                                }
                                mess +=' </select>'; 
                                document.getElementById("programa").value=programa;
                                document.getElementById("getsmes").innerHTML=mess;
                                document.getElementById("mes").disabled = false;
                                document.getElementById("mes").focus();
                            }
                        }
                    });
                }
            }
            function searchprogm(programa) {
                var lote = $('#lote').val();
                var estilo = $('#estilo').val();
                var pares = $('#pares').val();
                var corrida = $('#corrida').val();
                var combinacion = $('#combinacion').val();
                var mes = $('#mes').val();
                var uso = "buscarpm";
                $.ajax({
                    type: 'post',
                    data: {f: programa, f1: lote, f2: estilo, f3: pares, f4: corrida, f5: combinacion, f6: mes, uso: uso},
                    url: '../Getregs',
                    success: function (result) {
                        $('#getsmes').html(result);
                        document.getElementById("mes").disabled = true;
                        document.getElementById("lote").focus();
                    }
                });
            }
        </script>
    </body>
</html>
<%
}catch(Exception e){
out.println("<script type=\"text/javascript\">");
                    out.println("location='../index.jsp';");
                    out.println("</script>");
}
%>