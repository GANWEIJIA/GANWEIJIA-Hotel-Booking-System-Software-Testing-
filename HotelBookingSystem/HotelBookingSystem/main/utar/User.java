package utar;

import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ArrayList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class User {
	private String name;
	private String memberType;
	private boolean exclReward;

	public User(String name, String memberType, boolean exclReward) {
		  if (name == null || memberType == null) {
		        throw new IllegalArgumentException("Name and member type cannot be null");
		    }

		    // Check for empty or blank name
		    if (name.trim().isEmpty()) {
		        throw new IllegalArgumentException("Name cannot be empty or blank");
		    }

		    // Check for valid member type
		    if (!memberType.equals("VIP") && !memberType.equals("Normal") && !memberType.equals("Non-member")) {
		        throw new IllegalArgumentException("Invalid member type: " + memberType);
		    }
		    // Check for VIP reward
		    if(memberType.equals("VIP") && exclReward == true)
		        throw new IllegalArgumentException("No exlusive reward for " + memberType);
		    // Check for Non-member reward
		    if(memberType.equals("Non-member") && exclReward == true)
		        throw new IllegalArgumentException("No exlusive reward for " + memberType);
		    // Check for valid boolean value for exclReward
		    if (exclReward != true && exclReward != false) {
		        throw new IllegalArgumentException("Invalid boolean value for exclReward: " + exclReward);
		    }
		    
		this.name = name;
		this.memberType = memberType;
		this.exclReward = exclReward;
	}

	public String getName() {
		return name;
	}

	public String getMemberType() {
		return memberType;
	}

	public boolean hasExclReward() {
		return exclReward;
	}

	public void setExclReward(boolean b) {
		exclReward = b;
	}
	
	// Inside User class
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	        return false;
	    }
	    User other = (User) obj;
	    return Objects.equals(name, other.name) && Objects.equals(memberType, other.memberType) && Objects.equals(exclReward, other.exclReward) ;
	}

}