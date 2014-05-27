package com.invy.database.jpa.data;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;

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
	@OneToMany(mappedBy = "kit",cascade = CascadeType.ALL)
	private Set<Subkit> subkits;

	public Kit() {
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

	public boolean isSetupComplete() {
		return setupComplete;
	}

	public void setSetupComplete(boolean setupComplete) {
		this.setupComplete = setupComplete;
	}

	public Kittype getKittype() {
		return this.kittype;
	}

	public void setKittype(Kittype kittype) {
		this.kittype = kittype;
	}

	public Owner getOwner() {
		return this.owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Set<Subkit> getSubkits() {
		return this.subkits;
	}

	public void setSubkits(Set<Subkit> subkits) {
		this.subkits = subkits;
	}

}