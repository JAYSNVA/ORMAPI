package com.orm.model;

import java.io.Serializable;
import java.util.Date;

import com.orm.annotation.Column;
import com.orm.annotation.PrimaryKey;
import com.orm.annotation.Table;

@Table(name = "TBL_MARKED_PRODUCT")
public class MarkedProductTest implements Serializable {
	private static final long serialVersionUID = 1L;
	@PrimaryKey
	@Column(name = "ID", required = true, clazz = Long.class)
	private Long id;
	@Column(name = "COMPANY_NAME", required = true, clazz = String.class)
	private String companyName;
	@Column(name = "COMPANY_ADDRESS", required = true, clazz = String.class)
	private String companyAddress;
	@Column(name = "PRODUCT_NAME", required = true, clazz = String.class)
	private String productName;
	@Column(name = "MARKED_CAUSE", required = true, clazz = String.class)
	private String markedCause;
	@Column(name = "MARK", required = true, clazz = String.class)
	private String mark;
	@Column(name = "PARTY_DATE", required = true, clazz = Date.class)
	private Date partyDate;
	@Column(name = "SERIAL_NUMBER", required = true, clazz = String.class)
	private String serialNumber;
	@Column(name = "ANNO_DATE", required = true, clazz = Date.class)
	private Date annoDate;
	@Column(name = "CDATE", required = true, clazz = Date.class)
	private Date cdate;
	@Column(name = "LATITUDE", required = true, clazz = Double.class)
	private Double latitude;
	@Column(name = "LONGITUDE", required = true, clazz = Double.class)
	private Double longitude;
	@Column(name = "ISACTIVE", required = true, clazz = Integer.class)
	private int isActive;
	@Column(name = "FILE", required = true, clazz = byte[].class)
	private byte[] bytes;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return this.companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getMarkedCause() {
		return this.markedCause;
	}

	public void setMarkedCause(String markedCause) {
		this.markedCause = markedCause;
	}

	public String getMark() {
		return this.mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Date getPartyDate() {
		return this.partyDate;
	}

	public void setPartyDate(Date partyDate) {
		this.partyDate = partyDate;
	}

	public String getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Date getAnnoDate() {
		return this.annoDate;
	}

	public void setAnnoDate(Date annoDate) {
		this.annoDate = annoDate;
	}

	public Date getCdate() {
		return this.cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public int getIsActive() {
		return this.isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public byte[] getBytes() {
		return this.bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public MarkedProductTest() {
	}

	public MarkedProductTest(Long id, String companyName, String companyAddress, String productName, String markedCause,
			String mark, Date partyDate, String serialNumber, Date annoDate, Date cdate, Double latitude,
			Double longitude, int isActive) {
		this.id = id;
		this.companyName = companyName;
		this.companyAddress = companyAddress;
		this.productName = productName;
		this.markedCause = markedCause;
		this.mark = mark;
		this.partyDate = partyDate;
		this.serialNumber = serialNumber;
		this.annoDate = annoDate;
		this.cdate = cdate;
		this.latitude = latitude;
		this.longitude = longitude;
		this.isActive = isActive;
	}
}