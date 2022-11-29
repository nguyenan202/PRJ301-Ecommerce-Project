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

public class AddressServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        UserDAO ud = new UserDAO();

        try {
            boolean res = ud.deleteAddressById(id);
            if (res) {
                request.setAttribute("deleteSuccess", "Deleted Address Success");
            } else {
                request.setAttribute("deleteFail", "Delete Address Fail, plese try again");
            }
        } catch (Exception ex) {
            Logger.getLogger(AddressServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        UserDAO ud = new UserDAO();

        String address = request.getParameter("address");
        boolean isOke = true;
        
        try {
            if (session.getAttribute("user") != null) {
                User u = (User)session.getAttribute("user");
                int id = u.getUserId();

                isOke = ud.addNewAddress(id, address);

            } else {
                request.setAttribute("errorAdd", "Error to Add new Address, plese try again later");
            }
        } catch (Exception ex) {
            Logger.getLogger(AddressServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
}
