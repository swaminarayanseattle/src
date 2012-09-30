package com.baps.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.baps.dao.DataAccessService;
import com.baps.vo.ChildVO;
import com.baps.vo.ContactEntryVO;
import com.opensymphony.xwork2.ActionSupport;
/*
 * @Author JIGNESH SAKHIA
 */
public class UpdateContactAction extends ActionSupport {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	Logger log = Logger.getLogger(GetImage.class);
	
	public String execute(){
		
		ContactEntryVO  entryVo = new ContactEntryVO();
		entryVo.setSerialNumber(serialNumber);
		entryVo.setFirstName(firstName);
		entryVo.setLastName(lastName);
		entryVo.setAddress(address);
		entryVo.setCity(city);
		entryVo.setState(state);
		entryVo.setZip(zip);
		entryVo.setDob(getDateFromString(dob));
		entryVo.setEmail(email);
		entryVo.setHomePhone(homePhone);
		entryVo.setWorkPhone(workPhone);
		entryVo.setCellPhone(cellPhone);
		entryVo.setGaam(gaam);
		entryVo.setSpouseName(spouseName);
		entryVo.setSpouseDob(getDateFromString(spouseDob));
		entryVo.setSpouseEmail(spouseEmail);
		entryVo.setSpouseWorkPhone(spouseWorkPhone);
		entryVo.setSpouseCellPhone(spouseCellPhone);
		entryVo.setPrasad(prasad);
		entryVo.setBalPrakash(balPrakash);
		entryVo.setGuj(guj);
		entryVo.setInd(Ind);
		
		
		List<ChildVO> lstChildren = new ArrayList<ChildVO>();
		ChildVO childVo1  = getChildVO(childName1,childDob1,childGender1,enrollInd1);
		ChildVO childVo2  = getChildVO(childName2,childDob2,childGender2,enrollInd2);
		ChildVO childVo3  = getChildVO(childName3,childDob3,childGender3,enrollInd3);
		ChildVO childVo4  = getChildVO(childName4,childDob4,childGender4,enrollInd4);
		
		if(childVo1!= null)
			lstChildren.add(childVo1);
		if(childVo2!= null)
			lstChildren.add(childVo2);
		if(childVo3!= null)
			lstChildren.add(childVo3);
		if(childVo4!= null)
			lstChildren.add(childVo4);
		
		entryVo.setChildrens(lstChildren);
		
		if(photo1 != null) entryVo.setPhotoID1(photo1);
		if(photo2 != null) entryVo.setPhotoID2(photo2);
		if(photo3 != null) entryVo.setPhotoID3(photo3);
		if(photo4 != null) entryVo.setPhotoID4(photo4);
	
		if(goodPhoto1 != null) entryVo.setGoodPhoto1(goodPhoto1);
		if(goodPhoto2 != null) entryVo.setGoodPhoto2(goodPhoto2);
		if(goodPhoto3 != null) entryVo.setGoodPhoto3(goodPhoto3);
		if(goodPhoto4 != null) entryVo.setGoodPhoto4(goodPhoto4);
		
		DataAccessService access = new DataAccessService();
		try {
			if(serialNumber != null && !serialNumber.equals("")){
				access.updateRecord(entryVo);
			} else {
				access.addRecord(entryVo);
			}
			addActionError("Updated Successfully");
		} catch (Exception e) {
			addActionError("Update Failed. Try Again");
			e.printStackTrace();
			log.error(e);
		}
		return "success";
	}

	private ChildVO getChildVO(String childName, String ChildDob,String childGender,String aEnrollInd){
		if(!isEmpty(childName)){
			ChildVO childVo = new ChildVO();
			childVo.setChildName(childName);
			childVo.setChildGender(childGender);
			childVo.setChildDob(getDateFromString(ChildDob));
			childVo.setEnrolledGujClass(aEnrollInd);
			return childVo;
		}
		return null;
	}
	private boolean isEmpty(String s){
		
		if(s != null && !s.equals(""))
			return false;
		else
		   return true;
		
	}
	
