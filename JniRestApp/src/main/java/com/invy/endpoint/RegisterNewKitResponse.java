package com.invy.endpoint;

import java.io.Serializable;

public class RegisterNewKitResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7765437338160155678L;
	private int transactionSequenceNumber;
	private String transactionId;
	private int requestId;
	private KitBinding kitBinding;

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
