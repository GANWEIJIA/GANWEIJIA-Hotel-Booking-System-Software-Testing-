package utar;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value = Suite.class)
@SuiteClasses(value = {AddToWaitingListTest.class, CancelBookingTest.class, SetBookingTest.class,
		TextFileTest.class, UserTesting.class,WaitingListTest.class })
public class TestSuite {

}
