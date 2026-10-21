import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CityDAO {
    public String getInfo(String category) throws SQLException {
        String sql = "SELECT name, details FROM city_info WHERE category = ? ORDER BY name";
        StringBuilder result = new StringBuilder();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, category);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.append("• ").append(rs.getString("name")).append("\n")
                          .append("  ").append(rs.getString("details")).append("\n\n");
                }
            }
        }
        return result.length() == 0 ? "No information found. Add records to the city_info table." : result.toString();
    }
}
