package com.invy.database.jpa.data;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the subkittype database table.
 * 
 */
@Entity
@Table(name="subkittype")
public class Subkittype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID", unique=true, nullable=false)
	private int id;

	@Column(name="Description", length=50)
	private String description;

	@Column(name="Name", length=20)
	private String name;

	@Column(name="SubkitSequence")
	private int subkitSequence;

	//bi-directional many-to-one association to Optitemtemplate
	@OneToMany(mappedBy="subkittype")
	private Set<Optitemtemplate> optitemtemplates;

	//bi-directional many-to-one association to Kittype
    @ManyToOne
	@JoinColumn(name="KitTypeID", nullable=false)
	private Kittype kittype;

    public Subkittype() {
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

	public int getSubkitSequence() {
		return this.subkitSequence;
	}

	public void setSubkitSequence(int subkitSequence) {
		this.subkitSequence = subkitSequence;
	}

	public Set<Optitemtemplate> getOptitemtemplates() {
		return this.optitemtemplates;
	}

	public void setOptitemtemplates(Set<Optitemtemplate> optitemtemplates) {
		this.optitemtemplates = optitemtemplates;
	}
	
	public Kittype getKittype() {
		return this.kittype;
	}

	public void setKittype(Kittype kittype) {
		this.kittype = kittype;
	}
	
}