package controller;

import dal.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Item;
import model.Order;

public class ReasonCancelServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reason = req.getParameter("reason");
        int oId = Integer.parseInt(req.getParameter("oId"));

        OrderDAO od = new OrderDAO();
        Order d = new Order();
        List<Item> list = new ArrayList<>();

        if (reason.equals("") || reason == null) {
            req.setAttribute("data", list);
            req.setAttribute("orderId", oId);
            try {
                req.setAttribute("totalMoney", od.getTotalMoneyByOrderId(oId));
                req.setAttribute("shipper", od.getShipperByOrderId(oId));
                req.setAttribute("status", d.getStatus());
                req.setAttribute("shipPrice", od.getShipPriceByOrderId(oId));
                req.setAttribute("msg", "Plese tell a reason");
                req.getRequestDispatcher("orderdetail.jsp").forward(req, resp);
            } catch (Exception ex) {
                Logger.getLogger(ReasonCancelServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            return;
        }

        boolean isOke = true;

        try {
            isOke = od.cancelOrder(oId, reason);
            d = od.getOrderById(oId);
            list = od.getAllOrderDetailByOrderId(oId);
            if (isOke) {
                req.setAttribute("msg", "Cancel Order Successfully");
                req.setAttribute("data", list);
                req.setAttribute("orderId", oId);
                req.setAttribute("totalMoney", od.getTotalMoneyByOrderId(oId));
                req.setAttribute("shipper", od.getShipperByOrderId(oId));
                req.setAttribute("status", d.getStatus());
                req.setAttribute("shipPrice", od.getShipPriceByOrderId(oId));
            } else {
                req.setAttribute("msg", "Error, Plese try again");
            }
        } catch (Exception ex) {
            Logger.getLogger(ReasonCancelServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        req.getRequestDispatcher("orderdetail.jsp").forward(req, resp);
    }

}
