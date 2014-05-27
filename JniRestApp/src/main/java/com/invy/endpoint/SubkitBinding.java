package com.invy.endpoint;

import java.io.Serializable;
import java.util.List;

public class SubkitBinding implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3562170637091506481L;
	private int subkitId;
	private String subkitName;
	private String subkitDescription;
	private int subkitTypeId;
	private byte[] image;
	private List<ItemBinding> itemBindings;

	public byte[] getImage() {
		return image;
	}

	public String getSubkitDescription() {
		return subkitDescription;
	}

	public int getSubkitId() {
		return subkitId;
	}

	public String getSubkitName() {
		return subkitName;
	}

	public int getSubkitTypeId() {
		return subkitTypeId;
	}

	public void setImage(final byte[] image) {
		this.image = image;
	}

	public void setSubkitDescription(final String subkitDescription) {
		this.subkitDescription = subkitDescription;
	}

	public void setSubkitId(final int subkitId) {
		this.subkitId = subkitId;
	}

	public void setSubkitName(final String subkitName) {
		this.subkitName = subkitName;
	}

	public void setSubkitTypeId(final int subkitTypeId) {
		this.subkitTypeId = subkitTypeId;
	}

	public List<ItemBinding> getItemBindings() {
		return itemBindings;
	}

	public void setItemBindings(List<ItemBinding> itemBindings) {
		this.itemBindings = itemBindings;
	}

}
