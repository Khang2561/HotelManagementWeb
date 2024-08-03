package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import app.bean.Room;
import app.bean.User;
import app.dao.RoomBillDAO;
import app.dao.RoomCategoryDAO;
import app.dao.TypeOfRoomDAO;
import app.dao.UserDAO;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/room-category")
public class RoomCategoryController {

    @Autowired
    private RoomCategoryDAO roomDAO;

    @Autowired
    private TypeOfRoomDAO typeOfRoomDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoomBillDAO roomBillDAO;

    private final String SCREEN = "QuyenDanhMucPhong";

    @GetMapping
    public String handleRoomCategory(
            @RequestParam(name = "ACTION", defaultValue = "LIST") String action,
            HttpSession session,
            Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        String roleGroupId = getRoleGroupOfUser(session);
        int permissionFlag = 0;

        try {
            permissionFlag = getPermission(roleGroupId, SCREEN);
        } catch (Exception e) {
            e.printStackTrace();
            // Return an error view or handle the exception appropriately
            return "error";
        }

        if (permissionFlag == 1) {
            try {
                switch (action) {
                    case "UPDATE":
                        return "redirect:/room-category/update";
                    case "ADD":
                        return "redirect:/room-category/add";
                    case "DELETE":
                        return "redirect:/room-category/delete";
                    case "LIST":
                    default:
                        return listRooms(model);
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Return an error view or handle the exception appropriately
                return "error";
            }
        } else {
            return "redirect:/error";
        }
    }

    private int getPermission(String roleGroupId, String _AUTHENTICATION_SCREEN) throws SQLException {
        return userDAO.getPermission(roleGroupId, _AUTHENTICATION_SCREEN);
    }

    private String getRoleGroupOfUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user.getAuthorizationID();
    }

    @PostMapping("/delete")
    public String deleteRoom(@RequestParam("roomId") String roomId) throws SQLException, IOException {
        roomDAO.deleteRoom(roomId);
        return "redirect:/room-category";
    }

    @PostMapping("/update")
    public String updateRoom(
            @RequestParam("roomId") String roomId,
            @RequestParam("nameRoom") String nameRoom,
            @RequestParam("typeRoom") String typeRoom,
            @RequestParam("noteRoom") String noteRoom) throws SQLException, IOException {
        Room room = new Room();
        room.setRoomId(roomId);
        room.setRoomName(nameRoom);
        room.setTypeOfRoom(typeRoom);
        room.setNoteRoom(noteRoom);
        roomDAO.updateRoom(room);
        roomBillDAO.autoUpdatePriceRoom_RoomBill();
        return "redirect:/room-category";
    }

    @PostMapping("/add")
    public String addRoom(
            @RequestParam("nameRoom") String nameRoom,
            @RequestParam("typeRoom") String typeRoom,
            @RequestParam("noteRoom") String noteRoom) throws SQLException, IOException {
        Room room = new Room();
        room.setRoomName(nameRoom);
        room.setTypeOfRoom(typeRoom);
        room.setNoteRoom(noteRoom);
        room.setStateRoom(0);
        roomDAO.addRoom(room);
        return "redirect:/room-category";
    }

    private String listRooms(Model model) throws SQLException {
        List<Room> listRooms = roomDAO.getAllRooms();
        List<app.bean.TypeRoom> listTypeOfRooms = typeOfRoomDAO.getAllTypeRooms();
        model.addAttribute("listRooms", listRooms);
        model.addAttribute("listTypeRooms", listTypeOfRooms);
        return "views/RoomCategory/RoomCategory";
    }
}
