package com.baps.vo;

import java.util.Date;

public class ChildVO {
	
	private int parentId;
	
	private String childName;
	private Date childDob;
	private String childGender;
	private String enrolledGujClass;
	
	public String getChildName() {
		return childName;
	}
	public void setChildName(String childName) {
		this.childName = childName;
	}
	public Date getChildDob() {
		return childDob;
	}
	public void setChildDob(Date childDob) {
		this.childDob = childDob;
	}
	public String getChildGender() {
		return childGender;
	}
	public void setChildGender(String childGender) {
		this.childGender = childGender;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getEnrolledGujClass() {
		return enrolledGujClass;
	}
	public void setEnrolledGujClass(String enrolledGujClass) {
		this.enrolledGujClass = enrolledGujClass;
	}
	
	
	

}
