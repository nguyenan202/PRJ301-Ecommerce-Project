package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Shipper;

public class ShipperDAO extends DBContext{
    
    public List<Shipper> getAll() throws Exception {
        List<Shipper> list = new ArrayList<>();
        String sql = "SELECT * FROM dbo.Shipper";
        
        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            
            ResultSet rs = st.executeQuery();
            
            while(rs.next()) {
                Shipper s = new Shipper();
                s.setId(rs.getInt(1));
                s.setName(rs.getString(2));
                s.setAddress(rs.getString(3));
                s.setPhone(rs.getString(4));
                s.setShipTime(rs.getString(5));
                s.setShipPrice(rs.getDouble(6));
                
                list.add(s);
            }
        }catch(SQLException e) {
            System.out.println(e);
        }
        
        return list;
    }
}
