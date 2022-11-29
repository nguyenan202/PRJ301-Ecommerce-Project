package controller;

import dal.CategoryDAO;
import dal.ProductDAO;
import dal.SupplierDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;
import model.Supplier;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class CreateProductServlet extends HttpServlet {
    
    public static final String DOMAIN = "http://localhost:8080";
    public static final String UPLOAD_DIR = "image";
    public String dbFileName = "";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        getData(req);

        req.getRequestDispatcher("addProduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int catId = Integer.parseInt(req.getParameter("catId"));
        String pName = req.getParameter("pName").trim();
        int supId = Integer.parseInt(req.getParameter("supId"));
        String price = req.getParameter("pPrice").trim();
        String stock = req.getParameter("pStock").trim();
        Part part = req.getPart("file");

        double pPrice = Double.NaN;
        int pStock = -1;
        boolean isOke = true;
        
        ProductDAO pd = new ProductDAO();

        //Check empty Product name
        if (pName.equalsIgnoreCase("") || pName == null) {
            req.setAttribute("msgName", "Product Name is empty");
            isOke = false;
        }

        //Check valid price
        try {
            pPrice = Double.parseDouble(price);

            if (pPrice <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            req.setAttribute("msgPrice", "Invalid Price");
            isOke = false;
        }

        //Check valid stock
        try {
            pStock = Integer.parseInt(stock);

            if (pStock < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            req.setAttribute("msgStock", "Invalid Stock");
            isOke = false;
        }

        //Check empty image and valid type image
        if (!part.getContentType().equals("image/jpeg") && !part.getContentType().equals("image/png")) {
            req.setAttribute("msgImage", "Plese upload image file .jpg or .png only");
            isOke = false;
        }

        if (!isOke) {
            req.setAttribute("catId", catId);
            req.setAttribute("pName", pName);
            req.setAttribute("supId", supId);
            req.setAttribute("pPrice", price);
            req.setAttribute("pStock", stock);
        } else {
            
            String url = getUrlImage(part);
            
            try {
                boolean result = pd.createNewProduct(catId, pName, supId, pPrice, pStock, url);
                
                if (result) {
                    req.setAttribute("msgResultSuccess", "Creat new product successfully");
                } else {
                    req.setAttribute("msgResultFail", "Creat new product failed");
                }
            } catch (Exception ex) {
                Logger.getLogger(CreateProductServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

        getData(req);
        req.getRequestDispatcher("addProduct.jsp").forward(req, resp);
    }

    public String getUrlImage(Part part) throws IOException {
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
        savePath = savePath.replace("\\", "/");
        
        return savePath;
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

    public void getData(HttpServletRequest req) {
        CategoryDAO cd = new CategoryDAO();
        SupplierDAO sd = new SupplierDAO();

        List<Category> categoryData = new ArrayList<>();
        List<Supplier> supplierData = new ArrayList<>();

        try {
            categoryData = cd.getAll();
            supplierData = sd.getAll();
        } catch (Exception ex) {
            Logger.getLogger(CreateProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        req.setAttribute("catData", categoryData);
        req.setAttribute("supData", supplierData);
    }

}
