package com.phonezilla.dareu.objects;

import java.io.Serializable;

public class Collection implements Serializable {
	public String itemId;
	public String itemName;
	public String itemdesc;

	// constructor
	public Collection(String itemId, String itemName, String itemdesc) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemdesc = itemdesc;
	}
}
