package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Supplier;

public class SupplierDAO extends DBContext{
    
    public List<Supplier> getAll() throws Exception {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM Supplier";
        
        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                Supplier s = new Supplier();
                s.setId(rs.getInt(1));
                s.setName(rs.getString(2));
                s.setTitle(rs.getString(3));
                s.setAddress(rs.getString(4));
                s.setPhone(rs.getString(5));
                s.setImage(rs.getString(6));
                list.add(s);
            }
        }catch(SQLException e) {
            System.out.println(e);
        }
        
        return list;
    }
    
    public List<Supplier> getTop5() throws Exception {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT TOP 5* FROM dbo.Supplier WHERE Image IS NOT NULL";
        
        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                Supplier s = new Supplier();
                
                s.setId(rs.getInt(1));
                s.setName(rs.getString(2));
                s.setTitle(rs.getString(3));
                s.setAddress(rs.getString(4));
                s.setPhone(rs.getString(5));
                s.setImage(rs.getString(6));
                list.add(s);
            }
        }catch(SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    
    public Supplier getSupplierById(int id) throws Exception {
        String sql = "SELECT * FROM dbo.Supplier WHERE SupplierID=?";
        
        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setInt(1, id);
            
            ResultSet rs = st.executeQuery();
            
            if (rs.next()) {
                Supplier s = new Supplier();
                
                s.setId(rs.getInt(1));
                s.setName(rs.getString(2));
                s.setTitle(rs.getString(3));
                s.setAddress(rs.getString(4));
                s.setPhone(rs.getString(5));
                
                return s;
            }
        }catch(SQLException e) {
            
        }
        return null;
    }
    
    public boolean checkSupplier(String[] suplier, String supId) {
        return Arrays.stream(suplier).anyMatch(supId::contains);
    }
}
