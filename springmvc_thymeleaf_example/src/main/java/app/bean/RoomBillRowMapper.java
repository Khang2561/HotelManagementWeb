package app.bean;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class RoomBillRowMapper implements RowMapper<RoomBill> {
    @Override
    public RoomBill mapRow(ResultSet rs, int rowNum) throws SQLException {
        String roomBillId = rs.getString("MaPhieuThue");
        String roomId = rs.getString("MaPhong");
        String roomName = rs.getString("TenPhong");
        String roomDateRent = rs.getString("NgayThue").substring(0, 16);
        String roomDateReturn = rs.getString("NgayTra").substring(0, 16);
        float roomPriceDay = rs.getFloat("DonGiaMotNgay");
        int roomPaymentStatus = rs.getInt("TinhTrangTraTien");

        RoomBill roomInfoIndex = new RoomBill(roomBillId, roomName, roomDateRent, roomPaymentStatus);
        roomInfoIndex.setRoomId(roomId);
        roomInfoIndex.setRoomDateReturn(roomDateReturn);
        roomInfoIndex.setRoomPriceDay(roomPriceDay);

        return roomInfoIndex;
    }
}
