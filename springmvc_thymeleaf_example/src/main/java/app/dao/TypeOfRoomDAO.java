package app.dao;

import app.bean.TypeRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TypeOfRoomDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TypeOfRoomDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TypeRoom> getAllTypeRooms() {
        String sql = "SELECT * FROM loaiphong";
        return jdbcTemplate.query(sql, new TypeRoomRowMapper());
    }

    public void updateTypeRoom(TypeRoom typeRoom) {
        String sql = "UPDATE loaiphong SET TenLoaiPhong = ?, DonGia = ? WHERE MaLoaiPhong = ?";
        jdbcTemplate.update(sql, typeRoom.getNameTypeRoom(), typeRoom.getPrice(), typeRoom.getTypeRoomID());
    }

    public void addTypeRoom(TypeRoom typeRoom) {
        String sqlGetId = "SELECT MaLoaiPhong FROM loaiphong ORDER BY LENGTH(MaLoaiPhong) ASC, MaLoaiPhong ASC";
        String sqlInsert = "INSERT INTO loaiphong VALUES (?, ?, ?)";

        List<String> roomIds = jdbcTemplate.query(sqlGetId, (rs, rowNum) -> rs.getString("MaLoaiPhong"));
        String nextTypeRoomId = generateNextTypeRoomId(roomIds);

        jdbcTemplate.update(sqlInsert, nextTypeRoomId, typeRoom.getNameTypeRoom(), typeRoom.getPrice());
    }

    public void deleteTypeRoom(String typeRoomId) {
        String sql = "DELETE FROM loaiphong WHERE MaLoaiPhong = ?";
        jdbcTemplate.update(sql, typeRoomId);
    }

    private String generateNextTypeRoomId(List<String> roomIds) {
        if (roomIds.isEmpty()) {
            return "LOAIPHONG1";
        } else {
            String lastRoomId = roomIds.get(roomIds.size() - 1);
            String prefix = lastRoomId.substring(0, 9);
            int suffix = Integer.parseInt(lastRoomId.substring(9)) + 1;
            return prefix + suffix;
        }
    }

    private static final class TypeRoomRowMapper implements RowMapper<TypeRoom> {
        @Override
        public TypeRoom mapRow(ResultSet rs, int rowNum) throws SQLException {
            String typeRoomId = rs.getString("MaLoaiPhong");
            String typeRoomName = rs.getString("TenLoaiPhong");
            int price = rs.getInt("DonGia");
            return new TypeRoom(typeRoomId, typeRoomName, price);
        }
    }
}
