/**
 * 
 */
package com.invy.endpoint;

import java.io.Serializable;

/**
 * @author ema
 * 
 */
public class AddNewSubkitRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5496239564065667039L;
	private String createByUsername;
	private int transactionSequenceNumber;
	private String transactionId;
	private int requestId;
	private KitBinding kitBinding;

	public String getCreateByUsername() {
		return createByUsername;
	}

	public void setCreateByUsername(String createByUsername) {
		this.createByUsername = createByUsername;
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
