<%-- 
    Document   : depcosto
    Created on : Sep 15, 2017, 9:51:16 AM
    Author     : gateway1
--%>

<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="persistencia.Avances"%>
<%@page import="net.sf.jasperreports.engine.JasperRunManager"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <% HttpSession objSesion = request.getSession(true);
//i_d
            boolean estado;
            String usuario = (String) objSesion.getAttribute("usuario");
            String tipos = (String) objSesion.getAttribute("tipo");
            String ids = String.valueOf(objSesion.getAttribute("i_d"));

            if (usuario != null && tipos != null && tipos.equals("ADMIN")) {

            } else {
                response.sendRedirect("../index.jsp");
            }
            String f1 = request.getParameter("f1");
            String f2 = request.getParameter("f2");
            String maq= request.getParameter("maq").toUpperCase();
            String maquila=maq;
            String patt = "\\d{1,2}\\-\\d{1,2}\\-\\d{4}";
            Pattern pat = Pattern.compile(patt);
            Matcher match = pat.matcher(f1);
            if (match.matches()) {
                 Avances db = new Avances();
                   db.abrir();
               try { 
                    System.out.println(db.getConexion());
                    
                if(maq.equals("TODOS")){// reporte para elegir todos los pares
                 File reportfile = new File(application.getRealPath("usuario/avancespares.jasper"));
                    Map para = new HashMap();
                    System.out.println(db.getConexion() + "/" + f1 + "/+/" + f2);
                    para.put("f1", new String(f1));
                    para.put("f2", new String(f2));
                    byte[] bytes = JasperRunManager.runReportToPdf(reportfile.getPath(), para, db.getConexion());
                    response.setContentType("application/pdf");
                    response.setContentLength(bytes.length);
                    ServletOutputStream outputstream = response.getOutputStream();
                    outputstream.write(bytes, 0, bytes.length);
                    outputstream.flush();
                    outputstream.close();
                }else{// reporte para repote enespecifico
                 File reportfile = new File(application.getRealPath("usuario/reports/avancespares.jasper"));
                    Map para = new HashMap();
                    System.out.println(db.getConexion() + "/" + f1 + "/+/" + f2+"/+ maq/"+maq.charAt(0));
                    maq=String.valueOf(maq.charAt(0));
                    para.put("f1", new String(f1));
                    para.put("f2", new String(f2));
                    para.put("maq",new String(maq));
                    para.put("maquila",new String(maquila));
                    byte[] bytes = JasperRunManager.runReportToPdf(reportfile.getPath(), para, db.getConexion());
                    response.setContentType("application/pdf");
                    response.setContentLength(bytes.length);
                    ServletOutputStream outputstream = response.getOutputStream();
                    outputstream.write(bytes, 0, bytes.length);
                    outputstream.flush();
                    outputstream.close();
                }
                
                   
                } catch (Exception e) {
                    e.printStackTrace();
                    
                  //  response.sendRedirect("verpares.jsp");
                    
                } finally {
                    if (db.getConexion() != null) {
                        db.cerrar();
                       // response.sendRedirect("verpares.jsp");
                    }
                }
            } else {
                System.out.println("NO ES FECHA");
               // response.sendRedirect("verpares.jsp");
            }

        %>
    </body>

</html>
