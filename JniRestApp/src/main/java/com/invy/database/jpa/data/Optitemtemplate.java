package com.invy.database.jpa.data;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the optitemtemplate database table.
 * 
 */
@Entity
@Table(name="optitemtemplate")
public class Optitemtemplate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID", unique=true, nullable=false)
	private int id;

	@Column(name="OptUnitNum", nullable=false)
	private int optUnitNum;

	@Column(name="TemplateID", nullable=false)
	private int templateID;

	//bi-directional many-to-one association to Itemref
    @ManyToOne
	@JoinColumn(name="ItemRefID", nullable=false)
	private Itemref itemref;

	//bi-directional many-to-one association to Subkittype
    @ManyToOne
	@JoinColumn(name="SubkitTypeID", nullable=false)
	private Subkittype subkittype;

    public Optitemtemplate() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOptUnitNum() {
		return this.optUnitNum;
	}

	public void setOptUnitNum(int optUnitNum) {
		this.optUnitNum = optUnitNum;
	}

	public int getTemplateID() {
		return this.templateID;
	}

	public void setTemplateID(int templateID) {
		this.templateID = templateID;
	}

	public Itemref getItemref() {
		return this.itemref;
	}

	public void setItemref(Itemref itemref) {
		this.itemref = itemref;
	}
	
	public Subkittype getSubkittype() {
		return this.subkittype;
	}

	public void setSubkittype(Subkittype subkittype) {
		this.subkittype = subkittype;
	}
	
}