<%-- 
    Document   : depcosto
    Created on : Sep 15, 2017, 9:51:16 AM
    Author     : gateway1
--%>

<%@page import="persistencia.CES_movs"%>
<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="net.sf.jasperreports.engine.JasperRunManager"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CES</title>  
    </head>
    <body>

        <% HttpSession objSesion = request.getSession(true);
//i_d 
            boolean estado;
            String usuario = (String) objSesion.getAttribute("usuario");
            String tipos = (String) objSesion.getAttribute("tipo");
            String ids = String.valueOf(objSesion.getAttribute("i_d"));

            if (usuario != null && tipos != null && (tipos.equals("USUARIO")|| tipos.equals("ADMIN") || tipos.equals("VIGILANTE"))) {

            } else {
                response.sendRedirect("../index.jsp");
            }
            String f1 = request.getParameter("f1");
            String f2 = request.getParameter("f2");
            String nombre = request.getParameter("nombre");
            String area = request.getParameter("area");
            String depa = request.getParameter("depa");
            String tipo = request.getParameter("tipo");
            String patt = "\\d{1,2}\\-\\d{1,2}\\-\\d{4}";
            Pattern pat = Pattern.compile(patt);
            Matcher match = pat.matcher(f1);
            if (match.matches()) {
                 CES_movs db = new CES_movs();
                   db.abrir();
               try { 
              // reporte detallado de un progama
                 File reportfile = new File(application.getRealPath("usuario/titulos.jasper"));
                    Map para = new HashMap();
                    para.put("f1", new String(f1));
                    para.put("f2", new String(f2));
                    para.put("nombre",new String (nombre));
                    para.put("area",new String (area));
                    para.put("depa",new String (depa));
                    para.put("tipo",new String (tipo));
                    byte[] bytes = JasperRunManager.runReportToPdf(reportfile.getPath(), para, db.getconexion());
                    response.setContentType("application/pdf");
                    response.setContentLength(bytes.length);
                    ServletOutputStream outputstream = response.getOutputStream();
                    outputstream.write(bytes, 0, bytes.length);
                    outputstream.flush();
                    outputstream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    
                  //  response.sendRedirect("verpares.jsp");
                    
                } finally {
                    if (db.getconexion() != null) {
                        db.cerrar();
                       // response.sendRedirect("verpares.jsp");
                    }
                }
            } else {
                System.out.println("NO ES FECHA");
                out.print("<script>window.location='lote_detenido.jsp'</script>");
               // response.sendRedirect("index.jsp");
            }

        %>
    </body>

</html>
