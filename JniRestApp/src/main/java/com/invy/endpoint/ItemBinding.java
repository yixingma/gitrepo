/**
 * 
 */
package com.invy.endpoint;

import java.io.Serializable;

/**
 * @author ema
 *
 */
public class ItemBinding implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4302850239465970168L;
	private int itemId;
	private int unitNum;
	private String name;
	private String description;
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getUnitNum() {
		return unitNum;
	}
	public void setUnitNum(int unitNum) {
		this.unitNum = unitNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
