package app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import app.bean.User;

@Repository
public class UserDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM nguoidung ORDER BY LENGTH(TenDangNhap) ASC, TenDangNhap ASC";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    public User getUser(String username, String password) {
        String sql = "SELECT * FROM nguoidung WHERE TenDangNhap = ? AND MatKhau = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), username, password);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }
    
    public User findUser(String username, String password) {
        String sql = "SELECT * FROM nguoidung WHERE TenDangNhap = ? AND MatKhau = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(), username, password);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int getPermission(String roleGroupId, String _AUTHENTICATION_SCREEN) {
        String sql = "SELECT " + _AUTHENTICATION_SCREEN + " FROM phanquyen WHERE MaPhanQuyen = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, roleGroupId);
    }

    public void addUser(User user) {
        String sql = "INSERT INTO nguoidung (TenDangNhap, MatKhau, HoTen, MaPhanQuyen) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUserName(), user.getPassWord(), user.getFullName(), user.getAuthorizationID());
    }

    public void updateUser(String fullName, String userName, String roleId) {
        String sql = "UPDATE nguoidung SET HoTen = ?, MaPhanQuyen = ? WHERE TenDangNhap = ?";
        jdbcTemplate.update(sql, fullName, roleId, userName);
    }

    public void deleteUser(String email) {
        String sql = "DELETE FROM nguoidung WHERE TenDangNhap = ?";
        jdbcTemplate.update(sql, email);
    }

    private static final class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            String TenDangNhap = rs.getString("TenDangNhap");
            String MatKhau = rs.getString("MatKhau");
            String HoTen = rs.getString("HoTen");
            String MaPhanQuyen = rs.getString("MaPhanQuyen");

            return new User(TenDangNhap, MatKhau, HoTen, MaPhanQuyen);
        }
    }
}
