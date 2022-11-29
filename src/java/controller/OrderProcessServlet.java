package controller;

import dal.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cart;
import model.User;

public class OrderProcessServlet extends HttpServlet {
    private static final String errorMsg = "Have some Error, plese try again later!!";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = null;
        
        if (session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
        } else user = (User)session.getAttribute("user");
        
        //if Admin remove cart, cancel order
        if (user.getRole() == 1) {
            request.setAttribute("orderFailt", "Only customers are allowed to purchase");
            session.removeAttribute("cart");
            request.getRequestDispatcher("cart.jsp").forward(request, response);
            return;
        }
        
        int shipId = Integer.parseInt(request.getParameter("shipperId"));
        int addressId = Integer.parseInt(request.getParameter("addressId"));
    
        if (shipId == -1 || addressId == -1) {
            if(shipId == -1 && addressId != -1) {
                request.setAttribute("emptyShipper", "Chose 1 Shipper to Order");
                request.setAttribute("addId", addressId);
            }else if(addressId == -1 && shipId != -1) {
                request.setAttribute("emptyAddress", "Chose 1 Address to Order");
                request.setAttribute("shId", shipId);
            } else {
                request.setAttribute("emptyShipper", "Chose 1 Shipper to Order");
                request.setAttribute("emptyAddress", "Chose 1 Address to Order");
            }
            request.getRequestDispatcher("cart.jsp").forward(request, response);
            return;
        }
        
        
        Cart cart = null;
        OrderDAO od = new OrderDAO();
        
        cart = (Cart)session.getAttribute("cart");
        
        boolean isOke = true;
        
        try {
            int orderId = od.doOrder(user, cart, shipId, addressId);
            isOke = od.doOrderDetail(cart,orderId);
            
        } catch (Exception ex) {
            Logger.getLogger(OrderProcessServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (isOke) {
            request.setAttribute("orderSuccess", "Order Success, check your Order in ");
            session.removeAttribute("cart");
        } else {
            request.setAttribute("orderFailt", "Order Fail, plese try again later!!!");
        }
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
