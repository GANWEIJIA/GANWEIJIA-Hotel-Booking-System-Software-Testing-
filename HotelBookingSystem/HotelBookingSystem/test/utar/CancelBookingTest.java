package utar;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)

public class CancelBookingTest {

	WaitingList wl;

	@Before
	public void setUp() {
		wl = new WaitingList();

	}

	private Object[] ParamForCancelBookingVIP() {

		User[] vipWaitingList = { new User("Ali", "VIP", false) };
		return new Object[] {

				new Object[] { new User("Ali", "VIP", false), 3, vipWaitingList, 0 },// test 45

		};
	}

	@Test()
	@Parameters(method = "ParamForCancelBookingVIP")
	public void testCancelBookingVIP(User user, int bookRoomNumber, User[] er, int availableOfAllRoom) {
		Room rm = mock(Room.class);

		when(rm.getAvailableRooms("VIP")).thenReturn(availableOfAllRoom);
		when(rm.getAvailableRooms("VIP")).thenReturn(availableOfAllRoom);
		when(rm.getAvailableRooms("Deluxe")).thenReturn(availableOfAllRoom);
		when(rm.getAvailableRooms("Standard")).thenReturn(availableOfAllRoom);

		Printer printer = mock(Printer.class);

		Booking booking = new Booking(rm, wl, printer);
		booking.setBooking(user, bookRoomNumber);
		User[] actualResult = wl.getWaiting("VIP").toArray(new User[0]);
		assertArrayEquals(er, actualResult);

	}

	private Object[] ParamForCancelBookingNormal() {
		User[] memberWaitingList = {};
		User[] memberWaitingList2 = {};
		return new Object[] { 
				new Object[] { new User("Abu", "Normal", false), 2, memberWaitingList, 0 }, // test49
				new Object[] { new User("Abu", "Normal", true), 2, memberWaitingList2, 0 },// test47

		};
	}

	@Test()
	@Parameters(method = "ParamForCancelBookingNormal")
	public void testCancelBookingNormal(User user, int bookRoomNumber, User[] er, int availableOfAllRoom) {
		Room rm = mock(Room.class);

		when(rm.getAvailableRooms("VIP")).thenReturn(availableOfAllRoom);
		when(rm.getAvailableRooms("Deluxe")).thenReturn(availableOfAllRoom);
		when(rm.getAvailableRooms("Standard")).thenReturn(availableOfAllRoom);

		Printer printer = mock(Printer.class);

		Booking booking = new Booking(rm, wl, printer);
		booking.setBooking(user, bookRoomNumber);
		booking.cancelBooking(user);
		User[] actualResult = wl.getWaiting("Normal").toArray(new User[0]);
		assertArrayEquals(er, actualResult);

	}

	private Object[] ParamForCancelBookingNonMember() {
		User[] normalWaitingList = {};
		return new Object[] { 
				new Object[] { new User("Akao", "Non-member", false), 1, normalWaitingList, 1 },// test51

		};
	}

	@Test()
	@Parameters(method = "ParamForCancelBookingNonMember")
	public void testCancelBookingNonMember(User user, int bookRoomNumber, User[] er, int availableOfAllRoom) {
		Room rm = mock(Room.class);
		Printer printer = mock(Printer.class);

		when(rm.getAvailableRooms("Standard")).thenReturn(availableOfAllRoom);
		Booking booking = new Booking(rm, wl, printer);
		booking.setBooking(user, bookRoomNumber);

		booking.cancelBooking(user);

		User[] actualResult = wl.getWaiting("Non-member").toArray(new User[0]);

		assertArrayEquals(er, actualResult);

	}

	private Object[] ParamForCancelBookingInWaitingListNormal() {
		User[] memberWaitingList = {};
		User[] memberWaitingList2 = {};
		return new Object[] { 
				new Object[] { new User("Abu", "Normal", false), 2, memberWaitingList, 0 }, // test48
				new Object[] { new User("Abu", "Normal", true), 2, memberWaitingList2, 0 },// test46

		};
	}

	@Test()
	@Parameters(method = "ParamForCancelBookingInWaitingListNormal")
	public void testCancelBookingInWaitingListNormal(User user, int bookRoomNumber, User[] er, int availableOfAllRoom) {
		Room rm = mock(Room.class);
		Printer printer = mock(Printer.class);

		when(rm.getAvailableRooms("VIP")).thenReturn(availableOfAllRoom);
		when(rm.getAvailableRooms("Deluxe")).thenReturn(availableOfAllRoom);
		when(rm.getAvailableRooms("Standard")).thenReturn(availableOfAllRoom);

		Booking booking = new Booking(rm, wl, printer);
		booking.setBooking(user, bookRoomNumber);
		booking.cancelBooking(user);
		User[] actualResult = wl.getWaiting("Normal").toArray(new User[0]);
		assertArrayEquals(er, actualResult);

	}

	private Object[] ParamForCancelBookingInWaitingListNonMember() {
		User[] normalWaitingList = {};
		User[] vipWaitingList = {};
		return new Object[] { 
				new Object[] { new User("Akao", "Non-member", false), 1, normalWaitingList, 0 },// test50

		};

	}

	@Test
	@Parameters(method = "ParamForCancelBookingInWaitingListNonMember")
	public void testCancelBooking6(User user, int bookRoomNumber, User[] er, int availableOfAllRoom) {
		Room rm = mock(Room.class);
		Printer printer = mock(Printer.class);

		when(rm.getAvailableRooms("Standard")).thenReturn(availableOfAllRoom);
		Booking booking = new Booking(rm, wl, printer);
		booking.setBooking(user, bookRoomNumber);
		booking.cancelBooking(user);

		User[] actualResult = wl.getWaiting("Non-member").toArray(new User[0]);

		assertArrayEquals(er, actualResult);

	}

	private Object[] ParamForCancelBookingInWaitingListVIP() {

		User[] vipWaitingList = {};
		return new Object[] {

				new Object[] { new User("Ali", "VIP", false), 3, vipWaitingList, 0 }, // test44

		};
	}

	@Test
	@Parameters(method = "ParamForCancelBookingInWaitingListVIP")
	public void testCancelBookingInWaitingListVIP(User user, int bookRoomNumber, User[] er, int availableOfAllRoom) {
		Room rm = mock(Room.class);
		Printer printer = mock(Printer.class);

		when(rm.getAvailableRooms("VIP")).thenReturn(availableOfAllRoom);
		Booking booking = new Booking(rm, wl, printer);
		booking.setBooking(user, bookRoomNumber);
		booking.cancelBooking(user);

		User[] actualResult = wl.getWaiting("VIP").toArray(new User[0]);

		assertArrayEquals(er, actualResult);

	}
}
