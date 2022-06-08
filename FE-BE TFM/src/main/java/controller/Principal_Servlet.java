/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Condition;
import model.Patient;
import tools.Usuario_DAO;
import static tools.Usuario_DAO.getStatistics;

/**
 *
 * @author alumne
 */
public class Principal_Servlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest_1(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        System.out.println(request);
        String action = request.getParameter("action");
        
        String keyWord1 = null;
        String keyWord2 = null;
        String keyWord3 = null;
        String value1 = null;
        String value2 = null;
        String value3 = null;
        String value4 = null;
        String idFhir = null;
        

        switch (action) {
            case "search-patient":
                value1 = request.getParameter("patient1");
                value2 = (String) request.getSession().getAttribute("idFhir");
                request.getSession().removeAttribute("statistics");
                search_1(request,response,value1,value2);
                break;
                
            case "search-statistic":
                value1 = request.getParameter("minAge");
                value2 = request.getParameter("maxAge");
                keyWord1 = request.getParameter("keyWord1");
                keyWord2 = request.getParameter("keyWord2");
                keyWord3 = request.getParameter("keyWord3");
                value3 = request.getParameter("minPressure");
                value4 = request.getParameter("maxPressure");
              
                
                log("La estadística es: " + request.getParameter("statisticsCal"));
                if((request.getParameter("statisticsCal") != null)){
                    idFhir = "39ff939jgg";
                    
                }else{ 
                    idFhir = (String) request.getSession().getAttribute("idFhir");
                }
                System.out.println("El idFHIR es: " + idFhir.toString());
                search_2(request, response, value1, value2, value3, value4, keyWord1, keyWord2, keyWord3, idFhir);
                break;

            case "change-search":
                log("change-search");
                request.getSession().removeAttribute("Patient_list");
                request.getSession().removeAttribute("statistics");
                String requestorType = (String) request.getSession().getAttribute("requestorType");
                returnFE(request, response, requestorType);                
                
                break;
                
            case "Salir":
                request.getSession().removeAttribute("Patient_list");
                request.getSession().removeAttribute("statistics");
                response.sendRedirect("Login.jsp");
                
                break;

            default:
                break;
        }
      
    }
    
    private void returnFE(HttpServletRequest request, HttpServletResponse response, String requestorType) throws IOException {
        
                
                switch (requestorType){
                    case "Paciente":
                        response.sendRedirect("Patient.jsp");
                        break;
                        
                    case "Médico":
                        response.sendRedirect("Practitioner.jsp");
                        
                        break;
                        
                    case "Organización":
                        response.sendRedirect("Organization.jsp");
                        break;
                        
                    case "Administrador":
                        response.sendRedirect("Principal.jsp");
                        
                        break;
                        
                    default:
                        break;
                }
    
    }

    public void search_1(HttpServletRequest request, HttpServletResponse response, String value1, String value2) throws IOException {
        log("Buscando por " + value1 + " " + value2);              
        List<Patient> patient_1 = Usuario_DAO.getPatientFHIR(value1,value2);
        request.getSession().setAttribute("Patient_list", patient_1);
        String requestorType = (String) request.getSession().getAttribute("requestorType");
        returnFE(request, response, requestorType); 

    }
    
    protected void search_2(HttpServletRequest request, HttpServletResponse response, String minAge, String maxAge,
                            String minPressure, String maxPressure, String keyWord1, String keyWord2, String keyWord3,
                            String idFhir) throws IOException {
   


    List<Condition> conditions = new ArrayList<>();
    if (minAge != null && !minAge.equals("")) {
        conditions.add(new Condition(1, minAge + " " + maxAge));
    }
    if (keyWord1 != null && !keyWord1.equals("")) {
        conditions.add(new Condition(2, keyWord1));
    }
    if (keyWord2 != null && !keyWord2.equals("")) {
        conditions.add(new Condition(2, keyWord2));
    }
    if (keyWord3 != null && !keyWord3.equals("")) {
        conditions.add(new Condition(2, keyWord3));
    }
    if (minPressure != null && !minPressure.equals("")) {
        conditions.add(new Condition(3, minPressure + " " + maxPressure));
    }
    int count = getStatistics(conditions, idFhir);

    
    request.getSession().setAttribute("minAge", minAge);
    request.getSession().setAttribute("maxAge", maxAge);
    request.getSession().setAttribute("keyWord1", keyWord1);
    request.getSession().setAttribute("keyWord2", keyWord2);
    request.getSession().setAttribute("keyWord3", keyWord3);
    request.getSession().setAttribute("minPressure", minPressure);
    request.getSession().setAttribute("maxPressure", maxPressure);

    request.getSession().setAttribute("statistics", count);
    request.getSession().removeAttribute("Patient_list");
    String requestorType = (String) request.getSession().getAttribute("requestorType");
    returnFE(request, response, requestorType); 

    }
    
    protected void processRequest_2(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {   
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest_2(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest_1(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
