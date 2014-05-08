package com.invy.domain;

import java.io.Serializable;
import java.util.Set;

public class KitTypePojo implements Serializable {

	private static final long serialVersionUID = 819303221405846080L;
	private int id;

	private String description;

	private String name;

	private Set<SubkitTypePojo> subkittypes;

	public String getDescription() {
		return description;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Set<SubkitTypePojo> getSubkittypes() {
		return subkittypes;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setSubkittypes(final Set<SubkitTypePojo> subkittypes) {
		this.subkittypes = subkittypes;
	}
}
