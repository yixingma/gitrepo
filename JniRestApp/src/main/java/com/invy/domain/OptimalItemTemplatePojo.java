/**
 * 
 */
package com.invy.domain;

import java.io.Serializable;

/**
 * @author ema
 * 
 */
public class OptimalItemTemplatePojo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4706708385405167811L;
	private int id;
	private int optUnitNum;
	private int templateID;
	private ItemReferencePojo itemref;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOptUnitNum() {
		return optUnitNum;
	}

	public void setOptUnitNum(int optUnitNum) {
		this.optUnitNum = optUnitNum;
	}

	public int getTemplateID() {
		return templateID;
	}

	public void setTemplateID(int templateID) {
		this.templateID = templateID;
	}

	public ItemReferencePojo getItemref() {
		return itemref;
	}

	public void setItemref(ItemReferencePojo itemref) {
		this.itemref = itemref;
	}
}
