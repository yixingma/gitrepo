package com.invy.database.jpa.data;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the owner database table.
 * 
 */
@Entity
@Table(name="owner")
public class Owner implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID", unique=true, nullable=false)
	private int id;

	@Column(name="Firstname", nullable=false, length=20)
	private String firstname;

	@Column(name="Lastname", nullable=false, length=20)
	private String lastname;

	@Column(name="Middlename", length=20)
	private String middlename;

	@Column(name="UserId", nullable=false, length=20)
	private String userId;

	//bi-directional many-to-one association to Kit
	@OneToMany(mappedBy="owner")
	private Set<Kit> kits;

	//bi-directional many-to-one association to Location
    @ManyToOne
	@JoinColumn(name="LocationID", nullable=false)
	private Location location;

    public Owner() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMiddlename() {
		return this.middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Set<Kit> getKits() {
		return this.kits;
	}

	public void setKits(Set<Kit> kits) {
		this.kits = kits;
	}
	
	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
}