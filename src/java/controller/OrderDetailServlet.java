package controller;

import dal.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Item;
import model.Order;

public class OrderDetailServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderid"));
        OrderDAO od = new OrderDAO();
        
        try {
            List<Item> list = od.getAllOrderDetailByOrderId(orderId);
            Order d = od.getOrderById(orderId);
            
            request.setAttribute("data", list);
            request.setAttribute("orderId", orderId);
            request.setAttribute("totalMoney", od.getTotalMoneyByOrderId(orderId));
            request.setAttribute("shipper", od.getShipperByOrderId(orderId));
            request.setAttribute("status", d.getStatus());
            request.setAttribute("reason",d.getReason());
            request.setAttribute("shipPrice", od.getShipPriceByOrderId(orderId));
        } catch (Exception ex) {
            Logger.getLogger(OrderDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.getRequestDispatcher("orderdetail.jsp").forward(request, response);
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
