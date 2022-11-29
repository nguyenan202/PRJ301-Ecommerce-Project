package controller;

import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

public class ChangePasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String oldPass = request.getParameter("oldpass");
        String newPass = request.getParameter("newpass");
        String reNewPass = request.getParameter("re-newpass");
        
        boolean isOke = true;
        
        UserDAO ud = new UserDAO();
        HttpSession session = request.getSession();
        
        if (session.getAttribute("user") == null) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        
        User u = (User)session.getAttribute("user");
        
        try {
            //Check same with old password
            if (!ud.checkPassword(u.getUsername(), oldPass)) {
                isOke = false;
                request.setAttribute("oldPassMessage", "Old password incorect");
            }
            
            //check new Password not empty
            if(newPass.equalsIgnoreCase("")) {
                isOke = false;
                request.setAttribute("emptyPassMessage", "This feild not empty");
                request.getRequestDispatcher("changepassword.jsp").forward(request, response);
            }
            
            //check same password
            if (!newPass.equalsIgnoreCase(reNewPass)) {
                isOke = false;
                request.setAttribute("samePassMessage", "Re-password must same with password");
                request.getRequestDispatcher("changepassword.jsp").forward(request, response);
            }
            
            //start change
            if (isOke && ud.changePassword(u.getUserId(), newPass)) {
                request.setAttribute("changePassMessage", "You have changed your password success");
            } else {
                request.setAttribute("changePassMessage", "Error!!! Plese try again later");
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ChangePasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.getRequestDispatcher("changepassword.jsp").forward(request, response);
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
