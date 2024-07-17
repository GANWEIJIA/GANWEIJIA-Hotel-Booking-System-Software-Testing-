package utar;

public class Room {
	private int vip;
	private int deluxe;
	private int standard;

	public Room(int vip, int deluxe, int standard) {
		this.vip = vip;
		this.deluxe = deluxe;
		this.standard = standard;
	}

	public boolean checkRoom(String roomType, int numOfRooms) {
		switch (roomType) {
		case "VIP":
			return vip >= numOfRooms;
		case "Deluxe":
			return deluxe >= numOfRooms;
		case "Standard":
			return standard >= numOfRooms;
		default:
			return false;
		}
	}

	public void bookRoom(String roomType, int numOfRooms) {
		switch (roomType) {
		case "VIP":
			vip -= numOfRooms;
			break;
		case "Deluxe":
			deluxe -= numOfRooms;
			break;
		case "Standard":
			standard -= numOfRooms;
			break;
		}
	}

	public void cancelBooking(String roomType, int numOfRooms) {
		switch (roomType) {
		case "VIP":
			vip += numOfRooms;
			break;
		case "Deluxe":
			deluxe += numOfRooms;
			break;
		case "Standard":
			standard += numOfRooms;
			break;
		}
	}

	public int getAvailableRooms(String roomType) {
		switch (roomType) {
		case "VIP":
			return vip;
		case "Deluxe":
			return deluxe;
		case "Standard":
			return standard;
		default:
			return 0;
		}
	}

}

