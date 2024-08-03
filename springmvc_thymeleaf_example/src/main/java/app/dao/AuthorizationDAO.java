package app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import app.bean.Authorization;

@Repository
public class AuthorizationDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorizationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Authorization> getAllAuthorization() {
        String sql = "SELECT * FROM phanquyen";
        return jdbcTemplate.query(sql, new AuthorizationRowMapper());
    }

    public void updateRole(Authorization auth) {
        String sql = "UPDATE phanquyen SET QuyenDanhMucPhong = ?, QuyenPhieuThuePhong = ?, QuyenTraCuuPhong = ?, QuyenLapHoaDonThanhToan = ?, QuyenLapBaoCaoDoanhThu = ?, QuyenLapPhanQuyen = ?, QuyenLapQuyDinh = ? WHERE MaPhanQuyen = ?";
        jdbcTemplate.update(sql,
                auth.getRoomCategoryScreen(),
                auth.getBillForRentScreen(),
                auth.getSearchScreen(),
                auth.getRecieptScreen(),
                auth.getRevenueScreen(),
                auth.getAuthorizationScreen(),
                auth.getSettingScreen(),
                auth.getAuthorizationId());
    }

    private static final class AuthorizationRowMapper implements RowMapper<Authorization> {
        @Override
        public Authorization mapRow(ResultSet rs, int rowNum) throws SQLException {
            String maPhanQuyen = rs.getString("MaPhanQuyen");
            String tenPhanQuyen = rs.getString("TenPhanQuyen");
            int manHinhDanhMucPhong = rs.getInt("QuyenDanhMucPhong");
            int manHinhThuePhong = rs.getInt("QuyenPhieuThuePhong");
            int manHinhTraCuu = rs.getInt("QuyenTraCuuPhong");
            int manHinhThanhToan = rs.getInt("QuyenLapHoaDonThanhToan");
            int manHinhDoanhThu = rs.getInt("QuyenLapBaoCaoDoanhThu");
            int manHinhPhanQuyen = rs.getInt("QuyenLapPhanQuyen");
            int manHinhQuyDinh = rs.getInt("QuyenLapQuyDinh");

            return new Authorization(maPhanQuyen, tenPhanQuyen, manHinhDanhMucPhong, manHinhThuePhong, manHinhTraCuu, manHinhThanhToan, manHinhDoanhThu, manHinhPhanQuyen, manHinhQuyDinh);
        }
    }
}
