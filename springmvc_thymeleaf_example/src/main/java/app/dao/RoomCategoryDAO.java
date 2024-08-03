package app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import app.bean.Room;

@Repository
public class RoomCategoryDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public RoomCategoryDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Room> getAllRooms() {
        String sql = "SELECT MaPhong, TenPhong, TenLoaiPhong, DonGia, GhiChu, TinhTrang " +
                     "FROM phong join loaiphong on " +
                     "phong.MaLoaiPhong = loaiphong.MaLoaiPhong " +
                     "order by length(MaPhong)";
        return jdbcTemplate.query(sql, new RoomRowMapper());
    }

    public void updateRoom(Room room) {
        String sql = "UPDATE phong " +
                     "SET TenPhong = ?, MaLoaiPhong = ?, GhiChu = ? " +
                     "WHERE MaPhong = ?";
        jdbcTemplate.update(sql,
                room.getRoomName(),
                room.getTypeOfRoom(),
                room.getNoteRoom(),
                room.getRoomId());
    }

    public void deleteRoom(String roomId) {
        String sql = "DELETE FROM phong " +
                     "WHERE MaPhong = ?";
        jdbcTemplate.update(sql, roomId);
    }

    public void addRoom(Room room) {
        String sqlGetId = "SELECT MaPhong FROM phong order by length(MaPhong) asc, MaPhong asc";
        String sqlInsert = "INSERT INTO PHONG VALUES(?,?,?,?,?)";

        List<String> roomIds = jdbcTemplate.query(sqlGetId, (rs, rowNum) -> rs.getString("MaPhong"));
        String nextRoomId = generateNextRoomId(roomIds);

        jdbcTemplate.update(sqlInsert,
                nextRoomId,
                room.getRoomName(),
                room.getTypeOfRoom(),
                room.getNoteRoom(),
                0);
    }

    private String generateNextRoomId(List<String> roomIds) {
        if (roomIds.isEmpty()) {
            return "PHONG1";
        } else {
            String lastRoomId = roomIds.get(roomIds.size() - 1);
            String prefix = lastRoomId.substring(0, 5);
            int suffix = Integer.parseInt(lastRoomId.substring(5)) + 1;
            return prefix + suffix;
        }
    }

    public List<Room> searchRooms(String roomName, String typeRoomId, String price, String status) {
        String sql = "SELECT TenPhong, TenLoaiPhong, DonGia, TinhTrang " +
                     "FROM phong, loaiphong " +
                     "WHERE phong.MaLoaiPhong = loaiphong.MaLoaiPhong";
        
        if (!roomName.trim().isEmpty()) {
            sql += " AND TenPhong = ?";
        }
        if (!typeRoomId.equals("-1")) {
            sql += " AND loaiphong.MaLoaiPhong = ?";
        }
        if (!price.equals("-1")) {
            sql += " AND DonGia = ?";
        }
        if (!status.equals("-1")) {
            sql += " AND TinhTrang = ?";
        }

        Object[] params = buildParamsArray(roomName, typeRoomId, price, status);
        return jdbcTemplate.query(sql, params, new RoomRowMapper());
    }

    private Object[] buildParamsArray(String roomName, String typeRoomId, String price, String status) {
        return new Object[]{
            !roomName.trim().isEmpty() ? roomName : null,
            !typeRoomId.equals("-1") ? typeRoomId : null,
            !price.equals("-1") ? Integer.valueOf(price) : null,
            !status.equals("-1") ? Integer.valueOf(status) : null
        };
    }

    public List<Room> getAvailableRooms() {
        String sql = "SELECT MaPhong, TenPhong, TenLoaiPhong, DonGia, GhiChu, TinhTrang " +
                     "FROM phong join loaiphong on " +
                     "phong.MaLoaiPhong = loaiphong.MaLoaiPhong AND phong.TinhTrang = 0";
        return jdbcTemplate.query(sql, new RoomRowMapper());
    }

    private static final class RoomRowMapper implements RowMapper<Room> {
        @Override
        public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
            String roomId = rs.getString("MaPhong");
            String roomName = rs.getString("TenPhong");
            String typeOfRoom = rs.getString("TenLoaiPhong");
            int price = rs.getInt("DonGia");
            String note = rs.getString("GhiChu");
            int stateRoom = rs.getInt("TinhTrang");
            return new Room(roomId, roomName, typeOfRoom, price, note, stateRoom);
        }
    }
}
