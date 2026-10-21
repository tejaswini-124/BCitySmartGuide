import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public boolean register(String fullName, String email, String username, String password)
            throws SQLException {
        String sql = "INSERT INTO users(full_name, email, username, password_hash) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, fullName.trim());
            ps.setString(2, email.trim());
            ps.setString(3, username.trim());
            ps.setString(4, hash(password));
            return ps.executeUpdate() == 1;
        }
    }

    public boolean login(String username, String password) throws SQLException {
        String sql = "SELECT password_hash FROM users WHERE username = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username.trim());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getString("password_hash").equals(hash(password));
            }
        }
    }

    // Demo hashing only. For a production app, use a password-hashing algorithm
    // designed for passwords, such as Argon2id, bcrypt, or PBKDF2 with a unique salt.
    private String hash(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder out = new StringBuilder();
            for (byte b : bytes) out.append(String.format("%02x", b));
            return out.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is not available", e);
        }
    }
}
