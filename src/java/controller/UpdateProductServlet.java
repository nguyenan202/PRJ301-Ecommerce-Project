package controller;

import dal.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Product;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class UpdateProductServlet extends HttpServlet {

    public static final String DOMAIN = "http://localhost:8080";
    public static final String UPLOAD_DIR = "image";
    public String dbFileName = "";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        ProductDAO pd = new ProductDAO();
        Product p = null;

        try {
            p = pd.getProductById(Integer.parseInt(id));
        } catch (Exception ex) {
            Logger.getLogger(UpdateProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        req.setAttribute("product", p);
        req.getRequestDispatcher("updateProduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("pId");

        if (id == null) {
            req.setAttribute("updateMsg", "Plese Chose Product to Update");
            req.getRequestDispatcher("updateProduct.jsp").forward(req, resp);
            return;
        }

        int pId = Integer.parseInt(id);
        String pName = req.getParameter("pName");
        double pPrice = Double.parseDouble(req.getParameter("pPrice"));
        int pStock = Integer.parseInt(req.getParameter("pStock"));
        Part part = req.getPart("file");

        ProductDAO pd = new ProductDAO();

        if (part.getSize() != 0) {
            String fileName = extractFileName(part);

            String applicationPath = getServletContext().getRealPath("");
            String uploadPath = applicationPath + File.separator + UPLOAD_DIR;
            System.out.println("applicationPath:" + applicationPath);
            File fileUploadDirectory = new File(uploadPath);
            if (!fileUploadDirectory.exists()) {
                fileUploadDirectory.mkdirs();
            }
            String savePath = uploadPath + File.separator + fileName;
            System.out.println("savePath: " + savePath);
            String sRootPath = new File(savePath).getAbsolutePath();
            System.out.println("sRootPath: " + sRootPath);
            part.write(savePath + File.separator);
            File fileSaveDir1 = new File(savePath);

            dbFileName = UPLOAD_DIR + File.separator + fileName;
            part.write(savePath + File.separator);

            savePath = DOMAIN + savePath.substring(10);
            savePath = savePath.substring(0, 30) + savePath.substring(41);
            savePath = savePath.replace("\\","/");

            try {
                pd.updateProductWithImage(pId, pName, pPrice, pStock, savePath);
                req.setAttribute("updateMsg", "Update Product Successfully");
            } catch (Exception ex) {
                Logger.getLogger(UpdateProductServlet.class.getName()).log(Level.SEVERE, null, ex);
                req.setAttribute("updateMsg", "Update Product Failed");
            }
        } else {
            try {
                pd.updateProductNoImage(pId, pName, pPrice, pStock);
                req.setAttribute("updateMsg", "Update Product Successfully");
            } catch (Exception ex) {
                Logger.getLogger(UpdateProductServlet.class.getName()).log(Level.SEVERE, null, ex);
                req.setAttribute("updateMsg", "Update Product Failed");
            }
        }

        req.getRequestDispatcher("updateProduct.jsp").forward(req, resp);
    }

    private String extractFileName(Part part) {//This method will print the file name.
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}
