package com.amey.appdirect.integration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "result")
public class ReturnResult {
	String success;

	public String getSuccess() {
		return success;
	}

	@XmlElement
	public void setSuccess(String success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	@XmlElement
	public void setMessage(String message) {
		this.message = message;
	}

	public String getAccountIdentifier() {
		return accountIdentifier;
	}

	@XmlElement
	public void setAccountIdentifier(String accountIdentifier) {
		this.accountIdentifier = accountIdentifier;
	}

	String message;
	String accountIdentifier;
	/*
	 * <result><success>true</success><message>Account creation successful for
	 * Fake Co. by
	 * Alice</message><accountIdentifier>a.ameyjain@gmail.com</accountIdentifier
	 * ></result>
	 */
}
