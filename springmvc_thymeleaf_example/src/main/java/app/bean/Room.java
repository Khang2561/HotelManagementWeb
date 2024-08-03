package app.bean;

public class Room {
	private String roomId;
	   private String roomName;
	   private String typeOfRoom;
	   private int priceRoom;
	   private String noteRoom;
	   private int stateRoom;

	   public Room(String roomName, String typeOfRoom, int priceRoom, String noteRoom) {
	      this.roomName = roomName;
	      this.typeOfRoom = typeOfRoom;
	      this.priceRoom = priceRoom;
	      this.noteRoom = noteRoom;
	   }

	   public Room(String roomId, String roomName, String typeOfRoom, int priceRoom, String noteRoom, int stateRoom) {
	      this.roomId = roomId;
	      this.roomName = roomName;
	      this.typeOfRoom = typeOfRoom;
	      this.priceRoom = priceRoom;
	      this.noteRoom = noteRoom;
	      this.stateRoom = stateRoom;
	   }

	   public Room(String roomId, String roomName, String typeOfRoom, int priceRoom, String noteRoom) {
	      this.roomId = roomId;
	      this.roomName = roomName;
	      this.typeOfRoom = typeOfRoom;
	      this.priceRoom = priceRoom;
	      this.noteRoom = noteRoom;
	   }

	   public String getRoomId() {
	      return this.roomId;
	   }

	   public void setRoomId(String roomId) {
	      this.roomId = roomId;
	   }

	   public String getRoomName() {
	      return this.roomName;
	   }

	   public void setRoomName(String roomName) {
	      this.roomName = roomName;
	   }

	   public String getTypeOfRoom() {
	      return this.typeOfRoom;
	   }

	   public void setTypeOfRoom(String typeOfRoom) {
	      this.typeOfRoom = typeOfRoom;
	   }

	   public int getPriceRoom() {
	      return this.priceRoom;
	   }

	   public void setPriceRoom(int priceRoom) {
	      this.priceRoom = priceRoom;
	   }

	   public String getNoteRoom() {
	      return this.noteRoom;
	   }

	   public void setNoteRoom(String noteRoom) {
	      this.noteRoom = noteRoom;
	   }

	   public String toString() {
	      return "Room [roomId=" + this.roomId + ", roomName=" + this.roomName + ", typeOfRoom=" + this.typeOfRoom + ", priceRoom=" + this.priceRoom + ", noteRoom=" + this.noteRoom + "]";
	   }

	   public int getStateRoom() {
	      return this.stateRoom;
	   }

	   public void setStateRoom(int stateRoom) {
	      this.stateRoom = stateRoom;
	   }

	   public Room() {
	   }
}
