package com.loan.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

/**
 * @author essakijothi_v
 * 
 * Entity mapping for view :: bank_branches
 * 
 */
@Entity
@Immutable
@Table(name="bank_branches")
public class BankBranchView implements Serializable{
	
	private static final long serialVersionUID = -6766884787722182189L;

	@Id
	@Column(name="IFSC")
	private String ifsc;

	@Column(name="BANK_ID")
	private long bankId;

	@Column(name="BRANCH")
	String branch;

	@Column(name="ADDRESS")
	String address;

	@Column(name="CITY")
	String city;

	@Column(name="DISTRICT")
	String district;
	
	@Column(name="STATE")
	String state;

	@Column(name="BANK_NAME")
	String bankName;

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

}
