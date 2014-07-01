package com.invy.database.jpa.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the itemref database table.
 * 
 */
@Entity
@Table(name = "itemref")
public class Itemref implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	private int id;

	@Column(name = "Description", length = 50, updatable = false)
	private String description;

	@Column(name = "Name", nullable = false, length = 20, updatable = false)
	private String name;

	@Column(name = "UnitPrice", nullable = false, updatable = false)
	private double unitPrice;

	@Column(name = "catalogNumber", updatable = false)
	private String catalogNumber;

	@Column(name = "UnitClass", updatable = false)
	private String unitClass;

	public Itemref() {
	}

	public String getCatalogNumber() {
		return catalogNumber;
	}

	public String getDescription() {
		return this.description;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getUnitClass() {
		return unitClass;
	}

	public double getUnitPrice() {
		return this.unitPrice;
	}

	public void setCatalogNumber(final String catalogNumber) {
		this.catalogNumber = catalogNumber;
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

	public void setUnitClass(final String unitClass) {
		this.unitClass = unitClass;
	}

	public void setUnitPrice(final double unitPrice) {
		this.unitPrice = unitPrice;
	}

}