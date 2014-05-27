/**
 * 
 */
package com.invy.endpoint;

import java.io.Serializable;

/**
 * @author ema
 * 
 */
public class RegisterNewKitRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 505808654004686730L;
	private String createByUsername;
	private int transactionSequenceNumber;
	private String transactionId;
	private int requestId;
	private KitBinding kitBinding;

	public String getCreateByUsername() {
		return createByUsername;
	}

	public KitBinding getKitBinding() {
		return kitBinding;
	}

	public int getRequestId() {
		return requestId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public int getTransactionSequenceNumber() {
		return transactionSequenceNumber;
	}

	public void setCreateByUsername(final String createByUsername) {
		this.createByUsername = createByUsername;
	}

	public void setKitBinding(final KitBinding kitBinding) {
		this.kitBinding = kitBinding;
	}

	public void setRequestId(final int requestId) {
		this.requestId = requestId;
	}

	public void setTransactionId(final String transactionId) {
		this.transactionId = transactionId;
	}

	public void setTransactionSequenceNumber(final int transactionSequenceNumber) {
		this.transactionSequenceNumber = transactionSequenceNumber;
	}

}
