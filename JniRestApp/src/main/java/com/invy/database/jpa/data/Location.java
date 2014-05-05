package com.invy.database.jpa.data;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * The persistent class for the location database table.
 * 
 */
@Entity
@Table(name="location")
public class Location implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID", unique=true, nullable=false)
	private int id;

	@Column(name="Description", length=50)
	private String description;

	@Column(name="Name", nullable=false, length=20)
	private String name;

	@Column(name="Type", nullable=false, length=10)
	private String type;

	//bi-directional many-to-one association to Location
    @ManyToOne
	@JoinColumn(name="ParentLocID")
	private Location location;

	//bi-directional many-to-one association to Location
	@OneToMany(mappedBy="location")
	private Set<Location> locations;

    public Location() {
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

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Set<Location> getLocations() {
		return this.locations;
	}

	public void setLocations(Set<Location> locations) {
		this.locations = locations;
	}
	@Override
    public final String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}