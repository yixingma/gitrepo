package com.invy.database.jpa.data;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the kit database table.
 * 
 */
@Entity
@Table(name = "kit")
public class Kit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int id;

	@Column(name = "Description", length = 50)
	private String description;

	@Column(name = "Name", length = 20)
	private String name;

	@Column(name = "SetupComplete", length = 1)
	private boolean setupComplete;

	// bi-directional many-to-one association to Kittype
	@ManyToOne
	@JoinColumn(name = "KitTypeID", nullable = false)
	private Kittype kittype;

	// bi-directional many-to-one association to Owner
	@ManyToOne
	@JoinColumn(name = "OwnerID", nullable = false)
	private Owner owner;

	// bi-directional many-to-one association to Subkit
	@OneToMany(mappedBy = "kit", cascade = CascadeType.ALL)
	private Set<Subkit> subkits;

	@Column(name = "Status", length = 10)
	private String status;

	@Column(name = "SerialNumber", length = 50)
	private String serialNumber;

	public Kit() {
	}

	public String getDescription() {
		return this.description;
	}

	public int getId() {
		return this.id;
	}

	public Kittype getKittype() {
		return this.kittype;
	}

	public String getName() {
		return this.name;
	}

	public Owner getOwner() {
		return this.owner;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public String getStatus() {
		return status;
	}

	public Set<Subkit> getSubkits() {
		return this.subkits;
	}

	public boolean isSetupComplete() {
		return setupComplete;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public void setKittype(final Kittype kittype) {
		this.kittype = kittype;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setOwner(final Owner owner) {
		this.owner = owner;
	}

	public void setSerialNumber(final String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public void setSetupComplete(final boolean setupComplete) {
		this.setupComplete = setupComplete;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public void setSubkits(final Set<Subkit> subkits) {
		this.subkits = subkits;
	}

}