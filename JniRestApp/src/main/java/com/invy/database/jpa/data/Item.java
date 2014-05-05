package com.invy.database.jpa.data;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the item database table.
 * 
 */
@Entity
@Table(name="item")
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID", unique=true, nullable=false)
	private int id;

	@Column(name="UnitNum")
	private int unitNum;

	//bi-directional many-to-one association to Itemref
    @ManyToOne
	@JoinColumn(name="ItemRefID", nullable=false)
	private Itemref itemref;

	//bi-directional many-to-one association to Subkit
    @ManyToOne
	@JoinColumn(name="SubkitID", nullable=false)
	private Subkit subkit;

    public Item() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUnitNum() {
		return this.unitNum;
	}

	public void setUnitNum(int unitNum) {
		this.unitNum = unitNum;
	}

	public Itemref getItemref() {
		return this.itemref;
	}

	public void setItemref(Itemref itemref) {
		this.itemref = itemref;
	}
	
	public Subkit getSubkit() {
		return this.subkit;
	}

	public void setSubkit(Subkit subkit) {
		this.subkit = subkit;
	}
	
}