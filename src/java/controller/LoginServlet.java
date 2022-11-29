package controller;

import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

public class LoginServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        UserDAO ud = new UserDAO();
        //check username
        try {
            if (username.equals("")) {
                request.setAttribute("usernameExistMessage", "Username is empty");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
            if (ud.checkExistUsername(username)) {
                request.setAttribute("usernameExistMessage", "Username is not exist");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        //check password
        try {
            if (password.equals("")) {
                request.setAttribute("passwordMessage", "Password is empty");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
            if (ud.checkPassword(username, password)) {
                HttpSession session = request.getSession();
                User u = ud.getUser(username, password);
                
                session.setAttribute("user", u);
                request.getRequestDispatcher("home.jsp").forward(request, response);
            } else {
                request.setAttribute("passwordMessage", "Password is Incorrect");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
