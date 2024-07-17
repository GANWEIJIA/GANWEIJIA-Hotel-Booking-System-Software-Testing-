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
public class AddToWaitingListTest {

	WaitingList wl;

	@Before
	public void setUp() {
		wl = new WaitingList();

	}

	private Object[] ParamForCheckingWaitingListNonMember() {
		User[] normalWaitingList = { new User("Akao", "Non-member", false) };
		return new Object[] { new Object[] { new User("Akao", "Non-member", false), 1, 0, normalWaitingList }, // test 43

		};
	}

	@Test()
	@Parameters(method = "ParamForCheckingWaitingListNonMember")
	public void testWaitingListNonMember(User user, int bookRoomNumber, int availableOfAllRoom, User[] er) {
		Room rm = mock(Room.class);
		Printer printer = mock(Printer.class);

		when(rm.getAvailableRooms("Standard")).thenReturn(availableOfAllRoom);
		Booking booking = new Booking(rm, wl, printer);
		booking.setBooking(user, bookRoomNumber);
		User[] actualResult = wl.getWaiting("Non-member").toArray(new User[0]);
		assertArrayEquals(er, actualResult);

	}

	private Object[] ParamForCheckingWaitingListVIP() {

		User[] vipWaitingList = { new User("Ali", "VIP", false) };
		return new Object[] {

				new Object[] { new User("Ali", "VIP", false), 3, 0, vipWaitingList },//test40
		};
	}

	@Test()
	@Parameters(method = " ParamForCheckingWaitingListVIP")
	public void testWaitingListVIP(User user, int bookRoomNumber, int availableOfAllRoom, User[] er) {
		Room rm = mock(Room.class);
		Printer printer = mock(Printer.class);

		when(rm.getAvailableRooms("VIP")).thenReturn(availableOfAllRoom);
		when(rm.getAvailableRooms("VIP")).thenReturn(availableOfAllRoom);
		when(rm.getAvailableRooms("Deluxe")).thenReturn(availableOfAllRoom);
		when(rm.getAvailableRooms("Standard")).thenReturn(availableOfAllRoom);

		Booking booking = new Booking(rm, wl, printer);
		booking.setBooking(user, bookRoomNumber);
		User[] actualResult = wl.getWaiting("VIP").toArray(new User[0]);
		assertArrayEquals(er, actualResult);

	}

	private Object[] ParamForCheckingWaitingListNormal() {
		User[] memberWaitingList = { new User("Abu", "Normal", false) };
		User[] memberWaitingList2 = { new User("Abu", "Normal", true) };
		return new Object[] { 
				new Object[] { new User("Abu", "Normal", false), 2, memberWaitingList, 0 }, // test42
				new Object[] { new User("Abu", "Normal", true), 2, memberWaitingList2, 0 },// test41

		};
	}

	@Test()
	@Parameters(method = "ParamForCheckingWaitingListNormal")
	public void testWaitingListNormal(User user, int bookRoomNumber, User[] er, int availableOfAllRoom) {
		Room rm = mock(Room.class);
		Printer printer = mock(Printer.class);

		when(rm.getAvailableRooms("VIP")).thenReturn(availableOfAllRoom);
		when(rm.getAvailableRooms("Deluxe")).thenReturn(availableOfAllRoom);
		when(rm.getAvailableRooms("Standard")).thenReturn(availableOfAllRoom);

		Booking booking = new Booking(rm, wl, printer);
		booking.setBooking(user, bookRoomNumber);
		User[] actualResult = wl.getWaiting("Normal").toArray(new User[0]);
		assertArrayEquals(er, actualResult);

	}
}
