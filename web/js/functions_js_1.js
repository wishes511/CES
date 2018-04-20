/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function autosearch_provs(){
                var proveedor =$('#search_prov').val();
                var prov_activo=$('input:radio[name=gg]:checked').val();
                var uso="buscar";
               $.ajax({
                    type: 'post',
                    data: {uso: uso,proveedor:proveedor,activo:prov_activo},
                    url: '../Getdata_user',
                    success: function (result) {
                        $('#body_table').html(result);
                    }
                });                
} // listo usuario
function delete_prov(id){ 
                var uso="delete";
                $.ajax({
                    type: 'post',
                    data: {uso: uso,id:id},
                    url: '../Getdata_prov',
                    success: function (result) {
                        // document.form2.uso.focus();
                        alert(result);
                       
                      }
                });                
}  // por ahora no aplica
function mod_prov(id){
                var uso="modificar";
                $.ajax({
                    type: 'post',
                    data: {uso: uso,id:id},
                    url: '../Getdata_prov',
                    success: function (result) {
                        // document.form2.uso.focus();
                        //alert("-"+result+"-");
                        $('#body_table').html(result);
                        document.getElementById("codigo").value = "";
                    }
                });                
}
function down_prov(id){
                var uso="baja";
                $.ajax({
                    type: 'post',
                    data: {uso: uso,id:id},
                    url: '../Getdata_user',
                    success: function (result) {
                        alert(result);
                        document.getElementById('search_prov').focus();                    
                    }
                });                
} //listo
function up_prov(id){
                var uso="alta";
                $.ajax({
                    type: 'post',
                    data: {uso: uso,id:id},
                    url: '../Getdata_user',
                    success: function (result) {
                        alert(result);
                        document.getElementById('search_prov').focus();                    
                    }
                });                
} //
function searchactivo_id(){
                var pactivo =$('#proveedor').val();
                var uso="buscar";
                $.ajax({
                    type: 'post',
                    data: {uso: uso,activo:pactivo},
                    url: 'Getdata_prov_personal',
                    success: function (result) {
                        $('#p_activos').html(result);
                    }
                });
            }
function saltop(){
    var auto=document.getElementById('autorizada').value;
    document.getElementById('p_activos_n').value=auto;
    document.getElementById('depa').focus();
}            
function nuevo_prov_data(){
                var pactivo =$('#prov_new').val();
                var uso="nuevo";
                $.ajax({
                    type: 'post',
                    data: {uso: uso,activo:pactivo},
                    url: '../Getdata_user',
                    success: function (result) {
                        $('#section_prov').html(result);
                        $('#table_prov').html("");
                        llenabarra_np();
                        document.getElementById('prov_new1').focus();
                    }
                });
            }
function nuevo_prov(){
                var user =$('#prov_new1').val();
                var depa =$('#depa').val();
                var tipo=$('#tipo_usuario').val();
                var empresa="";
                var pass="";
                var tipo_cod="";
                var nametipo = document.getElementById('tipo_usuario').options[document.getElementById('tipo_usuario').selectedIndex].text;
                if(nametipo == "ADMIN" || nametipo=="VIGILANTE"){
                    pass=$('#pass_u').val();
                    tipo_cod="8";
                    empresa="ATH";
                }else if(nametipo=="MAQUILA"){
                    pass="123";
                    tipo_cod="3";
                    empresa=$('#maquilas').val();
                }else{
                pass="123";
                tipo_cod="8";  
                empresa="ATH";
                }
                var uso="nuevo_row";
                if(user=="" || depa=="" || tipo== "" || pass==""){
                    document.getElementById('prov_new1').focus();
                    $('#response_nprov').html("<br>El campo de proveedores no debe ir vacio");
                }else{
                 $.ajax({
                    type: 'post',
                    data: {uso: uso,user:user,depa:depa,tipo:tipo,pass:pass,tipo_cod:tipo_cod,empresa:empresa},
                    url: '../Getdata_user',
                    success: function (result) {
                        $('#response_nprov').html(result);
                        document.getElementById('prov_new1').value="";
                    }
                });  
                }  
}
function display_pass(){
                var tipo = document.getElementById('tipo_usuario').options[document.getElementById('tipo_usuario').selectedIndex].text;
                if(tipo=="ADMIN" || tipo=="VIGILANTE"){
                    document.getElementById("field_pass").innerHTML="<input type=password id=pass_u class=form-control onchange=salto_btn placeholder='contrasena'>";
                    document.getElementById("pass_u").focus();
                }else if(tipo=="MAQUILA"){ 
                    document.getElementById("field_pass").innerHTML="<input type=text id=maquilas class=form-control onchange=salto_btn placeholder='Nombre Maquila'>";
                    document.getElementById("maquilas").focus();
                }else{
                    document.getElementById("field_pass").innerHTML="<label></label>";
                    document.getElementById("btn_nuevo_u").focus();
                    
                }
            }
function salto_btn(){
    document.getElementById("btn_nuevo_u").focus;
}
function llenabarra_np(){
    document.getElementById('barra_nav').innerHTML="<li class=\"nav-item\">\n" +
"              <a class=\"nav-link\" href=\"\">Busqueda </a>\n" +
"            </li>\n" +
"            <li class=\"nav-item\">\n" +
"              <a class=\"nav-link active\" onclick=nuevo_prov_data()>Nuevo usuario</a>\n" +
"            </li>";
}

function ver_regs(){
                var f1 =$('#f1').val();
                var f2 =$('#f2').val();
                var nombre =$('#nombre').val();
                var area =$('#area').val()
                var dep = $('#nombre_dep').val();
                var tipo= $('#mov').val();
                var uso="report";
               $.ajax({
                    type: 'post',
                    data: {uso: uso,f1:f1,f2:f2,nombre:nombre,area:area,depa:dep,tipo:tipo},
                    url: '../Movimientos',
                    success: function (result) {
                        $('#body_table').html(result);
                    }
                });                
}
function reporte(){
   var f1 =$('#f1').val();
                var f2 =$('#f2').val();
                var nombre =$('#nombre').val();
                var area =$('#area').val()
                var dep = $('#nombre_dep').val();
                var tipo= $('#mov').val();
    location='reporte_movs.jsp?f1='+f1+"&f2="+f2+"&nombre="+nombre+"&area="+area+"&depa="+dep+"&tipo="+tipo;
}
function reporte_det(){
   var f1 =$('#f1').val();
                var f2 =$('#f2').val();
                var nombre =$('#nombre').val();
                var area =$('#area').val()
                var dep = $('#nombre_dep').val();
                var tipo= $('#mov').val();
    location='reporte_movs_det.jsp?f1='+f1+"&f2="+f2+"&nombre="+nombre+"&area="+area+"&depa="+dep+"&tipo="+tipo;
}
/* 
 * 
  catch(ClassNotFoundException c){
            System.out.println(c);
        }catch(IOException i){
        System.out.println(i);
        }catch (SQLException ex) {
            Logger.getLogger(Getfields.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception e){
        System.out.println(e);
        }
 * 
 * 
 * */