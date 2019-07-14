package com.loan.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author essakijothi_v
 *
 * Entity mapping for table :: banks
 * 
 */
@Entity
@Table(name="banks")
public class Bank implements Serializable {

	private static final long serialVersionUID = 6970493459253436618L;

	@Id
	@GeneratedValue
	@Column(name="ID")
	long id;

	@Column(name="NAME")
	String bankName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
}
