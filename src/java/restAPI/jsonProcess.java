package restAPI;

import dal.CategoryDAO;
import dal.OrderDAO;
import dal.SupplierDAO;
import dal.ProductDAO;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.Category;
import model.Product;
import model.Supplier;
import com.google.gson.Gson;
import dal.UserDAO;
import model.User;

public class jsonProcess {

    public String getMoneyOrder() throws Exception {
        OrderDAO od = new OrderDAO();

        return new Gson().toJson(od.getOrderMoney());
    }

    public String getAllProduct() throws Exception {
        ProductDAO pd = new ProductDAO();
        SupplierDAO sd = new SupplierDAO();
        CategoryDAO cd = new CategoryDAO();

        List<Product> list = new ArrayList<>();
        list = pd.getAll();
        
        List<Map<String, Object>> json = new ArrayList<>();
        
        for (Product p : list) {

            Map<String, Object> lhm = new LinkedHashMap<>();
            lhm.put("id", p.getId());
            lhm.put("name", p.getName());

            Supplier s = sd.getSupplierById(p.getSupplierId());
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", s.getId());
            map.put("supName", s.getName());
            map.put("title", s.getTitle());
            map.put("address", s.getAddress());
            map.put("phone", s.getPhone());

            lhm.put("supplier", map);
            lhm.put("unitPrice", p.getUnitPrice());
            lhm.put("unitInStock", p.getUnitInStock());
            lhm.put("unitOnOrder", p.getUnitOnOrder());
            
            Category c = cd.getCatById(p.getCatId());
            Map<String, Object> map2 = new LinkedHashMap<>();
            map2.put("id", c.getId());
            map2.put("catName", c.getName());
            map2.put("description", c.getDescription());
            
            lhm.put("category", map2);
            
            json.add(lhm);
        }
        
        return new Gson().toJson(json);
    }
    
    public String getTop5CustomerOrder() throws Exception {
        UserDAO ud = new UserDAO();
        
        List<User> list = ud.getTop5UserByTotalMoneyOrder();
        
        return new Gson().toJson(list);
    }
    
    public String getTop5ProductBuy() throws Exception {
        ProductDAO pd = new ProductDAO();
        
        List<Product> list = pd.getTop5Order();
        
        return new Gson().toJson(list);
    }
}
