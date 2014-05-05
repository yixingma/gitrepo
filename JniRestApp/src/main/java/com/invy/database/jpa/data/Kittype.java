package com.invy.database.jpa.data;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the kittype database table.
 * 
 */
@Entity
@Table(name="kittype")
public class Kittype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID", unique=true, nullable=false)
	private int id;

	@Column(name="Description", length=50)
	private String description;

	@Column(name="Name", length=20)
	private String name;

	//bi-directional many-to-one association to Kit
	@OneToMany(mappedBy="kittype")
	private Set<Kit> kits;

	//bi-directional many-to-one association to Requestmaster
	@OneToMany(mappedBy="kittype")
	private Set<Requestmaster> requestmasters;

	//bi-directional many-to-one association to Subkittype
	@OneToMany(mappedBy="kittype")
	private Set<Subkittype> subkittypes;

    public Kittype() {
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

	public Set<Kit> getKits() {
		return this.kits;
	}

	public void setKits(Set<Kit> kits) {
		this.kits = kits;
	}
	
	public Set<Requestmaster> getRequestmasters() {
		return this.requestmasters;
	}

	public void setRequestmasters(Set<Requestmaster> requestmasters) {
		this.requestmasters = requestmasters;
	}
	
	public Set<Subkittype> getSubkittypes() {
		return this.subkittypes;
	}

	public void setSubkittypes(Set<Subkittype> subkittypes) {
		this.subkittypes = subkittypes;
	}
	
}