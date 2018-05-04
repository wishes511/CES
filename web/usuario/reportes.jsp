<%-- 
    Document   : reportes
    Created on : Mar 15, 2018, 11:16:48 AM
    Author     : mich
--%>
<%@page import="java.util.Calendar"%>
<% 
     HttpSession objSesion = request.getSession(false);
    boolean estado;
    String usuario = (String) objSesion.getAttribute("usuario");
   try{
       String tipos = (String) objSesion.getAttribute("tipo");
    String ids = String.valueOf(objSesion.getAttribute("i_d"));
    if (usuario != null && tipos != null && (tipos.equals("ADMIN")||tipos.equals("USUARIO")) ) {
       // out.println(usuario);
    } else {
        response.sendRedirect("../index.jsp");
    } 
Calendar fecha = Calendar.getInstance();
int año = fecha.get(Calendar.YEAR);
        String mes =String.valueOf(fecha.get(Calendar.MONTH) + 1);
        String dia = String.valueOf(fecha.get(Calendar.DAY_OF_MONTH));
        String sdia="";
        if(dia.length()==1){
        dia="0"+dia;
        }
        if(mes.length()==1){
            mes= "0"+mes;
        }
        String fechac =dia+"-"+mes+"-"+año;
%>
<!DOCTYPE html>
<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0" />
        <title>CES/<%=tipos%></title>
        <link rel="shortcut icon" type="image/x-icon" href="../images/icono.png" />
        <link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="../css/dashboard.css">
        <link rel="stylesheet" type="text/css" href="../css/fondos.css">
        <link rel="stylesheet" type="text/css" href="../css/dhtmlgoodies_calendar.css" media="screen">
        <script type="text/javascript" src="../js/jquery-3.1.1.min.js"></script>
        <script type="text/javascript" src="../js/tether.min.js"></script>
        <script type="text/javascript" src="../js/bootstrap.min.js"></script>
        <script type="text/javascript" src="../js/jquery-3.1.1.min.js"></script>
        <script type="text/javascript" src="../js/dhtmlgoodies_calendar.js"></script>
        <script type="text/javascript" src="../js/jquery.tablesorter.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                document.getElementById("f1").focus();
            });
            $(document).ready(function () {
                $("#tablasor").tablesorter();
            });
        </script>
    </head>
    <body >
        <div class="">
      <nav class="navbar navbar-toggleable-md navbar-inverse fixed-top bg-inverse">
      <button class="navbar-toggler navbar-toggler-right hidden-lg-up" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
           <a class="navbar-brand" ><img src="../images/AF.png;base64" width="170px" height="50"></a>
      <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
            <% 
                if(tipos.equals("ADMIN")){
                out.print("<a class=\"nav-link\" href=index.jsp>Proveedores</a>");
                out.print("<a class=\"nav-link\" href=usuarios.jsp>Usuarios</a>"
                        + "<a class=\"nav-link active\" href=\"reportes.jsp\">Reportes<span class=\"sr-only\">(current)</span></a>");
                }else{
                    if(tipos.equals("VIGILANTE")){
                    out.print("<a class=\"nav-link\" href=\"index.jsp\">Entradas / Salidas<span class=\"sr-only\">(current)</span></a>");
                    }
                out.print("<a class=\"nav-link active\">Reportes<span class=\"sr-only\">(current)</span></a>");
                //pendiente al usuario
                }
            %>
        </ul>
        <div class="row">
            <!--<input class="form-control mr-sm-2" type="text" placeholder="Search">-->
            <a href="../Logout"><button class="btn btn-outline-secondary my-2 my-sm-0" >Cerrar Sesion</button></a>
        </div>
      </div>
    </nav> 
            </div>
            <div class="container-fluid " align="center">
                <div class="row">
        <%
        if(tipos.equals("ADMIN") || tipos.equals("USUARIO")|| tipos.equals("VIGILANTE")){
        out.print("<main class=\"col-sm-12 pt-4\" id=main_prov>\n" +
"          <section class=\"row text-center offset-sm-3\" id=section_prov align=center>\n" +
"            <div class=\"col-6 col-sm-3 placeholder\">\n" +
"              <h4>Inicial</h4>\n" +
"              <input type=\"text\" class=\"form-control\" id=\"f1\" onkeypress=ver_regs() value="+fechac+">\n" +
"            </div>\n" +
"            <div class=\"col-6 col-sm-1 placeholder\">\n" +
"             <h4>&nbsp</h4> <button class=\"btn btn-outline-secondary\" onclick=\"displayCalendar(document.getElementById('f1'), 'dd-mm-yyyy', this)\">...</button>\n" +
"            </div>\n" +          
"              <div class=\"col-6 col-sm-3 placeholder\">\n" +
"              <h4>Final</h4>\n" +                
"                  <input type=\"input\" class=form-control name=\"f2\" onkeypress=ver_regs() id=\"f2\" value="+fechac+">\n" +
"              </div>\n" +
"            <div class=\"col-6 col-sm-1 placeholder\">\n" +
"             <h4>&nbsp</h4> <button class=\"btn btn-outline-secondary\" onclick=\"displayCalendar(document.getElementById('f2'), 'dd-mm-yyyy', this)\">...</button>\n" +
"            </div>\n" +                              
"          </section>\n" +
"          <h4 class=>Filtros</h4>     \n"+        
"          <section class=\"row text-center fondos redondeado\" id=section_prov align=center >\n" +
"            <div class=\"col-6 col-sm-2 placeholder\">\n" +
"              <br><input type=\"text\" class=\"form-control\" id=\"nombre\" placeholder=Nombre onkeypress=ver_regs()>\n" +
"            </div>\n" +
"            <div class=\"col-6 col-sm-2 placeholder\">\n" +
"            <br><input type=\"text\" class=\"form-control\" id=\"area\" placeholder=\"Area\" onkeypress=ver_regs()>\n" +
"            </div>\n" +          
"              <div class=\"col-6 col-sm-2 placeholder\">\n" +        
"                 <br><input type=\"input\" class=form-control name=\"nombre_dep\" id=\"nombre_dep\" placeholder=\"Nombre del depar.\" onkeypress=ver_regs()>\n" +
"              </div>\n" +
"            <div class=\"col-6 col-sm-2 placeholder\">\n" +
"             <br><select class=form-control id=mov onchange=ver_regs()><option value=>Ambas</option><option value=E>Entrada</option><option value=S>Salida</option></select>\n" +
"            </div>\n" +
"            <div class=\"col-6 col-sm-2 placeholder\">\n" +
"             <br><input type=\"text\" class=\"form-control\" id=\"destino\" placeholder=\"Nombre a quien visito\" onkeypress=ver_regs()>\n" +
"            </div>\n" +         
"            <div class=\"col-6 col-sm-2 placeholder\">\n" +
"             <br><select class=form-control id=transporte onchange=ver_regs()><option value=></option><option value=\"SN\">SIN TRANSPORTE</option><option value=\"AUTOMOVIL\">AUTOMOVIL</option><option value=\"MOTOCICLETA\">MOTOCICLETA</option><option value=\"BICICLETA\">BICICLETA</option></select>\n" +
"            </div>\n" + 
"          </section>\n" +
"           <section class=\"row text-center espacio-xs-down\" align=center>\n" +  
"              <div class=\"col-10 col-sm-8 offset-sm-2 placeholder\">\n" +                              
"                  <br><button type=\"input\" class=btn  onclick=ver_regs()>Buscar Registros</button>\n" +
"              <button type=\"input\" class=\"btn btn-success\"  onclick=reporte()>Reporte Condensado</button>\n" +
"                  <button type=\"input\" class=\"btn btn-success\"  onclick=reporte_det()>Reporte Detallado</button>\n" +        
"              </div>\n" +  
"          </section>\n" +
"          <div id=table_prov class=\"table-responsive\">\n" +
"            <table id=tablasor class=\"table table-hover tablesorter\">\n" +
"              <thead>\n" +
"                <tr align=\"center\" style=color:white;background:black class=redondeado>\n" +
"                  <td>Fecha</td>\n" +
"                  <td>Nombre</td>\n" +
"                  <td>Proveedor</td>\n" +
"                  <td>Procedencia</td>\n" +
"                  <td>Area</td>\n" +
"                  <td>Departamento</td>\n" +
"                  <td>Hora Inicial</td>\n" +
"                  <td>Hora final</td>\n" +

"                  <td>Visita</td>\n" +
"                  <td>Duracion  </td>\n" +        
"                </tr>\n" +
"              </thead>\n" +
"              <tbody id=\"body_table\" class=body-table>\n" +
"              </tbody>\n" +
"            </table>\n" +
"          </div>\n" +
"        </main>");
            }
        %>
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
          <form method="post" action="">
          <div align="center"><button class="btn btn-success">Iniciar</button></div>
          </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
        <script type="text/javascript" src="../js/functions_js_1.js"></script>
    </body>
</html>
<%
  }catch(Exception e){
System.out.println(e);

out.println("<script type=\"text/javascript\">");
                    out.println("location='../index.jsp';");
                    out.println("</script>");
        }
%>