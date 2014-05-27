package com.invy.endpoint;

import java.io.Serializable;
import java.util.List;

public class KitBinding implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4943114834241003877L;
	private List<SubkitBinding> subkitBindings;
	private String kitName;
	private String kitDescription;
	private String ownerId;
	private boolean setupComplete;
	private int kitTypeId;
	private int kitId;

	public String getKitDescription() {
		return kitDescription;
	}

	public int getKitId() {
		return kitId;
	}

	public String getKitName() {
		return kitName;
	}

	public int getKitTypeId() {
		return kitTypeId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public List<SubkitBinding> getSubkitBindings() {
		return subkitBindings;
	}

	public boolean isSetupComplete() {
		return setupComplete;
	}

	public void setKitDescription(final String kitDescription) {
		this.kitDescription = kitDescription;
	}

	public void setKitId(final int kitId) {
		this.kitId = kitId;
	}

	public void setKitName(final String kitName) {
		this.kitName = kitName;
	}

	public void setKitTypeId(final int kitTypeId) {
		this.kitTypeId = kitTypeId;
	}

	public void setOwnerId(final String ownerId) {
		this.ownerId = ownerId;
	}

	public void setSetupComplete(final boolean setupComplete) {
		this.setupComplete = setupComplete;
	}

	public void setSubkitBindings(final List<SubkitBinding> subkitBindings) {
		this.subkitBindings = subkitBindings;
	}
}
