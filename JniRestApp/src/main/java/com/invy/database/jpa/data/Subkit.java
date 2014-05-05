package com.invy.database.jpa.data;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the subkit database table.
 * 
 */
@Entity
@Table(name="subkit")
public class Subkit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID", unique=true, nullable=false)
	private int id;

	@Column(name="Description", length=50)
	private String description;

	@Column(name="Name", length=20)
	private String name;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="subkit")
	private Set<Item> items;

	//bi-directional many-to-one association to Kit
    @ManyToOne
	@JoinColumn(name="KitID", nullable=false)
	private Kit kit;

	//bi-directional many-to-one association to Subkittype
    @ManyToOne
	@JoinColumn(name="SubkitTypeID", nullable=false)
	private Subkittype subkittype;

    public Subkit() {
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

	public Set<Item> getItems() {
		return this.items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}
	
	public Kit getKit() {
		return this.kit;
	}

	public void setKit(Kit kit) {
		this.kit = kit;
	}
	
	public Subkittype getSubkittype() {
		return this.subkittype;
	}

	public void setSubkittype(Subkittype subkittype) {
		this.subkittype = subkittype;
	}
	
}