	private Date getDateFromString(String a)
	{
		Date d;
		try {
			if(a !=null && !"".equals(a)){
				d = sdf.parse(a);
				return d;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	private String serialNumber;
	
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getGaam() {
		return gaam;
	}

	public void setGaam(String gaam) {
		this.gaam = gaam;
	}

	public String getSpouseName() {
		return spouseName;
	}

	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}

	public String getSpouseDob() {
		return spouseDob;
	}

	public void setSpouseDob(String spouseDob) {
		this.spouseDob = spouseDob;
	}

	public String getSpouseEmail() {
		return spouseEmail;
	}

	public void setSpouseEmail(String spouseEmail) {
		this.spouseEmail = spouseEmail;
	}

	public String getSpouseWorkPhone() {
		return spouseWorkPhone;
	}

	public void setSpouseWorkPhone(String spouseWorkPhone) {
		this.spouseWorkPhone = spouseWorkPhone;
	}

	public String getSpouseCellPhone() {
		return spouseCellPhone;
	}

	public void setSpouseCellPhone(String spouseCellPhone) {
		this.spouseCellPhone = spouseCellPhone;
	}

	public String getChildName1() {
		return childName1;
	}

	public void setChildName1(String childName1) {
		this.childName1 = childName1;
	}

	public String getChildDob1() {
		return childDob1;
	}

	public void setChildDob1(String childDob1) {
		this.childDob1 = childDob1;
	}

	public String getChildGender1() {
		return childGender1;
	}

	public void setChildGender1(String childGender1) {
		this.childGender1 = childGender1;
	}

	public String getChildName2() {
		return childName2;
	}

	public void setChildName2(String childName2) {
		this.childName2 = childName2;
	}

	public String getChildDob2() {
		return childDob2;
	}

	public void setChildDob2(String childDob2) {
		this.childDob2 = childDob2;
	}

	public String getChildGender2() {
		return childGender2;
	}

	public void setChildGender2(String childGender2) {
		this.childGender2 = childGender2;
	}

	public String getChildName3() {
		return childName3;
	}

	public void setChildName3(String childName3) {
		this.childName3 = childName3;
	}

	public String getChildDob3() {
		return childDob3;
	}

	public void setChildDob3(String childDob3) {
		this.childDob3 = childDob3;
	}

	public String getChildGender3() {
		return childGender3;
	}

	public void setChildGender3(String childGender3) {
		this.childGender3 = childGender3;
	}

	public String getChildName4() {
		return childName4;
	}

	public void setChildName4(String childName4) {
		this.childName4 = childName4;
	}

	public String getChildDob4() {
		return childDob4;
	}

	public void setChildDob4(String childDob4) {
		this.childDob4 = childDob4;
	}

	public String getChildGender4() {
		return childGender4;
	}

	public void setChildGender4(String childGender4) {
		this.childGender4 = childGender4;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String dob;
	private String email;
	private String homePhone;
	private String workPhone;
	private String cellPhone;
	private String gaam;
	private String spouseName;
	private String spouseDob;
	private String spouseEmail;
	private String spouseWorkPhone;
	private String spouseCellPhone;
	private String prasad;
	private String balPrakash;
	private String guj;
	private String Ind;
	
	private String childName1;
	private String childDob1;
	private String childGender1;
	private String enrollInd1;
	
	private String childName2;
	private String childDob2;
	private String childGender2;
	private String enrollInd2;
	
	private String childName3;
	private String childDob3;
	private String childGender3;
	private String enrollInd3;
	
	private String childName4;
	private String childDob4;
	private String childGender4;
	private String enrollInd4;
	
	private String photo1;
	private String goodPhoto1;
	
	private String photo2;
	private String goodPhoto2;
	
	private String photo3;
	private String goodPhoto3;
	
	private String photo4;
	private String goodPhoto4;

	public String getPhoto1() {
		return photo1;
	}

	public void setPhoto1(String photo1) {
		this.photo1 = photo1;
	}

	public String getGoodPhoto1() {
		return goodPhoto1;
	}

	public void setGoodPhoto1(String goodPhoto1) {
		this.goodPhoto1 = goodPhoto1;
	}

	public String getPhoto2() {
		return photo2;
	}

	public String getPrasad() {
		return prasad;
	}

	public void setPrasad(String prasad) {
		this.prasad = prasad;
	}

	public String getBalPrakash() {
		return balPrakash;
	}

	public void setBalPrakash(String balPrakash) {
		this.balPrakash = balPrakash;
	}

	public String getGuj() {
		return guj;
	}

	public void setGuj(String guj) {
		this.guj = guj;
	}

	public String getInd() {
		return Ind;
	}

	public void setInd(String ind) {
		Ind = ind;
	}

	public void setPhoto2(String photo2) {
		this.photo2 = photo2;
	}

	public String getGoodPhoto2() {
		return goodPhoto2;
	}

	public void setGoodPhoto2(String goodPhoto2) {
		this.goodPhoto2 = goodPhoto2;
	}

	public String getPhoto3() {
		return photo3;
	}

	public void setPhoto3(String photo3) {
		this.photo3 = photo3;
	}

	public String getGoodPhoto3() {
		return goodPhoto3;
	}

	public void setGoodPhoto3(String goodPhoto3) {
		this.goodPhoto3 = goodPhoto3;
	}

	public String getPhoto4() {
		return photo4;
	}

	public void setPhoto4(String photo4) {
		this.photo4 = photo4;
	}

	public String getGoodPhoto4() {
		return goodPhoto4;
	}

	public void setGoodPhoto4(String goodPhoto4) {
		this.goodPhoto4 = goodPhoto4;
	}

	public String getEnrollInd1() {
		return enrollInd1;
	}

	public void setEnrollInd1(String enrollInd1) {
		this.enrollInd1 = enrollInd1;
	}

	public String getEnrollInd2() {
		return enrollInd2;
	}

	public void setEnrollInd2(String enrollInd2) {
		this.enrollInd2 = enrollInd2;
	}

	public String getEnrollInd3() {
		return enrollInd3;
	}

	public void setEnrollInd3(String enrollInd3) {
		this.enrollInd3 = enrollInd3;
	}

	public String getEnrollInd4() {
		return enrollInd4;
	}

	public void setEnrollInd4(String enrollInd4) {
		this.enrollInd4 = enrollInd4;
	}
	
	


	
}
