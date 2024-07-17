package utar;

public class Printer {
	public void printInfo(String name, String member_type, String room_type, int numOfRoom) {
		System.out.println("Booking Information:");
		System.out.println("User: " + name + ", Member Type: " + member_type + ", Room Type: " + room_type +", Number of Room is: "+ numOfRoom);
	}
}