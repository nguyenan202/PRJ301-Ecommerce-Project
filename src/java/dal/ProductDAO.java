package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Product;

public class ProductDAO extends DBContext {

    private static final int numberPerPage = 20;

    public List<Product> getAll() throws Exception {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Products";

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Product pd = new Product();

                pd.setId(rs.getInt(1));
                pd.setName(rs.getString(2));
                pd.setSupplierId(rs.getInt(3));
                pd.setUnitPrice(rs.getDouble(4));
                pd.setUnitInStock(rs.getInt(5));
                pd.setUnitOnOrder(rs.getInt(6));
                pd.setCatId(rs.getInt(7));
                pd.setImage(rs.getString(8));

                list.add(pd);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    public List<Product> getTop4() throws Exception {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT TOP 4 * FROM Products ORDER BY UnitOnOrder DESC";

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Product pd = new Product();

                pd.setId(rs.getInt(1));
                pd.setName(rs.getString(2));
                pd.setSupplierId(rs.getInt(3));
                pd.setUnitPrice(rs.getDouble(4));
                pd.setUnitInStock(rs.getInt(5));
                pd.setUnitOnOrder(rs.getInt(6));
                pd.setCatId(rs.getInt(7));
                pd.setImage(rs.getString(8));

                list.add(pd);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    public int getNumberProducts() throws Exception {
        String sql = "SELECT COUNT(*) FROM Products";

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public List<Product> getProductByPage(List<Product> list, int page) {
        int size = list.size();
        List<Product> newList = new ArrayList<>();

        int start = (page - 1) * numberPerPage;
        int end = (page * numberPerPage < size) ? page * numberPerPage : size;

        for (int i = start; i < end; i++) {
            newList.add(list.get(i));
        }

        return newList;
    }

    public int getNumberOfPageFirst() throws Exception {
        return getNumberOfPage(getAll());
    }

    public int getNumberOfPage(List<Product> list) {
        return list.size() % numberPerPage == 0 ? list.size() / numberPerPage : (list.size() / numberPerPage) + 1;
    }

    public List<Product> getAllFilter(String[] supplier, String name, String sort, double priceS, double priceE) throws Exception {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Products  WHERE UnitPrice >= ? AND UnitPrice <= ?";
        if (supplier != null) {
            sql += " AND SupplierID IN " + Arrays.toString(supplier).replace('[', '(').replace(']', ')');
        }
        if (!name.equalsIgnoreCase("")) {
            sql += " AND ProductName LIKE '%" + name + "%'";
        }
        if (sort.equalsIgnoreCase("0")) {
            sql += " ORDER BY UnitPrice DESC";
        } else if (sort.equalsIgnoreCase("1")) {
            sql += " ORDER BY UnitPrice ASC";
        }

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setDouble(1, priceS);
            st.setDouble(2, priceE);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Product pd = new Product();

                pd.setId(rs.getInt(1));
                pd.setName(rs.getString(2));
                pd.setSupplierId(rs.getInt(3));
                pd.setUnitPrice(rs.getDouble(4));
                pd.setUnitInStock(rs.getInt(5));
                pd.setUnitOnOrder(rs.getInt(6));
                pd.setCatId(rs.getInt(7));
                pd.setImage(rs.getString(8));

                list.add(pd);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    public List<Product> getFilter(String[] supplier, String name, String sort, int catId, double priceS, double priceE) throws Exception {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Products  WHERE CategoryID=? AND UnitPrice >= ? AND UnitPrice <= ? AND SupplierID IN " + Arrays.toString(supplier).replace('[', '(').replace(']', ')');

        if (!name.equals("")) {
            sql += " AND ProductName LIKE '%" + name + "%'";
        }
        if (sort.equalsIgnoreCase("0")) {
            sql += " ORDER BY UnitPrice DESC";
        } else if (sort.equalsIgnoreCase("1")) {
            sql += " ORDER BY UnitPrice ASC";
        }

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setInt(1, catId);
            st.setDouble(2, priceS);
            st.setDouble(3, priceE);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Product pd = new Product();

                pd.setId(rs.getInt(1));
                pd.setName(rs.getString(2));
                pd.setSupplierId(rs.getInt(3));
                pd.setUnitPrice(rs.getDouble(4));
                pd.setUnitInStock(rs.getInt(5));
                pd.setUnitOnOrder(rs.getInt(6));
                pd.setCatId(rs.getInt(7));
                pd.setImage(rs.getString(8));

                list.add(pd);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    public Product getProductById(int id) throws Exception {
        Product pd = null;
        String sql = "SELECT * FROM Products WHERE ProductID=?";

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                pd = new Product();

                pd.setId(rs.getInt(1));
                pd.setName(rs.getString(2));
                pd.setSupplierId(rs.getInt(3));
                pd.setUnitPrice(rs.getDouble(4));
                pd.setUnitInStock(rs.getInt(5));
                pd.setUnitOnOrder(rs.getInt(6));
                pd.setCatId(rs.getInt(7));
                pd.setImage(rs.getString(8));

                return pd;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return pd;
    }

    public int getUnitInStockByProductId(int id) throws Exception {
        String sql = "SELECT UnitInStock FROM Products WHERE ProductID=?";

        try {
            PreparedStatement st = getConnection().prepareCall(sql);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

    public List<Product> getAllWithNoneSupplier(String name, String sort, int catId, double priceS, double priceE) throws Exception {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Products  WHERE CategoryID=? AND UnitPrice >= ? AND UnitPrice <= ?";

        if (!name.equals("")) {
            sql += " AND ProductName LIKE '%" + name + "%'";
        }
        if (sort.equalsIgnoreCase("0")) {
            sql += " ORDER BY UnitPrice DESC";
        } else if (sort.equalsIgnoreCase("1")) {
            sql += " ORDER BY UnitPrice ASC";
        }

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setInt(1, catId);
            st.setDouble(2, priceS);
            st.setDouble(3, priceE);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Product pd = new Product();

                pd.setId(rs.getInt(1));
                pd.setName(rs.getString(2));
                pd.setSupplierId(rs.getInt(3));
                pd.setUnitPrice(rs.getDouble(4));
                pd.setUnitInStock(rs.getInt(5));
                pd.setUnitOnOrder(rs.getInt(6));
                pd.setCatId(rs.getInt(7));
                pd.setImage(rs.getString(8));

                list.add(pd);
            }
        } catch (SQLException e) {
        }

        return list;
    }

    public List<Product> getTop5Order() throws Exception {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT TOP 5 ProductID,ProductName,SUM(Quantity) AS 'quantity' FROM dbo.OrderDetail INNER JOIN dbo.Products\n"
                + "ON Products.ProductID = OrderDetail.ProdcutID INNER JOIN dbo.Orders \n"
                + "ON Orders.OrderID = OrderDetail.OrderID\n"
                + "WHERE Status = 1\n"
                + "GROUP BY ProductID,ProductName\n"
                + "ORDER BY Quantity DESC";

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt(1));
                p.setName(rs.getString(2));
                p.setUnitOnOrder(rs.getInt(3));

                list.add(p);
            }

        } catch (SQLException e) {
        }

        return list;
    }

    public boolean updateProductNoImage(int id, String name, double price, int stock) throws Exception {
        String sql = "UPDATE dbo.Products\n"
                + "SET ProductName = ?,\n"
                + "UnitPrice = ?,\n"
                + "UnitInStock = ?\n"
                + "WHERE ProductID = ?";
        
        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setString(1, name);
            st.setDouble(2, price);
            st.setInt(3, stock);
            st.setInt(4, id);
            
            st.executeUpdate();
            return true;
        }catch(SQLException e) {}
        
        return false;
    }
    
    public boolean updateProductWithImage(int id, String name, double price, int stock, String url) throws Exception {
        String sql = "UPDATE dbo.Products\n"
                + "SET ProductName = ?,\n"
                + "UnitPrice = ?,\n"
                + "UnitInStock = ?,\n"
                + "[image] = ?\n"
                + "WHERE ProductID = ?";
        
        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setString(1, name);
            st.setDouble(2, price);
            st.setInt(3, stock);
            st.setString(4, url);
            st.setInt(5, id);
            
            st.executeUpdate();
            return true;
        }catch(SQLException e) {}
        
        return false;
    }
    
    public boolean createNewProduct(int catId, String name, int supId, double price, int stock, String url) throws Exception {
        String sql = "INSERT INTO dbo.Products VALUES(?,?,?,?,DEFAULT,?,?)";
        
        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setString(1, name);
            st.setInt(2, supId);
            st.setDouble(3, price);
            st.setInt(4, stock);
            st.setInt(5, catId);
            st.setString(6, url);
            
            st.executeUpdate();
            
            return true;
        }catch(SQLException e) {}
        
        return false;
    }
    
    public boolean deleteProductById(int id) throws Exception {
        String sql = "DELETE dbo.Products WHERE ProductID = ?";
        
        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setInt(1, id);
            
            st.executeUpdate();
            return true;
        }catch(SQLException e) {}
        
        return false;
    }
}
