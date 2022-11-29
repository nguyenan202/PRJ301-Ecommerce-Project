package controller;

import dal.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cart;
import model.Item;

public class CartQuantityServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        int productId = Integer.parseInt(request.getParameter("productId"));
        Cart cart = (Cart) session.getAttribute("cart");

        cart.removeItemByProductId(productId);
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ProductDAO pd = new ProductDAO();

        int productId = Integer.parseInt(request.getParameter("productId"));
        int num = Integer.parseInt(request.getParameter("quantity"));

        Cart cart = (Cart) session.getAttribute("cart");

        Item item = cart.getItemByProductId(productId);

        try {
            if (num == -1 && item.getQuantity() == 1) {
                cart.removeItemByProductId(productId);
            } else {
                int unitInStock = pd.getUnitInStockByProductId(productId);

                if (item.getQuantity() + num > unitInStock) {
                    request.setAttribute("quantityMessage", "Not Enought Product");
                } else {

                    item.setQuantity(item.getQuantity() + num);
                    item.setPrice(item.getPrice() + item.getProduct().getUnitPrice() * num);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(CartQuantityServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
