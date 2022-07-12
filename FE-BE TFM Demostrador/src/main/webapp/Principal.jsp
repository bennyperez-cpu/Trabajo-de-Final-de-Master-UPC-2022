<%-- 
    Document   : Principal.jsp
    Created on : 22-feb-2022, 1:55:32
    Author     : Benny Hammer Pérez Vásquez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">

        <title>Página de Búsqueda Pacientes FHIR</title>
        <link href="css/Estilos_listadoVid.css" rel="stylesheet" type="text/css"/>
        <link href="css/style_button.css" rel="stylesheet" media="all"/>
        <script src="https://kit.fontawesome.com/4e646a13f9.js" crossorigin="anonymous"></script>

        <!-- Bootstrap CSS CDN -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">

        <!-- Font Awesome JS -->
        <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/solid.js" integrity="sha384-tzzSw1/Vo+0N5UhStP3bvwWPq+uvzCMfrN1fEFe+xBmv1C/AtVX5K0uZtmcHitFZ" crossorigin="anonymous"></script>
        <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/fontawesome.js" integrity="sha384-6OIrr52G08NpOFSZdxxz1xdNSndlD4vdcf/q2myIUVO0VsqaGHJsB0RaBE01VTOY" crossorigin="anonymous"></script>
        
        <style>
            .fondo{
                margin: 0;
                padding: 0;     
                background-image: url("img/video_list.jpg");
                background-size: auto;
                background-position: center;
                font-family: sans-serif;    
                background-color: #32baf3;
               
                
            }
            
            .container1{
                background-color:silver;
                border-radius: 10px;
                width: 1000px;
                padding:10px;
                margin-top: 80px
            }
            .container2{
                background-color:silver;
                width: 1000px;
                border-radius: 10px;
                padding:10px;
                margin-top: 80px
            }
            .container3{
                background-color:silver;
                border-radius: 10px;
                padding:25px;
                margin-top: 80px
            }

        </style>
        
        
    </head>
    <body>
        
        <% 
            response.setHeader("Cache-Control","no-cache, no-store, must-revalidate"); 
            response.setHeader("Pragma","no-cache"); 
            response.setHeader("Expires", "0");
            if (session.getAttribute("sessionName") == null || session.getAttribute("sessionName").equals("")) {
                response.sendRedirect("Login.jsp");
            }
        %>
        
        <div class="wrapper">
            <!-- Sidebar -->
            <nav id="sidebar">
                <div class="sidebar-header">
                   <%-- <h3>${empty userName ? '' : userName}</h3> --%>
                   <h4>${empty requestorType ? '' : requestorType}</h4> 
                   <h5>Usuario: ${empty userName ? '' : userName}</h5>
                   <h5>FHIR ID: ${empty idFhir ? '' : idFhir}</h5>
                    
                </div>
                
                <ul class="list-unstyled components  text-center">

                </ul>
                <ul class='list-unstyled CTAs  text-center'>
                    <li>
                        <a href="<%=request.getContextPath()%>" + "accion=Salir">Salir</a>
                    </li> 
                </ul>
            </nav>

            <!-- Page Content -->
            <div class="fondo   text-center" id="content">
                <nav class="navbar navbar-expand-lg navbar-light">
                    <div class="container-fluid">
                        <button type="button" id="sidebarCollapse" class="btn btn-info">
                            <i class="fas fa-align-left"></i>
                            <span>User profile</span>
                        </button>
                    </div>
                </nav>
                <div name="BODY" style="width:100%;" class="col-12">
                    <div>
                        <div class='list_title_header   text-center'>${empty requestorType ? '' : requestorType}</div>
                        
                        <% if (session.getAttribute("Patient_list") == null && session.getAttribute("statistics") == null) { %>
                            <div class='row row justify-content-md-center'>
                                <div class='container1 col-3 border border-primary m-1'>
                                    <form action="${pageContext.request.contextPath}/Principal_Servlet" method="POST">
                                        <div class = "form-group">  
                                            <label for="patient1" >Paciente</label>
                                            <input name="patient1" type="text" class="form-control" id="paciente1" placeholder="ID del Paciente" required>

                                            <br>
                                        </div>

                                        <button name="action" value="search-patient" type="submit" class="button" ><i class="fa-solid fa-magnifying-glass" opacity="1"></i> Search</button>

                                    </form>
                                </div>

                            
                                <div class='container2 col-3 border border-primary m-1   text-center'>
                                    <form action="${pageContext.request.contextPath}/Principal_Servlet" method="POST">
                                        
                                        <div class = "form-group">
                                            <label for="minAge" >Edad Mínima</label>
                                            <input name="minAge" type="text" class="form-control" id="minAge" placeholder="30">
                                        </div>

                                        <div class = "form-group">
                                            <label for="maxAge" >Edad Máxima</label>
                                            <input name="maxAge" type="text" class="form-control" id="maxAge" placeholder="50">
                                        </div>

                                        <div class = "form-group">  
                                            <label for="keyWord1" >Condición 1</label>
                                            <input name="keyWord1" type="text" class="form-control" id="keyWord1" placeholder="Glucosa">

                                        </div>
                                        <div class = "form-group">  
                                            <label for="keyWord1" >Condición 2</label>
                                            <input name="keyWord2" type="text" class="form-control" id="keyWord2" placeholder="Hearth Disease">

                                        </div>
                                        <div class = "form-group">  
                                            <label for="keyWord3" >Condición 3</label>
                                            <input name="keyWord3" type="text" class="form-control" id="keyWord3" placeholder="Diabetes">

                                        </div>

                                        <div class = "form-group">
                                            <label for="minPressure" >Presión Mínima</label>
                                            <input name="minPressure" type="text" class="form-control" id="minPressure" placeholder="70">
                                        </div>

                                        <div class = "form-group">
                                            <label for="maxPressure" >Presión Máxima</label>
                                            <input name="maxPressure" type="text" class="form-control" id="maxPressure" placeholder="115">
                                        </div>

                                        <br>
                                        <button name="action" value="search-statistic" type="submit" class="button"><i class="fa-solid fa-magnifying-glass"></i> Search</button> 
                                    </form>
                                </div>                                                              
                                
                            </div>
                        <% }else if (session.getAttribute("Patient_list") != null && session.getAttribute("statistics") == null){ %>
                            <form action="${pageContext.request.contextPath}/Principal_Servlet" method="post">
                                <button name="action" value="change-search" type="submit" class="btn btn-primary btn-sm m-2">Cambio Búsqueda de Paciente</button>
                            </form>
                            <table class="table   text-center">
                            <thead>
                                <tr>
                                    <th scope="col">Nombre</th>
                                    <th scope="col">Identificador</th>
                                    <th scope="col">Género</th>
                                    <th scope="col">Fecha de Nacimiento</th>
                                    <th scope="col">Médico</th>
                                    <th scope="col">Clínica</th>
                                    <th scope="col">Problema de Salud</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="patient_1" items="${Patient_list}">
                                    <tr>
                                        <td>${patient_1.getNames()}</td> 
                                        <td>${patient_1.getId()}</td> 
                                        <td>${patient_1.getGender()}</td> 
                                        <td>${patient_1.getBirthday()}</td>
                                        <td>${patient_1.getPractitioner()}</td>
                                        <td>${patient_1.getOrganization()}</td>
                                        <td>${patient_1.getObservation()}</td>
    
                                    </tr> 
                                </c:forEach>
                            </tbody>
                            </table>
                        <%}else{ %>
                            <form action="${pageContext.request.contextPath}/Principal_Servlet" method="post">
                                <button name="action" value="change-search" type="submit" class="btn btn-primary btn-sm m-2">Cambio Búsqueda de Paciente</button>
                            </form>
                            <div class="container-fluid">
                                <h6>Edad Mínima: ${empty minAge ? '' : minAge}</h6>
                                <h6>Edad Máxima: ${empty maxAge ? '' : maxAge}</h6>
                                <h6>Condición 1: ${empty keyWord1 ? '' : keyWord1}</h6>
                                <h6>Condición 1: ${empty keyWord2 ? '' : keyWord2}</h6>
                                <h6>Condición 3: ${empty keyWord3 ? '' : keyWord3}</h6>                                                       
                                <h6>Presión Mínima: ${empty minPressure ? '' : minPressure}</h6>
                                <h6>Presión Máxima: ${empty maxPressure ? '' : maxPressure}</h6>                              
                                <h6>Cantidad: ${empty statistics ? '' : statistics}</h6>

                            </div>
                            
                        <%} %>
                    </div>
                </div>
            </div>
        </div>       


        <!-- jQuery CDN - Slim version (=without AJAX) -->
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <!-- Popper.JS -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
        <!-- Bootstrap JS -->
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>

        <script>
            $(document).ready(function () {
                $('#sidebarCollapse').on('click', function () {
                    $('#sidebar').toggleClass('closed');
                });
            });
        </script>


        });
    </body>
</html>