package com.phonezilla.dareu.objects;

import java.util.ArrayList;

public class Group extends Collection {


	private ArrayList<Collection> acceptedchallenges = new ArrayList<Collection>();
	private ArrayList<Collection> pendingchallenges = new ArrayList<Collection>();
	private int userCount;
	public Group(String itemId, String itemName, String itemdesc) {
		super(itemId, itemName, itemdesc);
	}
	public void clearAccepted()
	{
		acceptedchallenges.clear();
	}
	public void clearPending()
	{
		pendingchallenges.clear();
	}
	public void setAccepted(Collection x)
	{
		acceptedchallenges.add(x);
	}
	public void setPending(Collection x)
	{
		pendingchallenges.add(x);
	}
	public ArrayList<Collection> getAccepted()
	{
		return acceptedchallenges;
	}
	public ArrayList<Collection> getPending()
	{
		return pendingchallenges;
	}
	public int getUserCount() {
		return userCount;
	}
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

}
