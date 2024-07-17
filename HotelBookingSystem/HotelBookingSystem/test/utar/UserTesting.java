package utar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
@RunWith(JUnitParamsRunner.class)
public class UserTesting {

	    
	    @Test
	    public void testValidUserCreation() {
	        User user = new User("John", "VIP", false);
	        
	        assertEquals("John", user.getName());
	        assertEquals("VIP", user.getMemberType());
	        assertFalse(user.hasExclReward());
	    }
	    

		private Object[] ParamForInvalidUser() {

			return new Object[] {

					new Object[] {null, "VIP", true},// test 40
					new Object[] {"John", null, true},
					new Object[] {"", "VIP", true},
					new Object[] {"John", "Guest", true}
			};
		}

	    
	    @Test(expected = IllegalArgumentException.class)
		@Parameters(method = "ParamForInvalidUser")
	    public void testExceptionForUser(String name, String userType, boolean exclusiveReward) {
	        new User(name,userType, exclusiveReward);
	    }
	    
	  
	    
}


