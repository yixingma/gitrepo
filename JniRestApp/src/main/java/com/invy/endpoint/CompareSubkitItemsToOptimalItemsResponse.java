package com.invy.endpoint;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class CompareSubkitItemsToOptimalItemsResponse implements Serializable {

	private static final long serialVersionUID = -6593782111041800563L;
	private Map<Integer, ItemBinding> originalItems;
	private Map<Integer, ItemBinding> optItems;
	private Set<Integer> diffItemRefIds;

	public Map<Integer, ItemBinding> getOriginalItems() {
		return originalItems;
	}

	public void setOriginalItems(Map<Integer, ItemBinding> originalItems) {
		this.originalItems = originalItems;
	}

	public Map<Integer, ItemBinding> getOptItems() {
		return optItems;
	}

	public void setOptItems(Map<Integer, ItemBinding> optItems) {
		this.optItems = optItems;
	}

	public Set<Integer> getDiffItemRefIds() {
		return diffItemRefIds;
	}

	public void setDiffItemRefIds(Set<Integer> diffItemRefIds) {
		this.diffItemRefIds = diffItemRefIds;
	}
}
