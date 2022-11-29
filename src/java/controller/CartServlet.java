package controller;

import dal.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cart;
import model.Item;
import model.Product;

public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String nameSearch = request.getParameter("nameSearch") != null ? request.getParameter("nameSearch") : "";
        int catId = request.getParameter("catId") != null ? Integer.parseInt(request.getParameter("catId")) : -1;
        int sort = request.getParameter("sort") != null ? Integer.parseInt(request.getParameter("sort")) : -1;
        String suppliers = request.getParameter("suplier") != null ? request.getParameter("suplier") : "";
        double priceS = request.getParameter("priceStart") != null ? Double.parseDouble(request.getParameter("priceStart")) : 0;
        double priceE = request.getParameter("priceEnd") != null ? Double.parseDouble(request.getParameter("priceEnd")) : 99999;
        int pageActive = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        String backHome = request.getParameter("home");

        String[] supplier = suppliers.split("[, ]+");

        Cart cart;
        Item item = new Item();
        List<Product> xList = new ArrayList<>();
        List<Product> list = new ArrayList<>();
        ProductDAO pd = new ProductDAO();

        if (session.getAttribute("cart") == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        } else {
            cart = (Cart) session.getAttribute("cart");
        }

        try {
            item.setProduct(pd.getProductById(productId));
            item.setQuantity(quantity);
            item.setPrice(pd.getProductById(productId).getUnitPrice());

            int result = cart.addToCart(item);
            if (result == 1) {
                session.setAttribute("cart", cart);
            } else if (result == -1) {
                request.setAttribute("errorCart", "Not Enought Product");
            }

            if (catId == -1) {
                if((suppliers.equals("") || suppliers.equals("ul"))&& suppliers!=null) {
                    xList = pd.getAll();
                }else
                    xList = pd.getAllFilter(supplier, nameSearch, sort + "", priceS, priceE);
            } else {
                if((suppliers.equals("") || suppliers.equals("ul"))&& suppliers!=null) {
                    xList = pd.getAllWithNoneSupplier(nameSearch, sort+"", catId, priceS, priceE);
                }else {
                    xList = pd.getFilter(supplier, nameSearch, sort + "", catId, priceS, priceE);
                }
            }

            list = pd.getProductByPage(xList, pageActive);
        } catch (Exception ex) {
            Logger.getLogger(CartServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (backHome != null) {
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } else {
            request.setAttribute("totalProduct", xList.size());
            request.setAttribute("pageSize", pd.getNumberOfPage(xList));
            request.setAttribute("data", list);
            request.setAttribute("nameSearch", nameSearch);
            request.setAttribute("sort", sort);
            request.setAttribute("catChose", catId);
            request.setAttribute("supChose", supplier);
            request.setAttribute("supChoseString", Arrays.toString(supplier).substring(1, Arrays.toString(supplier).length()-1));
            request.setAttribute("pageActive", pageActive);
            request.setAttribute("priceStartChose", priceS);
            request.setAttribute("priceEndChose", priceE);
            request.getRequestDispatcher("product.jsp").forward(request, response);
        }
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
