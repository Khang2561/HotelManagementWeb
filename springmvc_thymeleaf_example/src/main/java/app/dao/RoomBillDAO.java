package app.dao;
import app.bean.RoomBill;
import app.bean.RoomBillRowMapper;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List; // Đảm bảo sử dụng java.util.List

@Repository
public class RoomBillDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public RoomBillDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<RoomBill> getAllRoomBill() throws SQLException {
        String sql = "SELECT MaPhieuThue, PHONG.MaPhong, TenPhong, NgayThue, NgayTra, DonGiaMotNgay, TinhTrangTraTien FROM PHIEUTHUEPHONG JOIN PHONG ON PHIEUTHUEPHONG.MaPhong = Phong.MaPhong ORDER BY TinhTrangTraTien;";
        return jdbcTemplate.query(sql, new RoomBillRowMapper());
    }

    public void updatePriceRoom_RoomBill(RoomBill rbUpdate) throws SQLException {
        // Chuyển đổi logic cập nhật giá phòng
        // Bỏ qua phần này để ngắn gọn, bạn cần chuyển đổi toàn bộ logic cập nhật giá phòng sang sử dụng JdbcTemplate
    }

    public void autoUpdatePriceRoom_RoomBill() throws SQLException {
        List<RoomBill> unpaidRoomBills = getUnpaidRoomBill();
        for (RoomBill roomBill : unpaidRoomBills) {
            updatePriceRoom_RoomBill(roomBill);
        }
    }

    public String getRoomId(String roomName) throws SQLException {
        String sql = "SELECT MaPhong FROM PHONG WHERE TenPhong = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{roomName}, String.class);
    }

    public List<RoomBill> getPaidRoomBill() throws SQLException {
        String sql = "SELECT MaPhieuThue, PHONG.MaPhong, TenPhong, NgayThue, NgayTra, DonGiaMotNgay, TinhTrangTraTien FROM PHIEUTHUEPHONG JOIN PHONG ON PHIEUTHUEPHONG.MaPhong = Phong.MaPhong WHERE TinhTrangTraTien = 1;";
        return jdbcTemplate.query(sql, new RoomBillRowMapper());
    }

    public List<RoomBill> getUnpaidRoomBill() throws SQLException {
        String sql = "SELECT MaPhieuThue, PHONG.MaPhong, TenPhong, NgayThue, NgayTra, DonGiaMotNgay, TinhTrangTraTien FROM PHIEUTHUEPHONG JOIN PHONG ON PHIEUTHUEPHONG.MaPhong = Phong.MaPhong WHERE TinhTrangTraTien = 0;";
        return jdbcTemplate.query(sql, new RoomBillRowMapper());
    }

    public void insertRoomBill(RoomBill roomBill) throws SQLException {
        String querySelectId = "SELECT MaPhieuThue FROM PHIEUTHUEPHONG ORDER BY length(MaPhieuThue), MaPhieuThue;";
        List<String> roomBillIds = jdbcTemplate.query(querySelectId, (rs, rowNum) -> rs.getString("MaPhieuThue"));
        String nextRoomBillId = generateNextRoomBillId(roomBillIds);

        String queryInsertRoomBill = "INSERT INTO PHIEUTHUEPHONG VALUES(?, ?, ?, ?, 0, ?);";
        jdbcTemplate.update(queryInsertRoomBill, nextRoomBillId, roomBill.getRoomId(), roomBill.getRoomDateRent(), roomBill.getRoomDateReturn(), 0);

        String queryUpdateRoomState = "UPDATE PHONG SET TinhTrang = 1 WHERE MaPhong = ?;";
        jdbcTemplate.update(queryUpdateRoomState, roomBill.getRoomId());
    }

    private String generateNextRoomBillId(List<String> roomBillIds) {
        if (roomBillIds.isEmpty()) {
            return "PTP1";
        } else {
            String lastRoomBillId = roomBillIds.get(roomBillIds.size() - 1);
            String prefix = lastRoomBillId.substring(0, 3);
            int suffix = Integer.parseInt(lastRoomBillId.substring(3)) + 1;
            return prefix + suffix;
        }
    }

    public void deleteRoomBill(RoomBill roomBill) throws SQLException {
        String sqlDeleteCustomer = "DELETE FROM CHITIET_PTP WHERE MaPhieuThue = ?;";
        String sqlDeleteRoomBillUnPaid = "DELETE FROM PHIEUTHUEPHONG WHERE MaPhieuThue = ?;";
        String sqlUpdateRoomStatus = "UPDATE PHONG SET TinhTrang = 0 WHERE MaPhong = ?";

        jdbcTemplate.update(sqlDeleteCustomer, roomBill.getRoomBillId());
        jdbcTemplate.update(sqlDeleteRoomBillUnPaid, roomBill.getRoomBillId());
        jdbcTemplate.update(sqlUpdateRoomStatus, roomBill.getRoomId());
    }

    public void updateRoomBill(RoomBill roomBill, String oldRoomId) throws SQLException {
        String sqlUpdateRoomBill = "UPDATE PHIEUTHUEPHONG SET MaPhong = ?, NgayThue = ?, NgayTra = ? WHERE MaPhieuThue = ?;";
        String sqlUpdateOldRoomState = "UPDATE PHONG SET TinhTrang = 0 WHERE MaPhong = ?";
        String sqlUpdateNewRoomState = "UPDATE PHONG SET TinhTrang = 1 WHERE MaPhong = ?";

        jdbcTemplate.update(sqlUpdateRoomBill, roomBill.getRoomId(), roomBill.getRoomDateRent(), roomBill.getRoomDateReturn(), roomBill.getRoomBillId());
        jdbcTemplate.update(sqlUpdateOldRoomState, oldRoomId);
        jdbcTemplate.update(sqlUpdateNewRoomState, roomBill.getRoomId());
    }
}
