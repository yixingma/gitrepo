/**
 * 
 */
package com.invy.endpoint;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ema
 * 
 */
public class ItemCharge implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2117307413894365106L;
	private BigDecimal cost;
	private int quantity;
	private BigDecimal unitPrice;
	private int itemrefId;
	private String name;
	private String description;

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getItemrefId() {
		return itemrefId;
	}

	public void setItemrefId(int itemrefId) {
		this.itemrefId = itemrefId;
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
