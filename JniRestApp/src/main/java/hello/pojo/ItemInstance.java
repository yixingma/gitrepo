package hello.pojo;

import java.io.Serializable;

public class ItemInstance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2763872046642971169L;
	private int quantity;
	private String name;
	private String id;
	public ItemInstance(){
		
	}
	public ItemInstance(String id, String name, int quantity) {
		this.id = id;
		this.name = name;
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
