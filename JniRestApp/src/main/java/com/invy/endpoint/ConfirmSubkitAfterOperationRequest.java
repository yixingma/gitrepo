/**
 * 
 */
package com.invy.endpoint;

import java.io.Serializable;

/**
 * @author ema
 * 
 */
public class ConfirmSubkitAfterOperationRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1764373011207224817L;
	private int transactionSequenceNumber;
	private String transactionId;
	private int requestId;

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
}
