package hello;

import java.io.Serializable;

public class Product implements Serializable {
	/**
	 * This is a TEST TODO: Remove
	 */
	private static final long serialVersionUID = 1L;
	public long id;
	public long quantity;
	public String unit;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}
