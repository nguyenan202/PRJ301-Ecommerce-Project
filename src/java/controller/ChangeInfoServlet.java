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

public class ChangeInfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        
        boolean isOke = true;
        
        HttpSession session = request.getSession();
        
        //check email
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            isOke = false;
            request.setAttribute("emailMessage", "Invalid Email");
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        }
        
        //check phone
        if (!phone.matches("\\d+")) {
            isOke = false;
            request.setAttribute("phoneMessage", "Invalid Phone Number");
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        }
        
        UserDAO ud = new UserDAO();
        
        if (isOke && session.getAttribute("user") != null) {
            User u = (User)session.getAttribute("user");
            try {
                if (ud.updateUser(u.getUserId(), fname, lname, email, phone)) {
                    request.setAttribute("updateMessage","Update User Success");
                    u.setFirstName(fname);
                    u.setLastName(lname);
                    u.setEmail(email);
                    u.setPhone(phone);
                    session.setAttribute("user", u);
                } else {
                    request.setAttribute("updateMessage", "Update User Fail");
                }
            } catch (Exception ex) {
                Logger.getLogger(ChangeInfoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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
