/**
 * 
 */
package com.invy.endpoint;

import java.io.Serializable;
import java.util.Map;

/**
 * @author ema
 * 
 */
public class ConfirmSubkitAfterOperationResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8569011487041725402L;
	private int transactionSequenceNumber;
	private String transactionId;
	private int requestId;
	private KitBinding kitBinding;
	private Map<Integer, ItemCharge> itemChargeMap;

	public Map<Integer, ItemCharge> getItemChargeMap() {
		return itemChargeMap;
	}

	public void setItemChargeMap(Map<Integer, ItemCharge> itemChargeMap) {
		this.itemChargeMap = itemChargeMap;
	}

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

}