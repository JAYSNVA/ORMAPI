package com.orm.model;

import java.util.Date;

import com.orm.annotation.Column;
import com.orm.annotation.PrimaryKey;
import com.orm.annotation.Table;

@Table(name = "TBL_STUDENT")
public class Student {
	@PrimaryKey
	@Column(name = "ID", required = false, clazz = Long.class)
	private long id;
	@Column(name = "TCNO", required = false, clazz = String.class)
	private String tcNo;
	@Column(name = "NAME", required = false, clazz = String.class)
	private String name;
	@Column(name = "CDATE", required = false, clazz = Date.class)
	private Date cdate;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTcNo() {
		return this.tcNo;
	}

	public void setTcNo(String tcNo) {
		this.tcNo = tcNo;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCdate() {
		return this.cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}
	
	public Student() {
	}

	public Student(Long id, String tcNo, String name, Date cdate) {
		this.id = id;
		this.tcNo = tcNo;
		this.name = name;
		this.cdate = cdate;
	}
}