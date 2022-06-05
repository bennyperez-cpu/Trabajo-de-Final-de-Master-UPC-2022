<%-- 
    Document   : login
    Created on : 21-feb-2022, 23:19:39 <div class="container "> <div class = "form-group text-center"> 
    Author     : Benny Hammer Pérez Vásquez
        <style>

        </style>
        
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <title>Pacientes FHIR</title>
        <link href="css/Estilos_login.css" rel="stylesheet"/>
        <link href="css/style_button.css" rel="stylesheet" media="all"/> 
        <script src="https://kit.fontawesome.com/4e646a13f9.js" crossorigin="anonymous"></script>
        
        <style>
            body{
                margin: 0;
                padding: 0;     
                background-image: url("img/Fondo_video.jpg");
                background-size: cover;
                background-position: center;
                font-family: sans-serif;    
                background-color: #32baf3;
            }
            
            .container{
                background-color:silver;
                border-radius: 10px;
                padding:25px;
                margin-top: 80px
            }
        </style>

        
    </head>
    <body>             
        
        
        
        <div class="container col-lg-3">
            <form action = "User_Servlet" method ="get"> 
                <div class = "form-group text-center">
                    <div class="form-group title_header">Pacientes FHIR</div>
                    <p><strong><br/></strong></p>
                    <img src="Images/usuario.jpg" height="80" width="80" alt=""/>
                </div>
                
                <div class = "form-group">                    
                    <label>Nombre de Usuario</label>
                    <input class="form-control text-center" type="text" name="txtnom" placeholder="Ingrese usuario">                    
                </div>
                <div class = "form-groupr">
                     <label for="passwordInput" style="float: left">Contraseña</label>
                     <input type="password" name="password" class="form-control text-center" id="passwordInput" placeholder="************" required>
                </div>
                <div class = "form-group"></div>
                <div class = "form-group text-center">
                    <%--<input type="submit" name="accion" value ="Ingresar" class="btn btn-primary mb-2 text-center">--%>
                    <button type="submit" name="accion" value ="Ingresar" class="button"><i class="fa-solid fa-door-open"></i> Ingresar</button>
                </div>
            </form>

           <%--   </form> --%>
                 
        </div>
        
            <p> 
                <br/>
                <br/>
            </p>
        
         <div>
            <form>
                <div class="project-made-text text-center">Proyecto hecho por:</div>
                <div class="project-names-text text-center">Benny Pérez</div>
                    <br/>
                <div class="project-end-text text-center"><strong>TFM Mayo 2022</strong></div>
                    
                
            </form>
                 
        </div>
            
            
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>


    </body>
</html>