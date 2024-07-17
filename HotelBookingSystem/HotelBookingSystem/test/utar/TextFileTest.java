package utar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TextFileTest {
	private static User user;
    private static Room rm;
    private static Printer printer;
    private static int bookRoomNumber;

    
    private Booking booking;
    private static WaitingList waitingList;
    private List<User> users = new ArrayList<>();
    private List<Integer> numOfRoomBook = new ArrayList<>();
    private List<Integer> availableVIPRooms = new ArrayList<>();
    private List<Integer> availableDeluxeRooms = new ArrayList<>();
    private List<Integer> availableStandardRooms = new ArrayList<>();
    @BeforeClass
    public static void setUp()
    {
    	waitingList = new WaitingList();
    }
    
    @Before
    public void setup() throws IOException {
        parseInputFile("validInput.txt");
    }

    private void parseInputFile(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            String[] params = line.split(",");
            String name = params[0];
            String memberType = params[1];
            boolean exclReward = Boolean.parseBoolean(params[2]);
            bookRoomNumber = Integer.parseInt(params[3].trim());
            int availableVIPRoom = Integer.parseInt(params[4].trim());
            int  availableDeluxeRoom = Integer.parseInt(params[5].trim());
            int availableStandardRoom = Integer.parseInt(params[6].trim());
            user = new User(name, memberType, exclReward);
            users.add(user);
            numOfRoomBook.add(bookRoomNumber);
            availableVIPRooms.add(availableVIPRoom);
            availableDeluxeRooms.add(availableDeluxeRoom);
            availableStandardRooms.add(availableStandardRoom);
        }
        br.close();
    }
    
    @Test //The room is sufficient to book, The room for VIP is consider as 0 for the Normal Member Booking.
    public void testBookingWithValidInput() throws IOException{
    	rm = mock(Room.class);
        printer = mock(Printer.class);
        booking = new Booking(rm, waitingList,printer);
    	for(int i =0; i<users.size();i ++)
    	{
    		if (user.getMemberType() == "VIP")
    		{
    			when(rm.getAvailableRooms("VIP")).thenReturn(availableVIPRooms.get(i));
    			
    			booking.setBooking(users.get(i), numOfRoomBook.get(i));
    			Map<User, Map<String, Integer>> bookings = booking.getBookings();
    			assertTrue(bookings.containsKey(user));
    			verify(rm).bookRoom("VIP", availableVIPRooms.get(i));
    			verify(printer).printInfo(user.getName(), user.getMemberType(), "VIP", availableVIPRooms.get(i));
    		}
    		
    		else if (user.getMemberType() =="Normal")
    		{
    			
    			when(rm.getAvailableRooms("VIP")).thenReturn(availableVIPRooms.get(i));
    			when(rm.getAvailableRooms("Deluxe")).thenReturn(availableDeluxeRooms.get(i));
    			booking.setBooking(users.get(i), numOfRoomBook.get(i));
    			Map<User, Map<String, Integer>> bookings = booking.getBookings();
    			assertTrue(bookings.containsKey(user));
    			assertEquals(user.hasExclReward(), true);
    			verify(rm).bookRoom("Deluxe", numOfRoomBook.get(i));
    			verify(printer).printInfo(user.getName(), user.getMemberType(), "Deluxe", availableDeluxeRooms.get(i));
    		}
    		else if (user.getMemberType() == "Non-member")
    		{
    			when(rm.getAvailableRooms("Standard")).thenReturn(availableStandardRooms.get(i));

    			booking.setBooking(users.get(i), numOfRoomBook.get(i));
    			Map<User, Map<String, Integer>> bookings = booking.getBookings();
    			assertTrue(bookings.containsKey(user));
    			verify(rm).bookRoom("Standard", numOfRoomBook.get(i));
    			verify(printer).printInfo(user.getName(), user.getMemberType(), "Standard", availableStandardRooms.get(i));
    		}
    	}
    	
	}
    
    
    @Test(expected = IllegalArgumentException.class)
    public void testBookingWithInvalidInput() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("invalidInput.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Parse input parameters from each line
                String[] params = line.split(",");
                String name = params[0];
                String memberType = params[1];
                boolean exclReward = Boolean.parseBoolean(params[2]);
                int numOfRooms = Integer.parseInt(params[3]);

                // Create a User object and invoke the method under test with the input parameters
                rm = mock(Room.class);
                printer = mock(Printer.class);
                booking = new Booking(rm, waitingList,printer);
                booking.setBooking(new User(name, memberType, exclReward), numOfRooms);
            }
        }
    }
    
   
}
