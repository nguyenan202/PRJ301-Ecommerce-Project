package controller;

import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResetPasswordServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("username");
        String email = req.getParameter("email");
        
        UserDAO ud = new UserDAO();
        
        try {
            if (ud.isValidResetPassword(userName, email)) {
                String newPass = ud.genNewPassword(4);
                boolean result = ud.changePasswordByUsername(userName, newPass);
                
                if (result) {
                    req.setAttribute("newPass", newPass);
                    req.setAttribute("resultSuccess", "Reset password successfully");
                } else {
                    req.setAttribute("resultFaild", "Reset password failed");
                }
            } else {
                req.setAttribute("resultFaild", "Invalid username or email");
            }
        } catch (Exception ex) {
            Logger.getLogger(ResetPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        req.getRequestDispatcher("forgotPassword.jsp").forward(req, resp);
    }
    
}
