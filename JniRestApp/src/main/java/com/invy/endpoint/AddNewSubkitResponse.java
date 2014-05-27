/**
 * 
 */
package com.invy.endpoint;

import java.io.Serializable;

/**
 * @author ema
 *
 */
public class AddNewSubkitResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7273655171117181257L;
	private int transactionSequenceNumber;
	private String transactionId;
	private int requestId;
	private KitBinding kitBinding;
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
