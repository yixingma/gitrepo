package com.invy.endpoint;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class CompareSubkitAfterOperationResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3456501452974988320L;
	private int transactionSequenceNumber;
	private String transactionId;
	private int requestId;
	private KitBinding kitBinding;
	private Map<Integer, ItemBinding> existingItems;
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

	public Map<Integer, ItemBinding> getExistingItems() {
		return existingItems;
	}

	public void setExistingItems(Map<Integer, ItemBinding> existingItems) {
		this.existingItems = existingItems;
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
