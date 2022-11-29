package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Banner;

public class BannerDAO extends DBContext{
    public List<Banner> getAllImage() throws Exception {
        List<Banner> list = new ArrayList<>();
        String sql = "SELECT SupplierID,SupplierName,Image FROM dbo.Supplier WHERE Image IS NOT NULL";
        
        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                Banner b = new Banner();
                b.setId(rs.getInt(1));
                b.setSuppilerName(rs.getString(2));
                b.setImage(rs.getString(3));
                list.add(b);
            }
        }catch(SQLException e) {
            System.out.println(e);
        }
        return list;
    }
}
