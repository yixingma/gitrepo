package com.invy.endpoint;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class CompareKitWithExistingResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3761749761428148106L;
	private int transactionSequenceNumber;
	private String transactionId;
	private int requestId;
	private KitBinding kitBinding;
	private Map<Integer, ItemBinding> originalItems;
	private Map<Integer, ItemBinding> newItems;
	private Set<Integer> diffItemRefIds;

	public int getTransactionSequenceNumber() {
		return transactionSequenceNumber;
	}

	public void setTransactionSequenceNumber(int transactionSequenceNumber) {
		this.transactionSequenceNumber = transactionSequenceNumber;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public KitBinding getKitBinding() {
		return kitBinding;
	}

	public void setKitBinding(KitBinding kitBinding) {
		this.kitBinding = kitBinding;
	}

	public Map<Integer, ItemBinding> getOriginalItems() {
		return originalItems;
	}

	public void setOriginalItems(Map<Integer, ItemBinding> originalItems) {
		this.originalItems = originalItems;
	}

	public Map<Integer, ItemBinding> getNewItems() {
		return newItems;
	}

	public void setNewItems(Map<Integer, ItemBinding> newItems) {
		this.newItems = newItems;
	}

	public Set<Integer> getDiffItemRefIds() {
		return diffItemRefIds;
	}

	public void setDiffItemRefIds(Set<Integer> diffItemRefIds) {
		this.diffItemRefIds = diffItemRefIds;
	}
}
