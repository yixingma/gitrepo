package com.invy.database.jpa.data;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the requestimage database table.
 * 
 */
@Entity
@Table(name="requestimage")
public class Requestimage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID", unique=true, nullable=false)
	private int id;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="CreateDateTime")
	private Date createDateTime;

    @Lob()
	@Column(name="RequestImage")
	private byte[] requestImage;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="UpdateDateTime")
	private Date updateDateTime;

	//bi-directional many-to-one association to Itemtx
	@OneToMany(mappedBy="requestimage",cascade = CascadeType.ALL)
	private Set<Itemtx> itemtxs;

	//bi-directional many-to-one association to Requestmaster
    @ManyToOne
	@JoinColumn(name="RequestMasterID", nullable=false)
	private Requestmaster requestmaster;

	//bi-directional many-to-one association to Subkittype
    @ManyToOne
	@JoinColumn(name="SubkitTypeID", nullable=false)
	private Subkittype subkittype;

    public Requestimage() {
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

	public byte[] getRequestImage() {
		return this.requestImage;
	}

	public void setRequestImage(byte[] requestImage) {
		this.requestImage = requestImage;
	}

	public Date getUpdateDateTime() {
		return this.updateDateTime;
	}

	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public Set<Itemtx> getItemtxs() {
		return this.itemtxs;
	}

	public void setItemtxs(Set<Itemtx> itemtxs) {
		this.itemtxs = itemtxs;
	}
	
	public Requestmaster getRequestmaster() {
		return this.requestmaster;
	}

	public void setRequestmaster(Requestmaster requestmaster) {
		this.requestmaster = requestmaster;
	}
	
	public Subkittype getSubkittype() {
		return this.subkittype;
	}

	public void setSubkittype(Subkittype subkittype) {
		this.subkittype = subkittype;
	}
	
}