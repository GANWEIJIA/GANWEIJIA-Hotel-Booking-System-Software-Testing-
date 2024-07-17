package utar;

import java.util.HashMap;
import java.util.Map;

public class Booking {
    private Room room;
    private Map<User, Map<String, Integer>> bookings; // Modified to store user, room type, and number of rooms booked
    private int vip;
    private int deluxe;
    private int standard;
    private WaitingList waitingList;
    private Printer printer;
    public Map<User, Map<String, Integer>> getBookings() {
        return bookings;
    }

    public Booking() {
        waitingList = new WaitingList();
        bookings = new HashMap<>(); // Initialize the bookings HashMap
        printer = new Printer();
    }

    public Booking(Room room, WaitingList waitingList,Printer printer) {
        this.room = room;
        this.printer = printer;
        this.waitingList = waitingList;
        bookings = new HashMap<>(); // Initialize the bookings HashMap
    }

    public void setBooking(User user, int numOfRooms) {
        String memberType = user.getMemberType();
        boolean exclReward = user.hasExclReward();
        if (numOfRooms <= 0)
            throw new IllegalArgumentException("Invalid number of Booking");
        if (memberType.equals("VIP")) {
            if (numOfRooms > 3)
                throw new IllegalArgumentException("Invalid number of Booking");
            if (room.getAvailableRooms("VIP") >= numOfRooms) {
                room.bookRoom("VIP", numOfRooms);
                printer.printInfo(user.getName(), user.getMemberType(), "VIP", numOfRooms);

                // Store user, room type, and number of rooms booked in the bookings HashMap
                Map<String, Integer> bookingDetails = new HashMap<>();
                bookingDetails.put("VIP", numOfRooms);
               
                bookings.put(user, bookingDetails);
        		

                return;
            } else {
                int remainingRooms = numOfRooms;

                int vipRoomsAvailable = room.getAvailableRooms("VIP");
                int vipRoomsBooked = Math.min(3, vipRoomsAvailable);
                room.bookRoom("VIP", vipRoomsBooked);

                Map<String, Integer> bookingDetails = new HashMap<>();
                bookingDetails.put("VIP", vipRoomsBooked);
                bookings.put(user, bookingDetails);
                printer.printInfo(user.getName(), user.getMemberType(), "VIP", vipRoomsBooked);

                remainingRooms -= vipRoomsBooked;
                
                if (remainingRooms > 0) {
                    int deluxeRooms = Math.min(remainingRooms, room.getAvailableRooms("Deluxe"));
                    room.bookRoom("Deluxe", deluxeRooms);
                    printer.printInfo(user.getName(), user.getMemberType(), "Deluxe", deluxeRooms);

                    bookingDetails.put("Deluxe", deluxeRooms);
                    bookings.put(user, bookingDetails);
                    remainingRooms -= deluxeRooms;

                    if (remainingRooms > 0 && room.getAvailableRooms("Standard") >=remainingRooms) {
                        room.bookRoom("Standard", remainingRooms);
                        printer.printInfo(user.getName(), user.getMemberType(), "Standard", remainingRooms);

                        bookingDetails.put("Standard", remainingRooms);
                        bookings.put(user, bookingDetails);
                      

                        return;
                    } else if (remainingRooms > 0) {
                        waitingList.addWaiting(user, "VIP");
                        room.cancelBooking("VIP", vipRoomsBooked);
                        room.cancelBooking("Deluxe", deluxeRooms);
                        
                        System.out.println("Requested number of rooms not available. Adding to VIP waiting list.");
                        return;
                    }
                    return;
                }
            }
        }
        if (memberType.equals("Normal")) {
            if (numOfRooms > 2)
                throw new IllegalArgumentException("Invalid number of Booking");
            int remainingRooms = numOfRooms;
            if (exclReward && room.getAvailableRooms("VIP") >= 1) {
                room.bookRoom("VIP", 1);
                
                Map<String, Integer> bookingDetails = new HashMap<>();
                bookingDetails.put("VIP", 1);
                bookings.put(user, bookingDetails);
                printer.printInfo(user.getName(), user.getMemberType(), "VIP", 1);

                user.setExclReward(false);
                remainingRooms--;

                if (remainingRooms > 0) {
                    int deluxeRooms = Math.min(remainingRooms, room.getAvailableRooms("Deluxe"));
                    room.bookRoom("Deluxe", deluxeRooms);
                    printer.printInfo(user.getName(), user.getMemberType(), "Deluxe", deluxeRooms);

                    bookingDetails.put("Deluxe", deluxeRooms);
                    bookings.put(user, bookingDetails);
                    remainingRooms -= deluxeRooms;

                    if (remainingRooms > 0 && room.getAvailableRooms("Standard")>= remainingRooms) {
                        room.bookRoom("Standard", remainingRooms);
                        printer.printInfo(user.getName(), user.getMemberType(), "Standard", remainingRooms);

                        bookingDetails.put("Standard", remainingRooms);
                        bookings.put(user, bookingDetails);
                        return;
                    } else if (remainingRooms > 0) {
                        waitingList.addWaiting(user, "VIP");
                        room.cancelBooking("VIP", 1);
                        room.cancelBooking("Deluxe", deluxeRooms);
                        System.out.println("Requested number of rooms not available. Adding to VIP waiting list.");
                        return;
                    }
                    return;
                }
                return;
            } else {
                int deluxeRoomsAvailable = room.getAvailableRooms("Deluxe");
                int deluxeRooms = Math.min(deluxeRoomsAvailable, 3);
                room.bookRoom("Deluxe", deluxeRooms);
                printer.printInfo(user.getName(), user.getMemberType(), "Deluxe", deluxeRooms);

                Map<String, Integer> bookingDetails = new HashMap<>();
                bookingDetails.put("Deluxe", deluxeRooms);
                bookings.put(user, bookingDetails);
                remainingRooms -= deluxeRooms;

                if (remainingRooms > 0 && room.getAvailableRooms("Standard")>= remainingRooms) {
                    room.bookRoom("Standard", remainingRooms);
                    printer.printInfo(user.getName(), user.getMemberType(), "Standard", remainingRooms);

                    bookingDetails.put("Standard", remainingRooms);
                    bookings.put(user, bookingDetails);
                    return;
                } else if (remainingRooms > 0){
                    waitingList.addWaiting(user, "Normal");
                    room.cancelBooking("Deluxe", deluxeRooms);
                    System.out.println("No rooms available. Adding to normal member waiting list.");
                    return;
                    
                  
                }
                return;
            }
        }
        else { // Non-member
            if (numOfRooms > 1)
                throw new IllegalArgumentException("Invalid number of Booking");
            if (room.getAvailableRooms("Standard")>= numOfRooms) {
                room.bookRoom("Standard", numOfRooms);
                printer.printInfo(user.getName(), user.getMemberType(), "Standard", numOfRooms);

                Map<String, Integer> bookingDetails = new HashMap<>();
                bookingDetails.put("Standard", numOfRooms);
                bookings.put(user, bookingDetails);
                return;
            } else  if (room.getAvailableRooms("Standard") >= numOfRooms == false){
                waitingList.addWaiting(user, "Non-member");
                System.out.println("Standard Room fully booked. Adding to non-member waiting list.");
                return;
            }
        }
    }

    public void cancelBooking(User user) {
    	   String memberType = user.getMemberType();
    	if (bookings.containsKey(user)) {
            Map<String, Integer> bookingDetails = bookings.get(user);
            for (Map.Entry<String, Integer> entry : bookingDetails.entrySet()) {
                String roomType = entry.getKey();
                int numOfRooms = entry.getValue();
                room.cancelBooking(roomType, numOfRooms);
            }
            bookings.remove(user);
            System.out.println("Booking canceled for user: " + user.getName());
        
        
      
         
            System.out.print(memberType);
            if (memberType.equals("VIP")) {
                waitingList.removeWaiting(user, "VIP");
            } else if (memberType.equals("Normal")) {
                waitingList.removeWaiting(user, "Normal");
            } 
        }
        else if(!bookings.containsKey(user) && memberType.equals("Non-member"))
        {
        	  waitingList.removeWaiting(user, "Non-member");
        }
         else {
            System.out.println("No booking found for user: " + user.getName());
        }
    }
    
    
}
