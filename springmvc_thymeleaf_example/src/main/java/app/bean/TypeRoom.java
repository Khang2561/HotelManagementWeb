package app.bean;

public class TypeRoom {
	private String typeRoomID;
	private String nameTypeRoom;
	private int price;

	public TypeRoom(String nameTypeRoom, int price) {
		this.nameTypeRoom = nameTypeRoom;
		this.price = price;
	}

	public TypeRoom(String typeRoomID, String nameTypeRoom, int price) {
		this.typeRoomID = typeRoomID;
		this.nameTypeRoom = nameTypeRoom;
		this.price = price;
	}

	public TypeRoom() {
	}

	public String getTypeRoomID() {
		return this.typeRoomID;
	}

	public void setTypeRoomID(String typeRoomID) {
		this.typeRoomID = typeRoomID;
	}

	public String getNameTypeRoom() {
		return this.nameTypeRoom;
	}

	public void setNameTypeRoom(String nameTypeRoom) {
		this.nameTypeRoom = nameTypeRoom;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
