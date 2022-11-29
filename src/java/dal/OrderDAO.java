package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import model.Cart;
import model.Item;
import model.Order;
import model.Product;
import model.User;

public class OrderDAO extends DBContext {

    private String toStringMonth(int month) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return months[month - 1];
    }

    //Total Rejected By Month
    private double getTotalMoneyRejectedByMoth(int month) throws Exception {
        String sql = "SELECT * FROM dbo.Orders WHERE MONTH(OrderDate)=? AND Status = -1";

        double total = 0;

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setInt(1, month);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                total += rs.getDouble(6);
            }
        } catch (SQLException e) {
        }

        return total;
    }

    //Total Pending By Month
    private double getTotalMoneyPendingByMoth(int month) throws Exception {
        String sql = "SELECT * FROM dbo.Orders WHERE MONTH(OrderDate)=? AND Status = 0";

        double total = 0;

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setInt(1, month);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                total += rs.getDouble(6);
            }
        } catch (SQLException e) {
        }

        return total;
    }

    //Total Accepted By Month
    private double getTotalMoneyAcceptedByMoth(int month) throws Exception {
        String sql = "SELECT * FROM dbo.Orders WHERE MONTH(OrderDate)=? AND Status = 1";

        double total = 0;

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setInt(1, month);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                total += rs.getDouble(6);
            }
        } catch (SQLException e) {
        }

        return total;
    }

    public List<Object> getOrderMoney() throws Exception {
        List<Object> list = new ArrayList<>();
        int thisMonth = LocalDate.now().getMonthValue();

        for (int i = 1; i <= thisMonth; ++i) {
            LinkedHashMap<String, Double> orders = new LinkedHashMap<>();
            orders.put("Rejected", this.getTotalMoneyRejectedByMoth(i));
            orders.put("Pending", this.getTotalMoneyPendingByMoth(i));
            orders.put("Accepted", this.getTotalMoneyAcceptedByMoth(i));

            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("id", i);
            map.put("month", this.toStringMonth(i));
            map.put("order", orders);

            list.add(map);
        }

        return list;
    }

    public int doOrder(User user, Cart cart, int shipId, int addressId) throws Exception {
        String sql1 = "INSERT INTO dbo.Orders VALUES (?,?,?,?,?,?,NULL)";

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();

        try {
            PreparedStatement st = getConnection().prepareStatement(sql1);
            st.setInt(1, user.getUserId());
            st.setInt(2, shipId);
            st.setString(3, dtf.format(now));
            st.setInt(4, addressId);
            st.setDouble(5, getTotalmoney(cart) + getShipPrice(shipId));
            st.setInt(6, 0);

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return -1;
        }

        // Get Order ID this order
        String sql2 = "SELECT TOP(1) * FROM dbo.Orders ORDER BY OrderID DESC";

        try {
            PreparedStatement st = getConnection().prepareStatement(sql2);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return -1;
    }

    //Get ship price by ShipperId
    private double getShipPrice(int shipId) throws Exception {
        String sql = "SELECT * FROM dbo.Shipper WHERE ShipperID=?";

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);

            st.setInt(1, shipId);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getDouble(6);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

    public boolean doOrderDetail(Cart cart, int orderId) throws Exception {
        String sql = "INSERT INTO dbo.OrderDetail VALUES (?,?,?,?)";

        for (Item item : cart.getList()) {
            try {
                PreparedStatement st = getConnection().prepareStatement(sql);

                st.setInt(1, orderId);
                st.setInt(2, item.getProduct().getId());
                st.setInt(3, item.getQuantity());
                st.setDouble(4, Double.parseDouble(String.format("%.2f", item.getPrice())));

                st.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e);
                return false;
            }
        }

        return true;
    }

    public List<Order> getAllOrderByUserId(User u) throws Exception {
        List<Order> list = new ArrayList<>();
        String sql
                = "SELECT OrderID,dbo.Shipper.CompanyName,OrderDate,AddressLine,dbo.Shipper.ShipPrice,TotalMoney,[Status], LastName + ' ' + FirstName AS 'FullName'  FROM dbo.Orders\n"
                + "INNER JOIN dbo.Shipper ON Shipper.ShipperID = Orders.ShipperID\n"
                + "INNER JOIN dbo.[Address] ON Address.AddressID = Orders.AddressID\n"
                + "INNER JOIN dbo.[User] ON [User].UserID = Orders.UserID\n"
                + "WHERE Address.UserID=?\n"
                + "ORDER BY OrderDate DESC,OrderID DESC";

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setInt(1, u.getUserId());

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt(1));
                o.setShipper(rs.getString(2));
                o.setDate(rs.getString(3));
                o.setAddress(rs.getString(4));
                o.setShipPrice(rs.getDouble(5));
                o.setTotalPrice(rs.getDouble(6));
                o.setStatus(rs.getInt(7));
                o.setUserName(rs.getString(8));

                list.add(o);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    public List<Item> getAllOrderDetailByOrderId(int orderId) throws Exception {
        List<Item> list = new ArrayList<>();
        String sql
                = "SELECT OrderID,ProductName,Categories.CategoryID,Quantity,OrderDetail.UnitPrice FROM dbo.OrderDetail\n"
                + "INNER JOIN dbo.Products ON Products.ProductID = OrderDetail.ProdcutID\n"
                + "INNER JOIN dbo.Categories ON Categories.CategoryID = Products.CategoryID\n"
                + "WHERE OrderID=?";

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setInt(1, orderId);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Item i = new Item();
                Product p = new Product();

                p.setName(rs.getString("ProductName"));
                p.setCatId(rs.getInt("CategoryID"));

                i.setProduct(p);
                i.setQuantity(rs.getInt("Quantity"));
                i.setPrice(rs.getDouble("UnitPrice"));

                list.add(i);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    public String getShipperByOrderId(int orderId) throws Exception {
        String sql = "SELECT dbo.Shipper.CompanyName FROM dbo.Orders\n"
                + "INNER JOIN dbo.Shipper ON Shipper.ShipperID = Orders.ShipperID\n"
                + "WHERE OrderID=?";

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);

            st.setInt(1, orderId);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return "None";
    }

    public double getShipPriceByOrderId(int orderId) throws Exception {
        String sql = "SELECT dbo.Shipper.ShipPrice FROM dbo.Orders\n"
                + "INNER JOIN dbo.Shipper ON Shipper.ShipperID = Orders.ShipperID\n"
                + "WHERE OrderID=?";

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);

            st.setInt(1, orderId);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public double getTotalMoneyByOrderId(int orderId) throws Exception {
        String sql = "SELECT * FROM dbo.Orders WHERE OrderID=?";

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setInt(1, orderId);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getDouble(6);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public List<Order> getAllOrderForAdmin() throws Exception {
        List<Order> list = new ArrayList<>();

        String sql = "SELECT OrderID,LastName+' '+FirstName AS'FullName',OrderDate,TotalMoney,Status FROM dbo.Orders\n"
                + "INNER JOIN dbo.[User] ON [User].UserID = Orders.UserID ORDER BY OrderDate DESC";

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt(1));
                o.setUserName(rs.getString(2));
                o.setDate(rs.getString(3));
                o.setTotalPrice(rs.getDouble(4));
                o.setStatus(rs.getInt(5));

                list.add(o);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    public void updateStatusOrder(int orderId, int status) throws Exception {
        String sql = "UPDATE dbo.Orders SET Status=? WHERE OrderID=?";

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);

            st.setInt(1, status);
            st.setInt(2, orderId);

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private double getTotalmoney(Cart cart) {
        double total = 0;

        for (Item i : cart.getList()) {
            total += i.getPrice();
        }
        return total;
    }

    public List<Order> getOrderInManagment(String input) throws Exception {
        input = input.toLowerCase();
        List<Order> listAll = this.getAllOrderForAdmin();
        List<Order> listAfterFilter = new ArrayList<>();

        for (Order o : listAll) {
            if ((o.getOrderId() + "").toLowerCase().contains(input)
                    || o.getUserName().toLowerCase().contains(input)
                    || o.getDate().toLowerCase().contains(input)
                    || (o.getTotalPrice() + "").contains(input)) {
                listAfterFilter.add(o);
            }
        }

        return listAfterFilter;
    }
    
    public Order getOrderById(int id) throws Exception {
        String sql = "SELECT * FROM dbo.Orders WHERE OrderID = ?";
        
        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setInt(1, id);
            
            ResultSet rs = st.executeQuery();
            
            if (rs.next()) {
                Order d = new Order();
                
                d.setOrderId(id);
                d.setDate(rs.getString(4));
                d.setTotalPrice(rs.getDouble(6));
                d.setStatus(rs.getInt(7));
                d.setReason(rs.getString(8));
                
                return d;
            }
        }catch(SQLException e) {}
        
        return null;
    }
    
    public boolean cancelOrder(int oId, String reason) throws Exception {
        String sql = "UPDATE dbo.Orders SET Reason = ?,Status=-2 WHERE OrderID = ?";
        
        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setString(1, reason);
            st.setInt(2, oId);
            st.executeUpdate();
            
            return true;
        }catch(SQLException e) {}
        
        return false;
    }
}
