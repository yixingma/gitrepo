/**
 * 
 */
package com.invy.domain;

import java.io.Serializable;
import java.util.Set;

/**
 * @author ema
 * 
 */
public class SubkitTypePojo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4709098998699276221L;
	private int id;
	private String description;
	private String name;
	private int subkitSequence;
	private Set<OptimalItemTemplatePojo> optitemtemplates;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSubkitSequence() {
		return subkitSequence;
	}

	public void setSubkitSequence(int subkitSequence) {
		this.subkitSequence = subkitSequence;
	}

	public Set<OptimalItemTemplatePojo> getOptitemtemplates() {
		return optitemtemplates;
	}

	public void setOptitemtemplates(
			Set<OptimalItemTemplatePojo> optitemtemplates) {
		this.optitemtemplates = optitemtemplates;
	}
}
