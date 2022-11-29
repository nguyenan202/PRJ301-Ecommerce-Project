package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Category;

public class CategoryDAO extends DBContext{
    
    public List<Category> getAll() throws Exception{
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM Categories";
        
        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt(1));
                c.setName(rs.getString(2));
                c.setDescription(rs.getString(3));
                
                list.add(c);
            }
        }catch(SQLException e) {
            System.out.println(e);
        }
        
        return list;
    }
    
    public String getNameById(int id) throws Exception {
        String sql = "SELECT CategoryName FROM Categories WHERE CategoryID=?";
        
        try {
            PreparedStatement st = getConnection().prepareCall(sql);
            st.setInt(1, id);
            
            ResultSet rs = st.executeQuery();
            
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch(SQLException e) {
            System.out.println(e);
        }
        
        return null;
    }
    
    public Category getCatById(int id) throws Exception {
        String sql = "SELECT * FROM dbo.Categories WHERE CategoryID=?";
        
        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setInt(1, id);
            
            ResultSet rs = st.executeQuery();
            
            if (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt(1));
                c.setName(rs.getString(2));
                c.setDescription(rs.getString(3));
                
                return c;
            }
        }catch(SQLException e) {}
        return null;
    }
}
