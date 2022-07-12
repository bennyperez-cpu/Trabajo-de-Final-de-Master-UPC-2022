/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import tools.Usuario_DAO;
import static tools.Usuario_DAO.requestorType;

/**
 *
 * @author alumne
 */
@WebServlet(name = "User_Servlet", urlPatterns = {"/User_Servlet"})
public class User_Servlet extends HttpServlet {
    Usuario_DAO dao = new Usuario_DAO();
    User usu=new User();
    User usuario= new User();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String accion=request.getParameter("accion");
        if(accion.equals("Ingresar")){
            String nombre=request.getParameter("txtnom");
            String contrasenha=request.getParameter("password");
            usu.setNombre_de_Usuario(nombre);
            usu.setContrasenha(contrasenha);
            usuario = dao.validar(usu);
            if(usuario!=null){

                String idFhir = usuario.getId_FHIR();
                String getRequestorType = null;
                
                if(idFhir.equals("none")){
                    getRequestorType = "Other";
                }else{
                    getRequestorType = requestorType(idFhir);
                }
                HttpSession session = request.getSession();
                session.setAttribute("sessionName",nombre);
                

                switch (getRequestorType) {
                    case "Patient":
                    request.getSession().setAttribute("userName",usuario.getNombre_de_Usuario());
                    request.getSession().setAttribute("idFhir",usuario.getId_FHIR());
                    request.getSession().setAttribute("requestorType","Paciente");
                    request.getRequestDispatcher("Patient.jsp").forward(request, response);  
                    break;

                    case "Practitioner":
                    request.getSession().setAttribute("userName",usuario.getNombre_de_Usuario());
                    request.getSession().setAttribute("idFhir",usuario.getId_FHIR());
                    request.getSession().setAttribute("requestorType","Médico");
                    request.getRequestDispatcher("Practitioner.jsp").forward(request, response);
                    break;

                    case "Organization":
                    request.getSession().setAttribute("userName",usuario.getNombre_de_Usuario());
                    request.getSession().setAttribute("idFhir",usuario.getId_FHIR());
                    request.getSession().setAttribute("requestorType","Organización");
                    request.getRequestDispatcher("Organization.jsp").forward(request, response);
                    break;

                    case "Other":
                    request.getSession().setAttribute("userName",usuario.getNombre_de_Usuario());
                    request.getSession().setAttribute("idFhir",usuario.getId_FHIR());
                    request.getSession().setAttribute("requestorType","Administrador");
                    request.getRequestDispatcher("Principal.jsp").forward(request, response);
                    break;
                
                    default:
                    request.getRequestDispatcher("error_login.jsp").forward(request, response);
                    break;
                }

            }else{
    
                request.getRequestDispatcher("error_login.jsp").forward(request, response);
            }
        } else {           
            
            HttpSession session = request.getSession();
            session.removeAttribute("sessionName");
            session.invalidate();
            response.sendRedirect("Login.jsp");

        }


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
        processRequest(request, response);
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
