package controller;

import dal.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteProductServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id").trim();
        int pId = 0;
        
        ProductDAO pd = new ProductDAO();
        
        try {
            pId = Integer.parseInt(id);
            
            boolean result = pd.deleteProductById(pId);
            
            if (!result) throw new Exception();
            else 
                req.setAttribute("msgDelete", "Delete product with id "+pId+" successfully");
            
        }catch(NumberFormatException e) {
            req.setAttribute("msgDelete", "Invalid Product ID");
        } catch (Exception ex) {
            Logger.getLogger(DeleteProductServlet.class.getName()).log(Level.SEVERE, null, ex);
            req.setAttribute("msgDelete", "Error, plese try again later");
        }
        
        req.getRequestDispatcher("product.jsp").forward(req, resp);
    }
    
}
