package utar;

import java.util.ArrayList;

public class WaitingList {
	private ArrayList<User> vipWaitingList;
	private ArrayList<User> memberWaitingList;
	private ArrayList<User> normalWaitingList;

	public WaitingList() {
		vipWaitingList = new ArrayList<>();
		memberWaitingList = new ArrayList<>();
		normalWaitingList = new ArrayList<>();
	}

	public void addWaiting(User user, String memberType) {
		
		if (user == null || memberType == null) {
            throw new IllegalArgumentException("User and member type cannot be null");
        }
		if(!memberType.equals("VIP") && !memberType.equals("Non-member") && !memberType.equals("Normal"))
			 throw new IllegalArgumentException("Invalid Member Type");
		if(user.getMemberType() != memberType)
			 throw new IllegalArgumentException("Mismatch Member Type");
		switch (memberType) {
		case "VIP":
			vipWaitingList.add(user);
			break;
		case "Normal":
			memberWaitingList.add(user);
			break;
		default:
			normalWaitingList.add(user);
			break;
		}
	}

	public ArrayList<User> getWaiting(String memberType) {
		switch (memberType) {
		case "VIP":
			return vipWaitingList;
		case "Normal":
			return memberWaitingList;
		default:
			return normalWaitingList;
		}
	}

	public void removeWaiting(User user, String memberType) {
		
		if (user == null || memberType == null) {
            throw new IllegalArgumentException("User and member type cannot be null");
        }
		if(!memberType.equals("VIP") && !memberType.equals("Non-member") && !memberType.equals("Normal"))
			 throw new IllegalArgumentException("Invalid Member Type");
		if(user.getMemberType() != memberType)
			 throw new IllegalArgumentException("Mismatch Member Type");
        switch (memberType) {
            case "VIP":
                if (!vipWaitingList.contains(user)) {
                    throw new IllegalArgumentException("User is not in the VIP waiting list");
                }
                vipWaitingList.remove(user);
                break;
            case "Normal":
                if (!memberWaitingList.contains(user)) {
                    throw new IllegalArgumentException("User is not in the normal member waiting list");
                }
                memberWaitingList.remove(user);
                break;
            default:
                if (!normalWaitingList.contains(user)) {
                    throw new IllegalArgumentException("User is not in the waiting list");
                }
                normalWaitingList.remove(user);
                break;
		}
	}
	
	
}