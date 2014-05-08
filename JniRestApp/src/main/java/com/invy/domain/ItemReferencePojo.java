/**
 * 
 */
package com.invy.domain;

import java.io.Serializable;

/**
 * @author ema
 * 
 */
public class ItemReferencePojo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8997025427117193618L;
	private int id;

	private String description;

	private String name;

	private double unitPrice;

	public String getDescription() {
		return description;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setUnitPrice(final double unitPrice) {
		this.unitPrice = unitPrice;
	}
}
