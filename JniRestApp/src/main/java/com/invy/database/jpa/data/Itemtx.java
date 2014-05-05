package com.invy.database.jpa.data;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the itemtx database table.
 * 
 */
@Entity
@Table(name="itemtx")
public class Itemtx implements Serializable {
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

	//bi-directional many-to-one association to Requestimage
    @ManyToOne
	@JoinColumn(name="RequestImageID", nullable=false)
	private Requestimage requestimage;

    public Itemtx() {
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
	
	public Requestimage getRequestimage() {
		return this.requestimage;
	}

	public void setRequestimage(Requestimage requestimage) {
		this.requestimage = requestimage;
	}
	
}