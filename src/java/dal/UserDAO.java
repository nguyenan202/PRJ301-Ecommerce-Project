package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Address;
import model.User;

public class UserDAO extends DBContext {

    //already exist => return false, else return true
    public boolean checkExistUsername(String username) throws Exception {
        String sql = "SELECT * FROM dbo.[User] WHERE Username=?";

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();

            if (!rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    //Success => return true, else return false
    public boolean createUser(User u) throws Exception {
        String sql = "INSERT INTO dbo.[User] VALUES(?,?,?,?,?,?,?)";

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setString(1, u.getUsername());
            st.setString(2, u.getPassword());
            st.setString(3, u.getFirstName());
            st.setString(4, u.getLastName());
            st.setString(5, u.getEmail());
            st.setString(6, u.getPhone());
            st.setInt(7, u.getRole());

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    //username,password correct => return true, else return false
    public boolean checkPassword(String username, String password) throws Exception {
        String sql = "SELECT * FROM dbo.[User] WHERE Username=? AND Password=?";

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public User getUser(String username, String password) throws Exception {
        String sql = "SELECT * FROM dbo.[User] WHERE Username=? AND Password=?";
        User u = null;

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                u = new User();
                u.setUserId(rs.getInt(1));
                u.setUsername(rs.getString(2));
                u.setPassword(rs.getString(3));
                u.setFirstName(rs.getString(4));
                u.setLastName(rs.getString(5));
                u.setEmail(rs.getString(6));
                u.setPhone(rs.getString(7));
                u.setRole(rs.getInt(8));
                u.setImage(rs.getString(9));

                return u;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return u;
    }

    public boolean updateUser(int id, String fname, String lname, String email, String phone) throws Exception {
        String sql = "UPDATE dbo.[User] SET FirstName=?,LastName=?,Email=?,Phone=? WHERE UserID=?";

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setString(1, fname);
            st.setString(2, lname);
            st.setString(3, email);
            st.setString(4, phone);
            st.setInt(5, id);

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }

        return true;
    }

    public boolean changePassword(int id, String newPassword) throws Exception {
        String sql = "UPDATE dbo.[User] SET Password=? WHERE UserID=?";

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setString(1, newPassword);
            st.setInt(2, id);

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }

        return true;
    }
    
    public boolean changePasswordByUsername(String username, String newPassword) throws Exception {
        String sql = "UPDATE dbo.[User] SET Password=? WHERE Username=?";

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setString(1, newPassword);
            st.setString(2, username);

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }

        return true;
    }

    public List<Address> getAddressByUserId(int id) throws Exception {
        List<Address> list = new ArrayList<>();
        String sql = "SELECT * FROM dbo.Address WHERE UserID=?";

        try {
            PreparedStatement st = getConnection().prepareCall(sql);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Address ad = new Address();
                ad.setId(rs.getInt(1));
                ad.setAddressLine(rs.getString(2));
                ad.setUserId(id);

                list.add(ad);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    public boolean deleteAddressById(int id) throws Exception {
        String sql = "DELETE dbo.Address WHERE AddressID=?";

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setInt(1, id);

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean addNewAddress(int userId, String address) throws Exception {
        String sql = "INSERT INTO dbo.[Address] VALUES (?,?)";

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setString(1, address);
            st.setInt(2, userId);

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public List<User> getTop5UserByTotalMoneyOrder() throws Exception {
        String sql = "SELECT TOP 5 dbo.[User].UserID,Username, SUM(dbo.Orders.TotalMoney) AS 'Total' FROM dbo.[User] INNER JOIN dbo.Orders\n"
                + "ON Orders.UserID = [User].UserID\n"
                + "WHERE Status = 1\n"
                + "GROUP BY dbo.[User].UserID,Username\n"
                + "ORDER BY [User].UserID";

        List<User> list = new ArrayList<>();

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                User u = new User();

                u.setUserId(rs.getInt(1));
                u.setUsername(rs.getString(2));
                u.setTotalMoney(rs.getDouble(3));

                list.add(u);
            }
        } catch (SQLException e) {
        }

        return list;
    }

    public boolean updateAvatar(int id, String path) throws Exception {
        String sql = "UPDATE dbo.[User] SET [Image] = ?\n"
                + "WHERE UserID = ?";

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setString(1, path);
            st.setInt(2, id);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
        }
        return false;
    }

    public boolean isValidResetPassword(String username, String email) throws Exception {
        String sql = "SELECT * FROM dbo.[User] WHERE Username = ? AND Email = ?";

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, email);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
        }

        return false;
    }
    
    public String genNewPassword(int size) {
        
        String pass = "";
        int min = 0, max = 9;
        
        for (int i = 0; i < size; ++i) {
            pass += (int)Math.floor(Math.random()*(max-min+1)+min);
        }
        
        return pass;
    }
}
