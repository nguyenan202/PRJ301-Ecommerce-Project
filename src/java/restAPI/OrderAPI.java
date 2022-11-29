package restAPI;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(urlPatterns = {"/api-admin-order"})
public class OrderAPI extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        jsonProcess jp = new jsonProcess();
        
        String jsonRes = "";
        try {
            jsonRes = jp.getMoneyOrder();
        } catch (Exception ex) {
            Logger.getLogger(OrderAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.outputResponse(resp, jsonRes, 200);
    }
    
    private void outputResponse(HttpServletResponse res, String payload, int status) {
        res.setHeader("Content-Type", "application/json");
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.setCharacterEncoding("UTF-8");
        
        try {
            res.setStatus(status);
            if (payload != null) {
                OutputStream outputStream = res.getOutputStream();
                outputStream.write(payload.getBytes());
                outputStream.flush();
            }
        }catch(IOException e) {}
    } 
}
