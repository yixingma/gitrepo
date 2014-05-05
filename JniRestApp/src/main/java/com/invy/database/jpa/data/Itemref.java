package com.invy.database.jpa.data;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the itemref database table.
 * 
 */
@Entity
@Table(name="itemref")
public class Itemref implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID", unique=true, nullable=false)
	private int id;

	@Column(name="Description", length=50)
	private String description;

	@Column(name="Name", nullable=false, length=20)
	private String name;

	@Column(name="UnitPrice", nullable=false)
	private double unitPrice;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="itemref")
	private Set<Item> items;

	//bi-directional many-to-one association to Itemtx
	@OneToMany(mappedBy="itemref")
	private Set<Itemtx> itemtxs;

	//bi-directional many-to-one association to Optitemtemplate
	@OneToMany(mappedBy="itemref")
	private Set<Optitemtemplate> optitemtemplates;

    public Itemref() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Set<Item> getItems() {
		return this.items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}
	
	public Set<Itemtx> getItemtxs() {
		return this.itemtxs;
	}

	public void setItemtxs(Set<Itemtx> itemtxs) {
		this.itemtxs = itemtxs;
	}
	
	public Set<Optitemtemplate> getOptitemtemplates() {
		return this.optitemtemplates;
	}

	public void setOptitemtemplates(Set<Optitemtemplate> optitemtemplates) {
		this.optitemtemplates = optitemtemplates;
	}
	
}