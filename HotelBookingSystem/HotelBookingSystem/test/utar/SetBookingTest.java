package utar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class SetBookingTest {

	WaitingList wl;

	@Before
	public void setUp() {
		wl = new WaitingList();

	}

	private Object[] ParamForSetBookingVIP1() {
		return new Object[] { new Object[] { new User("Ali", "VIP", false), 3, 3 }, // test1
				new Object[] { new User("Ali", "VIP", false), 2, 2 }, // test11
				new Object[] { new User("Ali", "VIP", false), 1, 1 }, // test17
		};
	}

	@Test
	@Parameters(method = "ParamForSetBookingVIP1")
	public void testSetBookingVIP1(User user, int availableVIPRoom, int bookRoomNumber) {
		Room rm = mock(Room.class);
		Printer printer = mock(Printer.class);
		when(rm.getAvailableRooms("VIP")).thenReturn(availableVIPRoom);
		Booking booking = new Booking(rm, wl, printer);
		booking.setBooking(user, bookRoomNumber);
		Map<User, Map<String, Integer>> bookings = booking.getBookings();
		assertTrue(bookings.containsKey(user));
		verify(rm).bookRoom("VIP", availableVIPRoom);
		verify(printer).printInfo(user.getName(), user.getMemberType(), "VIP", availableVIPRoom);
	}

	private Object[] ParamForSetBookingNonMember1() {
		return new Object[] { new Object[] { new User("Akao", "Non-member", false), 1, 1 } // test37
		};
	}

	@Test
	@Parameters(method = "ParamForSetBookingNonMember1")
	public void testSetBookingNonMember1(User user, int availableStandardRoom, int bookRoomNumber) {
		Room rm = mock(Room.class);
		Printer printer = mock(Printer.class);

		when(rm.getAvailableRooms("Standard")).thenReturn(availableStandardRoom);
		Booking booking = new Booking(rm, wl, printer);
		booking.setBooking(user, bookRoomNumber);
		Map<User, Map<String, Integer>> bookings = booking.getBookings();
		assertTrue(bookings.containsKey(user));
		verify(rm).bookRoom("Standard", availableStandardRoom);
		verify(printer).printInfo(user.getName(), user.getMemberType(), "Standard", availableStandardRoom);

	}

	private Object[] ParamForSetBookingNormal1() {
		return new Object[] {

				new Object[] { new User("Abu", "Normal", true), 0, 1, 1 }, // test28
				new Object[] { new User("Abu", "Normal", true), 0, 2, 2 }, // test22

		};
	}

	@Test
	@Parameters(method = "ParamForSetBookingNormal1")
	public void testSetBookingNormal1(User user, int availableVIPRoom, int availableDeluxeRoom, int bookRoomNumber) {
		Room rm = mock(Room.class);
		when(rm.getAvailableRooms("VIP")).thenReturn(availableVIPRoom);
		when(rm.getAvailableRooms("Deluxe")).thenReturn(availableDeluxeRoom);
		Printer printer = mock(Printer.class);

		Booking booking = new Booking(rm, wl, printer);
		booking.setBooking(user, bookRoomNumber);
		Map<User, Map<String, Integer>> bookings = booking.getBookings();
		assertTrue(bookings.containsKey(user));
		assertEquals(user.hasExclReward(), true);
		verify(rm).bookRoom("Deluxe", availableDeluxeRoom);
		verify(printer).printInfo(user.getName(), user.getMemberType(), "Deluxe", availableDeluxeRoom);

	}

	private Object[] ParamForSetBookingNormal2() {
		return new Object[] {

				new Object[] { new User("Abu", "Normal", false), 0, 1, 1, 2 }, // test27
				new Object[] { new User("Abu", "Normal", false), 0, 0, 1, 1 }, // test32

		};
	}

	@Test
	@Parameters(method = "ParamForSetBookingNormal2")
	public void testSetBookingNormal2(User user, int availableVIPRoom, int availableDeluxeRoom,
			int availableStandardRoom, int bookRoomNumber) {
		Room rm = mock(Room.class);
		when(rm.getAvailableRooms("Deluxe")).thenReturn(availableDeluxeRoom);
		Printer printer = mock(Printer.class);
		when(rm.getAvailableRooms("VIP")).thenReturn(availableVIPRoom);
		when(rm.getAvailableRooms("Standard")).thenReturn(availableStandardRoom);
		Booking booking = new Booking(rm, wl, printer);
		booking.setBooking(user, bookRoomNumber);
		Map<User, Map<String, Integer>> bookings = booking.getBookings();
		assertTrue(bookings.containsKey(user));
		verify(rm).bookRoom("Deluxe", availableDeluxeRoom);
		verify(printer).printInfo(user.getName(), user.getMemberType(), "Deluxe", availableDeluxeRoom);

		verify(rm).bookRoom("Standard", availableStandardRoom);
		verify(printer).printInfo(user.getName(), user.getMemberType(), "Standard", availableStandardRoom);

	}

	private Object[] ParamForSetBookingNormal3() {
		return new Object[] { new Object[] { new User("Abu", "Normal", true), 0, 1, 1, 2 }, // test26
				new Object[] { new User("Abu", "Normal", true), 0, 0, 1, 1 }, // test31
		};
	}

	@Test
	@Parameters(method = "ParamForSetBookingNormal3")
	public void testSetBookingNormal3(User user, int availableVIPRoom, int availableDeluxeRoom,
			int availableStandardRoom, int bookRoomNumber) {
		Room rm = mock(Room.class);
		Printer printer = mock(Printer.class);

		when(rm.getAvailableRooms("Deluxe")).thenReturn(availableDeluxeRoom);
		when(rm.getAvailableRooms("VIP")).thenReturn(availableVIPRoom);
		when(rm.getAvailableRooms("Standard")).thenReturn(availableStandardRoom);
		Booking booking = new Booking(rm, wl, printer);
		booking.setBooking(user, bookRoomNumber);
		assertEquals(user.hasExclReward(), true);
		Map<User, Map<String, Integer>> bookings = booking.getBookings();
		assertTrue(bookings.containsKey(user));
		verify(rm).bookRoom("Deluxe", availableDeluxeRoom);
		verify(printer).printInfo(user.getName(), user.getMemberType(), "Deluxe", availableDeluxeRoom);
		verify(rm).bookRoom("Standard", availableStandardRoom);
		verify(printer).printInfo(user.getName(), user.getMemberType(), "Standard", availableStandardRoom);
	}

	private Object[] ParamForSetBookingVIP2() {
		return new Object[] { new Object[] { new User("Ali", "VIP", false), 2, 1, 3 }, // test2
				new Object[] { new User("Ali", "VIP", false), 1, 2, 3 }, // test4
				new Object[] { new User("Ali", "VIP", false), 0, 3, 3 }, // test7
				new Object[] { new User("Ali", "VIP", false), 1, 1, 2 }, // test12
				new Object[] { new User("Ali", "VIP", false), 0, 1, 1 }, // test18
				new Object[] { new User("Ali", "VIP", false), 0, 2, 2 }, // test14
		};
	}

	@Test
	@Parameters(method = "ParamForSetBookingVIP2")
	public void testSetBookingVIP2(User user, int availableVIPRoom, int availableDeluxeRoom, int bookRoomNumber) {
		Room rm = mock(Room.class);
		Printer printer = mock(Printer.class);

		when(rm.getAvailableRooms("VIP")).thenReturn(availableVIPRoom);
		when(rm.getAvailableRooms("VIP")).thenReturn(availableVIPRoom);
		when(rm.getAvailableRooms("Deluxe")).thenReturn(availableDeluxeRoom);

		Booking booking = new Booking(rm, wl, printer);
		booking.setBooking(user, bookRoomNumber);
		Map<User, Map<String, Integer>> bookings = booking.getBookings();
		assertTrue(bookings.containsKey(user));
		verify(rm).bookRoom("VIP", availableVIPRoom);
		verify(printer).printInfo(user.getName(), user.getMemberType(), "VIP", availableVIPRoom);
		verify(rm).bookRoom("Deluxe", availableDeluxeRoom);
		verify(printer).printInfo(user.getName(), user.getMemberType(), "Deluxe", availableDeluxeRoom);

	}

	private Object[] ParamForSetBookingVIP3() {
		return new Object[] { new Object[] { new User("Ali", "VIP", false), 3, 2, 0, 1 }, // test3
				new Object[] { new User("Ali", "VIP", false), 3, 1, 0, 2 }, // test5
				new Object[] { new User("Ali", "VIP", false), 2, 1, 0, 1 }, // test13
				new Object[] { new User("Ali", "VIP", false), 3, 0, 0, 3 }, // test8
				new Object[] { new User("Ali", "VIP", false), 3, 1, 1, 1 }, // test6
				new Object[] { new User("Ali", "VIP", false), 3, 0, 2, 1 }, // test9
				new Object[] { new User("Ali", "VIP", false), 3, 0, 1, 2 }, // test10
				new Object[] { new User("Ali", "VIP", false), 2, 0, 1, 1 }, // test15
				new Object[] { new User("Ali", "VIP", false), 2, 0, 0, 2 }, // test16
				new Object[] { new User("Ali", "VIP", false), 1, 0, 0, 1 }, // test19
		};
	}

	@Test
	@Parameters(method = "ParamForSetBookingVIP3")
	public void testSetBookingVIP3(User user, int bookRoomNumber, int availableVIPRoom, int availableDeluxeRoom,
			int availableStandardRoom) {
		Room rm = mock(Room.class);
		Printer printer = mock(Printer.class);

		when(rm.getAvailableRooms("VIP")).thenReturn(availableVIPRoom);
		when(rm.getAvailableRooms("Deluxe")).thenReturn(availableDeluxeRoom);
		when(rm.getAvailableRooms("Standard")).thenReturn(availableStandardRoom);

		when(rm.getAvailableRooms("VIP")).thenReturn(availableVIPRoom);
		when(rm.getAvailableRooms("Standard")).thenReturn(availableStandardRoom);

		Booking booking = new Booking(rm, wl, printer);
		booking.setBooking(user, bookRoomNumber);
		Map<User, Map<String, Integer>> bookings = booking.getBookings();
		assertTrue(bookings.containsKey(user));
		// Verify the booking behavior based on the availability of rooms

		verify(rm).bookRoom("VIP", availableVIPRoom);
		verify(printer).printInfo(user.getName(), user.getMemberType(), "VIP", availableVIPRoom);

		verify(rm).bookRoom("Deluxe", availableDeluxeRoom);
		verify(printer).printInfo(user.getName(), user.getMemberType(), "Deluxe", availableDeluxeRoom);
		verify(rm).bookRoom("Standard", availableStandardRoom);
		verify(printer).printInfo(user.getName(), user.getMemberType(), "Standard", availableStandardRoom);

	}

	private Object[] ParamForSetBookingNormal4() {
		return new Object[] { new Object[] { new User("Abu", "Normal", true), 2, 1, 1 }, // test24

		};
	}

	@Test
	@Parameters(method = "ParamForSetBookingNormal4")
	public void testSetBookingNormal4(User user, int bookRoomNumber, int availableVIPRoom, int availableDeluxeRoom) {
		Room rm = mock(Room.class);
		Printer printer = mock(Printer.class);

		when(rm.getAvailableRooms("VIP")).thenReturn(availableVIPRoom);
		when(rm.getAvailableRooms("Deluxe")).thenReturn(availableDeluxeRoom);

		Booking booking = new Booking(rm, wl, printer);
		booking.setBooking(user, bookRoomNumber);
		assertEquals(user.hasExclReward(), false);
		Map<User, Map<String, Integer>> bookings = booking.getBookings();
		assertTrue(bookings.containsKey(user));
		verify(rm).bookRoom("VIP", availableVIPRoom);
		verify(printer).printInfo(user.getName(), user.getMemberType(), "VIP", availableVIPRoom);

		verify(rm).bookRoom("Deluxe", availableDeluxeRoom);
		verify(printer).printInfo(user.getName(), user.getMemberType(), "Deluxe", availableDeluxeRoom);

	}

	private Object[] ParamForSetBookingNormal5() {
		return new Object[] { new Object[] { new User("Abu", "Normal", true), 2, 1, 0, 1 }, // test25
		};
	}

	@Test
	@Parameters(method = "ParamForSetBookingNormal5")
	public void testSetBookingNormal5(User user, int bookRoomNumber, int availableVIPRoom, int availableDeluxeRoom,
			int availableStandardRoom) {
		Room rm = mock(Room.class);
		Printer printer = mock(Printer.class);

		when(rm.getAvailableRooms("VIP")).thenReturn(availableVIPRoom);
		when(rm.getAvailableRooms("Deluxe")).thenReturn(availableDeluxeRoom);
		when(rm.getAvailableRooms("Standard")).thenReturn(availableStandardRoom);
		Booking booking = new Booking(rm, wl, printer);
		booking.setBooking(user, bookRoomNumber);
		assertEquals(user.hasExclReward(), false);
		Map<User, Map<String, Integer>> bookings = booking.getBookings();
		assertTrue(bookings.containsKey(user));
		verify(rm).bookRoom("VIP", availableVIPRoom);
		verify(printer).printInfo(user.getName(), user.getMemberType(), "VIP", availableVIPRoom);
		verify(rm).bookRoom("Deluxe", availableDeluxeRoom);
		verify(printer).printInfo(user.getName(), user.getMemberType(), "Deluxe", availableDeluxeRoom);
		verify(rm).bookRoom("Standard", availableStandardRoom);
		verify(printer).printInfo(user.getName(), user.getMemberType(), "Standard", availableStandardRoom);

	}

	private Object[] ParamForSetBookingNormal6() {
		return new Object[] { new Object[] { new User("Abu", "Normal", false), 2, 0, 0, 2 }, // test23

		};
	}

	@Test
	@Parameters(method = "ParamForSetBookingNormal6")
	public void testSetBookingNormal6(User user, int bookRoomNumber, int availableVIPRoom, int availableDeluxeRoom,
			int availableStandardRoom) {
		Room rm = mock(Room.class);
		Printer printer = mock(Printer.class);

		when(rm.getAvailableRooms("VIP")).thenReturn(availableVIPRoom);
		when(rm.getAvailableRooms("Deluxe")).thenReturn(availableDeluxeRoom);
		when(rm.getAvailableRooms("Standard")).thenReturn(availableStandardRoom);
		Booking booking = new Booking(rm, wl, printer);
		booking.setBooking(user, bookRoomNumber);
		Map<User, Map<String, Integer>> bookings = booking.getBookings();
		assertTrue(bookings.containsKey(user));
		verify(rm).bookRoom("Deluxe", availableDeluxeRoom);
		verify(printer).printInfo(user.getName(), user.getMemberType(), "Deluxe", availableDeluxeRoom);

		verify(rm).bookRoom("Standard", availableStandardRoom);
		verify(printer).printInfo(user.getName(), user.getMemberType(), "Standard", availableStandardRoom);

	}

	private Object[] ParamForSetBookingNormal7() {
		return new Object[] {

				new Object[] { new User("Abu", "Normal", true), 1, 0, 0, 1 }, // test29
		};
	}

	@Test
	@Parameters(method = "ParamForSetBookingNormal7")
	public void testSetBookingNormal7(User user, int bookRoomNumber, int availableVIPRoom, int availableDeluxeRoom,
			int availableStandardRoom) {
		Room rm = mock(Room.class);
		Printer printer = mock(Printer.class);

		when(rm.getAvailableRooms("VIP")).thenReturn(availableVIPRoom);
		when(rm.getAvailableRooms("Deluxe")).thenReturn(availableDeluxeRoom);
		when(rm.getAvailableRooms("Standard")).thenReturn(availableStandardRoom);
		Booking booking = new Booking(rm, wl, printer);
		booking.setBooking(user, bookRoomNumber);
		assertEquals(user.hasExclReward(), true);
		Map<User, Map<String, Integer>> bookings = booking.getBookings();
		assertTrue(bookings.containsKey(user));
		verify(rm).bookRoom("Deluxe", availableDeluxeRoom);
		verify(printer).printInfo(user.getName(), user.getMemberType(), "Deluxe", availableDeluxeRoom);

		verify(rm).bookRoom("Standard", availableStandardRoom);
		verify(printer).printInfo(user.getName(), user.getMemberType(), "Standard", availableStandardRoom);

	}

	private Object[] ParamForSetBookingNormal8() {
		return new Object[] { new Object[] { new User("Abu", "Normal", true), 1, 1 }, // test30

		};
	}

	@Test
	@Parameters(method = "ParamForSetBookingNormal8")
	public void testSetBookingNormal8(User user, int bookRoomNumber, int availableVIPRoom) {
		Room rm = mock(Room.class);
		when(rm.getAvailableRooms("VIP")).thenReturn(availableVIPRoom);
		Printer printer = mock(Printer.class);

		Booking booking = new Booking(rm, wl, printer);
		booking.setBooking(user, bookRoomNumber);
		assertEquals(user.hasExclReward(), false);
		Map<User, Map<String, Integer>> bookings = booking.getBookings();
		assertTrue(bookings.containsKey(user));
		verify(rm).bookRoom("VIP", availableVIPRoom);
		verify(printer).printInfo(user.getName(), user.getMemberType(), "VIP", availableVIPRoom);

	}

	private Object[] ParamForSetBookingExceptionVIP() {
		return new Object[] { new Object[] { new User("Ali", "VIP", false), 4 }, // test20
				new Object[] { new User("Ali", "VIP", false), 0 }, // test21
		};
	}

	@Test(expected = IllegalArgumentException.class)
	@Parameters(method = "ParamForSetBookingExceptionVIP")
	public void testSetBookingExceptionVIP(User user, int bookRoomNumber) {
		Room rm = mock(Room.class);
		Printer printer = mock(Printer.class);

		Booking booking = new Booking(rm, wl, printer);
		booking.setBooking(user, bookRoomNumber);

		// Verify the booking behavior based on the availability of rooms

	}

	private Object[] ParamForSetBookingExceptionNormal() {
		return new Object[] { new Object[] { new User("Abu", "Normal", true), 3 }, // test 33
				new Object[] { new User("Abu", "Normal", true), 0 }, // test 34
				new Object[] { new User("Abu", "Normal", false), 3 }, // test 35
				new Object[] { new User("Abu", "Normal", false), 0 },// test 36
		};
	}

	@Test(expected = IllegalArgumentException.class)
	@Parameters(method = "ParamForSetBookingExceptionNormal")
	public void testSetBookingExceptionNormal(User user, int bookRoomNumber) {
		Room rm = mock(Room.class);
		Printer printer = mock(Printer.class);

		Booking booking = new Booking(rm, wl, printer);
		booking.setBooking(user, bookRoomNumber);

		// Verify the booking behavior based on the availability of rooms

	}

	private Object[] ParamForSetBookingExceptionNonMember() {
		return new Object[] { new Object[] { new User("Akao", "Non-member", false), 2 }, // test38
				new Object[] { new User("Akao", "Non-member", false), 0 }, // test39
		};
	}

	@Test(expected = IllegalArgumentException.class)
	@Parameters(method = "ParamForSetBookingExceptionNonMember")
	public void testSetBookingExceptionNonMember(User user, int bookRoomNumber) {
		Room rm = mock(Room.class);
		Printer printer = mock(Printer.class);

		Booking booking = new Booking(rm, wl, printer);
		booking.setBooking(user, bookRoomNumber);

		// Verify the booking behavior based on the availability of rooms

	}
}

