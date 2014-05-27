package com.invy.database.jpa.data;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the requestmaster database table.
 * 
 */
@Entity
@Table(name="requestmaster")
public class Requestmaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID", unique=true, nullable=false)
	private int id;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="CreateDateTime")
	private Date createDateTime;

	@Column(name="TransactionID", nullable=false, length=50)
	private String transactionID;

	@Column(name="TransactionSeq", nullable=false)
	private int transactionSeq;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="UpdateDateTime")
	private Date updateDateTime;

	//bi-directional many-to-one association to Requestimage
	@OneToMany(mappedBy="requestmaster", cascade = CascadeType.ALL)
	private Set<Requestimage> requestimages;

	//bi-directional many-to-one association to Kit
    @ManyToOne (cascade = CascadeType.PERSIST)
	@JoinColumn(name="KitID", nullable=false)
	private Kit kit;

	//bi-directional many-to-one association to Kittype
    @ManyToOne
	@JoinColumn(name="KitTypeID", nullable=false)
	private Kittype kittype;

    public Requestmaster() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateDateTime() {
		return this.createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getTransactionID() {
		return this.transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public int getTransactionSeq() {
		return this.transactionSeq;
	}

	public void setTransactionSeq(int transactionSeq) {
		this.transactionSeq = transactionSeq;
	}

	public Date getUpdateDateTime() {
		return this.updateDateTime;
	}

	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public Set<Requestimage> getRequestimages() {
		return this.requestimages;
	}

	public void setRequestimages(Set<Requestimage> requestimages) {
		this.requestimages = requestimages;
	}
	
	public Kit getKit() {
		return this.kit;
	}

	public void setKit(Kit kit) {
		this.kit = kit;
	}
	
	public Kittype getKittype() {
		return this.kittype;
	}

	public void setKittype(Kittype kittype) {
		this.kittype = kittype;
	}
	
}