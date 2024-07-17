package utar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class WaitingListTest {
	 	@Test
	    public void testAddWaiting() {
	        WaitingList waitingList = new WaitingList();
	        User user = new User("John", "VIP", false);
	        waitingList.addWaiting(user, "VIP");
	        assertTrue(waitingList.getWaiting("VIP").contains(user));
	    }

	    @Test
	    public void testRemoveWaiting() {
	        WaitingList waitingList = new WaitingList();
	        User user = new User("John", "VIP", false);

	        waitingList.addWaiting(user, "VIP");
	        waitingList.removeWaiting(user, "VIP");

	        assertFalse(waitingList.getWaiting("VIP").contains(user));
	    }
	    

	    private Object[] ParamsForAddWaitingException() {
		
			return new Object[] { 
					new Object[] {new User("John", "VIP", false), "zz"}, 
					new Object[] {new User("John", "VIP", false), "Normal"},
					new Object[] {null, "VIP"}
			};
		} 
	   
	    
	    @Test(expected = IllegalArgumentException.class)
	    @Parameters(method = "ParamsForAddWaitingException")
	    public void testAddWaitingException(User user, String userType) {
	        WaitingList waitingList = new WaitingList();
	        waitingList.addWaiting(user, userType);
	    }

	    private Object[] ParamsForRemoveWaitingException() {
			
			return new Object[] { 
					new Object[] {new User("John", "VIP", false), null}, 
					new Object[] {new User("John", "VIP", false), "Normal"},
					new Object[] {null, "VIP"}
			};
		} 

	    @Test(expected = IllegalArgumentException.class)
	    @Parameters(method = "ParamsForRemoveWaitingException")
	    public void testRemoveWaitingWithNullMemberType(User user, String userType) {
	        WaitingList waitingList = new WaitingList();
	        waitingList.removeWaiting(user, null);
	    }

}
