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

@WebServlet(urlPatterns = {"/api-admin-product"})
public class ProductApi extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        jsonProcess jp = new jsonProcess();

        String products = "";
        try {
            products = jp.getAllProduct();
        } catch (Exception ex) {
            Logger.getLogger(ProductApi.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.outputResponse(resp, products, 200);
    }

    private void outputResponse(HttpServletResponse res, String payload, int status) {
        res.setHeader("Content-Type", "application/json");
        res.addHeader("Access-Control-Allow-Origin", "*");

        try {
            res.setStatus(status);
            if (payload != null) {
                OutputStream outputStream = res.getOutputStream();
                outputStream.write(payload.getBytes());
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
