package controller;

import dal.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Product;

public class FilterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("search");
        int catId = Integer.parseInt(request.getParameter("category"));
        String order = request.getParameter("sort");
        String xPage = request.getParameter("page");
        String[] supplier = request.getParameterValues("supplier");
        double priceS = Double.parseDouble(request.getParameter("priceBegin"));
        double priceE = Double.parseDouble(request.getParameter("priceEnd"));
        int page = 1;
        
        if (xPage != null) page = Integer.parseInt(xPage);
        
        ProductDAO pd = new ProductDAO();
        
        try {
            List<Product> xList = new ArrayList<>();
            List<Product> list = new ArrayList<>();
            if (catId == -1) {
                xList = pd.getAllFilter(supplier, name, order, priceS, priceE);
            } else {
                if (supplier != null)
                    xList = pd.getFilter(supplier, name, order, catId, priceS, priceE);
                else
                    xList = pd.getAllWithNoneSupplier(name, order, catId, priceS, priceE);
            }
            
            list = pd.getProductByPage(xList, page);
            
            request.setAttribute("totalProduct", xList.size());
            request.setAttribute("pageSize", pd.getNumberOfPage(xList));
            request.setAttribute("nameSearch", name);
            request.setAttribute("sort", order);
            request.setAttribute("data", list);
            request.setAttribute("catChose", catId);
            request.setAttribute("supChose", supplier);
            request.setAttribute("supChoseString", Arrays.toString(supplier).substring(1, Arrays.toString(supplier).length()-1));
            request.setAttribute("pageActive", page);
            request.setAttribute("priceStartChose", priceS);
            request.setAttribute("priceEndChose", priceE);
            
        } catch (Exception ex) {
            Logger.getLogger(FilterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.getRequestDispatcher("product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    public static void main(String[] args) {
        String s = "";
        String[] arr = s.split("[, ]+");
        
        for (String string : arr) {
            System.out.println(string);
        }
    }
}
