package controller;

import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class ChangeAvatarServlet extends HttpServlet {
    //D:\JavaWeb    ->    localhost:8080
    public static final String DOMAIN = "http://localhost:8080";
    public static final String UPLOAD_DIR = "image";
    public String dbFileName = "";
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            Part part = req.getPart("file");

        String fileName = extractFileName(part);

        String applicationPath = getServletContext().getRealPath("");
        //applicationPath = DOMAIN+applicationPath.substring(10);
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
        
        HttpSession session = req.getSession();
        User u = (User)session.getAttribute("user");
        
        UserDAO ud = new UserDAO();
        try {
            savePath = DOMAIN+savePath.substring(10);
            savePath = savePath.substring(0,30)+savePath.substring(41);
            boolean s = ud.updateAvatar(u.getUserId(), savePath);
            User userUpdate = ud.getUser(u.getUsername(), u.getPassword());
            session.setAttribute("user", userUpdate);
        } catch (Exception ex) {
            Logger.getLogger(ChangeAvatarServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        req.getRequestDispatcher("profile.jsp").forward(req, resp);
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
